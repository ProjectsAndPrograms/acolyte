package com.oranbyte.acolyte.utils;

import com.oranbyte.acolyte.constants.AppConstants;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateUtils {

    public static String loadTemplate(String templatePath, Map<String, String> replacements) {
        try (InputStream inputStream = TemplateUtils.class.getClassLoader().getResourceAsStream(templatePath)) {
            if (inputStream == null) {
                throw new RuntimeException("Template file not found in the classpath: " + templatePath);
            }

            String template = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return template;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String indent(String code, int spaces) {
        String indent = " ".repeat(spaces);
        return Arrays.stream(code.split("\n"))
                .map(line -> indent + line)
                .collect(Collectors.joining("\n"));
    }

    public static void createSourceFileFromTemplate(String templatePath, String fileName, String packagePath, Map<String, String> replacements) {
        String content = TemplateUtils.loadTemplate(templatePath, replacements);
        if (content == null) {
            ConsolePrinter.error("Template not found or failed to load: " + templatePath);
            return;
        }

        String dirPath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath.replace(".", "/");
        File outputFile = new File(dirPath, fileName);

        try {
            outputFile.getParentFile().mkdirs();
            if (outputFile.exists()) {
                ConsolePrinter.error("File already exists: " + outputFile.getPath());
                return;
            }

            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(content);
            }

            ConsolePrinter.println("Created: " + outputFile.getPath());
        } catch (IOException e) {
            ConsolePrinter.error("Error writing file: " + e.getMessage());
        }
    }

}
