package com.pyco.datastax.benchmark.data.insert.datastax;

import com.datastax.driver.dse.DseCluster;
import com.pyco.datastax.benchmark.data.insert.datastax.connection.ConnectionPool;
import com.pyco.datastax.benchmark.data.insert.datastax.edge.*;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.BatchArticleInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.BatchPageInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.BatchParagraphInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.BatchSiteInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.BatchTagInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.BatchUserInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.IBatchInsert;
import com.pyco.datastax.benchmark.data.insert.datastax.vertex.StatisticDto;
import com.pyco.datastax.benchmark.dse.DSEFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pyco on 12/2/16.
 */
public class BatchApplication {

    private Logger LOGGER = LoggerFactory.getLogger(BatchApplication.class);

    public static void main(String... args) {
        BatchApplication app = new BatchApplication();
        app.batchInsert();

    }

    private void batchInsert() {

        try {
            final DseCluster cluster = new DSEFactory().getCluster();
            final ConnectionPool pool = new ConnectionPool(cluster, 16, 32);

            final IBatchInsert site = new BatchSiteInsert();
            //site.executeBatchInsert(pool);

            final IBatchInsert page = new BatchPageInsert();
            //page.executeBatchInsert(pool);

            final IBatchInsert tags = new BatchTagInsert();
            //tags.executeBatchInsert(pool);

            final IBatchInsert articles = new BatchArticleInsert();
            //articles.executeBatchInsert(pool);

            final IBatchInsert paragraph = new BatchParagraphInsert();
            //paragraph.executeBatchInsert(pool);

            final IBatchInsert users = new BatchUserInsert();
            users.executeBatchInsert(pool);

            final IBatchInsert articleHasParagraph = new BatchArticleHasParagraph();
            //articleHasParagraph.executeBatchInsert(pool);

            final IBatchInsert articlePublishedToSite = new BatchArticlePublishToSite();
            //articlePublishedToSite.executeBatchInsert(pool);

            final IBatchInsert userFollowsPage = new BatchUserFollowsPage();
            //userFollowsPage.executeBatchInsert(pool);

            final IBatchInsert articleInterestTag = new BatchArticleInterestTag();
            //articleInterestTag.executeBatchInsert(pool);

            final IBatchInsert userFollowsUser = new BatchUserFollowsUser();
            //userFollowsUser.executeBatchInsert(pool);

            final IBatchInsert userLikesArticle = new BatchUserLikesArticle();
            //userLikesArticle.executeBatchInsert(pool);

            pool.closeQueue();
            cluster.close();

        } catch (final Exception e) {
            LOGGER.error(StatisticDto.getInstance().toString());
            LOGGER.error(e.getMessage(), e);
        }
    }

}
