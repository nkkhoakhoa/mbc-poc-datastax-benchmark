package com.pyco.datastax.benchmark.data.insert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pyco on 12/6/16.
 */
public class StatisticDto {

    private List<String> infos = new LinkedList<>();
    private long rowIndexInserted = 0L;

    private static StatisticDto instance = new StatisticDto();

    private StatisticDto() {
    }

    public static StatisticDto getInstance() {
        return instance;
    }

    public void addInfo(final String info) {
        infos.add(info);
    }

    public void increaseIndex() {
        rowIndexInserted++;
    }

    public void resetIndex(){
        rowIndexInserted = 0L;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (String info : infos) {
            builder.append(info + newLine());
        }
        return String.format("StatisticDto: infos: %s %s row index inserted: %s", builder.toString(), newLine(), String.valueOf(rowIndexInserted));
    }

    private String newLine() {
        return System.getProperty("line.separator");
    }
}
