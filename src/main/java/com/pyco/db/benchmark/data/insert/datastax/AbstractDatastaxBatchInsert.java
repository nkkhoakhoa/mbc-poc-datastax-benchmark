package com.pyco.db.benchmark.data.insert.datastax;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.util.Counter;
import com.pyco.db.benchmark.data.util.SystemUtils;
import com.pyco.db.benchmark.data.util.Tracker;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.*;

/**
 * Created by pyco on 12/2/16.
 */
public abstract class AbstractDatastaxBatchInsert implements IBatchInsert {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatastaxBatchInsert.class);

    protected static final String ID = "id";
    protected static final String BASE_DIR = SystemUtils.getSystemProperty("data.path", "/Users/pyco/Public/benchmark-data/");

    private static final int minThread = Integer.valueOf(SystemUtils.getSystemProperty("min.thread", "16"));
    private static final int maxThread = Integer.valueOf(SystemUtils.getSystemProperty("max.thread", "32"));

    protected static final BlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(1000);

    protected static final ExecutorService executor = new ThreadPoolExecutor(minThread, maxThread, 30, TimeUnit.SECONDS, linkedBlockingDeque);


    private Counter counter;

    public AbstractDatastaxBatchInsert() {
    }

    @Override
    public void executeBatchInsert(DseCluster cluster) throws Exception {
        counter = new Counter(LOGGER, getInsertOptions().getLogfile());
        counter.start();

        Tracker tracker = Tracker.getInstance();
        if (isDirectory(getInsertOptions().getSourceFile())) {
            File directory = new File(getInsertOptions().getSourceFile());
            for(File file : directory.listFiles()) {
                LOGGER.info("Start loading data from: " + file.getAbsolutePath());
                executor.execute(new ExecuteFile(cluster, file, getInsertOptions(), counter, tracker));
            }
        }
    }

    private static class ExecuteFile implements Runnable {

        private final File file;
        private DseCluster cluster;
        private DseSession session;
        private final InsertOptions insertOptions;
        private final Counter counter;
        private final Tracker tracker;

        public ExecuteFile(final DseCluster cluster, final File file, final InsertOptions insertOptions, Counter counter, Tracker tracker) {
            this.file = file;
            this.insertOptions = insertOptions;
            this.counter = counter;
            this.tracker = tracker;
            this.cluster = cluster;
        }

        @Override
        public void run() {
            session = this.cluster.newSession();
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

        private boolean insertRow(final String row) {
            String[] args = StringUtils.split(row, ',');
            if (row.length() > 100) {
                args = new String[] {args[0], ""};
            }
            final String statement = String.format(insertOptions.getStatement(), args);
            executeCommand(statement);
            return true;
        }

        private void executeCommand(final String command) {
            try {
                final GraphResultSet result = session.executeGraph(command);
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


    protected abstract String[] schemaCommands();

    protected abstract InsertOptions getInsertOptions();

}
