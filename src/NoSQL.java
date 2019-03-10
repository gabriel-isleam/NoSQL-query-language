package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import static src.Functions.*;

/**
 * @author Gabriel-Mihai Isleam
 */
public class NoSQL {

    /**
     * @param args paramertii metodei main
     */
    public static void main(String[] args) {

        Scanner scanner = null;
        try {
            File input_file = new File(args[0]);
            scanner = new Scanner(input_file);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
            return;
        }

        PrintWriter writer = null;
        try {
            File output_file = new File(args[0] + "_out");
            writer = new PrintWriter(output_file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }

        String line = scanner.nextLine();
        String[] words = line.split(" ");
        int nodes_number = Integer.parseInt(words[2]);
        int max_capacity = Integer.parseInt(words[3]);
        ArrayList<ArrayList<Instance>> database = new ArrayList<>(nodes_number);
        for (int i = 0; i < nodes_number; i++) {
            database.add(new ArrayList<>(max_capacity));
        }

        ArrayList<Entity> entities = new ArrayList<>();
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            words = line.split(" ");
            if (words[0].equals("CREATE")) {
                create(entities, words);
            } else if (words[0].equals("INSERT")) {
                insert(database, entities, max_capacity, words);
            } else if (words[0].equals("SNAPSHOTDB")) {
                display_database(database, writer);
            } else if (words[0].equals("GET")) {
                get(database, words, writer);
            } else if (words[0].equals("UPDATE")) {
                update(database, words);
            } else if (words[0].equals("DELETE")) {
                delete(database, words[2], words[1], writer);
            } else if (words[0].equals("CLEANUP")) {
                cleanup(database, Long.parseLong(words[2]));
            }
        }
        writer.close();
    }
}
