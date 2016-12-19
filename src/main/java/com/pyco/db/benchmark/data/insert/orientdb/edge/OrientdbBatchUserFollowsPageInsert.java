package com.pyco.db.benchmark.data.insert.orientdb.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.orientdb.vertex.OrientdbAbstractBatchInsert;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchUserFollowsPageInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "CREATE EDGE user_follows_page FROM (SELECT FROM User WHERE id=%s) TO (SELECT FROM Page WHERE id=%s)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("user-follows-page")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user-follows-page.csv")
            .build();

    @Override
    protected String getClassName() {
        return "user_follows_page";
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
