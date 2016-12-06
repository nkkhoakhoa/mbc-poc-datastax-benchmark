package com.pyco.datastax.benchmark.data.insert;

import com.datastax.driver.dse.DseSession;
import com.pyco.datastax.benchmark.connection.ConnectionPool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by pyco on 12/2/16.
 */
public abstract class AbstractBatchInsert implements IBatchInsert {

    private Logger logger = LoggerFactory.getLogger(AbstractBatchInsert.class);

    protected static final String ID = "id";
    protected static final String BASE_DIR = "/Users/pyco/Public/benchmark-data/";

    private ConnectionPool pool;
    private int count;
    long startAt;


    public AbstractBatchInsert() {
    }

    public void executeBatchInsert(final ConnectionPool pool) throws Exception {
        count = 0;
        this.pool = pool;
        StatisticDto.getInstance().addInfo("Start insert: " + getInsertOptions().getLogfile());
        final Stream<String> stream = Files.lines(Paths.get(getInsertOptions().getSourceFile()));

        for(String statement : schemaCommands()) {
            executeCommand(statement);
        }
        startAt = System.currentTimeMillis();
        stream.parallel().map(row -> {
            try {
                return insert(row, getInsertOptions());
            } catch (Exception e) {
                logger.error(StatisticDto.getInstance().toString());
                logger.error(e.getMessage(), e);
            }
            return 0;
        }).count();
        StatisticDto.getInstance().addInfo("Inserted: " + getInsertOptions().getLogfile());
        StatisticDto.getInstance().resetIndex();
    }

    protected int insert(final String data, final InsertOptions options) throws Exception {
        String[] args = StringUtils.split(data, ',');
        if (args.length < 2) {
            return 0;
        } if (args.length > 2) {
            final String text = data.substring(args[0].length() + 2);
            args = new String[] {args[0], text};
        }
        final String statement = String.format(options.getStatement(), args);
        executeCommand(statement);
        StatisticDto.getInstance().increaseIndex();
        return 1;
    }

    private void executeCommand(final String statement) throws Exception {
        final DseSession session = pool.getSession();
        session.executeGraph(statement);
        pool.putToQueue(session);
        if (count % 1000 == 0) {
            long current = System.currentTimeMillis();
            logger.info(System.getProperty("log.name") + " - " + count + " - " + String.valueOf(current - startAt));
            startAt = current;
        }
        ++count;
    }

    protected abstract String[] schemaCommands();

    protected abstract InsertOptions getInsertOptions();

}
