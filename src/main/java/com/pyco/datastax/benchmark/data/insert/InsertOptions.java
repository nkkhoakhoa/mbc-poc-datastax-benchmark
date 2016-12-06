package com.pyco.datastax.benchmark.data.insert;

import com.datastax.driver.core.querybuilder.Insert;

/**
 * Created by pyco on 12/2/16.
 */
public class InsertOptions {

    private String logfile;
    /*
        insert command per every row of source file
        Example: graph.addVertex(label, 'user', 'id', '%s', 'username','%s')
     */
    private String statement;
    private String sourceFile;


    public InsertOptions() {
    }

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public static class Builder {
        private InsertOptions options = new InsertOptions();

        public Builder setLogFile(final String logFile) {
            System.setProperty("log.name", logFile);
            options.setLogfile(logFile);
            return this;
        }

        public Builder setStatement(final String statement){
            options.setStatement(statement);
            return this;
        }

        public Builder setSourceFile(final String sourceFile){
            options.setSourceFile(sourceFile);
            return this;
        }

        public InsertOptions build() {
            return options;
        }
    }

}
