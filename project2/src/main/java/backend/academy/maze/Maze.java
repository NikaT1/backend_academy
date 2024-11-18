package backend.academy.maze;

import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Graph;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Maze {
    @Getter
    private final int width;
    @Getter
    private final int height;
    @Getter
    private Graph graph;
    private Cell[][] grid;

    public Maze initMaze(Graph graph) {
        this.graph = graph;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate id = new Coordinate(i, j);
                Coordinate leftId = new Coordinate(i - 1, j);
                Coordinate bottomId = new Coordinate(i, j - 1);
                Cell cell = new Cell(id);
                if (!graph.hasEdge(id, leftId)) {
                    cell.type().add(Cell.Type.WALL);
                }
                if (!graph.hasEdge(id, bottomId)) {
                    cell.type().add(Cell.Type.FLOOR);
                }
                grid[i][j] = cell;
            }
        }
        return this;
    }

    public Cell getCell(int i, int j) {
        return grid[i][j];
    }
}
