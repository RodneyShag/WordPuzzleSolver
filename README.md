## WordPuzzleSolver
Solves word puzzles using backtracking search.<br/><br/>

Pictured below is a sample word puzzle.

![][example_1a]

Our goal is to fill the empty array with letters such that certain subsets of the letters form words from a given category.

In the solution below, the letters connected to red lines should form a word from the "furniture" category and similarly, the letters connected to the green lines should form a word belonging to the "clothing" category. All the words have a length of 3 and the list of candidate words for each category is provided in a word list.

![][example_1b]

Using backtracking search, we are able to find all solutions to a given problem.

[example_1a]: https://github.com/rshaghoulian/WordPuzzleSolver/blob/master/images/example_1a.jpg
[example_1b]: https://github.com/rshaghoulian/WordPuzzleSolver/blob/master/images/example_1b.png
