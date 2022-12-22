package com.company;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SearchFiles implements Callable<Integer> {
    private final File startDir;
    private final ExecutorService pool;

    public SearchFiles(File startDir, ExecutorService pool) {
        this.startDir = startDir;
        this.pool = pool;
    }

    @Override
    public Integer call() {
        int counter = 0;

        try {
            File[] files = startDir.listFiles();
            List<Future<Integer>> result = new ArrayList<>();

            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        SearchFiles process = new SearchFiles(f, pool);
                        Future<Integer> res = pool.submit(process);
                        result.add(res);
                    } else if (f.getName().endsWith(".txt")) {
                        new ProcessFile().process(f);
                        counter++;
                    }
                }
            }

            for (Future<Integer> res : result) {
                counter += res.get();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return counter;
    }
}
