package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DataWriter {
    public static void writeFile(String filePath, List<String> data) throws IOException {
        Files.write(Path.of(filePath), data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}