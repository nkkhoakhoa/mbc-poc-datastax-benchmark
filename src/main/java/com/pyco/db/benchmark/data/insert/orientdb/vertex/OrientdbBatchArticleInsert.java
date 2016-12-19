package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchArticleInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "create vertex Article set id=%s,title='%s'";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("articles")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article.csv")
            .build();

    @Override
    protected String getClassName() {
        return "Article";
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }

    @Override
    protected void executePreCommands() {
        OrientGraphNoTx graph = getGraph();
        final OrientVertexType type = graph.createVertexType(getClassName());

        type.createProperty("id", OType.INTEGER);
        type.createProperty("title", OType.STRING);

        type.createIndex("article_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("article_title", OClass.INDEX_TYPE.NOTUNIQUE, "title");

        graph.commit();
    }

}
