package com.pyco.datastax.benchmark.connection;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pyco on 12/6/16.
 */
public class ConnectionPool {

    private BlockingQueue<DseSession> pool;
    private int initPoolSize;
    private int maxPoolSize;
    private int currentPoolSize = 0;

    private DseCluster cluster;

    public ConnectionPool() {

    }

    public ConnectionPool(final DseCluster cluster, int init, int max) {
        this.initPoolSize = init;
        this.maxPoolSize = max;
        this.cluster = cluster;
        this.pool = new LinkedBlockingQueue<>();
        initPool();
    }

    private void initPool() {
        for (int i = 0; i < initPoolSize; i++) {
            pool.add(cluster.connect());
        }
    }

    private void openAndPoolConnection() throws InterruptedException {
        if (currentPoolSize == maxPoolSize) {
            return;
        }
        pool.put(cluster.newSession());
        currentPoolSize++;
    }

    public void putToQueue(final DseSession session) throws InterruptedException {
        if (currentPoolSize < maxPoolSize) {
            pool.put(session);
            currentPoolSize++;
        }
    }

    public DseSession getSession() throws InterruptedException {
        if (pool.peek() == null && currentPoolSize < maxPoolSize) {
            openAndPoolConnection();
        }
        currentPoolSize--;
        return pool.take();
    }

    public void closeQueue() throws InterruptedException {
        if (pool == null || pool.size() == 0) return;
        for (int i = 0; i < pool.size(); i++) {
            if (pool.peek() != null)
                pool.take().close();
        }
    }

    public int getSize() {
        return pool.size();
    }

}
