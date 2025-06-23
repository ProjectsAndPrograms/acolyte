package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.ServiceGenerationService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServiceGenerationServiceImpl implements ServiceGenerationService {

    @Override
    public void generateServiceInterface(String inputPath, String basePackage) {
        String serviceName = extractClassName(inputPath);
        String subPath = extractSubPath(inputPath);

        String packageName = basePackage + ".services" + (subPath.isEmpty() ? "" : "." + subPath.replace("/", "."));
        String packageDir = packageName.replace(".", "/");
        String interfaceName = CaseConverter.appendIfNotAvailable(CaseConverter.toPascalCase(serviceName), "Service");

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", packageName);
        replacements.put("interfaceName", interfaceName);

        String serviceContent = TemplateUtils.loadTemplate("templates/service.template", replacements);
        if (serviceContent == null) {
            ConsolePrinter.error("Error loading service interface template.");
            return;
        }

        File dir = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/java", packageDir);
        File file = new File(dir, interfaceName + ".java");
        writeToFile(file, serviceContent, "Service interface");
    }

    @Override
    public void generateServiceImplementation(String inputPath, String basePackage) {
        String serviceName = extractClassName(inputPath);
        String subPath = extractSubPath(inputPath);

        String interfaceName = CaseConverter.appendIfNotAvailable(CaseConverter.toPascalCase(serviceName), "Service");
        String implClassName = interfaceName + "Impl";

        String implPackage = basePackage + ".services.impl" + (subPath.isEmpty() ? "" : "." + subPath.replace("/", "."));
        String implPackageDir = implPackage.replace(".", "/");

        Map<String, String> replacements = new HashMap<>();

        String packageName = implPackage.replace(".impl", "");
        replacements.put("packageName", packageName);
        replacements.put("packageImplName", implPackage);
        replacements.put("interfaceName", interfaceName);
        replacements.put("interfaceNameImpl", interfaceName + "Impl");

        String implContent = TemplateUtils.loadTemplate("templates/service-impl.template", replacements);
        if (implContent == null) {
            ConsolePrinter.error("Error loading service implementation template.");
            return;
        }

        File dir = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/java", implPackageDir);
        File file = new File(dir, implClassName + ".java");

        writeToFile(file, implContent, "Service implementation");
    }

    private String extractClassName(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }

    private String extractSubPath(String fullPath) {
        return fullPath.contains("/") ? fullPath.substring(0, fullPath.lastIndexOf("/")) : "";
    }

    private void writeToFile(File file, String content, String label) {
        try {
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            if (file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(content);
                }
                ConsolePrinter.println(label + " created at: " + file.getPath());
            } else {
                ConsolePrinter.error(label + " already exists at: " + file.getPath());
            }
        } catch (IOException e) {
            ConsolePrinter.error("Error writing " + label.toLowerCase() + ": " + e.getMessage());
        }
    }
}
