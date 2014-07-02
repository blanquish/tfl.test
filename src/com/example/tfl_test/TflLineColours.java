package com.example.tfl_test;

public enum TflLineColours {

    BAKERLOO (1, "Bakerloo", 0x894e24),
    CENTRAL (2, "Central", 0x894e24),
    CIRCLE (3, "Circle", 0x894e24),
    DISTRICT (4, "District", 0x894e24),
    DLR (5, "DLR", 0x894e24),
    HAMMERSMITH_AND_CITY (6, "Hammersmith & City", 0x894e24),
    JUBILEE (7, "Jubilee", 0x894e24),
    LONDON_OVERGROUND (8, "London Overground", 0x894e24),
    METROPOLITAN (9, "Metropolitan", 0x894e24),
    NORTHERN (10, "Northern", 0x894e24),
    PICADILLY (11, "Picadilly", 0x894e24),
    VICTORIA (12, "Victoria", 0x894e24),
    WATERLOO_AND_CITY (13, "Waterloo & City", 0x894e24);

    private final int value;
    private final String lineName;
    private final int colourCode;

    TflLineColours(int value, String lineName, int colourCode) {

        this.value = value;
        this.lineName = lineName;
        this.colourCode = colourCode;
    }

    public int getValue() {
        return value;
    }

    public String getLineName() {
        return lineName;
    }

    public int getColourCode() {
        return colourCode;
    }
}
