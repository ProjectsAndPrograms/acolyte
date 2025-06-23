package com.oranbyte.acolyte.services.impl;

import com.oranbyte.acolyte.services.ApplicationService;
import com.oranbyte.acolyte.utils.ConsolePrinter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class ApplicationServiceImpl implements ApplicationService {
    @Override
    public String detectBuildTool(File projectDir) {
        if (new File(projectDir, "pom.xml").exists()) {
            return "maven";
        } else if (new File(projectDir, "build.gradle").exists() ||
                new File(projectDir, "build.gradle.kts").exists()) {
            return "gradle";
        } else {
            return null;
        }
    }

    @Override
    public void runSpringBootProject(String buildTool, File projectDir) throws IOException {
        ProcessBuilder processBuilder;

        ConsolePrinter.success("Starting Spring boot application...");
        switch (buildTool){
            case "maven":
                ConsolePrinter.success("Running Maven project...");
                processBuilder = new ProcessBuilder("mvn", "spring-boot:run");
                break;
            case "gradle":
                ConsolePrinter.success("Running Gradle project...");
                boolean isWindow = System.getProperty("os.name").toLowerCase().contains("win");
                File gradlew = new File(projectDir, isWindow ? "gradlew.bat" : "gradlew");

                if(gradlew.exists()){
                    String command = isWindow ? "gradlew.bat" : "./gradlew";
                    processBuilder = new ProcessBuilder(command, "bootRun");
                }else {
                    processBuilder = new ProcessBuilder("gradle", "bootRun");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknow build tool: " + buildTool);
        }

        System.out.println(projectDir);
        processBuilder.directory(projectDir);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        printProcessOutput(process);
    }

    void printProcessOutput(Process process) throws IOException{
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                ConsolePrinter.print(line);
            }
        }
    }

}
