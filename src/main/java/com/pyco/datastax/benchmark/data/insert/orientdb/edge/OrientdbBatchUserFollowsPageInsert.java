package com.pyco.datastax.benchmark.data.insert.orientdb.edge;

import com.pyco.datastax.benchmark.data.insert.datastax.vertex.InsertOptions;
import com.pyco.datastax.benchmark.data.insert.orientdb.vertex.OrientdbAbstractBatchInsert;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchUserFollowsPageInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "CREATE EDGE UserFollowsPage FROM (SELECT FROM User WHERE id=%s) TO (SELECT FROM Page WHERE id=%s)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("user-follows-page")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user-follows-page.csv")
            .build();

    @Override
    protected String getClassName() {
        return "UserFollowsPage";
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }

    @Override
    protected void executePreCommands() {
        getGraph().createEdgeType(getClassName());
        getGraph().commit();
    }

}
