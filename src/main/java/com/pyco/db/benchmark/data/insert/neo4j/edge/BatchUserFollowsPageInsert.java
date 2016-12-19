package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchUserFollowsPageInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (x:User {id:%s}), (y:Page {id:%s}) CREATE (x)-[:follows]->(y)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("use-follows-page")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user-follows-page")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
