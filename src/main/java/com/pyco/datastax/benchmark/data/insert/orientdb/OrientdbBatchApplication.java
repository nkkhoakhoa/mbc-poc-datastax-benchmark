package com.pyco.datastax.benchmark.data.insert.orientdb;

import com.pyco.datastax.benchmark.data.insert.orientdb.edge.OrientdbBatchUserFollowsPageInsert;
import com.pyco.datastax.benchmark.data.insert.orientdb.vertex.IOrientdbBatchInsert;
import com.pyco.datastax.benchmark.data.insert.orientdb.vertex.OrientdbBatchPageInsert;
import com.pyco.datastax.benchmark.data.insert.orientdb.vertex.OrientdbBatchSiteInsert;
import com.pyco.datastax.benchmark.data.insert.orientdb.vertex.OrientdbBatchUserInsert;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrientdbBatchApplication.class);

    public static void main(String... args){

        try {
            final OrientGraph graph = new OrientGraph("plocal:/Volumes/DATA/Data/java-tools/orientdb-community-2.2.13/databases/test", "admin", "admin");
            final IOrientdbBatchInsert site = new OrientdbBatchSiteInsert();
            //site.batchInsert(graph);
            final IOrientdbBatchInsert page = new OrientdbBatchPageInsert();
            //page.batchInsert(graph);
            final IOrientdbBatchInsert user = new OrientdbBatchUserInsert();
            //user.batchInsert(graph);
            final IOrientdbBatchInsert userFollowsPage = new OrientdbBatchUserFollowsPageInsert();
            userFollowsPage.batchInsert(graph);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}
