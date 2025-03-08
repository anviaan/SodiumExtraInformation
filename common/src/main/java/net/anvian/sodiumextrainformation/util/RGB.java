package net.anvian.sodiumextrainformation.util;

public class RGB {
    int r, g, b;

    public RGB() {
        this.r = 255;
        this.g = 255;
        this.b = 255;
    }

    public int rgbToDecimal() {
        return (r << 16) + (g << 8) + b;
    }
}
