package com.pyco.db.benchmark.data.insert.orientdb;

import com.pyco.db.benchmark.data.insert.orientdb.edge.*;
import com.pyco.db.benchmark.data.insert.orientdb.vertex.*;
import com.pyco.db.benchmark.data.util.SystemUtils;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by KhoaNguyenKieu on 12/6/16.
 */
public class OrientdbBatchApplication {

    private Logger LOGGER = LoggerFactory.getLogger(OrientdbBatchApplication.class);

    public static void main(String... args){

        new OrientdbBatchApplication().run();
    }

    public void run() {
        final String host = SystemUtils.getSystemProperty("orientdb.host", "localhost/benchmarkdemo");
        final String username = SystemUtils.getSystemProperty("orientdb.user", "admin");
        final String password = SystemUtils.getSystemProperty("orientdb.password", "admin");

        try {

            final OrientGraphFactory graph = new OrientGraphFactory("remote:" + host, username, password).setupPool(16, 32);

            System.out.println("orientdb.host: " + "remote:" + host);
            System.out.println("orientdb.user: " + username);
            System.out.println("orientdb.password: " + password);
            System.out.println("log.path: " + SystemUtils.getSystemProperty("log.path", StringUtils.EMPTY));
            System.out.println("data.path: " + SystemUtils.getSystemProperty("data.path", StringUtils.EMPTY));


            GremlinPipeline pine = new GremlinPipeline();
            Vertex v = graph.getTx().getVertex("#113:3532");
            Iterable<Vertex> vs = graph.getTx().query().has("username", "user1").vertices();
            Iterable<Vertex> vertices = graph.getTx().query().has("label", "User").has("id", 100).vertices();
            Object object = pine.start(graph.getTx().query().has("label", "User").has("id", 100).vertices()).next();

            /*final IOrientdbBatchInsert site = new OrientdbBatchSiteInsert();
            site.batchInsert(graph);

            final IOrientdbBatchInsert page = new OrientdbBatchPageInsert();
            page.batchInsert(graph);

            final IOrientdbBatchInsert tag = new OrientdbBatchTagInsert();
            tag.batchInsert(graph);

            final IOrientdbBatchInsert article = new OrientdbBatchArticleInsert();
            article.batchInsert(graph);

            final IOrientdbBatchInsert paragraph = new OrientdbBatchParagraphInsert();
            paragraph.batchInsert(graph);

            final IOrientdbBatchInsert user = new OrientdbBatchUserInsert();
            user.batchInsert(graph);

            final IOrientdbBatchInsert articlePublishedbyPage = new OrientdbBatchArticlePublishedByPageInsert();
            articlePublishedbyPage.batchInsert(graph);

            final IOrientdbBatchInsert articleHasParagraph = new OrientdbBatchArticleHasParagraphInsert();
            articleHasParagraph.batchInsert(graph);

            final IOrientdbBatchInsert ariticlePublishedtoSite = new OrientdbBatchArticlePublishedToSiteInsert();
            ariticlePublishedtoSite.batchInsert(graph);

            final IOrientdbBatchInsert articleInterestTag = new OrientdbBatchArticleInterestTagInsert();
            articleInterestTag.batchInsert(graph);

            final IOrientdbBatchInsert userFollowsPage = new OrientdbBatchUserFollowsPageInsert();
            userFollowsPage.batchInsert(graph);

            final IOrientdbBatchInsert userFollowsUser = new OrientdbBatchUserFollowsUserInsert();
            userFollowsUser.batchInsert(graph);*/

            final IOrientdbBatchInsert userLikesArticle = new OrientdbBatchUserLikesArticleInsert();
            userLikesArticle.batchInsert(graph);

            graph.close();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
