package com.pyco.db.benchmark.data.insert.datastax.benchmark;

import com.pyco.db.benchmark.data.insert.InsertOptions;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;
import com.pyco.db.benchmark.data.insert.datastax.edge.BatchUserFollowsUser;

/**
 * Created by pyco on 12/20/16.
 */
public class UserFollowsUserBenchmarkImport extends AbstractBenchmarkImport {

    private BatchUserFollowsUser userFollowsUser = new BatchUserFollowsUser();

    @Override
    AbstractDatastaxBatchInsert getConcreate() {
        return userFollowsUser;
    }

    @Override
    public String[] schemaCommands() {
        return userFollowsUser.schemaCommands();
    }

    @Override
    public InsertOptions getInsertOptions() {
        return userFollowsUser.getInsertOptions();
    }
}
