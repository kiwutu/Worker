package work;

import data.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Work implements Runnable {


    private final String fileName;
    private final Worker worker;

    public Work(String fileName) {
        this.fileName = fileName;
        worker = Worker.getInstance();
    }

    @Override
    public void run() {
        File file = new File(fileName);
        String readLine;
        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            while ((readLine = bufferedReader.readLine()) != null) {
                worker.addDataToWork(new Data(fileName, readLine));
                Thread.sleep((long) (Math.random() * 150 + 150));
            }
        } catch (Exception ignored) {
        }
    }
}
