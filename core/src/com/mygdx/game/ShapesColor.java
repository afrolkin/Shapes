package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;

public class ShapesColor {
    private ShapesColor() {};
    public static final Color RED = parseColor("F44336");
    public static final Color GREEN = parseColor("4CAF50");
    public static final Color BLUE = parseColor("2196F3");
    public static final Color YELLOW = parseColor("FFEB3B");
    public static final Color BACKGROUND = parseColor("EEEEEE");
    public static final Color TEMP_CLEAR = parseColor("9C27B0"); //purple

    // Convert HEX to RGBA
    public static Color parseColor(String hex) {
        String s1 = hex.substring(0, 2);
        int v1 = Integer.parseInt(s1, 16);
        float f1 = (float) v1 / 255f;
        String s2 = hex.substring(2, 4);
        int v2 = Integer.parseInt(s2, 16);
        float f2 = (float) v2 / 255f;
        String s3 = hex.substring(4, 6);
        int v3 = Integer.parseInt(s3, 16);
        float f3 = (float) v3 / 255f;
        return new Color(f1, f2, f3, 1);
    }
}


