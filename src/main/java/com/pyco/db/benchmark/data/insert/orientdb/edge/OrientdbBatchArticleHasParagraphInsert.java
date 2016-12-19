package com.pyco.db.benchmark.data.insert.orientdb.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.orientdb.vertex.OrientdbAbstractBatchInsert;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchArticleHasParagraphInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "CREATE EDGE article_has_paragraph FROM (SELECT FROM Article WHERE id=%s) TO (SELECT FROM Paragraph WHERE id=%s)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("article-has-paragraph")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article-has-paragraph.csv")
            .build();

    @Override
    protected String getClassName() {
        return "article_has_paragraph";
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
