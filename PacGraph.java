package assignment9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javafx.scene.shape.Path;

public class PacGraph {

	Node[][] graph;
	Scanner scn;
	PrintWriter myWriter;

	Node start;
	Node goal;

	int height;
	int width;

	File input;
	File outputTo;

	public PacGraph(String input_, String outputTo_) {
		input = new File(input_);
		outputTo = new File(outputTo_);
		buildPacGraph();
	}

	void buildPacGraph() {
		try {
			scn = new Scanner(input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the dimensions of the graph

		String[] dimensions = scn.nextLine().split(" ");
		height = Integer.parseInt(dimensions[0]);
		width = Integer.parseInt(dimensions[1]);

		graph = new Node[width][height]; // construct the graph.
		// keep track of our position in the input and the matrix.

		for (int i = 0; i < height; i++) {
			String currentLine = scn.nextLine();
			for (int j = 0; j < width; j++) {
				Node myNode = new Node("" + currentLine.charAt(j));
				if (myNode.data.equals("S")) {
					start = myNode;
				}
				if (myNode.data.equals("G")) {
					goal = myNode;
				}
				graph[j][i] = myNode;
			}
		}

		// tell each node where its neighbors are
		Node currentNode;
		for (int i = 0; i < width; i++) { // i = width
			for (int j = 0; j < height; j++) { // j = height
				currentNode = graph[i][j];
				if (i != 0) {
					currentNode.neighbors.add(graph[i - 1][j]);
				}
				if (i != width - 1) {
					currentNode.neighbors.add(graph[i + 1][j]);
				}
				if (j != 0) {
					currentNode.neighbors.add(graph[i][j - 1]);
				}
				if (j != height - 1) {
					currentNode.neighbors.add(graph[i][j + 1]);
				}
			}
		}
	}

	public void breadthFirstSearch() {
		Queue<Node> queue = new LinkedList<Node>();
		Node current = start;
		queue.add(current);

		// TODO temporary console print for testing
		printGraph();

		current.isVisited = true;

		while (!queue.isEmpty()) {
			current = queue.remove();
			// If we met our goal already
			if (current.equals(goal)) {
				break;
			}
			for (Node neighbor : current.neighbors) {

				// If a condition is out of bounds
				if ((neighbor.data.equals("X")) || (neighbor.isVisited) || neighbor.data.equals("out of bounds")) {
					// If any of these conditions are met don't do anything

				} else {
					neighbor.isVisited = true;
					neighbor.previous = current;
					queue.add(neighbor);

				}
			}

		}
		// There is no solution or path
		if (!current.equals(goal)) {
			return;
		}

		// Build the path backwards starting from the goal
		else {
			for (Node item = goal; !item.data.equals("S"); item = item.previous) {
				if (!(item.data.equals("G")) && !(item.data.equals("S"))) {
					item.data = ".";
				}
			}

			printGraph();
			
			try {
				myWriter = new PrintWriter(outputTo, "UTF-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myWriter.println(height + " " + width);
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					myWriter.print(graph[j][i].data);
				}
				myWriter.println();
			}
			myWriter.close();
			return;
		}
	}

	private void printGraph() {
		System.out.println(height + " " + width);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(graph[j][i].data);
			}
			System.out.println();
		}
		return;
	}

}

class Node {
	String data;
	Node previous;
	boolean isVisited;
	LinkedList<Node> neighbors;

	public Node(String data_) {
		data = data_;
		previous = null;
		isVisited = false;
		neighbors = new LinkedList<>();

	}
}
