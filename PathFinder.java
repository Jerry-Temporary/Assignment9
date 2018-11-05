package assignment9;

import java.io.File;

import assignment9.PacGraph;

public class PathFinder {
	
	

	/**
	 * Finds the shortest path from the start to the goal in the
	 * input maze file.
	 * The output file should contain the same maze with the shortest 
	 * path marked. See the assignment instructions for details.
	 * @param inputFile - the file path to the input maze
	 * @param outputFile - the file path to the output maze
	 */
	public static void solveMaze(String inputFile, String outputFile)
	{
		//Create a graph
		PacGraph myGraph = new PacGraph(inputFile, outputFile);
		//Solve the graph
		myGraph.breadthFirstSearch();
	}
	
}