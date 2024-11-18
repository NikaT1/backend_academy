package backend.academy.display.image;

import backend.academy.display.UserMessages;
import backend.academy.graph.model.Coordinate;
import backend.academy.maze.Cell;
import backend.academy.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MazeRenderImpl implements MazeRender {

    public String render(Maze maze) {
        return render(maze, new ArrayList<>());
    }

    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder builder = new StringBuilder();
        builder.append(renderTopOfMaze(maze.width() * 2 + 1))
            .append(System.lineSeparator());
        for (int i = maze.height() - 1; i >= 0; i--) {
            renderCellLine(maze, i, path, builder);
        }
        return builder.toString();
    }

    private void renderCellLine(
        Maze maze,
        int lineIndex,
        List<Coordinate> path,
        StringBuilder builder
    ) {
        for (int j = 0; j < maze.width(); j++) {
            Cell cell = maze.getCell(j, lineIndex);
            builder.append(renderTopPartOfCell(cell, path));
        }
        builder.append(UserMessages.MAZE_PART_WALL_CELL).append(System.lineSeparator());
        for (int j = 0; j < maze.width(); j++) {
            Cell cell = maze.getCell(j, lineIndex);
            builder.append(renderBottomPartOfCell(cell));
        }
        builder.append(UserMessages.MAZE_PART_WALL_CELL).append(System.lineSeparator());
    }

    private String renderTopPartOfCell(Cell cell, List<Coordinate> path) {
        String cellStr = prepareCellStr(cell, path);
        if (cell.type().contains(Cell.Type.WALL)) {
            return UserMessages.MAZE_PART_WALL_CELL + cellStr;
        } else {
            return UserMessages.MAZE_PART_EMPTY_CELL + cellStr;
        }
    }

    private String prepareCellStr(Cell cell, List<Coordinate> path) {
        String cellStr = UserMessages.MAZE_PART_EMPTY_CELL;
        if (path.contains(cell.coordinate())) {
            cellStr = UserMessages.MAZE_PART_PATH_CELL;
            if (path.get(0).equals(cell.coordinate())) {
                cellStr = UserMessages.MAZE_PART_FINISH_CELL;
            }
            if (path.get(path.size() - 1).equals(cell.coordinate())) {
                cellStr = UserMessages.MAZE_PART_START_CELL;
            }
        }
        return cellStr;
    }

    private String renderBottomPartOfCell(Cell cell) {
        if (cell.type().contains(Cell.Type.FLOOR)) {
            return UserMessages.MAZE_FULL_WALL_CELL;
        } else if (cell.type().contains(Cell.Type.WALL)) {
            return UserMessages.MAZE_PART_WALL_CELL + UserMessages.MAZE_PART_EMPTY_CELL;
        } else {
            return UserMessages.MAZE_FULL_EMPTY_CELL;
        }
    }

    private String renderTopOfMaze(int count) {
        return UserMessages.MAZE_PART_WALL_CELL.repeat(count);
    }
}
