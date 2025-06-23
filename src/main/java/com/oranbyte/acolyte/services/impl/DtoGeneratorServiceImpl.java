package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.DtoGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DtoGeneratorServiceImpl implements DtoGeneratorService {

    @Override
    public void generateDto(String inputPath, String basePackage) {
        String className = extractClassName(inputPath);
        String subPath   = extractSubPath(inputPath);

        className = CaseConverter.toPascalCase(className.replaceAll("[^a-zA-Z0-9_-]", ""));
        className = CaseConverter.appendIfNotAvailable(className, "Dto");

        String dtoPackage = (basePackage + ".dto" +
                (subPath.isEmpty() ? "" : "." + subPath.replace("/", "."))).toLowerCase();

        String packagePath = dtoPackage.replace('.', '/');
        String templatePath = "templates/dto.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", dtoPackage);
        replacements.put("className", className);
        System.out.println(className);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);
        if (content == null) {
            ConsolePrinter.error("Error loading template or performing replacements.");
            return;
        }

        File dir = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/java", packagePath);
        File file = new File(dir, className + ".java");

        writeToFile(file, content, "Dto");
    }

    private String extractClassName(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }

    private String extractSubPath(String fullPath) {
        int cut = fullPath.lastIndexOf('/');
        return cut == -1 ? "" : fullPath.substring(0, cut);
    }

    private void writeToFile(File file, String content, String label) {
        try {
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (file.exists()) {
                ConsolePrinter.error(label + " already exists at: " + file.getPath());
                return;
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

            ConsolePrinter.println(label + " created at: " + file.getPath());

        } catch (IOException e) {
            ConsolePrinter.error("Error writing " + label.toLowerCase() + ": " + e.getMessage());
        }
    }
}
