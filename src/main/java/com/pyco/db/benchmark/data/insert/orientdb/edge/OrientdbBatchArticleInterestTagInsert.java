package com.pyco.db.benchmark.data.insert.orientdb.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.orientdb.vertex.OrientdbAbstractBatchInsert;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchArticleInterestTagInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "CREATE EDGE article_interest_tag FROM (SELECT FROM Article WHERE id=%s) TO (SELECT FROM Tag WHERE id=%s)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("article-interest-tag")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article-interest-tag.csv")
            .build();

    @Override
    protected String getClassName() {
        return "article_interest_tag";
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
