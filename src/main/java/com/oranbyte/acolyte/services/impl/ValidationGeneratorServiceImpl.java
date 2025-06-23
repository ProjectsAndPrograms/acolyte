package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.DtoGeneratorService;
import com.oranbyte.acolyte.services.ValidationGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ValidationGeneratorServiceImpl implements ValidationGeneratorService {

    private static final String VALIDATOR_TEMPLATE = "templates/validator-impl.template";
    private static final String ANNOTATION_TEMPLATE = "templates/validator-annotation.template";

    @Override
    public void generate(String validatorPath, String dtoPath, String basePackage) {

        String validatorClassNameRaw = extractClassName(validatorPath);
        String validatorSubPath = extractSubPath(validatorPath);

        String dtoClassNameRaw = extractClassName(dtoPath);
        String dtoSubPath = extractSubPath(dtoPath);

        String validatorBaseName = sanitizeValidatorBaseName(validatorClassNameRaw);
        String annotationName = "Valid" + validatorBaseName;
        String validatorClassName = validatorBaseName + "Validator";

        String dtoClassName = CaseConverter.toPascalCase(dtoClassNameRaw.replaceAll("[^a-zA-Z0-9_/-]", ""));
        dtoClassName = CaseConverter.appendIfNotAvailable(dtoClassName, "Dto");

        String dtoPackage = (basePackage + ".dto" +
                (dtoSubPath.isEmpty() ? "" : "." + dtoSubPath.replace("/", "."))).toLowerCase();

        String validatorPackage = (basePackage + ".validation" +
                (validatorSubPath.isEmpty() ? "" : "." + validatorSubPath.replace("/", "."))).toLowerCase();

        String validatorImplPackage = validatorPackage + ".impl";
        String dtoFullClassName = dtoPackage + "." + dtoClassName;

        System.out.println(dtoSubPath +"  "+ dtoClassNameRaw +"  "+ dtoPackage +"  "+ dtoFullClassName);
        ensureDtoExists(dtoFullClassName, dtoPath, basePackage);
        ensureDirectoryExists(validatorPackage);
        ensureDirectoryExists(validatorImplPackage);

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", validatorPackage);
        replacements.put("annotationName", annotationName);
        replacements.put("validatorClassName", validatorClassName);
        replacements.put("dtoClass", dtoClassName);
        replacements.put("dtoFullClassName", dtoFullClassName);

        TemplateUtils.createSourceFileFromTemplate(
                ANNOTATION_TEMPLATE,
                annotationName + ".java",
                validatorPackage,
                replacements
        );

        TemplateUtils.createSourceFileFromTemplate(
                VALIDATOR_TEMPLATE,
                validatorClassName + ".java",
                validatorImplPackage,
                replacements
        );
    }

    private String sanitizeValidatorBaseName(String name) {
        String className = extractClassName(name);
        className = CaseConverter.toClassName(className.replaceAll("[^a-zA-Z0-9_-]", ""));
        if (className.endsWith("Validator")) {
            className = className.substring(0, className.length() - "Validator".length());
        }
        if (className.startsWith("Valid")) {
            className = className.substring("Valid".length());
        }
        return className;
    }


    private String extractClassName(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }

    private String extractSubPath(String fullPath) {
        int index = fullPath.lastIndexOf('/');
        return index == -1 ? "" : fullPath.substring(0, index);
    }

    private void ensureDirectoryExists(String packageName) {
        String dirPath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + packageName.replace('.', '/');
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();

        }
    }

    private void ensureDtoExists(String fullClassName, String dtoPath, String basePackage) {
        String dtoFilePath = AppConstants.PROJECT_DIRECTORY + "/src/main/java/" + fullClassName.replace('.', '/') + ".java";
        File dtoFile = new File(dtoFilePath);

        if (!dtoFile.exists()) {
            ConsolePrinter.info("DTO not found. Generating: " + fullClassName);
            DtoGeneratorService dtoGeneratorService = new DtoGeneratorServiceImpl();

            System.out.println(dtoPath);
            dtoGeneratorService.generateDto(dtoPath, basePackage);
        }
    }

}
