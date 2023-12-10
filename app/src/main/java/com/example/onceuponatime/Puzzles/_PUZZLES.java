package com.example.onceuponatime.Puzzles;

public class _PUZZLES {
    // <<< PLAYER
    public static int[] registrationAnswers = {12, 100, 10};

    public static String[] achievements = {"firstEnd",
                                           "secondEnd",
                                           "thirdEnd",
                                           "logic",
                                           "Lepido",
                                           "logic2",
                                           "maestro",
                                           "vandal",
                                           "malivich" };

    public static String[] achievementsExplain = {"Bad ending",
                                                  "Neutral ending",
                                                  "Good ending",
                                                  "Tap on plates by their size",
                                                  "Choose only butterflies",
                                                  "Tap on images by their size",
                                                  "Crash the piano with a hammer",
                                                  "Open or close?",
                                                  "Colorful" };

    // <<< FIRST
    public static int[] firstTime = {2, 3};

    public static int[] firstWindowsSequence = {1, 1, 2, 1, 2, 2, 2, 1};

    public static int[] firstPlatesSequence = {1, 4, 5, 2, 3};

    public static int[] firstBooksSequence = {5, 3, 2, 1, 4};
    public static int[] firstBooksPosX = {500, 135};  // Start, offset
    public static int firstBooksPosY = 120;

    public static int[] firstClosetSequence = {1, 3, 2};
    public static int[] firstClosetPosX = {230, 240};  // Start, offset
    public static int firstClosetPosY = 400;

    public static int[] firstChestPosX = {380, 210};  // Start, offset
    public static int firstChestPosY = 450;

    public static String[] firstChestSequence = {"symbol_3", "symbol_5", "symbol_9", "symbol_11", "symbol_13"};

    // <<< SECOND
    public static int[] secondTime = {2, 3};

    public static int[] secondTableLockersSequence = {1, 3, 2};
    public static int[] secondTableImagesSequence = {1, 5, 2, 6, 3, 4};

    // <<< THIRD
    public static int[] thirdClocksSequence = {1, 0, 0, 0};
    public static int[] thirdTeethClickSequence = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static int[] thirdTeethShowSequence = {9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static boolean[] thirdEaselSequence = { true, false, false, false, false, false, false,
                                                false, true, false, false, false, false, false,
                                                false, false, true, false, false, false, false,
                                                false, false, false, true, false, false, false };

    // <<< ENDING
    public static int[] thirdEndingBad = {1, 2, 3};
    public static int[] thirdEndingNeutral = {2, 3, 1};
    public static int[] thirdEndingGood = {3, 1, 2};

    // <<< LORE
    public static String[] lore = {"Как я здесь очутился?",
                                   "Мне кажется, я не должен быть здесь",
                                   "Что за голос в моей голове?"};
}
