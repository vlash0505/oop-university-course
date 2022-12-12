package com.lab3.lines.elements;

import java.util.List;

public class Graph<T> {
    private final boolean[][] adjacencyMatrix;
    private final List<T> cellsData;

    public Graph(List<T> cellsData) {
        this.cellsData = cellsData;
        this.adjacencyMatrix = new boolean[cellsData.size()][cellsData.size()];
    }

    public void addEdge(T v1, T v2) {
        int i = cellsData.indexOf(v1);
        int j = cellsData.indexOf(v2);

        this.adjacencyMatrix[i][j] = true;
        this.adjacencyMatrix[j][i] = true;
    }

    public void removeEdge(T v1, T v2) {
        int i = cellsData.indexOf(v1);
        int j = cellsData.indexOf(v2);

        this.adjacencyMatrix[i][j] = false;
        this.adjacencyMatrix[j][i] = false;
    }

    public boolean isPathExists(T startV, T wantedV) {
        boolean[] visited = new boolean[this.adjacencyMatrix.length];
        int start = cellsData.indexOf(startV);
        int wanted = cellsData.indexOf(wantedV);

        return DFS(start, wanted, visited);
    }

    private boolean DFS(int current, int wanted, boolean[] visited) {
        visited[current] = true;

        boolean result;
        for (int i = 0; i < this.adjacencyMatrix.length; ++i)
            if (this.adjacencyMatrix[current][i] && !visited[i]) {
                if (i == wanted)
                    return true;
                result = DFS(i, wanted, visited);
                if (result) {
                    return true;
                }
            }
        return false;
    }
}

