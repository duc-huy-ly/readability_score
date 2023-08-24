package readability;

import java.util.Arrays;

public enum AgesTable {
    GROUP_ONE("5-6", 1,6),
    GROUP_TWO("6-7", 2,7),
    GROUP_THR("7-8", 3,8),
    GROUP_FOU("8-9", 4,9),
    GROUP_FIV("9-10", 5,10),
    GROUP_SIX("10-11", 6,11),
    GROUP_SEV("11-12", 7,12),
    GROUP_EIG("12-13", 8,13),
    GROUP_NIN("13-14", 9,14),
    GROUP_TEN("14-15", 10,15),
    GROUP_ELE("15-16", 11,16),
    GROUP_TWE("16-17", 12,17),
    GROUP_THI("17-18", 13,18),
    GROUP_FIN("18-22", 14,22);

    private final String group;
    private final int score;
    private final int upperBound;

    private final String postfix = "year-olds";

    AgesTable(String group, int score,int upperBound) {
        this.group = group;
        this.score = score;
        this.upperBound = upperBound;
    }

    private String getGroup() {
        return group;
    }

    private int getUpperBound() {
        return upperBound;
    }

    public static String getGroupByScore(double score) {
        int scoreCeilInt = (int) Math.ceil(score);
        return Arrays.stream(values()).filter(e -> e.score == scoreCeilInt)
                .findFirst()
                .map(e -> String.join("\s", e.getGroup(), e.postfix))
                .orElse("<< not specified >>");
    }

    public static int getAgeByScore(float score) {
        int scoreCeilInt = (int) Math.ceil(score);
        for (AgesTable value :
                values()) {
            if (value.score == scoreCeilInt) {
                return value.upperBound;
            }
        }
        return 0;
    }
}