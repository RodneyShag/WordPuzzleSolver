import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main (String [] args) throws FileNotFoundException, IOException{
		WordList wordList = new WordList();
		WordPuzzle puzzle1 = new WordPuzzle("puzzle1");
		WordPuzzle puzzle2 = new WordPuzzle("puzzle2");
		WordPuzzle puzzle3 = new WordPuzzle("puzzle3");
		WordPuzzle puzzle4 = new WordPuzzle("puzzle4");
		WordPuzzle puzzle5 = new WordPuzzle("puzzle5");
		
		System.out.println("*** solve by LETTER ***");
		
		LetterBasedSolver letterBasedSolver = new LetterBasedSolver();
		ArrayList<Character[]> solutions1 = letterBasedSolver.findSolution(puzzle1, wordList);
		printSolutionsLetterBased(solutions1, letterBasedSolver.mcv);
		ArrayList<Character[]> solutions2 = letterBasedSolver.findSolution(puzzle2, wordList);
		printSolutionsLetterBased(solutions2, letterBasedSolver.mcv);
		ArrayList<Character[]> solutions3 = letterBasedSolver.findSolution(puzzle3, wordList);
		printSolutionsLetterBased(solutions3, letterBasedSolver.mcv);
		ArrayList<Character[]> solutions4 = letterBasedSolver.findSolution(puzzle4, wordList);
		printSolutionsLetterBased(solutions4, letterBasedSolver.mcv);
		ArrayList<Character[]> solutions5 = letterBasedSolver.findSolution(puzzle5, wordList);
		printSolutionsLetterBased(solutions5, letterBasedSolver.mcv);
		
		System.out.println("\n*** solve by WORD ***");
		
		WordBasedSolver wordBasedSolver = new WordBasedSolver();
		ArrayList<Character[]> solutions6 = wordBasedSolver.findSolution(puzzle1, wordList);
		printSolutionsWordBased(solutions6);
		ArrayList<Character[]> solutions7 = wordBasedSolver.findSolution(puzzle2, wordList);
		printSolutionsWordBased(solutions7);
		ArrayList<Character[]> solutions8 = wordBasedSolver.findSolution(puzzle3, wordList);
		printSolutionsWordBased(solutions8);
		ArrayList<Character[]> solutions9 = wordBasedSolver.findSolution(puzzle4, wordList);
		printSolutionsWordBased(solutions9);
		ArrayList<Character[]> solutions10 = wordBasedSolver.findSolution(puzzle5, wordList);
		printSolutionsWordBased(solutions10);
	}
	
	
	private static void printSolutionsLetterBased(ArrayList<Character[]> solutions, Integer[] mcv){
		System.out.print("Search Order (MCV): ");
		for (int i = 0; i < mcv.length; i++){
			System.out.print(mcv[i]);
			if (i != mcv.length - 1)
				System.out.print(" -> ");
		}
		
		for (Character[] solution : solutions){
			System.out.print("\nroot -> ");
			for (int i = 0; i < mcv.length; i++){
				System.out.print(solution[mcv[i]]);
				if (i != mcv.length - 1)
					System.out.print(" -> ");
			}
			System.out.print(" (found result: ");
			for (Character ch : solution){
				System.out.print(ch);
			}
			System.out.print(")");
		}
		
		System.out.println("\n");
	}
	
	private static void printSolutionsWordBased(ArrayList<Character[]> solutions){
		System.out.println("\n\n");
	}
}