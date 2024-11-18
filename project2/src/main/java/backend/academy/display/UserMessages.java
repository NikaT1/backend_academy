package backend.academy.display;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMessages {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[31m";
    public static final String GREEN = "\033[32m";
    public static final String YELLOW = "\033[33m";
    public static final String BACKGROUND_RED = "\033[41m";
    public static final String BACKGROUND_YELLOW = "\033[43m";
    public static final String BACKGROUND_GREEN = "\033[42m";
    public static final String BACKGROUND_PURPLE = "\033[44m";

    public static final String DEFAULT_ARG_MSG = "(enter nothing for random selection):" + System.lineSeparator();
    public static final String START_MSG =
        GREEN + "Welcome to the maze generator program!" + System.lineSeparator() + RESET;
    public static final String MAZE_SOLVER_TYPE_INPUT_MSG = "Enter the number of the selected maze solver type "
        + DEFAULT_ARG_MSG;
    public static final String MAZE_GENERATOR_TYPE_INPUT_MSG = "Enter the number of the selected maze generator type "
        + DEFAULT_ARG_MSG;
    public static final String MAZE_WIDTH_INPUT_MSG = "Enter width of maze (between 3 and 20) " + DEFAULT_ARG_MSG;
    public static final String MAZE_HEIGHT_INPUT_MSG = "Enter height of maze (between 3 and 20) " + DEFAULT_ARG_MSG;
    public static final String MAZE_START_X_INPUT_MSG = "Enter x coordinate of start point " + DEFAULT_ARG_MSG;
    public static final String MAZE_START_Y_INPUT_MSG = "Enter y coordinate of start point " + DEFAULT_ARG_MSG;
    public static final String MAZE_FINISH_X_INPUT_MSG = "Enter x coordinate of finish point " + DEFAULT_ARG_MSG;
    public static final String MAZE_FINISH_Y_INPUT_MSG = "Enter y coordinate of finish point " + DEFAULT_ARG_MSG;
    public static final String MAZE_SOLVER_TYPE_OUTPUT_MSG = YELLOW + "Selected maze solver type: %s"
        + System.lineSeparator() + RESET;
    public static final String MAZE_GENERATOR_TYPE_OUTPUT_MSG = YELLOW + "Selected generator type: %s"
        + System.lineSeparator() + RESET;
    public static final String VALUE_OUTPUT_MSG = YELLOW + "Selected value: %d" + System.lineSeparator()
        + RESET;
    public static final String ERROR_INPUT_MSG = "Error, repeat the input: ";
    public static final String GENERATED_MAZE_MSG = GREEN + "The following maze was generated:" + RESET
        + System.lineSeparator();
    public static final String PATH_FOUND_MSG = GREEN + "Path was found in the generated maze:" + RESET
        + System.lineSeparator();
    public static final String NO_PATH_FOUND_MSG = RED + "Path was not found in the generated maze:(" + RESET;
    public static final String ERROR_USER_ARGS_MSG = RED + "Incorrect program startup arguments -"
        + " values will be ignored" + RESET;
    public static final String MAZE_PART_EMPTY_CELL = "  ";
    public static final String MAZE_FULL_EMPTY_CELL = "    ";
    public static final String MAZE_FULL_WALL_CELL = BACKGROUND_YELLOW + MAZE_FULL_EMPTY_CELL + RESET;
    public static final String MAZE_PART_WALL_CELL = BACKGROUND_YELLOW + MAZE_PART_EMPTY_CELL + RESET;
    public static final String MAZE_PART_PATH_CELL = BACKGROUND_PURPLE + MAZE_PART_EMPTY_CELL + RESET;
    public static final String MAZE_PART_START_CELL = BACKGROUND_GREEN + MAZE_PART_EMPTY_CELL + RESET;
    public static final String MAZE_PART_FINISH_CELL = BACKGROUND_RED + MAZE_PART_EMPTY_CELL + RESET;
    public static final int MIN_MAZE_SIZE = 3;
    public static final int MAX_MAZE_SIZE = 21;
}
