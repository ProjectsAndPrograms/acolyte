package com.oranbyte.acolyte.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ProjectMetadataUtil {

    public static String getBasePackage(String projectDir) {
        try {
            File pomFile = new File(projectDir + "/pom.xml");
            var doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pomFile);
            doc.getDocumentElement().normalize();

            String groupId = doc.getElementsByTagName("groupId").item(0).getTextContent();
            String artifactId = doc.getElementsByTagName("artifactId").item(0).getTextContent();

            String packageName = groupId + "." + artifactId;

            return packageName.toLowerCase();
        } catch (Exception e) {
            System.err.println("Failed to read base package from pom.xml: " + e.getMessage());
            return "com.example.demo";
        }
    }
}
