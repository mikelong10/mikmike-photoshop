package imageprocessing.model;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the set of operations which should be offered by an image consisting of a series of
 * pixels.
 */
public interface ManipulableImage {

  /**
   * Apply the given operation to each pixel in this image, and return the resulting image.
   *
   * @param operation the operation to apply to each pixel.
   * @return the resulting image after applying the operation to each pixel.
   */
  ManipulableImage applyToAll(Function<Pixel, Pixel> operation);

  /**
   * Return the greatest RGB channel value of any pixel in this image.
   *
   * @return an int representing the max channel value.
   */
  int getMaxValue();

  /**
   * Return the width of this image in pixels.
   *
   * @return the width in pixels.
   */
  int getWidth();

  /**
   * Return the height of this image in pixels.
   *
   * @return the height in pixels.
   */
  int getHeight();

  /**
   * Return a deep copy of all the pixels in this image as a 2D array. The row and column number of
   * a pixel in the array should represent its actual position in the image (for example, the pixel
   * returned by getPixels()[0][0] should be the top-leftmost pixel in the image).
   *
   * @return a deep copy 2D array of the pixels in this image.
   */
  Pixel[][] getPixels();

  /**
   * Returns a new Pixel object from the given integer channel values. A user can provide 3 values
   * to produce a RGBPixel (red, green, blue values) or 4 values to produce a RGBAPixel (red, green,
   * blue, alpha values).
   *
   * @param channels the channel values for the desired Pixel.
   * @return the new Pixel object.
   * @throws IllegalArgumentException if not given 3 or 4 channel values.
   */
  Pixel createPixel(int... channels) throws IllegalArgumentException;

  /**
   * Create a {@code BufferedImage} representation of this image.
   *
   * @return the BufferedImage representing this image.
   */
  BufferedImage toBufferedImage(int imgType);

  /**
   * Produces a Map of Integers to Integers representing the data for a histogram of this image
   * for a given channel value or other image attribute.
   *
   * @param data the Function object from a Pixel to Integer that represents the data for
   *             the histogram.
   * @return the map of value, frequency data points for the histogram.
   */
  Map<Integer, Integer> histogram(Function<Pixel, Integer> data);
}