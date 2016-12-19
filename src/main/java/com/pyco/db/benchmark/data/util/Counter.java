package com.pyco.db.benchmark.data.util;

import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by pyco on 12/16/16.
 */
public class Counter {
    private final Logger logger;
    private final String tag;
    private final AtomicLong counter;
    private long startAt;

    public Counter(Logger logger, String tag) {
        this.logger = logger;
        this.tag = tag;
        counter = new AtomicLong(0);
        startAt = System.nanoTime();
    }

    public void increase() {
        final long count = counter.incrementAndGet();
        if ((count <= 20000 && count % 1000 == 0) || (count > 20000 && count % 10000 == 0)) {
            long current = System.nanoTime();
            final StringBuilder sb = new StringBuilder();
            sb.append(tag).append(" - ").append(String.valueOf(count)).append(" - ").append(String.valueOf(current - startAt));
            logger.info(sb.toString());
            startAt = current;
        }
    }

    public void start() {
        startAt = System.nanoTime();
        counter.set(0);
    }
}
