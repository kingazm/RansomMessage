// KINGA ZMUDA - KEEPIT TECHNICAL ASSIGNMENT SOLUTION

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static HashMap<Character, Integer> countTheCharacters(String text) { //counts frequencies of each character in a string
        HashMap<Character, Integer> textCharCount = new HashMap<Character, Integer>(); //Every character will be counted in the hashmap - the information is stored with character as a key and the number of its appereances as value
        int textLength = text.length();

        for (int index = 0; index < textLength; index++) { //I check every character in the given string and increase its count accordingly

            char currentChar = text.charAt(index);

            if (textCharCount.containsKey(currentChar)) {
                textCharCount.put(currentChar, textCharCount.get(currentChar) + 1);
            }
            else {
                textCharCount.put(currentChar, 1); //if it is the character's first appereance I put its count as one
            }
        }

        return textCharCount;
    }

    //main function that determines whether the message from the messageFile is buildable from the magazineFile(s) given as next arguments
    //the arguments are the names of the files, i.e. "message.txt"
    public static boolean canCreateMessage(String messageFile, String...magazineFile) throws FileNotFoundException { //if files of given names where not found, the error is thrown

        //I open files one by one and read their contents, and convert those into string form, so that I can count the characters with the helper function countTheCharacters
        StringBuilder fileContent = new StringBuilder();
        File file = new File(messageFile);
        Scanner scanner = new Scanner(file);

        String desiredMessage = ""; //the contents of files will be converted to strings
        String availableMagazine = "";

        while (scanner.hasNext()) { //reading lines from file while there are contents to read
            String line = scanner.nextLine();
            fileContent.append(line); //building a string from file contents
        }

        desiredMessage = fileContent.toString().toLowerCase();
        fileContent.setLength(0);

        for (int i = 1; i < magazineFile.length; i++) { //reading lines from one or more magazine files just like above from the message file
            //index i starting from 1, as args[0] is a message, while magazines are the following args[1], args[2],...
            file = new File(magazineFile[i]);
            scanner.close();
            scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                fileContent.append(line);
            }
        }

        scanner.close();
        availableMagazine = fileContent.toString().toLowerCase(); //As described in RunningInstructions-Details.txt, letters for the message can be both upper and lower case, so I treat upper and lower cases of the same letter as the same character - in order to do that easily I make both strings into lower case before counting characters

        //I count the characters in a HashMap where key is a character and value is number of its appereances in a string
        HashMap<Character, Integer> messageCharCount = new HashMap<Character, Integer>();
        messageCharCount = countTheCharacters(desiredMessage);
        HashMap<Character, Integer> magazineCharCount = new HashMap<Character, Integer>();
        magazineCharCount = countTheCharacters(availableMagazine);

        for (Map.Entry<Character, Integer> entry : messageCharCount.entrySet()) { //I reach to the counted characters from the message in the HashMap
            char currentChar = entry.getKey();

            if (Character.isLetterOrDigit(currentChar)) { //I only take letters and digits into consideration when deciding whether the message is possible to build from the magazine
                if (entry.getValue() > magazineCharCount.getOrDefault(currentChar, 0)) { //if the number of appereances of a character in the magazine(s) is smaller than in the message, that means that I would need more cutouts of that letter than I have, so I know the message is not buildable with the magazine(s) I have
                    //if the characted did not appear at all in the magazine, its default number of appereances (value) is 0
                    return false;
                }
            }
        }

        return true; //If I checked above all the characters I need for my message and got to this point, that means that I have just the right amount or more of that character in my magazine(s), so the message is possible to build
    }

    //The driver function - I use main's arguments args[] to be able to run the function from the command line with any number of files (one message file and any number of magazine files) as input
    //Instructions on running are in the RunningInstructions-Details.txt file
    public static void main(String[] args) throws FileNotFoundException { //in args[] I provide names of the files described above

        /* I need to get the 2 or more file names given as arguments of the main function to solve the given problem
        so if none or not enough arguments are provided, the program cannot proceed to reading message and magazine(s), and perform its functionality. */

        if (args.length < 2) {
            System.out.println("Please provide two filenames as command-line arguments.");
            return; //program cannot be performed with one or more .txt files missing
        }

        //If enough arguments (file names) are provided, the program continues;
        System.out.println(canCreateMessage(args[0], args));

    }

}
