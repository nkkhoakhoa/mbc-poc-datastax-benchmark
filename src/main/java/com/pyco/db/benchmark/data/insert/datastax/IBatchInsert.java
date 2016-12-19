package com.pyco.db.benchmark.data.insert.datastax;

import com.datastax.driver.dse.DseCluster;

/**
 * Created by pyco on 12/2/16.
 */
public interface IBatchInsert {

    void executeBatchInsert(final DseCluster cluster) throws Exception;

}
