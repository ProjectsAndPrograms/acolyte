package com.oranbyte.acolyte.commands;

import picocli.CommandLine;

@CommandLine.Command(name = "serve", description = "Serve spring boot application.")
public class ServeCommand implements Runnable{

    @CommandLine.Option(names={"--port"}, description = "Dedicated port to run spring boot application")
    private int port = 8080;

    @Override
    public void run() {
        System.out.println(port);
    }
}
