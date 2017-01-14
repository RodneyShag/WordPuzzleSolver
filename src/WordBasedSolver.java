import java.util.ArrayList;

public class WordBasedSolver {
	
	ArrayList<String> path = new ArrayList<>();
	
	/* Wrapper */
	public ArrayList<Character[]> findSolution(WordPuzzle wordPuzzle, WordList wordList){
		System.out.print("Search Order: ");
		for (String word : wordPuzzle.smallWords.keySet()){
			System.out.print(word + " -> ");
		}
		/* Create and initialize assignment */
		Character[] assignment = new Character[wordPuzzle.puzzleSize];
		for (int i = 0; i < assignment.length; i++){
			assignment[i] = '0';
		}
		
		ArrayList<Character[]> solutions = new ArrayList<Character[]>();
		findSolutionRecursive(wordPuzzle, wordList, assignment, solutions);
		return solutions;
	}
	
	/* Recursive solver: Algorithm mainly from Lecture 7, Slide 26 */
	public void findSolutionRecursive(WordPuzzle wordPuzzle, WordList wordList, Character[] assignment, ArrayList<Character[]> solutions){
		if (allVariablesAssigned(assignment) && assignmentValid(wordPuzzle, wordList, assignment)){
			solutions.add((Character[]) assignment.clone());
			printTrace(wordPuzzle, wordList, assignment, path);
		}
		String wordKey = selectUnassignedWord(wordPuzzle, assignment);
		if (wordKey == null) // all characters in "assignment" are assigned a value
			return;

		for (String word : getValues(wordList, wordKey)){
			if (wordWorks(wordPuzzle, assignment, wordKey, word)){
				ArrayList<Integer> spotsWrittenTo = writeWord(wordPuzzle, assignment, wordKey, word);
				path.add(word);
				//printTrace(wordPuzzle, wordList, assignment, path); //this was here to have output match what they want
				if (assignmentValid(wordPuzzle, wordList, assignment))
					findSolutionRecursive(wordPuzzle, wordList, assignment, solutions);
				//System.out.print("backtrack "); //this was here to have output match what they want
				/* This erases what was just written */
				for (Integer spot : spotsWrittenTo){
					assignment[spot] = '0';
				}
				path.remove(word);
				
			}
		}
	}
	
	public void printTrace(WordPuzzle wordPuzzle, WordList wordList, Character[] assignment, ArrayList<String> path){
		System.out.print("\nroot -> ");
		for (String word : path){
			System.out.print(word + " -> ");
		}
		if (allVariablesAssigned(assignment) && assignmentValid(wordPuzzle, wordList, assignment)){
			System.out.print("(found result: ");
			print(assignment);
			System.out.print(")");
		}
	}
	
	/* First check if "word" fits with rest of "assignment" */
	public boolean wordWorks(WordPuzzle wordPuzzle, Character[] assignment, String wordKey, String word){
		Integer[] indices = wordPuzzle.smallWords.get(wordKey);
		for (int i = 0; i < 3; i++){
			if (assignment[indices[i] - 1] != '0' && assignment[indices[i] - 1] != word.charAt(i))
				return false;
		}
		return true;
	}
	
	/* Writes just the necessary letters of a "word" to the "assignment" */
	public ArrayList<Integer> writeWord(WordPuzzle wordPuzzle, Character[] assignment, String wordKey, String word){
		Integer[] indices = wordPuzzle.smallWords.get(wordKey);
		ArrayList<Integer> spotsWrittenTo = new ArrayList<>();
		/* Actually write "word" to "assignment" */
		for (int i = 0; i < 3; i++){
			if (assignment[indices[i] - 1] == '0')
				spotsWrittenTo.add(indices[i] - 1);
			assignment[indices[i] - 1] = word.charAt(i); // -1 since input counts starting from 1
		}
		return spotsWrittenTo;
	}
	
	/* Checks if a partial or complete assignment is valid thus far */
	public boolean assignmentValid(WordPuzzle wordPuzzle, WordList wordList, Character[] assignment){
		for (String wordSet : wordPuzzle.smallWords.keySet()){
			Integer[] indices = wordPuzzle.smallWords.get(wordSet);
			String indexedWord = createWord(assignment, indices);
			if (indexedWord != null){
				ArrayList<String> validWords = wordList.wordList.get(wordSet);
				if (!validWords.contains(indexedWord))
					return false;
			}
		}
		return true;
	}
	
	/* (unassigned denoted as '0') */
	public boolean allVariablesAssigned(Character[] assignment){
		for (int i = 0; i < assignment.length; i++){
			if (assignment[i] == '0')
				return false;
		}
		return true;
	}
	
	/* Selects 1st unassigned word */
	public String selectUnassignedWord(WordPuzzle wordPuzzle, Character[] assignment){
		for (String word : wordPuzzle.smallWords.keySet()){
			Integer[] indices = wordPuzzle.smallWords.get(word);
			if (createWord(assignment, indices) == null)
				return word;
		}
		return null;
	}
	
	/* Returns all possible words for a wordKey */
	public ArrayList<String> getValues(WordList wordList, String wordKey){
		ArrayList<String> values = new ArrayList<>();
		for (String word : wordList.wordList.get(wordKey)){
			values.add(word);
		}
		return values;
	}
	
	/* given 3 different index positions, it returns a word. Returns "null" if no word yet since assignment may be incomplete */
	private String createWord(Character[] assignment, Integer[] indices){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++){
			int index = indices[i] - 1;	//input files count starting from 1 (so I subtract 1)
			if (assignment[index] == '0')
				return null;
			sb.append(assignment[index]);
		}
		return sb.toString();
	}
	
	/* This is here for debugging purposes */
	public void print(Character[] assignment){
		for (int i = 0; i < assignment.length; i++){
			System.out.print(assignment[i]);
		}
	}
	
	/* Returns an array with the most "constrained" indices first */
//	private String[] mostPopular(WordPuzzle wordPuzzle){
//		/* Count the number of times each index is "constrained" */
//		Integer[] popular = new Integer[wordPuzzle.puzzleSize];
//		for (int i = 0; i < popular.length; i++){ //Java initializes Integers to null instead of 0!
//			popular[i] = 0;
//		}
//		for (Integer[] indices : wordPuzzle.smallWords.values()){
//			for (int i = 0; i < indices.length; i++){
//				popular[indices[i]-1]++; //input files count starting from 1 (so I subtract 1)
//			}
//		}
//		/* Crappy O(n^2) algorithm to get an array with the most "constrained" indices first */
//		Integer[] sortedDescending = new Integer[popular.length];
//		for (int i = 0; i < popular.length; i++){
//			int max = 0;
//			for (int j = 0; j < popular.length; j++){
//				if (popular[j] > popular[max])
//					max = j;
//			}
//			sortedDescending[i] = max;
//			popular[max] = 0;
//		}
//		return sortedDescending;
//	}
}
