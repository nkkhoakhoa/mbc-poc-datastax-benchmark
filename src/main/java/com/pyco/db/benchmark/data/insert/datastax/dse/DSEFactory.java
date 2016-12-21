package com.pyco.db.benchmark.data.insert.datastax.dse;

import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.pyco.db.benchmark.data.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by KhoaNguyenKieu on 11/30/16.
 */
public class DSEFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DSEFactory.class);

    private static final String SERVER = SystemUtils.getSystemProperty("dse.host", "127.0.0.1");
    private static final int PORT = Integer.valueOf(SystemUtils.getSystemProperty("dse.port", "9042"));
    private static final String DATABASE = SystemUtils.getSystemProperty("dse.database", "wtf");

    public DseCluster getCluster() {
        final  DseCluster cluster = DseCluster.builder().addContactPoint(SERVER)
                .withPort(PORT)
                .withProtocolVersion(ProtocolVersion.NEWEST_SUPPORTED)
                .build();
        createDatabaseIfNotExists(cluster, DATABASE);
        cluster.getConfiguration().getGraphOptions().setGraphName(DATABASE);
        return cluster;
    }

    private void createDatabaseIfNotExists(final DseCluster cluster, final String database) {
        final DseSession dseSession = cluster.newSession();
        final String createDatabaseStatement = "system.graph('" + database + "').ifNotExists().create()";
        LOGGER.info("create database: " + createDatabaseStatement);
        dseSession.executeGraph(createDatabaseStatement);
        dseSession.close();
    }

}
