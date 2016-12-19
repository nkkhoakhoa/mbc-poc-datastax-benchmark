package com.pyco.db.benchmark.data.insert.orientdb.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.orientdb.vertex.OrientdbAbstractBatchInsert;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchUserFollowsUserInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "CREATE EDGE user_follows_user FROM (SELECT FROM User WHERE id=%s) TO (SELECT FROM User WHERE id=%s)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("user-follows-user")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user-follows-user")
            .build();

    @Override
    protected String getClassName() {
        return "user_follows_user";
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }

    @Override
    protected void executePreCommands() {
        OrientGraphNoTx graph = getGraph();
        graph.createEdgeType(getClassName());
        graph.commit();
    }

}
