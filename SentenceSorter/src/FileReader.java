import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 * Class: FileReader
 * Author: Brennin Landrean
 * Purpose: The purpose of this class is to take in a filename, read the file, and distribute the file into required sentences
 * */
public class FileReader {

	// Delcaring the variables for filename and the String list that will be used to
	// store the sentences
	String fileName;
	ArrayList<String> sentences = new ArrayList<String>();

	// Constructor for the file reader class
	public FileReader(String file) {
		setFile(file);

	}

	// Getters and Setters
	private void setFile(String file) {
		this.fileName = file;

	}

	public String getFile() {
		return this.fileName;

	}

	// Method that will go through the file and split it into readable sentences (as
	// well as modify some strings to make the output more readable/sortable
	public List<String> getSentences() {
		// Read the file and scan it
		try {
			File readFile = new File(getFile());
			Scanner reader = new Scanner(readFile);
			while (reader.hasNextLine()) {
				// As long as there is still text in the file to be read, read the line, add it
				// to an arraylist, splitting everytime a period is read to denote a sentence
				String line = reader.nextLine();
				sentences.addAll(Arrays.asList(line.split("[\\.]")));

			}
			// Close the filereader
			reader.close();

		}

		catch (FileNotFoundException e) {
			System.out.println("The file was not found");
			e.printStackTrace();
		}

		// Remove certain special characters that cause issues with
		// readability/sortability/cause issues with the splitter
		// Removed the breaks between the stories in order to allow for a truly
		// alphabetical sort
		// Potential changes I would make if I re-did this project would be to allow the
		// user to choose whether to keep the story breaks or to remove them
		sentences = (ArrayList<String>) sentences.stream().map(s -> s.trim().replaceAll("[()-]", ""))
				.collect(Collectors.toList());
		sentences.removeIf(s -> s.startsWith("'"));
		sentences.removeIf(s -> s.startsWith("\"") & s.length() == 1);
		sentences.removeIf(s -> s.isEmpty());
		
		//Re-add the periods that are removed when the split function is used
		sentences = (ArrayList<String>) sentences.stream().map(s -> s.concat(".")).collect(Collectors.toList());

		return sentences;

	}

}
