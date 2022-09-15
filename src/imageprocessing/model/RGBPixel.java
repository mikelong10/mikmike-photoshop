package imageprocessing.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents an RGB pixel in a color image, consisting of three 8-bit channels.
 */
public class RGBPixel implements Pixel {

  protected final int r; // the red component of this pixel
  // INVARIANT: value must be between 0 and 255, inclusive
  protected final int g; // the green component of this pixel
  // INVARIANT: value must be between 0 and 255, inclusive
  protected final int b; // the blue component of this pixel
  // INVARIANT: value must be between 0 and 255, inclusive

  /**
   * Constructs an RGB pixel with the given RGB components.
   *
   * @param r the red component.
   * @param g the green component.
   * @param b the blue component.
   * @throws IllegalArgumentException if any arguments are negative or the RGB values are greater
   *                                  than 255.
   */
  public RGBPixel(int r, int g, int b) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("RGB values must be between 0 and 255");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  public int[] getChannels() {
    return new int[]{r, g, b};
  }

  @Override
  public int getValue() {
    return Math.max(r, Math.max(g, b));
  }

  @Override
  public int getLuma() {
    return (int) Math.rint(0.2126 * r + 0.7152 * g + 0.0722 * b);
  }

  @Override
  public int getIntensity() {
    return (int) Math.rint((r + g + b) / 3.0);
  }

  @Override
  public Color getColor() {
    return new Color(r, g, b);
  }

  @Override
  public String toString() {
    return r + " " + g + " " + b;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof RGBPixel)) {
      return false;
    }
    RGBPixel other = (RGBPixel) o;
    return other.r == this.r && other.g == this.g && other.b == this.b;
  }

  @Override
  public int hashCode() {
    return Objects.hash(r, g, b);
  }
}
