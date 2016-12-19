package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchArticlePublishedToSiteInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (x:Article {id:%s}), (y:Site {id:%s}) CREATE (x)-[:publishedto]->(y)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("article-published-to-site")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article-publishedto-site.csv")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
