package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchPageInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "create vertex Page set id=%s,name='%s'";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("pages")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "page.csv")
            .build();

    @Override
    protected String getClassName() {
        return "Page";
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

        type.createIndex("page_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("page_title", OClass.INDEX_TYPE.NOTUNIQUE, "title");

        graph.commit();
    }

}
