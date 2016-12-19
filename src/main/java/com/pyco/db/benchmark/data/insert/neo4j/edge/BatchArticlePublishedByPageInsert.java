package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchArticlePublishedByPageInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (x:Article {id:%s}), (y:Page {id:%s}) CREATE (x)-[:publishedby]->(y)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("article-published-by-page")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article-publishedby-page.csv")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
