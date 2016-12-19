package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.intent.OIntentMassiveInsert;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.util.SystemUtils;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public abstract class OrientdbAbstractBatchInsert implements IOrientdbBatchInsert {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrientdbAbstractBatchInsert.class);
    protected static final String BASE_DIR = SystemUtils.getSystemProperty("data.path", "/Users/pyco/Documents/benchmark-data/");

    private int cores = 8;
    private int threads = cores * 8;
    private Executor executor = Executors.newFixedThreadPool(cores);

    private OrientGraphFactory factory;
    private long startAt;
    private long count = 0;

    private Map<String, OrientGraphNoTx> graphPerThreads = new HashMap<>();

    @Override
    public void batchInsert(final OrientGraphFactory factory) throws IOException {
        this.factory = factory;
        if (!classExists()) {
            executePreCommands();
        }
        if (isDirectory(getInsertOptions().getSourceFile())) {
            File directory = new File(getInsertOptions().getSourceFile());
            for(File file : directory.listFiles()) {
                LOGGER.info("STARTED: " + file.getAbsolutePath());
                insertPerFile(file.getAbsolutePath());
            }
        } else {
            insertPerFile(getInsertOptions().getSourceFile());
        }
    }

    private void insertPerFile(final String path) throws IOException {
        final Stream<String> stream = Files.lines(Paths.get(path));
        //26210000 + 10058000 + 10069000 + 8927000 + 2000
        //missing: 19544000 -> 26210000
        stream.parallel().forEach(row -> insertRow(row));
    }

    private void insertPerFile2(final String path) throws IOException {
        LOGGER.info("START READING FILE: " + path);
        final List<String> lines = Files.readAllLines(Paths.get(path));
        LOGGER.info("END READING FILE: " + path);
        int rows = lines.size();
        int perThread = rows / threads;

        for (int i = 0; i < threads; i++) {
            int limit = (i == threads - 1) ? rows - (i * perThread) : perThread;
            final List<String> data = lines.subList(i * perThread, i * perThread + limit);
            final SubCsvRunnable runnable = new SubCsvRunnable(data);
            executor.execute(runnable);
        }
    }

    private class SubCsvRunnable implements Runnable{
        private List<String> data = Collections.emptyList();
        public SubCsvRunnable(final List<String> data) {
            this.data = data;
        }
        @Override
        public void run() {
            for (final String row : data) {
                insertRow(row);
            }
        }
    }

    private boolean isDirectory(final String path) {
        final File f = new File(path);
        return f.exists() && f.isDirectory();
    }


    private int insertRow(final String row) {
        String[] args = StringUtils.split(row, ',');
        if (args.length < 2) {
            return 0;
        } if (args.length > 2 || args[1].length() > 100) {
            final String text = row.substring(args[0].length() + 2);
            args = new String[] {args[0]};
        }
        final String statement = String.format(getInsertOptions().getStatement(), args);
        executeCommand(statement);
        return 1;
    }

    private void executeCommand(final String command) {
        OrientGraphNoTx tx = getGraphPerThread(Thread.currentThread().getName());
        try {
            tx.declareIntent(new OIntentMassiveInsert());
            tx.command(new OCommandSQL(command)).execute();
            tx.commit();
        } catch (final Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
        count++;
        if ((count <= 20000 && count % 1000 == 0) || (count > 20000 && count % 10000 == 0)) {
            long current = System.currentTimeMillis();
            LOGGER.info(getInsertOptions().getLogfile() + " - " + String.valueOf(count) + " - " + String.valueOf(current - startAt));
            startAt = current;
        }
    }

    private OrientGraphNoTx getGraphPerThread(final String threadName) {
        if (graphPerThreads.get(threadName) == null) {
            synchronized (this) {
                graphPerThreads.put(threadName, getGraphFactory().getNoTx());
            }
        }
        return graphPerThreads.get(threadName);
    }

    protected boolean classExists(){
        return getGraph().getRawGraph().getMetadata().getSchema().existsClass(getClassName());
    }

    private OrientGraphFactory getGraphFactory() {
        return this.factory;
    }

    protected OrientGraphNoTx getGraph() {
        return getGraphFactory().getNoTx();
    }

    protected abstract String getClassName();
    protected abstract InsertOptions getInsertOptions();
    protected abstract void executePreCommands();


}
