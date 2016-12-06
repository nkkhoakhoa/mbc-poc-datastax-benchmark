package com.pyco.datastax.benchmark.dse;

import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.graph.GraphOptions;

/**
 * Created by KhoaNguyenKieu on 11/30/16.
 */
public class DSEFactory {

    public DseCluster getCluster() {
        return DseCluster.builder().addContactPoint("127.0.0.1")
                .withPort(9042)
                .withProtocolVersion(ProtocolVersion.NEWEST_SUPPORTED)
                .withGraphOptions(new GraphOptions().setGraphName("BENCHMARK"))
                .build();
    }

}
