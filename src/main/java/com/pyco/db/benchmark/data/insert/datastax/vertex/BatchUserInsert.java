package com.pyco.db.benchmark.data.insert.datastax.vertex;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchUserInsert extends AbstractDatastaxBatchInsert {

    private static final String INSERT_COMMAND = "graph.addVertex(label, 'user', 'id', '%s', 'username','%s')";
    private static final String LABEL = "user";
    private static final String USERNAME = "username";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setStatement(INSERT_COMMAND)
            .setLogFile("users")
            .setSourceFile(BASE_DIR + "user.csv")
            .build();

    @Override
    protected String[] schemaCommands() {
        return new String[] {
                "schema.propertyKey('id').Int().ifNotExists().create()",
                "schema.propertyKey('username').Text().ifNotExists().create()",
                String.format("schema.vertexLabel('%s').properties('%s', '%s').ifNotExists().create()", LABEL, ID, USERNAME),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, ID, ID),
        };
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }
}
