package com.example.tfl_test;

import android.graphics.Color;

public enum TflLineColours {

    BAKERLOO (1, "Bakerloo", "#894e24"),
    CENTRAL (2, "Central", "#dc241f"),
    CIRCLE (3, "Circle", "#ffce00"),
    DISTRICT (4, "District", "#007229"),
    DLR (5, "DLR", "#00afad"),
    HAMMERSMITH_AND_CITY (6, "Hammersmith and City", "#d799af"),
    JUBILEE (7, "Jubilee", "#6a7278"),
    LONDON_OVERGROUND (8, "Overground", "#e86a10"),
    METROPOLITAN (9, "Metropolitan", "#751056"),
    NORTHERN (10, "Northern", "#000000"),
    PICADILLY (11, "Piccadilly", "#0019a8"),
    VICTORIA (12, "Victoria", "#00a0e2"),
    WATERLOO_AND_CITY (13, "Waterloo and City", "#76d0bd");

    private final int value;
    private final String lineName;
    private final int colourCode;

    TflLineColours(int value, String lineName, String colourCodeAsHex) {

        this.value = value;
        this.lineName = lineName;
        this.colourCode = Color.parseColor(colourCodeAsHex);
    }

    public String getLineName() {
        return lineName;
    }


    public static int getColourForLineName(String name) {
        for (TflLineColours tflLineColours : values()) {
            if (tflLineColours.getLineName().equalsIgnoreCase(name)) {
                return tflLineColours.colourCode;
            }
        }
        return Color.BLACK;
    }
}
