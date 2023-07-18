package io.aharoj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    public static void main(String[] args) {
        String filePath = "/Users/aharo/desk/research/mutations_loc/src/main/java/io/aharoj/Lala.java";
        List<String> lines;
        try {
            lines = readFile(filePath);
            Map<String, Integer> functionLineCount = getFunctionLineCount(lines);
            for (Map.Entry<String, Integer> entry : functionLineCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " lines");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR NOT FOUND ");
        }
    }



    private static List<String> readFile(String filePath) throws Exception {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (Exception e) {
            throw new Exception("File not found");
        }
        return lines;
    }


    private static Map<String, Integer> getFunctionLineCount(List<String> lines) {
        Map<String, Integer> functionLineCount = new HashMap<>();
        String patternString = "\\s*\\b(public|private|protected)?\\s*\\b(static)?\\s*\\b(void|\\w+)\\s+(\\w+)\\s*\\(.*\\)\\s*\\{";
        Pattern pattern = Pattern.compile(patternString);
        boolean isFunctionBlock = false;
        String currentFunctionName = null;
        int currentFunctionLineCount = 0;
        for (String line : lines) {
            if (isFunctionBlock) {
                currentFunctionLineCount++;
            }
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                if (isFunctionBlock) {
                    functionLineCount.put(currentFunctionName, currentFunctionLineCount);
                }
                isFunctionBlock = true;
                currentFunctionName = matcher.group(4);
                currentFunctionLineCount = 0;
            }
            if (line.contains("}") && isFunctionBlock) {
                isFunctionBlock = false;
                functionLineCount.put(currentFunctionName, currentFunctionLineCount);
                currentFunctionLineCount = 0;
            }
        }
        return functionLineCount;
    }

}