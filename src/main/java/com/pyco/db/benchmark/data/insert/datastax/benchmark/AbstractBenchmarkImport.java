package com.pyco.db.benchmark.data.insert.datastax.benchmark;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.datastax.driver.dse.graph.GraphResultSet;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;
import com.pyco.db.benchmark.data.insert.datastax.BatchApplication;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by pyco on 12/20/16.
 */
public abstract class AbstractBenchmarkImport extends AbstractDatastaxBatchInsert {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBenchmarkImport.class);
    private static final Logger APP_LOGGER = LoggerFactory.getLogger(BatchApplication.class);

    private DseCluster cluster;


    @Override
    public void executeBatchInsert(final DseCluster cluster) throws Exception {
        this.cluster = cluster;

        if (isDirectory(getInsertOptions().getSourceFile())) {
            File directory = new File(getInsertOptions().getSourceFile());
            for(File file : directory.listFiles()) {
                execute(Files.readAllLines(Paths.get(file.getAbsolutePath())));
            }
        } else {
            execute(Files.readAllLines(Paths.get(getConcreate().getInsertOptions().getSourceFile())));
        }
    }

    private void execute(final List<String> lines) {
        int count = 0;
        final DseSession session = cluster.newSession();
        LOGGER.info("STARTED: " + getConcreate().getInsertOptions().getLogfile());
        for (final String line : lines) {
            insertRow(session, line);
            log(++count);
            if (count >= 5000000) {
                break;
            }
        }
        session.close();
    }

    private boolean insertRow(final DseSession session, final String row) {
        String[] args = StringUtils.split(row, ',');
        final String statement = String.format(getConcreate().getInsertOptions().getStatement(), args);
        executeCommand(session, statement);
        return true;
    }

    private void executeCommand(final DseSession session, final String command) {
        try {
            final GraphResultSet result = session.executeGraph(command);
        } catch (final Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void log(int count) {
        if (count == 1000 || count == 100000 || count == 200000 || count == 1000000 || count == 5000000) {
            LOGGER.info("INSERTED: " + count);
        }
        if (count % 10000 == 0) {
            APP_LOGGER.info("INSERTED: " + count);
        }
    }

    abstract AbstractDatastaxBatchInsert getConcreate();
}
