import java.util.ArrayList;

public class LetterBasedSolver {
	Integer[] mcv; //mcv = Most Constrained Value.
	
	/* Wrapper */
	public ArrayList<Character[]> findSolution(WordPuzzle wordPuzzle, WordList wordList){
		mcv = mostPopular(wordPuzzle);

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
		if (allVariablesAssigned(assignment) && assignmentValid(wordPuzzle, wordList, assignment))
			solutions.add((Character[]) assignment.clone());

		int index = selectUnassignedVariable(assignment);
		if (index == -1) // all characters in "assignment" are assigned a value
			return;
		
		for (Character value : getValues()){
			assignment[index] = value;
			if (assignmentValid(wordPuzzle, wordList, assignment))
				findSolutionRecursive(wordPuzzle, wordList, assignment, solutions);
			assignment[index] = '0';
		}
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
	
	/* Selects Most Constrained Variable. This method had a HUGE impact on performance */
	public int selectUnassignedVariable(Character[] assignment){
		for (int i = 0; i < mcv.length; i++){
			if (assignment[mcv[i]] == '0')
				return mcv[i];
		}
		return -1;
	}
	
	/* Returns all characters from 'A' to 'Z' */
	public ArrayList<Character> getValues(){
		ArrayList<Character> values = new ArrayList<>();
		for (char ch = 'A'; ch <= 'Z'; ch++){
			values.add(ch);
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
	
	/* Returns an array with the most "constrained" indices first */
	private Integer[] mostPopular(WordPuzzle wordPuzzle){
		/* Count the number of times each index is "constrained" */
		Integer[] popular = new Integer[wordPuzzle.puzzleSize];
		for (int i = 0; i < popular.length; i++){ //Java initializes Integers to null instead of 0!
			popular[i] = 0;
		}
		for (Integer[] indices : wordPuzzle.smallWords.values()){
			for (int i = 0; i < indices.length; i++){
				popular[indices[i]-1]++; //input files count starting from 1 (so I subtract 1)
			}
		}
		/* Crappy O(n^2) algorithm to get an array with the most "constrained" indices first */
		Integer[] sortedDescending = new Integer[popular.length];
		for (int i = 0; i < popular.length; i++){
			int max = 0;
			for (int j = 0; j < popular.length; j++){
				if (popular[j] > popular[max])
					max = j;
			}
			sortedDescending[i] = max;
			popular[max] = 0;
		}
		return sortedDescending;
	}
}
