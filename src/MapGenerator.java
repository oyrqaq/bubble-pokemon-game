import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MapGenerator {
    private ArrayList<ArrayList<Integer>> levelInfo = new ArrayList<>();
    private FileReader file;
    
    public MapGenerator(int id) {
        this.file = updateFile(id);
        updateLevelInfo();
    }

    public FileReader updateFile(int id) {
        try {
            FileReader file = new FileReader("MapLevels/level"+id);
            return file;
        } catch (FileNotFoundException e) {
            System.out.println("File level"+id+".txt not found");
        }
        return null;
    }

    public void updateLevelInfo() {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            ArrayList<Integer> inner = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                try {
                    inner.add(Integer.parseInt("" + line.charAt(i)));
                } catch (NumberFormatException e) {
                }
            }
            levelInfo.add(inner);
        }
        sc.close();
    }

    public ArrayList<ArrayList<Integer>> getLevelInfo() {
        return this.levelInfo;
    }
}