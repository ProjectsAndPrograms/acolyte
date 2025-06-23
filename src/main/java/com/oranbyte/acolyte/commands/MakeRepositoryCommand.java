package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.RepositoryGeneratorService;
import com.oranbyte.acolyte.services.impl.RepositoryGeneratorServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:repository", description = "Create a simple repository class", aliases = {"make:repo"})
public class MakeRepositoryCommand implements Runnable {

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the entity")
    private String entityName;

    @CommandLine.Option(names = {"--id", "--id-type"}, description = "ID type of the entity (default: Long)", defaultValue = "Long")
    private String idType;

    private final RepositoryGeneratorService repositoryService = new RepositoryGeneratorServiceImpl();

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();
        repositoryService.generateRepository(entityName, basePackage, idType);
    }
}
