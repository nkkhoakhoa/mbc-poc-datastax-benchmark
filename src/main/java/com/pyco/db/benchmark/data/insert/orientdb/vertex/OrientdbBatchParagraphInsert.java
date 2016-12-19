package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchParagraphInsert extends OrientdbAbstractBatchInsert {

    private static final String STATEMENT = "create vertex Paragraph set id=%s";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("paragraphs")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "paragraph")
            .build();

    @Override
    protected String getClassName() {
        return "Paragraph";
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
        type.createIndex("paragraph_id", OClass.INDEX_TYPE.UNIQUE, "id");
        graph.commit();
    }
}
