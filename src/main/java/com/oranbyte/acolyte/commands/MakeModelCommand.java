package com.oranbyte.acolyte.commands;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "make:model", description = "Create a model with resources")
public class MakeModelCommand implements Runnable{


    @CommandLine.ParentCommand
    private AcolyteCommandRoot root;

    @CommandLine.Parameters(index = "0", description = "Name of the model (e.g., User)")
    private String modelName;

    @CommandLine.Option(
            names = "--with-controller",
            description = "Generate a controller class for the entity"
    )
    private boolean withController;

    @CommandLine.Option(
            names = "--with-entity",
            description = "Generate a controller class for the model"
    )
    private boolean withEntity;

    @CommandLine.Option(
            names = "--with-dto",
            description = "Generate a dto class for the model"
    )
    private boolean withDto;

    @CommandLine.Option(
            names = "--with-repository",
            description = "Generate a repository class for the model"
    )
    private boolean withRepository;

    @CommandLine.Option(
            names = "--with-service",
            description = "Generate a service class and implementation for the model"
    )
    private boolean withService;


    @Override
    public void run() {

    }



}
