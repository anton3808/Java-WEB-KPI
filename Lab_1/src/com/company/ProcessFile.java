package com.company;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class ProcessFile {
    protected final ArrayList<String> fileContext = new ArrayList<>();

    public void process(File file) {
        try (Scanner sc = new Scanner(Files.newInputStream(file.toPath()))) {
            while (sc.hasNextLine()) {
                String[] strings = sc.nextLine().split(" ");

                try {
                    String tempWord = strings[0];
                    strings[0] = strings[strings.length - 1];
                    strings[strings.length - 1] = tempWord;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Empty string");
                }

                fileContext.add(String.join(" ", strings));
            }
            changeWords(fileContext, file);

            System.out.println("File name: " + file.getPath());
        } catch (AccessDeniedException e) {
            System.out.println("Access denied!");
        } catch (IOException e) {
            System.out.println("Something wrong with IO!");
        }
    }

    private void changeWords(ArrayList<String> text, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String s : text) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with IO!");
        }
    }
}

