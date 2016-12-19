package com.pyco.db.benchmark.data.insert.neo4j;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.util.Counter;
import com.pyco.db.benchmark.data.util.SystemUtils;
import com.pyco.db.benchmark.data.util.Tracker;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.*;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public abstract class Neo4jAbstractBatchInsert implements INeo4jBatchInsert {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jAbstractBatchInsert.class);
    protected static final String BASE_DIR = SystemUtils.getSystemProperty("data.path", "/Users/pyco/Documents/benchmark-data/");;

    private static final int minThread = Integer.valueOf(SystemUtils.getSystemProperty("min.thread", "16"));
    private static final int maxThread = Integer.valueOf(SystemUtils.getSystemProperty("max.thread", "32"));

    protected static final BlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(1000);

    protected static final ExecutorService executor =
            new ThreadPoolExecutor(minThread, maxThread, 30, TimeUnit.SECONDS, linkedBlockingDeque);

    Executor exe = Executors.newFixedThreadPool(maxThread);

    private Counter counter;

    @Override
    public void batchInsert(final Driver driver) {
        counter = new Counter(LOGGER, getInsertOptions().getLogfile());
        counter.start();

        Tracker tracker = Tracker.getInstance();
        if (isDirectory(getInsertOptions().getSourceFile())) {
            File directory = new File(getInsertOptions().getSourceFile());
            for(File file : directory.listFiles()) {
                LOGGER.info("Start loading data from: " + file.getAbsolutePath());
                exe.execute(new ExecuteFile(driver, file, getInsertOptions(), counter, tracker));
            }
        }
    }

    private static class ExecuteFile implements Runnable {

        private final File file;
        private final Driver driver;
        private Session session;
        private final InsertOptions insertOptions;
        private final Counter counter;
        private final Tracker tracker;

        public ExecuteFile(final Driver driver, final File file, final InsertOptions insertOptions, Counter counter, Tracker tracker) {
            this.driver = driver;
            this.file = file;
            this.insertOptions = insertOptions;
            this.counter = counter;
            this.tracker = tracker;
        }

        @Override
        public void run() {
            session = driver.session();
            LOGGER.info("Open session: " + session);
            LOGGER.info("Start loading data from file " + file.getName());
            try {
                final int length = (int)file.length();
                ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
                IOUtils.copy(new FileInputStream(file), baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                final BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
                String line;
                long readLines = 0;
                long startAtLine = tracker.getTrackNumber(file.getName());
                while ((line = reader.readLine()) != null) {
                    readLines ++;
                    if (readLines <= startAtLine) continue;
                    insertRow(line);
                    tracker.track(file.getName(), readLines);
                }
                reader.close();
                LOGGER.info("Finish loading data from file " + file.getName());
            } catch (final IOException e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                session.close();
            }
        }

        private int insertRow(final String row) {
            String[] args = StringUtils.split(row, ',');
            final String statement = String.format(insertOptions.getStatement(), args);
            executeCommand(statement);
            return 1;
        }

        private void executeCommand(final String command) {
            try {
                session.run(command).consume();
                counter.increase();
            } catch (final Throwable e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private boolean isDirectory(final String path) {
        final File f = new File(path);
        return f.exists() && f.isDirectory();
    }

    protected abstract InsertOptions getInsertOptions();

}
