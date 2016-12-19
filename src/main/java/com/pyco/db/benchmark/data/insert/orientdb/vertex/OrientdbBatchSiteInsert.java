package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchSiteInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "create vertex Site set id=%s,name='%s'";
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
        OrientGraphNoTx graph = getGraph();
        final OrientVertexType type = graph.createVertexType(getClassName());

        type.createProperty("id", OType.INTEGER);
        type.createProperty("name", OType.STRING);

        type.createIndex("site_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("site_name", OClass.INDEX_TYPE.NOTUNIQUE, "name");

        graph.commit();
    }

}
