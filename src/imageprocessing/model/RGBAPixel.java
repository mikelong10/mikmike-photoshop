package imageprocessing.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents an RGB Pixel with an additional channel for its alpha value, allowing for support for
 * transparency, commonly with PNG images.
 */
public class RGBAPixel extends RGBPixel {

  private final int alpha;
  // INVARIANT: value must be between 0 and 255, inclusive

  /**
   * Constructs a RGBA pixel with the given r, g, b, and alpha values.
   *
   * @param r     the red component.
   * @param g     the green component.
   * @param b     the blue component.
   * @param alpha the alpha component.
   * @throws IllegalArgumentException if any values are not between 0 and 255 inclusive.
   */
  public RGBAPixel(int r, int g, int b, int alpha) throws IllegalArgumentException {
    super(r, g, b);
    if (alpha < 0 || alpha > 255) {
      throw new IllegalArgumentException("alpha must be between 0 and 255");
    }
    this.alpha = alpha;
  }

  @Override
  public int[] getChannels() {
    return new int[]{r, g, b, alpha};
  }

  @Override
  public Color getColor() {
    return new Color(r, g, b, alpha);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof RGBAPixel)) {
      return false;
    }
    RGBAPixel other = (RGBAPixel) o;
    return other.r == this.r &&
        other.g == this.g &&
        other.b == this.b &&
        other.alpha == this.alpha;
  }

  @Override
  public int hashCode() {
    return Objects.hash(r, g, b, alpha);
  }
}
