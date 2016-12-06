package com.pyco.datastax.benchmark.data.insert;

import com.pyco.datastax.benchmark.connection.ConnectionPool;

import java.io.IOException;

/**
 * Created by pyco on 12/2/16.
 */
public interface IBatchInsert {

    void executeBatchInsert(final ConnectionPool pool) throws Exception;

}
