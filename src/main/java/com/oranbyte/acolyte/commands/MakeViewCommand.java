package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.ViewGeneratorService;
import com.oranbyte.acolyte.services.impl.ViewGeneratorServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:view", description = "Create a simple view file")
public class MakeViewCommand implements Runnable{


    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the view file")
    private String viewName;

    private final ViewGeneratorService viewService = new ViewGeneratorServiceImpl();

    @Override
    public void run() {
        viewService.generateView(viewName);
    }

}
