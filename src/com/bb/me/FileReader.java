package com.bb.me;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class FileReader {

    public static String getString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
