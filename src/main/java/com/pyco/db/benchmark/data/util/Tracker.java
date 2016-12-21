package com.pyco.db.benchmark.data.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pyco on 12/16/16.
 */
public class Tracker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tracker.class);
    private static final String PATH = "tracking/log.tracking";

    private Map<String, Long> map = new ConcurrentHashMap<>();
    private static Tracker tracker;
    private Tracker() {
    }

    public static Tracker getInstance() {
        if (tracker == null) {
            tracker = new Tracker();
            tracker.initMap();
        }
        return tracker;
    }

    public void track(final String name, final Long count) {
        map.put(name, count);
    }

    public long getTrackNumber(String name) {
        if (map.get(name) != null) {
            return map.get(name);
        }
        return 0L;
    }

    public boolean persist() {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(getFilePath(), false);
            bufferedWriter = new BufferedWriter(writer);
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                bufferedWriter.write(entry.getKey() + ":" + entry.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            return true;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
            IOUtils.closeQuietly(writer);
        }
        return false;
    }

    private void initMap() {

        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(getFilePath()));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        for (final String line : lines) {
            String[] contents = StringUtils.split(line, ':');
            if (ArrayUtils.isNotEmpty(contents) && contents.length == 2) {
                map.put(contents[0], Long.valueOf(contents[1]));
            }
        }
    }

    public Map<String, Long> getMap() {
        return Collections.unmodifiableMap(map);
    }

    private String getFilePath() {
        String trackPath = SystemUtils.getSystemProperty("tracking.path", "");
        if (StringUtils.isNotEmpty(trackPath)) {
            final File file = new File(trackPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            return file.getAbsolutePath();
        }
        final URL file = this.getClass().getClassLoader().getResource(PATH);
        return file.getPath();
    }

}
