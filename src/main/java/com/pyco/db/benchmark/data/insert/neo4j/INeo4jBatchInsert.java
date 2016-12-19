package com.pyco.db.benchmark.data.insert.neo4j;

import org.neo4j.driver.v1.Driver;

import java.io.IOException;

/**
 * Created by pyco on 12/13/16.
 */
public interface INeo4jBatchInsert {

    void batchInsert(final Driver driver);

}
