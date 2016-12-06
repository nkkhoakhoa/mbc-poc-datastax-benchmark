package com.pyco.datastax.benchmark.data.insert;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchSiteInsert extends AbstractBatchInsert {

    private static final String INSERT_COMMAND = "graph.addVertex(label, 'site', 'id', '%s', 'name','%s')";
    private static final String LABEL = "site";
    private static final String NAME = "name";

    private InsertOptions insertOptions = new InsertOptions.Builder()
                                            .setStatement(INSERT_COMMAND)
                                            .setLogFile("sites")
                                            .setSourceFile(BASE_DIR + "site.csv")
                                            .build();

    @Override
    protected String[] schemaCommands() {
        return new String[] {
                "schema.propertyKey('id').Int().ifNotExists().create()",
                "schema.propertyKey('name').Text().ifNotExists().create()",
                String.format("schema.vertexLabel('%s').properties('%s', '%s').ifNotExists().create()", LABEL, ID, NAME),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, ID, ID),
                String.format("schema.vertexLabel('%s').index('%s').secondary().by('%s').ifNotExists().add()", LABEL, NAME, NAME)
        };
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
