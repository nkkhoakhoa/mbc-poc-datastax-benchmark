package com.pyco.db.benchmark.data.insert.datastax.benchmark;


import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;
import com.pyco.db.benchmark.data.insert.datastax.vertex.BatchUserInsert;

/**
 * Created by pyco on 12/20/16.
 */
public class UserBenchmarkImport extends AbstractBenchmarkImport {

    private BatchUserInsert user = new BatchUserInsert();

    @Override
    AbstractDatastaxBatchInsert getConcreate() {
        return user;
    }

    @Override
    public String[] schemaCommands() {
        return user.schemaCommands();
    }

    @Override
    public InsertOptions getInsertOptions() {
        return user.getInsertOptions();
    }
}
