package com.oranbyte.acolyte.utils;

public class CaseConverter {

    public static String toClassName(String input) {
        if(input == null || input.isEmpty()) return input;

        return CaseConverter.toPascalCase(input.replaceAll("[^a-zA-Z0-9_-]", ""));
    }

    public static String toUrlString(String input) {
        if (input == null) return "";

        return input
                .replaceAll("[^a-zA-Z0-9._/-]", "")
                .replaceAll("\\.", "/")
                .toLowerCase();
    }

    public static String toViewName(String input){
        if(input == null || input.isEmpty()) return input;

        return CaseConverter.toKebabCase(input.replaceAll("[^a-zA-Z0-9_-]", ""));
    }

    public static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) return input;

        String normalized = input.replaceAll("[-_]", " ");
        normalized = normalized.replaceAll("([a-z])([A-Z])", "$1 $2");

        StringBuilder pascalCase = new StringBuilder();
        for (String word : normalized.split("\\s+")) {
            if (!word.isEmpty()) {
                pascalCase.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    pascalCase.append(word.substring(1).toLowerCase());
                }
            }
        }

        return pascalCase.toString();
    }

    public static String toKebabCase(String input) {
        if (input == null || input.isEmpty()) return input;

        String normalized = input.replaceAll("_", "-");

        normalized = normalized.replaceAll("([a-z])([A-Z])", "$1-$2");
        normalized = normalized.replaceAll("([A-Z])([A-Z][a-z])", "$1-$2");
        return normalized.toLowerCase();
    }

    public static String appendIfNotAvailable(String source, String appendString) {
        if (source == null || appendString == null) {
            return source;
        }
        String trimmedSource = source.trim();
        if (!trimmedSource.endsWith(appendString) && !trimmedSource.equals(appendString)) {
            return source + appendString;
        }
        return source;
    }
    public static String ucFirst(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    public static String toFragmentFunctionName(String input) {
        return input.trim().replaceAll("[-\\s]", "_");
    }


}
