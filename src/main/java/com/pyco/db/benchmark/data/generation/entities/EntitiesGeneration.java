package com.pyco.db.benchmark.data.generation.entities;

import com.pyco.db.benchmark.data.generation.AbstractGeneration;
import com.pyco.db.benchmark.data.generation.Generation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pyco on 12/12/16.
 */
public class EntitiesGeneration extends AbstractGeneration {

    private Logger logger = LoggerFactory.getLogger(Generation.class);

    public void generateUser() {
        for (int i = 1; i <= 5000000; i++) {
            final String newLine = i != 5000000 ? getNewLine() : StringUtils.EMPTY;
            logger.info(String.format("%s,%s", i, "user " + i) + newLine);
        }
    }

    public void generateTag() {
        for (int i = 1; i <= 500; i++) {
            final String newLine = i != 500 ? getNewLine() : StringUtils.EMPTY;
            logger.info(String.format("%s,%s", i, "Tag " + i) + newLine);
        }
    }

    public void generateArticle() {
        for (int i = 1; i <= 200000; i++) {
            final String newLine = i != 200000 ? getNewLine() : StringUtils.EMPTY;
            logger.info(String.format("%s,%s", i, "Article " + i) + newLine);
        }
    }

    public void generatePage() {
        for (int i = 1; i <= 1000; i++) {
            final String newLine = i != 1000 ? getNewLine() : StringUtils.EMPTY;
            logger.info(String.format("%s,%s", i, "Page " + i) + newLine);
        }
    }

    public void generateSite() {
        for (int i = 1; i <= 10; i++) {
            final String newLine = i != 10 ? getNewLine() : StringUtils.EMPTY;
            logger.info(String.format("%s,%s", i, "Site " + i) + newLine);
        }
    }

}
