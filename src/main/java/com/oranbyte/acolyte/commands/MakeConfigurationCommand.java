package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.services.ConfigurationGeneratorService;
import com.oranbyte.acolyte.services.impl.ConfigurationGeneratorServiceImpl;
import com.oranbyte.acolyte.utils.CaseConverter;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CommandLine.Command(name = "make:configuration", description = "Create a simple configuration class", aliases = {"make:config"})
public class MakeConfigurationCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the configuration class")
    private String configName;
    private final ConfigurationGeneratorService generatorService = new ConfigurationGeneratorServiceImpl();

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();
        generatorService.generateConfiguration(configName, basePackage);
    }
}
