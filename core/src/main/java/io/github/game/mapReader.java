package io.github.game;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class mapReader {
    public static int[][] readMap(String mapPath) {

        String data;
        FileHandle mapFile = Gdx.files.internal(mapPath);
        List<String[]> blueprintStrings = new ArrayList<>();

        try (Scanner reader = new Scanner(mapFile.reader())) {
            while (reader.hasNextLine()) {
                data = reader.nextLine();

                // Ignores comments in map file
                if (!(data.startsWith("//"))) {
                    data = data.replace(" ", "");
                    String[] row = data.split(",");
                    blueprintStrings.add(row);
                }
            }
        }
        catch (Exception e) {
            System.out.println("File could not be found.");
        }
        return toIntArray(blueprintStrings);
    }

    private static int[][] toIntArray(List<String[]> stringList) {
        int[][] intArray = new int[stringList.size()][];

        for (int i = 0; i < stringList.size(); i++) {
            String[] stringRow = stringList.get(i);
            int[] intRow = new int[stringRow.length];

            for (int j = 0; j < stringRow.length; j++) {
                intRow[j] = Integer.parseInt(stringRow[j]);
            }

            intArray[i] = intRow;
        }
        return intArray;
    }
}
