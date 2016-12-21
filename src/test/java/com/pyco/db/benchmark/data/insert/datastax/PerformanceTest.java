package com.pyco.db.benchmark.data.insert.datastax;

import com.datastax.driver.dse.DseCluster;
import com.pyco.db.benchmark.data.insert.datastax.dse.DSEFactory;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by pyco on 12/20/16.
 */

public class PerformanceTest {

    protected static final BlockingQueue<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>(1000);

    protected static final ExecutorService executor = new ThreadPoolExecutor(100, 200, 30, TimeUnit.SECONDS, linkedBlockingDeque);

    @Test
    public void testWriteConcurrent() {
        DseCluster cluster = new DSEFactory().getCluster();

    }

}
