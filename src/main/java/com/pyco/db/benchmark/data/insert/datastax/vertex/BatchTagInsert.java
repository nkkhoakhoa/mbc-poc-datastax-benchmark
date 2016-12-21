package com.pyco.db.benchmark.data.insert.datastax.vertex;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchTagInsert extends AbstractDatastaxBatchInsert {

    private static final String INSERT_COMMAND = "graph.addVertex(label, 'tag', 'id', '%s', 'title','%s')";
    private static final String LABEL = "tag";
    private static final String TITLE = "title";

    private InsertOptions insertOptions = new InsertOptions.Builder()
                                            .setStatement(INSERT_COMMAND)
                                            .setLogFile("tags")
                                            .setSourceFile(BASE_DIR + "tag.csv")
                                            .build();

    @Override
    public String[] schemaCommands() {
        return new String[] {
                "schema.propertyKey('id').Int().ifNotExists().create()",
                "schema.propertyKey('title').Text().ifNotExists().create()",
                String.format("schema.vertexLabel('%s').properties('%s', '%s').ifNotExists().create()", LABEL, ID, TITLE),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, ID, ID),
        };
    }

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
