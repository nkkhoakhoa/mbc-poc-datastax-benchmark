package com.pyco.db.benchmark.data.insert.neo4j.vertex;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchPageInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "CREATE (a:Page {id:%s, name:'%s'})";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("pages")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "page.csv")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }
}
