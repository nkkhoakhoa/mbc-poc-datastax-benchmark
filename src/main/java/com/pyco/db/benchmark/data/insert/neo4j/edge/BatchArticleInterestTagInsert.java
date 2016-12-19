package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchArticleInterestTagInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (x:Article {id:%s}), (y:Tag {id:%s}) CREATE (x)-[:interest]->(y)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("article-interest-tag")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article-interested-tag.csv")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
