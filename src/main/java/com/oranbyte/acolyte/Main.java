package com.oranbyte.acolyte;

import picocli.CommandLine;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
       int exitCode = new CommandLine(new AcolyteCommandRoot()).execute(args);
       System.exit(exitCode);

//        ----------------------- BEGIN- testing ---------------------------
//        System.out.println(CaseConverter.toPascalCase("sElse"));
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter command : ");
//        String command = sc.nextLine();
//
//        int exitCode = new CommandLine(new AcolyteCommandRoot()).execute(command.split(" "));
//        System.exit(exitCode);
//        ----------------------- END- testing ---------------------------

//      ------------------- BEGIN - SERVE FEATURE - STILL NOT READY WORKING ON IT -----------------------

//        File file = new File("C:\\Users\\Shubham\\Documents\\GitHub\\Html-CkEditor-spring-boot");
//        ApplicationService service = new ApplicationServiceImpl();
//
//        String tool = service.detectBuildTool(file);
//        if(tool == null){
//            ConsolePrinter.error("No recognizable build tool found (Maven or Gradle).");
//            return;
//        }
//        try{
//
//            service.runSpringBootProject(tool, file);
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//      ------------------- END - SERVE FEATURE - STILL NOT READY WORKING ON IT -----------------------

    }
}
