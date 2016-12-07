package com.pyco.datastax.benchmark.data.insert.datastax.vertex;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchPageInsert extends AbstractBatchInsert {

    private static final String INSERT_COMMAND = "graph.addVertex(label, 'page', 'id', '%s', 'title','%s')";
    private static final String LABEL = "page";
    private static final String TITLE = "title";

    private InsertOptions insertOptions = new InsertOptions.Builder()
                                            .setStatement(INSERT_COMMAND)
                                            .setLogFile("pages")
                                            .setSourceFile(BASE_DIR + "page.csv")
                                            .build();

    @Override
    protected String[] schemaCommands() {
        return new String[] {
                "schema.propertyKey('id').Int().ifNotExists().create()",
                "schema.propertyKey('title').Text().ifNotExists().create()",
                String.format("schema.vertexLabel('%s').properties('%s', '%s').ifNotExists().create()", LABEL, ID, TITLE),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, ID, ID),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, TITLE, TITLE)
        };
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
