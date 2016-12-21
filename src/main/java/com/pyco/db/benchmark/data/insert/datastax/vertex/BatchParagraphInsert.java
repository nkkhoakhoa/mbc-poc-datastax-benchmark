package com.pyco.db.benchmark.data.insert.datastax.vertex;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchParagraphInsert extends AbstractDatastaxBatchInsert {

    private static final String INSERT_COMMAND = "graph.addVertex(label, 'paragraph', 'id', '%s', 'text','%s')";
    private static final String LABEL = "paragraph";
    private static final String TEXT = "text";

    private InsertOptions insertOptions = new InsertOptions.Builder()
                                            .setStatement(INSERT_COMMAND)
                                            .setLogFile("paragraphs")
                                            .setSourceFile(BASE_DIR + "paragraph")
                                            .build();

    @Override
    public String[] schemaCommands() {
        return new String[] {
                "schema.propertyKey('id').Int().ifNotExists().create()",
                "schema.propertyKey('text').Text().ifNotExists().create()",
                String.format("schema.vertexLabel('%s').properties('%s', '%s').ifNotExists().create()", LABEL, ID, TEXT),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, ID, ID)
        };
    }

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
