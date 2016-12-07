package com.pyco.datastax.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchSiteInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "insert into Site(id, name) values (%s, '%s')";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("sites")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "site.csv")
            .build();

    @Override
    protected String getClassName() {
        return "Site";
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }

    @Override
    protected void executePreCommands() {
        final OrientVertexType type = getGraph().createVertexType(getClassName());

        type.createProperty("id", OType.INTEGER);
        type.createProperty("name", OType.STRING);

        type.createIndex("site_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("site_name", OClass.INDEX_TYPE.NOTUNIQUE, "name");

        getGraph().commit();
    }

}
