package com.pyco.db.benchmark.data.insert.datastax;

import com.datastax.driver.dse.DseCluster;
import com.pyco.db.benchmark.data.insert.datastax.dse.DSEFactory;
import com.pyco.db.benchmark.data.insert.datastax.edge.*;
import com.pyco.db.benchmark.data.insert.datastax.vertex.*;
import com.pyco.db.benchmark.data.util.SystemUtils;
import com.pyco.db.benchmark.data.util.Tracker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchApplication {

    private Logger LOGGER = LoggerFactory.getLogger(BatchApplication.class);

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public static void main(String... args) {
        BatchApplication app = new BatchApplication();

        app.batchInsert();

    }

    private void batchInsert() {
        startTracking();
        try {
            final DseCluster cluster = new DSEFactory().getCluster();

            final IBatchInsert site = new BatchSiteInsert();
            site.executeBatchInsert(cluster);

            final IBatchInsert page = new BatchPageInsert();
            page.executeBatchInsert(cluster);

            final IBatchInsert tags = new BatchTagInsert();
            tags.executeBatchInsert(cluster);

            final IBatchInsert articles = new BatchArticleInsert();
            articles.executeBatchInsert(cluster);

            final IBatchInsert paragraph = new BatchParagraphInsert();
            paragraph.executeBatchInsert(cluster);

            final IBatchInsert users = new BatchUserInsert();
            users.executeBatchInsert(cluster);

            final IBatchInsert articleHasParagraph = new BatchArticleHasParagraph();
            articleHasParagraph.executeBatchInsert(cluster);

            final IBatchInsert articlePublishedToSite = new BatchArticlePublishToSite();
            articlePublishedToSite.executeBatchInsert(cluster);

            final IBatchInsert userFollowsPage = new BatchUserFollowsPage();
            userFollowsPage.executeBatchInsert(cluster);

            final IBatchInsert articleInterestTag = new BatchArticleInterestTag();
            articleInterestTag.executeBatchInsert(cluster);

            final IBatchInsert userFollowsUser = new BatchUserFollowsUser();
            userFollowsUser.executeBatchInsert(cluster);

            final IBatchInsert userLikesArticle = new BatchUserLikesArticle();
            userLikesArticle.executeBatchInsert(cluster);


        } catch (final Exception e) {
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
