package com.pyco.datastax.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchUserInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "insert into User(id, username) values (%s, '%s')";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("users")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user.csv")
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
        final OrientVertexType type = getGraph().createVertexType(getClassName());

        type.createProperty("id", OType.INTEGER);
        type.createProperty("username", OType.STRING);

        type.createIndex("user_id", OClass.INDEX_TYPE.UNIQUE, "id");
        type.createIndex("user_username", OClass.INDEX_TYPE.NOTUNIQUE, "username");

        getGraph().commit();
    }
}
