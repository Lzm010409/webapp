package de.lukegoll.application.logWriter;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class LogWriter {
    String logPath = "src/main/resources/log";
    AtomicBoolean open = new AtomicBoolean(true);

    public boolean writeLog(List<String> list) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logPath + "log.txt"));
        if (open.compareAndSet(true, false)) {
            for (int i = 0; i < list.size(); i++) {
                bufferedWriter.write(list.get(i));
            }
            bufferedWriter.close();
            open.set(true);
            return true;
        } else {
            return false;
        }

    }


    public List<String> readLog(File file) {
        List<String> string = new LinkedList<>();
        try {
            if (open.compareAndSet(true, false)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String zeile;
                while ((zeile = bufferedReader.readLine()) != null) {
                    string.add(zeile);
                }
            } else {
                throw new IllegalStateException("File wird gerade beschrieben!");
            }
        } catch (IOException e) {

        } finally {
            open.set(true);
        }
        return string;
    }
}
