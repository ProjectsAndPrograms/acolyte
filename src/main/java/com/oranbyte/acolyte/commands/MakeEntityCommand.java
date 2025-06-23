package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.EntityGeneratorService;
import com.oranbyte.acolyte.services.impl.EntityGeneratorServiceImpl;
import picocli.CommandLine;


@CommandLine.Command(name = "make:entity", description = "Create a simple entity class")
public class MakeEntityCommand implements Runnable {

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the entity")
    private String entityName;

    private final EntityGeneratorService entityService = new EntityGeneratorServiceImpl();

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();
        entityService.generateEntity(entityName, basePackage);
    }
}
