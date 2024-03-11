import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class KNN {
    private ArrayList<double[]> listOfRecords;
    private int k;

    public KNN(String filename, int k) {
        this.k = k;
        this.listOfRecords = loadFile(filename);
    }

    private ArrayList<double[]> loadFile(String filename) {
        ArrayList<double[]> list = new ArrayList<>();
        ArrayList<String> lines;
        try {
             lines = (ArrayList<String>) Files.readAllLines(Paths.get(filename));
            for(String line : lines ){
                String[] splittedLine = line.split(",");
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}