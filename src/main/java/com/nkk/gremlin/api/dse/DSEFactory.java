package com.nkk.gremlin.api.dse;

import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.graph.GraphOptions;

/**
 * Created by KhoaNguyenKieu on 11/30/16.
 */
public class DSEFactory {

    public DseCluster getCluster() {
        return DseCluster.builder().addContactPoint("172.16.40.10")
                .withPort(9042)
                .withGraphOptions(new GraphOptions().setGraphName("BENCHMARK_DEMO"))
                .build();
    }

}
