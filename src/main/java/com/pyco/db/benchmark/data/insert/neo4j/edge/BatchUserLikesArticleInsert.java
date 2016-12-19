package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchUserLikesArticleInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (x:User {id:'%s'}), (y:Article {id:'%s'}) CREATE (x)-[:likes]->(y)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("user-likes-article")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user-likes-article")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
