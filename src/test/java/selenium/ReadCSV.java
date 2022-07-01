package selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    public static void main(String... args) {
        List<NamesDto> names = readNamesFromCSV("src/main/resources/names.csv");

        // let's print all the person read from CSV file
        for (NamesDto b : names) {
            System.out.println(b);
        }
    }

    public static ArrayList<NamesDto> readNamesFromCSV(String fileName) {
        ArrayList<NamesDto> names = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the csv file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                NamesDto name = createBook(attributes);

                // adding name into ArrayList
                names.add(name);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return names;
    }

    private static NamesDto createBook(String[] metadata) {
        String id = metadata[0];
        String description = metadata[1];
        String name = metadata[2];
        String surname = metadata[3];

        // create and return names of this metadata
        return new NamesDto(id,description,name,surname);
    }
}
