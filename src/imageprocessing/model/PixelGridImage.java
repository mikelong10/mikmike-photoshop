package imageprocessing.model;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Representation of a manipulable image as a rectangular grid of pixels. This class allows the
 * image to be manipulated by processing the 2D array of Pixels and creating new grids.
 */
public class PixelGridImage implements ManipulableImage {

  private final Pixel[][] pixels;

  /**
   * Construct a pixel grid image from the given 2D array of pixels.
   *
   * @param pixels the pixels of the image to be constructed.
   * @throws IllegalArgumentException if the 2D array is null.
   */
  public PixelGridImage(Pixel[][] pixels) throws IllegalArgumentException {
    if (pixels == null) {
      throw new IllegalArgumentException("Pixel grid can't be null");
    }
    this.pixels = pixels;
  }

  @Override
  public ManipulableImage applyToAll(Function<Pixel, Pixel> operation) {
    Pixel[][] result = new Pixel[this.getHeight()][this.getWidth()];
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[row].length; col++) {
        result[row][col] = operation.apply(pixels[row][col]);
      }
    }
    return new PixelGridImage(result);
  }


  @Override
  public int getMaxValue() {
    int curMax = 0;
    for (Pixel[] row : pixels) {
      for (Pixel p : row) {
        if (p.getValue() > curMax) {
          curMax = p.getValue();
        }
      }
    }
    return curMax;
  }

  @Override
  public int getWidth() {
    return this.pixels[0].length;
  }

  @Override
  public int getHeight() {
    return this.pixels.length;
  }

  @Override
  public Pixel[][] getPixels() {
    Pixel[][] result = new Pixel[this.pixels.length][this.pixels[0].length];
    for (int r = 0; r < this.pixels.length; r += 1) {
      for (int c = 0; c < this.pixels[0].length; c += 1) {
        result[r][c] = createPixel(this.pixels[r][c].getChannels());
      }
    }
    return result;
  }

  @Override
  public Pixel createPixel(int... channels) throws IllegalArgumentException {
    if (channels.length == 3) {
      return new RGBPixel(channels[0], channels[1], channels[2]);
    } else if (channels.length == 4) {
      return new RGBAPixel(channels[0], channels[1], channels[2], channels[3]);
    } else {
      throw new IllegalArgumentException("Pixel must be created with 3 or 4 channel values.");
    }
  }

  @Override
  public BufferedImage toBufferedImage(int imgType) {
    BufferedImage result = new BufferedImage(getWidth(), getHeight(), imgType);
    for (int r = 0; r < getHeight(); r++) {
      for (int c = 0; c < getWidth(); c++) {
        int rgbVal = pixels[r][c].getColor().getRGB();
        result.setRGB(c, r, rgbVal);
      }
    }
    return result;
  }

  @Override
  public Map<Integer, Integer> histogram(Function<Pixel, Integer> data) {
    Map<Integer, Integer> result = new HashMap<>();
    for (int r = 0; r < getHeight(); r++) {
      for (int c = 0; c < getWidth(); c++) {
        int value = data.apply(pixels[r][c]);
        int frequency = result.getOrDefault(value, 0) + 1;
        result.put(value, frequency);
      }
    }
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof PixelGridImage)) {
      return false;
    }
    PixelGridImage o = (PixelGridImage) other;
    for (int r = 0; r < this.pixels.length; r += 1) {
      for (int c = 0; c < this.pixels[0].length; c += 1) {
        if (!this.pixels[r][c].equals(o.pixels[r][c])) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.deepHashCode(pixels));
  }
}
