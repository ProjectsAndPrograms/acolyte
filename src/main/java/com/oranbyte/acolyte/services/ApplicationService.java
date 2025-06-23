package com.oranbyte.acolyte.services;

import java.io.File;
import java.io.IOException;

public interface ApplicationService {

    String detectBuildTool(File projectDir) ;

    void runSpringBootProject(String buildTool, File projectDir) throws IOException;

}
