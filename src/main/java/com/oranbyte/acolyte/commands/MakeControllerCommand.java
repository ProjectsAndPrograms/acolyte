package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.ControllerService;
import com.oranbyte.acolyte.services.impl.ControllerServiceImpl;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "make:controller", description = "Create a new simple controller")
public class MakeControllerCommand implements Runnable {

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the controller")
    private String controllerName;

    @CommandLine.Option(
            names = "--resource",
            arity = "0..*",
            description = "Specify resource functions to generate (e.g. index, show, create, store, edit, update, destroy)"
    )
    private List<String> resourceFunctions;

    @Override
    public void run() {
        ControllerService service = new ControllerServiceImpl();
        service.generateController(controllerName, root.getContext().getBasePackage(), resourceFunctions);
    }

}
