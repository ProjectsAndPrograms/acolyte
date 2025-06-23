package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.RepositoryGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RepositoryGeneratorServiceImpl implements RepositoryGeneratorService {

    @Override
    public void generateRepository(String inputPath, String basePackage, String idType) {
        String className = extractClassName(inputPath);
        String subPath   = extractSubPath(inputPath);

        className = CaseConverter.toClassName(className.replaceAll("[^a-zA-Z0-9_-]", ""));

        String repositoryClassName = CaseConverter.appendIfNotAvailable(className, "Repository");
        String entityName = className; // this is the actual entity name, not the repo class

        String repositoryPackage = basePackage + ".repository" +
                (subPath.isEmpty() ? "" : "." + subPath.replace("/", "."));

        String entityPackage = basePackage + ".entity" +
                (subPath.isEmpty() ? "" : "." + subPath.replace("/", "."));

        String packagePath = repositoryPackage.replace('.', '/');
        String templatePath = "templates/repository.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", repositoryPackage);
        replacements.put("repositoryClassName", repositoryClassName);
        replacements.put("className", entityName);
        replacements.put("idType", idType);
        replacements.put("entityPackage", entityPackage);

        String content = TemplateUtils.loadTemplate(templatePath, replacements);
        if (content == null) {
            ConsolePrinter.error("Error loading template or performing replacements.");
            return;
        }

        File dir = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/java", packagePath);
        File file = new File(dir, repositoryClassName + ".java");

        writeToFile(file, content, "Repository");
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
