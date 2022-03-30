package fr.lernejo.navy_battle;

import java.util.Arrays;

public enum Result {
    MISS("miss"), HIT("hit"), SUNK("sunk");

    private final String apiString;

    Result(String res) {
        this.apiString = res;
    }

    public static Result fromAPI(String value) {
        var res = Arrays.stream(Result.values()).filter(f -> f.apiString.equals(value)).findFirst();

        if (res.isEmpty())
            throw new RuntimeException("Invalid value!");

        return res.get();
    }

    public String toAPI() {
        return apiString;
    }
}
