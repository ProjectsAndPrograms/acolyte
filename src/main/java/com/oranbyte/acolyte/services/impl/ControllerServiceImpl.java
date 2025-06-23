package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.ControllerService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ControllerServiceImpl implements ControllerService {

    public void generateController(String controllerName, String basePackage, List<String> resourceFunctions) {
        String parentFolder = null;

        if (controllerName.contains(".")) {
            controllerName = controllerName.substring(0, controllerName.lastIndexOf(".java")).replaceAll("\\.", "/");
        }

        if (controllerName.contains("/")) {
            parentFolder = controllerName.substring(0, controllerName.lastIndexOf("/")).toLowerCase();
            controllerName = controllerName.substring(controllerName.lastIndexOf("/") + 1);
        }

        String className = CaseConverter.toClassName(controllerName);
        className = CaseConverter.appendIfNotAvailable(className, "Controller");

        String controllerPackage = basePackage + ".controller" + (parentFolder == null ? "" : "." + parentFolder);
        String packagePath = controllerPackage.replaceAll("\\.", "/");
        String templatePath = "templates/controller.template";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", controllerPackage.replaceAll("/", "."));

        String classNameLowerCase = ((Objects.isNull(parentFolder) ? "" : (parentFolder + "/")) + controllerName).toLowerCase();

        String withoutJavaExtension = classNameLowerCase.replaceAll("\\.java$", "");

        String baseName = withoutJavaExtension.endsWith("controller")
                ? withoutJavaExtension.substring(0, withoutJavaExtension.lastIndexOf("controller"))
                : withoutJavaExtension;

        String endpointUrl = CaseConverter.toUrlString(baseName);
        System.out.println(endpointUrl + " " + baseName +" "+ withoutJavaExtension +" "+  classNameLowerCase);
        replacements.put("endpoint", endpointUrl);
        replacements.put("className", className);

        StringBuilder methodBuilder = new StringBuilder();

        if (resourceFunctions != null && !resourceFunctions.isEmpty()) {
            ConsolePrinter.info("Generating resource functions: " + String.join(", ", resourceFunctions));

            Set<String> functions = new HashSet<>();
            for (String func : resourceFunctions) {
                functions.add(func.toLowerCase());
            }

            for (String function : functions) {
                methodBuilder.append(generateFunction(function));
            }

            replacements.put("resourceFunctions", TemplateUtils.indent(methodBuilder.toString().trim(), 4));
        } else if (resourceFunctions != null) {
            methodBuilder.append(generateFunction("index"))
                    .append(generateFunction("create"))
                    .append(generateFunction("store"))
                    .append(generateFunction("show"))
                    .append(generateFunction("edit"))
                    .append(generateFunction("update"))
                    .append(generateFunction("destroy"));
            replacements.put("resourceFunctions", TemplateUtils.indent(methodBuilder.toString().trim(), 4));
        } else {
            ConsolePrinter.info("Generating simple controller (no resource functions).");
            replacements.put("resourceFunctions", "");
        }

        String content = TemplateUtils.loadTemplate(templatePath, replacements);

        if (content != null) {
            String filePath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packagePath + "/" + className + ".java";
            File file = new File(filePath);

            try {
                file.getParentFile().mkdirs();
                if (file.exists()) {
                    ConsolePrinter.error("File already exists: " + filePath);
                } else {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content);
                    }
                    ConsolePrinter.println("Controller created at: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                ConsolePrinter.error("Error creating controller: " + e.getMessage());
            }
        } else {
            ConsolePrinter.error("Error loading template or performing replacements.");
        }
    }

    private String generateFunction(String function) {
        return switch (function) {
            case "index" -> """
                @GetMapping
                public String index() {
                    return "index";
                }

                """;
            case "create" -> """
                @GetMapping("/create")
                public String create() {
                    return "create";
                }

                """;
            case "store" -> """
                @PostMapping
                public String store() {
                    return "store";
                }

                """;
            case "show" -> """
                @GetMapping("/{id}")
                public String show(@PathVariable Long id) {
                    return "show";
                }

                """;
            case "edit" -> """
                @GetMapping("/{id}/edit")
                public String edit(@PathVariable Long id) {
                    return "edit";
                }

                """;
            case "update" -> """
                @PutMapping("/{id}")
                public String update(@PathVariable Long id) {
                    return "update";
                }

                """;
            case "destroy" -> """
                @DeleteMapping("/{id}")
                public String destroy(@PathVariable Long id) {
                    return "destroy";
                }

                """;
            default -> "";
        };
    }

}
