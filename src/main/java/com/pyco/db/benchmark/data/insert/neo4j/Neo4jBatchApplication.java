package com.pyco.db.benchmark.data.insert.neo4j;

import com.pyco.db.benchmark.data.insert.neo4j.edge.BatchUserFollowsPageInsert;
import com.pyco.db.benchmark.data.insert.neo4j.edge.BatchUserFollowsUserInsert;
import com.pyco.db.benchmark.data.insert.neo4j.edge.BatchUserLikesArticleInsert;
import com.pyco.db.benchmark.data.util.SystemUtils;
import com.pyco.db.benchmark.data.util.Tracker;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by pyco on 12/13/16.
 */
public class Neo4jBatchApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jBatchApplication.class);

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void main(final String... args) {
        new Neo4jBatchApplication().batchInsert();
    }

    private void batchInsert() {

        final String host = SystemUtils.getSystemProperty("neo4j.host", "172.16.40.10");
        final String username = SystemUtils.getSystemProperty("neo4j.user", "neo4j");
        final String password = SystemUtils.getSystemProperty("neo4j.pass", "admin");

        final Driver driver = GraphDatabase.driver( "bolt://" + host, AuthTokens.basic( username, password ),
                Config.build()
                        .withMaxIdleSessions(100)
                        .toConfig());

        System.out.println("neo4j.host: " + "bolt://" + host);
        System.out.println("neo4j.user: " + username);
        System.out.println("neo4j.password: " + password);
        final String[] systemProperties = new String[] {"log.path", "data.path", "tracking.period", "tracking.path"};
        printSystemProperties(systemProperties);

        try {

            startTracking();

            INeo4jBatchInsert userFollowsPage = new BatchUserFollowsPageInsert();
            //userFollowsPage.batchInsert(driver);

            INeo4jBatchInsert userFollowsUser = new BatchUserFollowsUserInsert();
            //userFollowsUser.batchInsert(driver);

            INeo4jBatchInsert userLikesArticle = new BatchUserLikesArticleInsert();
            userLikesArticle.batchInsert(driver);

        } catch (final Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void startTracking() {
        final String tracking = SystemUtils.getSystemProperty("tracking.period", "5");
        final long period = Long.valueOf(tracking);
        executor.scheduleAtFixedRate((Runnable) () -> Tracker.getInstance().persist(), 0, period, TimeUnit.MINUTES);
    }

    private void printSystemProperties(final String... keys) {
        for (final String key : keys) {
            System.out.println(key + ": " + SystemUtils.getSystemProperty(key, StringUtils.EMPTY));
        }
    }

}
