package com.oranbyte.acolyte.services;

import java.util.List;

public interface ControllerService {

    void generateController(String controllerName, String basePackage, List<String> resourceFunctions);

}
