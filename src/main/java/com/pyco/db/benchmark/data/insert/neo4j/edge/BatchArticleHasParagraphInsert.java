package com.pyco.db.benchmark.data.insert.neo4j.edge;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.neo4j.Neo4jAbstractBatchInsert;

/**
 * Created by pyco on 12/13/16.
 */
public class BatchArticleHasParagraphInsert extends Neo4jAbstractBatchInsert {

    private static final String STATEMENT = "MATCH (a:Article {id:%s}), (p:Paragraph {id:%s}) CREATE (a)-[:has]->(p)";
    private InsertOptions insertOptions = new InsertOptions.Builder()
            .setLogFile("article-has-paragraph")
            .setStatement(STATEMENT)
            .setSourceFile(BASE_DIR + "article-has-paragraph.csv")
            .build();

    @Override
    public InsertOptions getInsertOptions() {
        return insertOptions;
    }


}
