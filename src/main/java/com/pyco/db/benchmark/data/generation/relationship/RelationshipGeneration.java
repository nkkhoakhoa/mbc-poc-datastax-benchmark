package com.pyco.db.benchmark.data.generation.relationship;

import com.pyco.db.benchmark.data.generation.AbstractGeneration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by pyco on 12/1/16.
 */
public class RelationshipGeneration extends AbstractGeneration {

    private Logger logger = LoggerFactory.getLogger(RelationshipGeneration.class);
    private Logger paragraphLogger = LoggerFactory.getLogger(ParagraphGeneration.class);

    public void generateArticleHasParagraph() throws IOException {
        final String text = getText();
        final String[] splited = text.split(" ");
        int maxStart = 15500;
        int k = 1;
        for (int i = 1; i <= 200000; i++) {
            int paragraphAmount = random(1, 10);
            int[] words = generateRandomNumbers(500, 5000, paragraphAmount);
            int[] startWords = generateRandomNumbers(0, maxStart, paragraphAmount);
            for (int j = 0; j < paragraphAmount; j++) {
                final String randomText = getRandomText(splited, startWords[j], words[j]);
                paragraphLogger.info(String.format("%s,%s", k, StringEscapeUtils.escapeCsv(randomText)) + getNewLine());
                logger.info(String.format("%s,%s", i, k) + getNewLine());
                k++;
            }
        }
    }

    private static String getRandomText(final String[] text, int startWord, int wordsCount) throws IOException {
        return StringUtils.join(Arrays.copyOfRange(text, startWord, wordsCount + startWord), " ");
    }

    public void generateArticleInterestTag(){
        final GenerationDTO dto = new GenerationDTO(1, 200000, 1, 10, 500);
        generate(dto);
    }

    public void generateArticlePublishedToSite(){
        final GenerationDTO dto = new GenerationDTO(1, 200000, 1, 5, 10);
        generate(dto);
    }

    public void generateUserFollowsPage(){
        final GenerationDTO dto = new GenerationDTO(1, 5000000, 1, 10, 1000);
        generate(dto);
    }

    public void generateUserFollowsUser(){
        final GenerationDTO dto = new GenerationDTO(1, 5000000, 5, 500, 5000000);
        generate(dto);
    }

    public void generateUserLikesArticle(){
        final GenerationDTO dto = new GenerationDTO(1, 5000000, 10, 1000, 200000);
        generate(dto);
    }

    public void generateArticlePublishedByPage() {
        final GenerationDTO dto = new GenerationDTO(1, 200000, 1, 1, 1000);
        generate(dto);
    }

    private void generate(final GenerationDTO dto){
        for (int i = dto.getStartRow(); i <= dto.getEndRow(); i++) {
            int followAmount = random(dto.getRandomAmountStart(), dto.getRandomAmountEnd());
            int[] randomNumbers = generateRandomNumbers(1, dto.getRandomArrange(), followAmount);
            for (int j = 0; j < randomNumbers.length; j++) {
                logger.info(String.format("%s,%s", i, randomNumbers[j]) + getNewLine());
            }
        }
    }

    private String getText() throws IOException {
        final URL path = this.getClass().getClassLoader().getResource("dummy-text.txt");
        return IOUtils.toString(path, "UTF-8");
    }

    private static BufferedWriter getWriter(final String fileName) throws IOException {
        return Files.newBufferedWriter(Paths.get(DEFAULT_PATH + fileName));
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