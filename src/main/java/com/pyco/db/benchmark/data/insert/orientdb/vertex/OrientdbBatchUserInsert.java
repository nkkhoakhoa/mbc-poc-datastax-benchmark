package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchUserInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "create vertex User set id=%s,username='%s'";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("users")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "users.csv")
            .build();

    @Override
    protected String getClassName() {
        return "User";
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
        type.createProperty("username", OType.STRING);

        type.createIndex("user_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("user_username", OClass.INDEX_TYPE.NOTUNIQUE, "username");

        graph.commit();
    }
}
