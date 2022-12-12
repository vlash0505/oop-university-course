package com.lab3.lines.engine;

import com.lab3.lines.elements.Ball;
import com.lab3.lines.elements.Graph;
import com.lab3.lines.elements.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameEngine {
    private static final int TOTAL_COLORS = 4;

    private static final int INITIAL_CAPACITY = 10;
    private static final int GRID_PANEL_SIZE = 8;
    public static final int NEW_BALLS = 3;
    private static final int MINIMAL_SEQUENCE = 5;

    private final Integer[][] gridPanel;
    private final Graph<Tile> graph;
    private final Ball[] nextBalls;

    private Tile fromTile = new Tile(-1, -1);
    private Tile toTile = new Tile(-1, -1);

    private int score;

    public GameEngine() {
        gridPanel = new Integer[GRID_PANEL_SIZE][GRID_PANEL_SIZE];
        nextBalls = new Ball[NEW_BALLS];

        buildGridPanel();
        List<Tile> pos = new ArrayList<>();
        for (int i = 0; i < GRID_PANEL_SIZE; ++i) {
            for (int j = 0; j < GRID_PANEL_SIZE; ++j) {
                pos.add(new Tile(i, j));
            }
        }

        graph = new Graph<>(pos);
        buildUnderlyingGraph();

        generateInitialSet();
        generateInitialIncomingSet();
    }

    private void buildGridPanel() {
        for (int i = 0; i < GRID_PANEL_SIZE; ++i) {
            Arrays.fill(gridPanel[i], 0);
        }
    }

    private void buildUnderlyingGraph() {
        for (int i = 0; i < GRID_PANEL_SIZE; ++i) {
            for (int j = 0; j < GRID_PANEL_SIZE; ++j) {
                if (i > 0) {
                    graph.addEdge(new Tile(i, j), new Tile(i - 1, j));
                }
                if (i < GRID_PANEL_SIZE - 1) {
                    graph.addEdge(new Tile(i, j), new Tile(i + 1, j));
                }
                if (j > 0) {
                    graph.addEdge(new Tile(i, j), new Tile(i, j - 1));
                }
                if (j < GRID_PANEL_SIZE - 1) {
                    graph.addEdge(new Tile(i, j), new Tile(i, j + 1));
                }
            }
        }
    }

    private void generateInitialSet() {
        for (int i = 0; i < INITIAL_CAPACITY; ++i) {
            Ball ball = getRandomBall();
            addBall(ball.tile, ball.color);
        }
    }

    private void generateInitialIncomingSet() {
        for (int i = 0; i < NEW_BALLS; ++i) {
            nextBalls[i] = getRandomBall();
            gridPanel[nextBalls[i].tile.row][nextBalls[i].tile.col] = -nextBalls[i].color;
        }
    }

    private boolean isFull() {
        boolean hasSpace = false;
        for (int i = 0; i < GRID_PANEL_SIZE; ++i) {
            for (int j = 0; j < GRID_PANEL_SIZE; ++j)
                if (gridPanel[i][j] <= 0) {
                    hasSpace = true;
                    break;
                }
            if (hasSpace)
                break;
        }
        return !hasSpace;
    }

    private Ball getRandomBall() {
        Random random = new Random();
        int color = 1 + random.nextInt(TOTAL_COLORS);
        if (!hasZero()) {
            if (isLastRand()) {
                nextBalls[NEW_BALLS - 2] = new Ball(1 + random.nextInt(TOTAL_COLORS), toTile);
            }
            return new Ball(color, fromTile);
        }
        int r = random.nextInt(8);
        int c = random.nextInt(8);
        while (gridPanel[r][c] != 0) {
            r = random.nextInt(8);
            c = random.nextInt(8);
        }
        return new Ball(color, new Tile(r, c));
    }

    public GameStates moveBall(Tile from, Tile to) {
        if (from.equals(to)) {
            return GameStates.REMOVE_SELECT;
        }
        if (gridPanel[from.row][from.col] == 0) {
            return GameStates.NOBALL;
        }
        fromTile = from;
        toTile = to;
        int color = gridPanel[from.row][from.col];

        removeBall(from);

        if (!graph.isPathExists(from, to)) {
            addBall(from, color);
            return GameStates.INVALID_PATH;
        }
        for (int i = 0; i < NEW_BALLS; ++i) {
            addBall(nextBalls[i].tile, nextBalls[i].color);
        }

        addBall(to, color);
        updateField();

        if (this.isFull()) {
            return GameStates.ENDGAME;
        }

        for (int i = 0; i < NEW_BALLS; ++i) {
            nextBalls[i] = getRandomBall();
            gridPanel[nextBalls[i].tile.row][nextBalls[i].tile.col] = -nextBalls[i].color;
        }

        return GameStates.SUCCESS;
    }

    private void updateField() {
        boolean[][] deletionMatrix = new boolean[GRID_PANEL_SIZE][GRID_PANEL_SIZE];
        Tile last = new Tile(-1, -1);

        checkForSequence(deletionMatrix, last, TraverseDirections.ROW);
        checkForSequence(deletionMatrix, last, TraverseDirections.COLUMN);

        int totalBalls = 0;
        for (int i = 0; i < GRID_PANEL_SIZE; ++i) {
            for (int j = 0; j < GRID_PANEL_SIZE; ++j) {
                if (deletionMatrix[i][j]) {
                    totalBalls++;
                    removeBall(new Tile(i, j));
                }
            }
        }
        score += 5 * totalBalls;
    }

    private void checkForSequence(boolean[][] deletionMatrix, Tile last, TraverseDirections direction) {
        final boolean isRowDirection = direction == TraverseDirections.ROW;

        for (int i = 0; i < GRID_PANEL_SIZE; ++i) {
            int seq = 0;
            int currentColor = 0;
            for (int j = 0; j < GRID_PANEL_SIZE; ++j) {
                int currentPanelColor = isRowDirection ? gridPanel[i][j] : gridPanel[j][i];
                if (currentColor != currentPanelColor) {
                    if (seq >= MINIMAL_SEQUENCE) {
                        updateGridLine(deletionMatrix, last, seq, direction);
                    }
                    currentColor = isRowDirection ? gridPanel[i][j] : gridPanel[j][i];
                    seq = currentColor <= 0 ? 0 : 1;
                } else if (currentColor != 0) {
                    seq++;
                    last.col = isRowDirection ? j : i;
                    last.row = isRowDirection ? i : j;
                }
            }
            if (seq >= MINIMAL_SEQUENCE) {
                updateGridLine(deletionMatrix, last, seq, direction);
            }
        }
    }

    private void updateGridLine(boolean[][] deletionMatrix, Tile last, int seq, TraverseDirections direction) {
        int r = last.row;
        int c = last.col;
        while (seq != 0) {
            deletionMatrix[r][c] = true;
            if (direction == TraverseDirections.ROW) {
                c--;
            } else {
                r--;
            }
            seq--;
        }
    }

    private void addBall(Tile p, int color) {
        int row = p.row;
        int col = p.col;
        gridPanel[row][col] = color;

        if (row > 0) {
            graph.removeEdge(p, new Tile(row - 1, col));
        }
        if (row < GRID_PANEL_SIZE - 1) {
            graph.removeEdge(p, new Tile(row + 1, col));
        }
        if (col > 0) {
            graph.removeEdge(p, new Tile(row, col - 1));
        }
        if (col < GRID_PANEL_SIZE - 1) {
            graph.removeEdge(p, new Tile(row, col + 1));
        }
    }

    private void removeBall(Tile p) {
        int row = p.row;
        int col = p.col;
        this.gridPanel[row][col] = 0;

        if (row > 0 && gridPanel[row - 1][col] <= 0) {
            graph.addEdge(p, new Tile(row - 1, col));
        }
        if (row < GRID_PANEL_SIZE - 1 && gridPanel[row + 1][col] <= 0) {
            graph.addEdge(p, new Tile(row + 1, col));
        }
        if (col > 0 && gridPanel[row][col - 1] <= 0) {
            graph.addEdge(p, new Tile(row, col - 1));
        }
        if (col < GRID_PANEL_SIZE - 1 && gridPanel[row][col + 1] <= 0) {
            graph.addEdge(p, new Tile(row, col + 1));
        }
    }

    private boolean hasZero() {
        for (int i = 0; i < GRID_PANEL_SIZE; ++i)
            for (int j = 0; j < GRID_PANEL_SIZE; ++j)
                if (gridPanel[i][j] == 0) {
                    return true;
                }
        return false;
    }

    private boolean isLastRand() {
        int count = 0;
        for (int i = 0; i < GRID_PANEL_SIZE; ++i)
            for (int j = 0; j < GRID_PANEL_SIZE; ++j)
                if (gridPanel[i][j] <= 0) {
                    count++;
                }
        return count == 1;
    }

    public int getScore() {
        return this.score;
    }

    public Integer[][] getGridPanel() {
        return this.gridPanel;
    }
}
