package com.livetechstudy.graph.directed.topologicalsort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class TopologicalSortUsingReverseOfFinishTime {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int m = s.nextInt();
		Graph graph = new Graph(n, m);
		for (int i = 0; i < m; i++) {
			int u = s.nextInt();
			int v = s.nextInt();
			graph.addEdge(u, v);
		}
		graph.topSort();
		s.close();
	}

	private static class Graph {
		private int n;
		private int m;
		private List<List<Integer>> adjList;

		public Graph(int n, int m) {
			this.n = n;
			this.m = m;
			adjList = new ArrayList<List<Integer>>();
			for (int i = 0; i < n; i++) {
				adjList.add(new ArrayList<Integer>());
			}
		}

		public void addEdge(int u, int v) {
			adjList.get(u).add(v);
		}
		// Use only When we know that given Graph is a DAG
		public void topSort() {
			boolean[] visited = new boolean[n];
			Stack<Integer> stack = new Stack<>();
			for (int i = 0; i < n; i++) {
				if (!visited[i]) {
					topSortUtil(i, visited, stack);
				}
			}
			while (!stack.isEmpty()) {
				System.out.print(stack.pop() + " ");
			}
			System.out.println();
		}

		private void topSortUtil(int u, boolean[] visited, Stack<Integer> stack) {
			visited[u] = true;
			for (int v : adjList.get(u)) {
				if (!visited[v]) {
					topSortUtil(v, visited, stack);
				}
			}
			stack.push(u);
		}
	}

}
