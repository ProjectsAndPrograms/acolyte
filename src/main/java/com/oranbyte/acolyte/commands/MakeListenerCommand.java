package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.ListenerGeneratorService;
import com.oranbyte.acolyte.services.impl.ListenerGeneratorServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:listener", description = "Create a simple entity listener class")
public class MakeListenerCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the listener")
    private String listenerName;

    private ListenerGeneratorService service = new ListenerGeneratorServiceImpl();

    @Override
    public void run() {
        String basePackage = root.getContext().getBasePackage();
        service.generateListener(listenerName, basePackage);

    }
}
