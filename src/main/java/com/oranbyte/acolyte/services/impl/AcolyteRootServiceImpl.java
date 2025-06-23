package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.AcolyteCommandRoot;
import com.oranbyte.acolyte.constants.AppConstants;
import com.oranbyte.acolyte.constants.ConsoleColor;
import com.oranbyte.acolyte.services.AcolyteRootService;
import com.oranbyte.acolyte.utils.ConsolePrinter;
import com.oranbyte.acolyte.utils.TemplateUtils;
import picocli.CommandLine;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AcolyteRootServiceImpl implements AcolyteRootService {

    @Override
    public void printBanner() {
        String content = TemplateUtils.loadTemplate("templates/banner.template", Collections.emptyMap());
        ConsolePrinter.success(content);
    }

    @Override
    public void printVersionString() {
        String versionString = ConsolePrinter.builder()
                .addText(AppConstants.APP_NAME, ConsoleColor.GREEN)
                .addText(" ", ConsoleColor.DEFAULT)
                .addText("Version", ConsoleColor.DEFAULT)
                .addText(" ", ConsoleColor.DEFAULT)
                .addText(AppConstants.APP_VERSION, ConsoleColor.CYAN)
                .addText(" \uD83C\uDF4A ", ConsoleColor.DEFAULT)
                .addText(AppConstants.APP_LAST_UPDATED_AT, ConsoleColor.PURPLE)
                .build();

        System.out.println(versionString);
    }

    @Override
    public void printCurrentCommandDetails(AcolyteCommandRoot root) {
        CommandLine cmd = new CommandLine(root);
        cmd.getHelpSectionMap().remove(CommandLine.Model.UsageMessageSpec.SECTION_KEY_COMMAND_LIST);
        cmd.usage(System.out, CommandLine.Help.Ansi.ON);
    }

    @Override
    public void printCommandList() {
        CommandLine root = new CommandLine(new AcolyteCommandRoot());
        String green = ConsoleColor.GREEN.code();
        String reset = ConsoleColor.DEFAULT.code();

        Set<CommandLine.Model.CommandSpec> printedSpecs = new HashSet<>();

        for (CommandLine sub : root.getSubcommands().values()) {
            CommandLine.Model.CommandSpec spec = sub.getCommandSpec();

            if (printedSpecs.add(spec)) {
                String mainName = spec.name();
                List<String> aliases = List.of(spec.aliases());

                String names = mainName + (aliases.isEmpty() ? "" : " (" + String.join(", ", aliases) + ")");
                String desc = String.join(" ", spec.usageMessage().description());

                System.out.printf("%s%-40s%s %s%n", green, names, reset, desc);
            }
        }
    }






}
