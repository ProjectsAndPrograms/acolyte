package com.oranbyte.acolyte.services;

public interface ServiceGenerationService {
    void generateServiceInterface(String serviceName, String basePackage);
    void generateServiceImplementation(String serviceName, String basePackage);
}
