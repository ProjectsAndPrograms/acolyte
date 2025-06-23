package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.FragmentGeneratorService;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FragmentGeneratorServiceImpl implements FragmentGeneratorService {

    @Override
    public void generateFragment(String inputPath) {
        String fragmentFileName = extractFileName(inputPath);
        String subFolder = extractSubPath(inputPath);

        String viewName = CaseConverter.toViewName(fragmentFileName);
        String fullPath = (subFolder.isEmpty() ? "" : subFolder + "/") + viewName + ".html";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("fragmentName", fragmentFileName);

        String content = TemplateUtils.loadTemplate("templates/fragment.template", replacements);
        if (content == null) {
            ConsolePrinter.error("Error loading template or performing replacements.");
            return;
        }

        File dir = new File(AppConstants.PROJECT_DIRECTORY + "/src/main/resources/templates", subFolder);
        File file = new File(dir, viewName + ".html");

        writeToFile(file, content, "Fragment");

        String thymeleafUsage = String.format(
                "<th:block th:replace=\"~{/%s :: %s}\"></th:block>",
                fullPath.replace(".html", ""),
                CaseConverter.toFragmentFunctionName(fragmentFileName)
        );

        ConsolePrinter.println("You can use this fragment in Thymeleaf as:");
        ConsolePrinter.println(thymeleafUsage);
    }

    private String extractFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private String extractSubPath(String path) {
        int cut = path.lastIndexOf('/');
        return cut == -1 ? "" : path.substring(0, cut);
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
