/*
 * CS2852 - 041
 * Spring 2016
 * Lab
 * Name: Ian Guswiler
 * Created: 4/26/2016
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Loads encoded files and operates on them using a given morse code tree then saves the decoded message to a file.
 *
 * @author Ian Guswiler
 * @version 5/2/16
 */
public class MorseDecoder {
    private static MorseTree tree = new MorseTree();

    private static void loadDecoder(File file){
        try(Scanner fileScan = new Scanner(file)){
            while(fileScan.hasNext()){
                tree.add(fileScan.next(), fileScan.next());
            }
        } catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Main functioning of the program. Utilizes scanners to receive system and file inputs
     * @param args Ignored
     */
    public static void main(String[] args) {
        loadDecoder(new File("morsecode.txt"));
        Scanner input = new Scanner(System.in);

        System.out.println("Enter an input filename:");
        String inputFileName = input.next();

        System.out.println("Enter an output filename:");
        String outputFileName = input.next();
        File output = new File(outputFileName);

        try(Scanner inputScan = new Scanner(new File(inputFileName))){
            Scanner lineScan;
            FileWriter writer = new FileWriter(output);

            while(inputScan.hasNextLine()){
                lineScan = new Scanner(inputScan.nextLine());
                while (lineScan.hasNext()) {
                    String nextIn = lineScan.next();
                    if(nextIn.equals("|")){
                        writer.write(" ");
                    } else {
                        try{
                            writer.write("" + tree.decode(nextIn));
                        } catch (IllegalArgumentException e){
                            System.err.println(e.getMessage());
                        }
                    }
                }
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e){
            System.err.println("The input file " + inputFileName + " could not be found");
        } catch (IOException e1){
            System.err.println(e1.getMessage());
        }
    }
}
