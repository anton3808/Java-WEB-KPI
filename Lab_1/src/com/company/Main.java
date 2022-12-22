package com.company;


import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter directory: ");
        String dir = sc.nextLine();

        while (!new File(dir).isDirectory()) {
            System.out.println("Wrong directory! Please try again\n");
            System.out.print("Enter directory: ");
            dir = sc.nextLine();
        }

        System.out.println("!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!");

        ExecutorService pool = Executors.newCachedThreadPool();
        SearchFiles running = new SearchFiles(new File(dir), pool);
        Future<Integer> process = pool.submit(running);

        try {
            System.out.println(process.get() + " files processed.");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }
}

