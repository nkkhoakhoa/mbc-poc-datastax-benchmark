package com.pyco.db.benchmark.data.insert.datastax.edge;

import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;
import com.pyco.db.benchmark.data.insert.InsertOptions;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchArticleHasParagraph extends AbstractBatchEdgeInsert {

    private static final String INSERT_COMMAND =    "def v1 = g.V().has('article', 'id', %s).next()\n" +
                                                    "def v2=g.V().has('paragraph', 'id', %s).next()\n" +
                                                    "v1.addEdge('has',v2)";

    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setStatement(INSERT_COMMAND)
            .setLogFile("article-has-paragraph")
            .setSourceFile(BASE_DIR + "article-has-paragraph.csv")
            .build();

    @Override
    public String[] schemaCommands() {
        return new String[0];
    }

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
