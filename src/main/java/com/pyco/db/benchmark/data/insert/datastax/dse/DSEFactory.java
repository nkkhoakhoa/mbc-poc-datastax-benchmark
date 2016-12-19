package com.pyco.db.benchmark.data.insert.datastax.dse;

import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.graph.GraphOptions;
import com.pyco.db.benchmark.data.util.SystemUtils;

/**
 * Created by KhoaNguyenKieu on 11/30/16.
 */
public class DSEFactory {

    private static final String SERVER = SystemUtils.getSystemProperty("dse.host", "127.0.0.1");
    private static final int PORT = Integer.valueOf(SystemUtils.getSystemProperty("dse.port", "9042"));
    private static final String DATABASE = SystemUtils.getSystemProperty("dse.database", "BENCHMARK");

    public DseCluster getCluster() {
        return DseCluster.builder().addContactPoint(SERVER)
                .withPort(PORT)
                .withProtocolVersion(ProtocolVersion.NEWEST_SUPPORTED)
                .withGraphOptions(new GraphOptions().setGraphName(DATABASE))
                .build();
    }

}
