package com.rater.application.helper;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    public static String loadAsString(final String path) {
        try {
            File file = new File(path);
            byte[] encoded = Files.readAllBytes(file.toPath());

            return new String(encoded, StandardCharsets.UTF_8);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
