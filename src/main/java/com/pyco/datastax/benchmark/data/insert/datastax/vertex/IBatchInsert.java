package com.pyco.datastax.benchmark.data.insert.datastax.vertex;

import com.pyco.datastax.benchmark.data.insert.datastax.connection.ConnectionPool;

/**
 * Created by pyco on 12/2/16.
 */
public interface IBatchInsert {

    void executeBatchInsert(final ConnectionPool pool) throws Exception;

}
