package com.pyco.db.benchmark.data.generation;

import com.pyco.db.benchmark.data.generation.entities.EntitiesGeneration;
import com.pyco.db.benchmark.data.generation.relationship.RelationshipGeneration;

import java.io.IOException;

/**
 * Created by pyco on 12/1/16.
 */
public class Generation extends AbstractGeneration {

    public static void main(final String[] args) {
        try {
            new Generation().generate();
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }

    private void batchGenerate() throws IOException {
        final EntitiesGeneration entitiesGeneration = new EntitiesGeneration();
        final RelationshipGeneration relationshipGeneration = new RelationshipGeneration();

        entitiesGeneration.generateSite();
        entitiesGeneration.generatePage();
        entitiesGeneration.generateArticle();
        entitiesGeneration.generateUser();
        entitiesGeneration.generateTag();
        relationshipGeneration.generateUserFollowsUser();
        relationshipGeneration.generateUserFollowsPage();
        relationshipGeneration.generateUserLikesArticle();
        relationshipGeneration.generateArticlePublishedByPage();
        relationshipGeneration.generateArticlePublishedToSite();
        relationshipGeneration.generateArticleInterestTag();
        relationshipGeneration.generateArticleHasParagraph();
    }

    private void generate() throws IOException {
        final String file = System.getProperty("file.name");

        final EntitiesGeneration entitiesGeneration = new EntitiesGeneration();
        final RelationshipGeneration relationshipGeneration = new RelationshipGeneration();

        switch (file) {
            case "site":
                entitiesGeneration.generateSite();
                break;
            case "page":
                entitiesGeneration.generatePage();
                break;
            case "article":
                entitiesGeneration.generateArticle();
                break;
            case "user":
                entitiesGeneration.generateUser();
                break;
            case "tag":
                entitiesGeneration.generateTag();
                break;
            case "user-follows-user":
                relationshipGeneration.generateUserFollowsUser();
                break;
            case "user-follows-page":
                relationshipGeneration.generateUserFollowsPage();
                break;
            case "user-likes-article":
                relationshipGeneration.generateUserLikesArticle();
                break;
            case "article-publishedby-page":
                relationshipGeneration.generateArticlePublishedByPage();
                break;
            case "article-publishedto-site":
                relationshipGeneration.generateArticlePublishedToSite();
                break;
            case "article-interested-tag":
                relationshipGeneration.generateArticleInterestTag();
                break;
            case "article-has-paragraph":
                relationshipGeneration.generateArticleHasParagraph();
                break;
            default:
                System.out.println("You should set file.name and dir.path correctly. Please read README.md");
                break;
        }
    }

}
