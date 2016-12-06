package com.pyco.datastax.benchmark.data.insert.edge;

import com.pyco.datastax.benchmark.data.insert.AbstractBatchInsert;
import com.pyco.datastax.benchmark.data.insert.InsertOptions;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchUserFollowsPage extends AbstractBatchInsert {

    private static final String INSERT_COMMAND =    "def v1 = g.V().has('user', 'id', %s).next()\n" +
                                                    "def v2=g.V().has('page', 'id', %s).next()\n" +
                                                    "v1.addEdge('follows',v2)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setStatement(INSERT_COMMAND)
            .setLogFile("user-follows-page")
            .setSourceFile(BASE_DIR + "user-follows-page.csv")
            .build();

    @Override
    protected String[] schemaCommands() {
        return new String[0];
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
