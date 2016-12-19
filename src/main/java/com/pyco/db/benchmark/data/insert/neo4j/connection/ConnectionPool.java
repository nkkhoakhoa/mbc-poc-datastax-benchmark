package com.pyco.db.benchmark.data.insert.neo4j.connection;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pyco on 12/13/16.
 */
public class ConnectionPool {

    private BlockingQueue<Session> pool;
    private int initPoolSize;
    private int maxPoolSize;
    private int currentPoolSize = 0;

    private Driver driver;

    public ConnectionPool() {

    }

    public ConnectionPool(final Driver driver, int init, int max) {
        this.initPoolSize = init;
        this.maxPoolSize = max;
        this.driver = driver;
        this.pool = new LinkedBlockingQueue<>();
        initPool();
    }

    private void initPool() {
        for (int i = 0; i < initPoolSize; i++) {
            pool.add(driver.session());
        }
    }

    private void openAndPoolConnection() throws InterruptedException {
        if (currentPoolSize == maxPoolSize) {
            return;
        }
        pool.put(driver.session());
        currentPoolSize++;
    }

    public void putToQueue(final Session session) throws InterruptedException {
        if (currentPoolSize < maxPoolSize) {
            pool.put(session);
            currentPoolSize++;
        }
    }

    public Session getSession() throws InterruptedException {
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
