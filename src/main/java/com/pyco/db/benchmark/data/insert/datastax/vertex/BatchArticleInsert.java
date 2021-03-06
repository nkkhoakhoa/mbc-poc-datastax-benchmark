package com.pyco.db.benchmark.data.insert.datastax.vertex;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchArticleInsert extends AbstractDatastaxBatchInsert {

    private static final String INSERT_COMMAND = "graph.addVertex(label, 'article', 'id', '%s', 'title','%s')";
    private static final String LABEL = "article";
    private static final String TITLE = "title";

    private InsertOptions insertOptions = new InsertOptions.Builder()
                                            .setStatement(INSERT_COMMAND)
                                            .setLogFile("articles")
                                            .setSourceFile(BASE_DIR + "article.csv")
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
