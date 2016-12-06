package com.pyco.datastax.benchmark.data.generation;

/**
 * Created by pyco on 12/1/16.
 */
public abstract class AbstractGeneration {

    protected static final String PATH        = "/BatchUserInsert/pyco/Public/benchmark-data/";

    protected static String getNewLine(){
        return System.getProperty("line.separator");
    }

}
