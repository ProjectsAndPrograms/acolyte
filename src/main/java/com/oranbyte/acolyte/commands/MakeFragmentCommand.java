package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.services.FragmentGeneratorService;
import com.oranbyte.acolyte.services.impl.FragmentGeneratorServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "make:fragment", description = "Create a simple fragment view file", aliases = {"make:component"})
public class MakeFragmentCommand implements Runnable{

    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the fragment view file")
    private String framentName;

    private final FragmentGeneratorService fragmentService = new FragmentGeneratorServiceImpl();

    @Override
    public void run() {
        fragmentService.generateFragment(framentName);
    }
}
