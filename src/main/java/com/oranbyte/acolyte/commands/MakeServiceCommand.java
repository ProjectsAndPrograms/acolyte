package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.ServiceGenerationService;
import com.oranbyte.acolyte.services.impl.ServiceGenerationServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:service", description = "Create a new service with implementation class")
public class MakeServiceCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of Service Interface")
    private String serviceName;

    private final ServiceGenerationService serviceGenerationService = new ServiceGenerationServiceImpl();

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();

        serviceGenerationService.generateServiceInterface(serviceName, basePackage);
        serviceGenerationService.generateServiceImplementation(serviceName, basePackage);
    }
}
