package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.services.ListenerGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.util.HashMap;
import java.util.Map;

public class ListenerGeneratorServiceImpl implements ListenerGeneratorService {

    private static final String LISTENER_TEMPLATE = "templates/listener.template";

    @Override
    public void generateListener(String listenerPath, String basePackage) {
        String classNameRaw = extractClassName(listenerPath);
        String subPath = extractSubPath(listenerPath);

        String className = CaseConverter.appendIfNotAvailable(CaseConverter.toClassName(classNameRaw), "Listener");

        String listenerPackage = basePackage + ".listener" + (subPath.isEmpty() ? "" : "." + subPath.replace("/", "."));
        String packagePath = listenerPackage.replace(".", "/");

        Map<String, String> replacements = new HashMap<>();
        replacements.put("packageName", listenerPackage);
        replacements.put("className", className);

        String fileName = className + ".java";
        TemplateUtils.createSourceFileFromTemplate(LISTENER_TEMPLATE, fileName, packagePath, replacements);
    }

    private String extractClassName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private String extractSubPath(String path) {
        int index = path.lastIndexOf('/');
        return index == -1 ? "" : path.substring(0, index);
    }
}
