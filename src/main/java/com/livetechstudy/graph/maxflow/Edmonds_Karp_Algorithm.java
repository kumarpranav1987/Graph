package com.livetechstudy.graph.maxflow;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

//https://practice.geeksforgeeks.org/problems/find-the-maximum-flow/0
//https://www.topcoder.com/community/competitive-programming/tutorials/maximum-flow-section-1/
public class Edmonds_Karp_Algorithm {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int numberOfTestCases = s.nextInt();
		for (int i = 0; i < numberOfTestCases; i++) {
			int n = s.nextInt();
			int m = s.nextInt();
			Graph graph = new Graph(n, m);
			for (int j = 0; j < m; j++) {
				int start = s.nextInt();
				int end = s.nextInt();
				int capacity = s.nextInt();
				graph.addCapacity(start, end, capacity);
			}
			System.out.println(graph.getMaxFlow());
		}
		s.close();
	}

	private static class Graph {
		private int n;
		private int m;
		private int capacity[][];
		private int residualCapaity[][];

		public Graph(int n, int m) {
			this.n = n;
			this.m = m;
			capacity = new int[n][n];
			residualCapaity = new int[n][n];
		}

		public void addCapacity(int start, int end, int x) {
			capacity[start - 1][end - 1] = x;
			residualCapaity[start - 1][end - 1] = x;
		}

		public int getMaxFlow() {
			int result = 0;
			while (true) {
				int flow = getAugmentedPathUsingBFS();
				if (flow == 0) {
					break;
				} else {
					result += flow;
				}
			}
			return result;
		}

		private int getAugmentedPathUsingBFS() {
			boolean visited[] = new boolean[n];
			int parent[] = new int[n];
			Queue<Integer> queue = new LinkedList<Integer>();
			int currentMin = Integer.MAX_VALUE;
			queue.add(0);
			parent[0] = -1;
			visited[0] = true;
			boolean sinkFound = false;
			while (!queue.isEmpty() && !sinkFound) {
				int current = queue.remove();	
				for (int i = 0; i < n; i++) {
					if (!visited[i] && residualCapaity[current][i] > 0) {
						queue.add(i);
						visited[i]=true;
						parent[i] = current;
						currentMin = Integer.min(currentMin, residualCapaity[current][i]);
						if (i == n - 1) {
							sinkFound = true;
							break;
						}
					}
				}
			}
			if (sinkFound) {
				int end = n-1;
				while (end != 0) {
					int start = parent[end];
					residualCapaity[start][end]-=currentMin;
					residualCapaity[end][start]+=currentMin;
					end = start;
				}
			} else {
				currentMin = 0;
			}
			return currentMin;
		}
	}
}
