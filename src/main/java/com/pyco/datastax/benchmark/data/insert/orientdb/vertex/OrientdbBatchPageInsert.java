package com.pyco.datastax.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchPageInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "insert into Page(id, title) values (%s, '%s')";
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
        final OrientVertexType type = getGraph().createVertexType(getClassName());

        type.createProperty("id", OType.INTEGER);
        type.createProperty("title", OType.STRING);

        type.createIndex("page_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("page_title", OClass.INDEX_TYPE.NOTUNIQUE, "title");

        getGraph().commit();
    }

}
