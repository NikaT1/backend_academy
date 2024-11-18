package backend.academy.display.image;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HangedManPartsImages {

    public static final String BOTTOM = "___|___" + System.lineSeparator();
    public static final String SIMPLE_MIDDLE = """
           |
           |
        """;
    public static final String TOP = """
           |-------|
        """;

    public static final String MIDDLE_WITH_ROPE = """
           |       |
           |       |
        """;

    public static final String[] MIDDLE_WITH_HUMAN = {"""
                                                      |       O
                                                      |
                                                   """, """
                                                      |       O
                                                      |      /
                                                   """, """
                                                      |       O
                                                      |      / \\
                                                   """, """
                                                      |       O
                                                      |      /|\\
                                                   """, """
                                                      |      /
                                                      |
                                                   """, """
                                                      |      / \\
                                                      |
                                                   """};
    // на сколько этапов отличается рисование человека от рисования веревки
    public static final int HUMAN_STEPS_DIFF = 4;
    // сколько этапов занимает рисование человека
    public static final int HUMAN_STEPS = 6;
}
