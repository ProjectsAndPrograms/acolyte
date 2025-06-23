package com.oranbyte.acolyte;

import com.oranbyte.acolyte.commands.*;
import com.oranbyte.acolyte.constants.AcolyteContext;
import com.oranbyte.acolyte.services.AcolyteRootService;
import com.oranbyte.acolyte.services.impl.AcolyteRootServiceImpl;
import com.oranbyte.acolyte.utils.ProjectMetadataUtil;
import picocli.CommandLine;

@CommandLine.Command(
        name = "acolyte",
        mixinStandardHelpOptions = true,
        subcommands = {
//                ServeCommand.class,
                MakeControllerCommand.class,
                MakeServiceCommand.class,
                MakeEntityCommand.class,
                MakeViewCommand.class,
                MakeDtoCommand.class,
                MakeConfigurationCommand.class,
                MakeRepositoryCommand.class,
                MakeValidationCommand.class,
                MakeListenerCommand.class,
                MakeFragmentCommand.class
        }
)
public class AcolyteCommandRoot implements Runnable {
    private AcolyteContext context;

    @CommandLine.Option(names = {"-v", "--version", "-version", "-V"}, description = "Get the version application")
    private boolean version;

    public AcolyteCommandRoot() {
        String basePackage = ProjectMetadataUtil.getBasePackage(System.getProperty("user.dir"));
        this.context = new AcolyteContext(basePackage);
    }

    public AcolyteContext getContext() {
        return context;
    }
    public void setContext(AcolyteContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        AcolyteRootService acolyteRootService = new AcolyteRootServiceImpl();

        if(version){
            acolyteRootService.printVersionString();
            return;
        }

        acolyteRootService.printBanner();
        acolyteRootService.printVersionString();

        System.out.println();
        acolyteRootService.printCurrentCommandDetails(this);
        acolyteRootService.printCommandList();

    }
}
