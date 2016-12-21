package com.pyco.db.benchmark.data.insert.datastax.edge;

import com.datastax.driver.dse.DseCluster;
import com.pyco.db.benchmark.data.insert.datastax.AbstractDatastaxBatchInsert;
import com.pyco.db.benchmark.data.util.Tracker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pyco on 12/20/16.
 */
public abstract class AbstractBatchEdgeInsert extends AbstractDatastaxBatchInsert{

    private static final Map<String, Long> nodes = new HashMap<String, Long>() {{
        put("user.csv", 5000000L);
        put("article.csv", 200000L);
        put("page.csv", 1000L);
        put("site.csv", 10L);
        put("tag.csv", 500L);
    }};

    @Override
    public void executeBatchInsert(DseCluster cluster) throws Exception {
        while (true) {
            if (insertedAllNodes()) {
                break;
            }
            System.out.println(System.currentTimeMillis());
            Thread.sleep(600000);
        }
        super.executeBatchInsert(cluster);
    }

    private boolean insertedAllNodes() {
        final Map<String, Long> tracking = Tracker.getInstance().getMap();
        for (final Map.Entry<String, Long> entry : nodes.entrySet()) {
            if (tracking.get(entry.getKey()) == null || !tracking.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
