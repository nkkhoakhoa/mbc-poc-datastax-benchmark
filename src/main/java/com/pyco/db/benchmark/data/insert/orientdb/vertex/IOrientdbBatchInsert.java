package com.pyco.db.benchmark.data.insert.orientdb.vertex;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import java.io.IOException;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public interface IOrientdbBatchInsert {

    void batchInsert(final OrientGraphFactory factory) throws IOException;

}
