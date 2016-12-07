package com.pyco.datastax.benchmark.data.insert.datastax.edge;

import com.pyco.datastax.benchmark.data.insert.datastax.vertex.AbstractBatchInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.InsertOptions;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchArticleInterestTag extends AbstractBatchInsert {

    private static final String INSERT_COMMAND =    "def v1 = g.V().has('article', 'id', %s).next()\n" +
                                                    "def v2=g.V().has('tag', 'id', %s).next()\n" +
                                                    "v1.addEdge('interest',v2)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setStatement(INSERT_COMMAND)
            .setLogFile("article-interest-tag")
            .setSourceFile(BASE_DIR + "article-interest-tag.csv")
            .build();

    @Override
    protected String[] schemaCommands() {
        return new String[0];
    }

    @Override
    protected InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
