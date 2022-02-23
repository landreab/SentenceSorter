import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Project: Sentence Sorter
 * Author: Brennin Landrean
 * Purpose: The purpose of this project is to take an input of a text file, read the contents from the text file, and then sort all the sentences
 * from the file in Alphabetical order. 
 * */

public class SentenceSorter {

	public static void main(String[] args) {
		// Declaring variables for the filename input and output
		final String fileNameIn = "ShortStory.txt";
		final String fileNameOut = "ShortStoryOut.txt";

		// Creating a file reader class, and setting a string ArrayList to be the
		// sentences that are parsed from the file
		FileReader reader = new FileReader(fileNameIn);
		List<String> sentences = reader.getSentences();

		// Re-adds quotes that are removed during the parse
		sentences = quoteAdder(sentences);

		// Streams the arraylist in order to map to itself to add newlines to the end of
		// each sentence
		sentences = (ArrayList<String>) sentences.stream().map(s -> s.concat("\n")).collect(Collectors.toList());

		// Sort using a custom comparator that will ignore quotation marks during the
		// sorting, in order to provide accurate sorting
		Collections.sort(sentences, new Comparator<String>() {

			@Override
			public int compare(String arg0, String arg1) {

				return arg0.replaceAll("^\"+", "").compareTo(arg1.replaceAll("^\"+", ""));
			}

		});

		// Print the sorted sentences to a file
		fileWrite(fileNameOut, sentences);

	}

	// Method that will take in a filename and a string List, and write the string
	// list to the file
	public static void fileWrite(String name, List<String> lines) {
		try {
			FileWriter fileWriter = new FileWriter(name);
			for (String line : lines) {
				fileWriter.write(line);
			}
			fileWriter.close();
			System.out.println("Successfully wrote to the file.");

		}
		// Catch any errors that happen when writing the file
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// Method that fixes an invalid input from the file, as well as adds quotes that
	// are removed due to how splitting strings work.
	public static List<String> quoteAdder(List<String> lines) {

		for (int i = 0; i < lines.size(); i++) {
			// If the line starts with a quote, then it will either have a quote added or
			// not depending on conditions

			if (lines.get(i).startsWith("\"")) {
				if (lines.get(i).substring(1, 2).equals(" ")) {
					lines.set(i, lines.get(i).substring(2));
				} else {
					// Check if the quotation mark already exists twice in the sentence
					long quoteCounter = lines.get(i).chars().filter(ch -> ch != '"').count();
					if (quoteCounter >= 2) {
						continue;
					} else {
						lines.set(i, lines.get(i).concat("\""));
					}

				}
			}
		}
		//Return the arraylist that has added quotes
		return lines;
	}

}
