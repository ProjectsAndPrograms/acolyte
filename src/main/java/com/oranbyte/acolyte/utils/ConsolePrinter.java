package com.oranbyte.acolyte.utils;

import com.oranbyte.acolyte.constants.ConsoleColor;
import com.oranbyte.acolyte.records.TextSegment;

import java.util.*;

public class ConsolePrinter {

    private final List<TextSegment> segments = new ArrayList<>();

    public static void print(String message){
        System.out.print(message);
    }
    public static void println(String message){
        System.out.println(message);
    }
    public static void print(String message,ConsoleColor color){
        System.out.print(color + message + color.code());
    }
    public static void println(String message, ConsoleColor color){
        System.out.println(color + message + ConsoleColor.RESET.code());
    }
    public static void error(String message){
        System.out.println(ConsoleColor.RED.code() + message + ConsoleColor.RESET.code());
    }
    public static void danger(String message){
        System.out.println(ConsoleColor.RED.code() + message + ConsoleColor.RESET.code());
    }
    public static void success(String message){
        System.out.println(ConsoleColor.GREEN.code() + message + ConsoleColor.RESET.code());
    }
    public static void info(String message){
        System.out.println(ConsoleColor.CYAN.code() + message + ConsoleColor.RESET.code());
    }
    public static void warning(String message){
        System.out.println(ConsoleColor.YELLOW.code() + message + ConsoleColor.RESET.code());
    }
    public static void primary(String message){
        System.out.println(ConsoleColor.BLUE.code() + message + ConsoleColor.RESET.code());
    }
    public static void secondary(String message){
        System.out.println(ConsoleColor.PURPLE.code() + message + ConsoleColor.RESET.code());
    }

    public static ConsolePrinter builder() {
        return new ConsolePrinter();
    }

    public ConsolePrinter addText(String text, ConsoleColor color) {
        segments.add(new TextSegment(text, color));
        return this;
    }

    public String build() {
        StringBuilder finalString = new StringBuilder();
        for (TextSegment segment : segments) {
            finalString.append(segment.color().code())
                    .append(segment.text())
                    .append(ConsoleColor.RESET.code());
        }
        return finalString.toString();
    }


}
