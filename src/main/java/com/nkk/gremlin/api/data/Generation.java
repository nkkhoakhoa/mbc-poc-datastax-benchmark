package com.nkk.gremlin.api.data;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by pyco on 12/1/16.
 */
public class Generation extends AbstractGeneration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Generation.class);

    private static final String USER        = "user.csv";
    private static final String PAGE        = "page.csv";
    private static final String ARTICLE     = "article.csv";
    private static final String PARAGRAPH   = "paragraph.csv";
    private static final String SITE        = "site.csv";
    private static final String TAG         = "tag.csv";

    public static void main(final String[] args) {
        final String path = PATH + TAG;
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(Paths.get(path));
            for (int i = 1; i <= 500; i++) {
                writer.write(generate(i) + getNewLine());
                if (i % 10000 == 0) {
                    LOGGER.info(String.valueOf(i));
                }
            }
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    private static String generate(int i) {
        return String.format("%s,%s", i, "tag " + i);
    }


}
