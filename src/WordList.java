import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WordList {
	public HashMap<String, ArrayList<String>> wordList = new HashMap<>();
	
	/* Constructor */
	public WordList() throws FileNotFoundException, IOException{
		for (File file : new File("wordlist/").listFiles()){
			String filename = file.getName();
			filename = filename.substring(0, filename.length() - 4);
			addWords(filename);
		}
		//System.out.println(this);
	}
	
	/* Updates "wordList" with new words from a text file */
	private void addWords(String filename) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader("wordlist/" + filename + ".txt"));	//can throw FileNotFoundException
		wordList.put(filename, new ArrayList<String>());
		ArrayList<String> currentList = wordList.get(filename);
		String word;
        while ((word = br.readLine()) != null) { //can throw IOException
	        currentList.add(word);
	    }
	    br.close();
	}
	
    /* Enables us to print WordList information with System.out.println() */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (String filename : wordList.keySet()){
			sb.append(filename + ": ");
			ArrayList<String> words = wordList.get(filename);
			for (String word : words){
				sb.append(word + " ");
			}
			sb.append(System.lineSeparator());
		}
	    return sb.toString();
	}
}
