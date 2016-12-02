package com.nkk.gremlin.api.data;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pyco on 12/1/16.
 */
public class RelationshipGeneration extends AbstractGeneration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipGeneration.class);

    private static final String USER_FOLLOW_USER            = "user-follows-user.csv";
    private static final String USER_FOLLOW_PAGE            = "user-follows-page.csv";
    private static final String USER_LIKES_ARTICLE          = "user-likes-article.csv";

    private static final String ARTICLE_HAS_PARAGRAPH       = "article-has-paragraph.csv";
    private static final String ARTICLE_INTEREST_TAG        = "article-interest-tag.csv";
    private static final String ARTICLE_PUBLISHEDBY_PAGE    = "article-publishedby-page.csv";
    private static final String ARTICLE_PUBLISHEDTO_SITE    = "article-publishedto-site.csv";

    public static void main(String... args) throws IOException {
        //generateArticleInterestTag();
        //generateUserFollowsUser();
        //generateUserFollowsPage();
        //generateArticlePublishedToSite();
        //generateUserLikesArticle();
        //generateArticleHasParagraph();
        generateArticleHasParagraph();
    }

    private static void generateArticleHasParagraph() throws IOException {
        BufferedWriter paragraphWriter = null;
        BufferedWriter articleParagraphWriter = null;

        final String text = getText();
        final String[] splited = text.split(" ");
        int maxStart = 15500;

        try {
            paragraphWriter = getWriter("paragraph.csv");
            articleParagraphWriter = getWriter(ARTICLE_HAS_PARAGRAPH);
            int k = 1;
            for (int i = 1; i <= 200000; i++) {
                int paragraphAmount = random(1, 10);
                int[] words = generateRandomNumbers(500, 5000, paragraphAmount);
                int[] startWords = generateRandomNumbers(0, maxStart, paragraphAmount);
                for (int j = 0; j < paragraphAmount; j++) {
                    final String randomText = getRandomText(splited, startWords[j], words[j]);
                    paragraphWriter.write(String.format("%s,%s", k, StringEscapeUtils.escapeCsv(randomText)) + getNewLine());
                    articleParagraphWriter.write(String.format("%s,%s", i, k) + getNewLine());
                    k++;
                }

                if (i % 10000 == 0) {
                    LOGGER.info("wrote: " + i);
                }
            }
            LOGGER.info(String.valueOf(k));
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(paragraphWriter);
            IOUtils.closeQuietly(articleParagraphWriter);
        }
    }

    private static String getRandomText(final String[] text, int startWord, int wordsCount) throws IOException {
        return StringUtils.join(Arrays.copyOfRange(text, startWord, wordsCount + startWord), " ");
    }

    private static void generateArticleInterestTag(){
        final GenerationDTO dto = new GenerationDTO(1, 200000, 1, 10, 500, ARTICLE_INTEREST_TAG);
        generate(dto);
    }

    private static void generateArticlePublishedToSite(){
        final GenerationDTO dto = new GenerationDTO(1, 200000, 1, 5, 10, ARTICLE_PUBLISHEDTO_SITE);
        generate(dto);
    }

    private static void generateUserFollowsPage(){
        final GenerationDTO dto = new GenerationDTO(1, 5000000, 1, 10, 1000, USER_FOLLOW_PAGE);
        generate(dto);
    }

    private static void generateUserFollowsUser(){
        final GenerationDTO dto = new GenerationDTO(1, 5000000, 5, 500, 5000000, USER_FOLLOW_USER);
        generate(dto);
    }

    private static void generateUserLikesArticle(){
        final GenerationDTO dto = new GenerationDTO(1, 5000000, 10, 1000, 200000, USER_LIKES_ARTICLE);
        generate(dto);
    }

    private static void generate(final GenerationDTO dto){
        BufferedWriter writer = null;
        try {
            writer = getWriter(dto.getFile());
            for (int i = dto.getStartRow(); i <= dto.getEndRow(); i++) {
                int followAmount = random(dto.getRandomAmountStart(), dto.getRandomAmountEnd());
                int[] randomNumbers = generateRandomNumbers(1, dto.getRandomArrange(), followAmount);
                for (int j = 0; j < randomNumbers.length; j++) {
                    final String content = String.format("%s,%s", i, randomNumbers[j]) + getNewLine();
                    writer.write(content);
                }

                if (i % 10000 == 0) {
                    LOGGER.info("wrote: " + i);
                }
            }
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

    private static String getText() throws IOException {
        final String path = "./src/main/resources/dummy-text";
        return FileUtils.readFileToString(new File(path), Charset.forName("UTF-8"));
    }

    private static BufferedWriter getWriter(final String fileName) throws IOException {
        return Files.newBufferedWriter(Paths.get(PATH + fileName));
    }

    private static int[] generateRandomNumbers(int min, int max, int amount){
        int[] result = new int[amount];
        for (int i = 0; i < amount; i++) {
            int temp = random(min, max);
            if (!ArrayUtils.contains(result, temp)) {
                result[i] = temp;
            } else {
                --i;
            }
        }
        return result;
    }

    private static String arrayToString(int[] array) {
        final String result = ArrayUtils.toString(array);
        return result.substring(1, result.length() - 1);
    }

    private static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}