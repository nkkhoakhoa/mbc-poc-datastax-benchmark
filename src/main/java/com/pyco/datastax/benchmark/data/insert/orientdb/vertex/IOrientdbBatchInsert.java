package com.pyco.datastax.benchmark.data.insert.orientdb.vertex;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.io.IOException;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public interface IOrientdbBatchInsert {

    void batchInsert(final OrientGraph db) throws IOException;

}
