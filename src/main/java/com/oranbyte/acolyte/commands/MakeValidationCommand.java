package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.ValidationGeneratorService;
import com.oranbyte.acolyte.services.impl.ValidationGeneratorServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:validator", description = "Create a custom validator and annotation")
public class MakeValidationCommand implements Runnable {

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Base name of the validator (e.g., ValidUserDto)")
    private String validatorName;

    @CommandLine.Option(names = "--dto", description = "DTO class the validator is for")
    private String dtoClass = "YourDto";

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();
        ValidationGeneratorService validationService = new ValidationGeneratorServiceImpl();
        validationService.generate(validatorName, dtoClass, basePackage);
    }
}
