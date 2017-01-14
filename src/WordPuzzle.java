
import java.io.*;
import java.util.HashMap;

public class WordPuzzle {
	public int puzzleSize;
	public Character[] puzzle;	//we never use this. It's just here for completion, or future use
	public HashMap<String, Integer[]> smallWords = new HashMap<>();
	
	public WordPuzzle(String name) throws FileNotFoundException, IOException{
		BufferedReader br = new BufferedReader(new FileReader("puzzles/" + name + ".txt"));	//can throw FileNotFoundException
		puzzleSize = Integer.parseInt(br.readLine());
		puzzle = new Character[puzzleSize];
		String line;
        while ((line = br.readLine()) != null) { //can throw IOException
	        String[] words = line.split(":"); // works, but may have used "split" incorrectly. It is meant to take a "String regex" as parameter
	        String word = words[0];
	        
	        words = line.split(",");		  // works, but may have used "split" incorrectly. It is meant to take a "String regex" as parameter
	        String firstWord  = words[0];
	        String secondWord = words[1];
	        String thirdWord  = words[2];
	        
	        char xChar = firstWord.charAt(firstWord.length() - 1);
	        int x = Character.getNumericValue(xChar);
	        
	        char yChar = secondWord.charAt(secondWord.length() - 1);
	        int y = Character.getNumericValue(yChar);
	        
	        char zChar = thirdWord.charAt(thirdWord.length() - 1);
	        int z = Character.getNumericValue(zChar);
	        
	        /* Add smallWords */
	        smallWords.put(word, new Integer[3]);
	        Integer[] indices = smallWords.get(word);
	        indices[0] = x;
	        indices[1] = y;
	        indices[2] = z;
	    }
        br.close();
	}
	
    /* Enables us to print Puzzle information with System.out.println() */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("*** arraySize = " + puzzleSize + System.lineSeparator() + "*** array = ");
		for (int i = 0; i < puzzle.length; i++){
			sb.append(puzzle[i]);
		}
		sb.append(System.lineSeparator());
		
	    for (String key : smallWords.keySet()){
	    	sb.append(key + ": ");
	    	Integer[] values = smallWords.get(key);
	    	sb.append(values[0] + "  " + values[1] + "  " + values[2] + System.lineSeparator());
	    }
	    return sb.toString();
	}
}
