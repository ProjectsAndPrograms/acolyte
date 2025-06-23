package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.DtoGeneratorService;
import com.oranbyte.acolyte.services.impl.DtoGeneratorServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:dto", description = "Create a simple dto or pojo class", aliases = {"make:pojo"})
public class MakeDtoCommand implements Runnable {

    @CommandLine.ParentCommand
    protected AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the dto class")
    protected String dtoName;

    private final DtoGeneratorService generatorService = new DtoGeneratorServiceImpl();

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();
        generatorService.generateDto(dtoName, basePackage);
    }
}
