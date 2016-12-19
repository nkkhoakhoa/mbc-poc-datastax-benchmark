package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchUserFollowsUserInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (u:User {id:%s}), (u2:User {id:%s}) CREATE (u)-[:follows]->(u2)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("user-follows-user")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "user-follows-user")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
