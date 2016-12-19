package com.pyco.db.benchmark.data.generation;

/**
 * Created by pyco on 12/1/16.
 */
public abstract class AbstractGeneration {

    protected static final String DEFAULT_PATH = "/usr/";

    protected static String getNewLine(){
        return System.getProperty("line.separator");
    }

}
