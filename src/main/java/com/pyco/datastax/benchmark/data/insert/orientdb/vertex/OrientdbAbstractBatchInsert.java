package com.pyco.datastax.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public abstract class OrientdbAbstractBatchInsert implements IOrientdbBatchInsert {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrientdbAbstractBatchInsert.class);
    protected static final String BASE_DIR = "/Volumes/ENJOY/Temp/benckmark-data/";

    private OrientGraph db;
    private long startAt;
    private long count = 0;

    @Override
    public void batchInsert(final OrientGraph db) throws IOException {
        this.db = db;
        if (!classExists()) {
            executePreCommands();
        }
        final Stream<String> stream = Files.lines(Paths.get(getInsertOptions().getSourceFile()));
        stream.map(row -> insertRow(row)).count();
    }

    private int insertRow(final String row) {
        String[] args = StringUtils.split(row, ',');
        if (args.length < 2) {
            return 0;
        } if (args.length > 2) {
            final String text = row.substring(args[0].length() + 2);
            args = new String[] {args[0], text};
        }
        final String statement = String.format(getInsertOptions().getStatement(), args);
        executeCommand(statement);
        return 1;
    }

    private void executeCommand(final String command) {
        getGraph().command(new OCommandSQL(command)).execute();
        getGraph().commit();
        if (count % 1000 == 0) {
            long current = System.currentTimeMillis();
            LOGGER.info(getInsertOptions().getLogfile() + " - " + String.valueOf(count) + " - " + String.valueOf(current - startAt));
            startAt = current;
        }
        count++;
    }

    protected boolean classExists(){
        return getGraph().getRawGraph().getMetadata().getSchema().existsClass(getClassName());
    }

    protected OrientGraph getGraph() {
        return this.db;
    }

    protected abstract String getClassName();
    protected abstract InsertOptions getInsertOptions();
    protected abstract void executePreCommands();


}
