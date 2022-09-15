package imageprocessing.model;

import java.awt.Color;

/**
 * Represents the operations that should be offered by a single pixel.
 */
public interface Pixel {

  /**
   * Return an array of all the channel values of this pixel.
   *
   * @return the array of channel values.
   */
  int[] getChannels();

  /**
   * Return the greatest channel value of this pixel.
   *
   * @return the greatest channel value.
   */
  int getValue();

  /**
   * Return the luma value of this pixel as an integer.
   *
   * @return the luma value of this pixel.
   */
  int getLuma();

  /**
   * Return the intensity value of this pixel as an integer.
   *
   * @return the intensity value of this pixel.
   */
  int getIntensity();

  /**
   * Returns a Color object representing the color of this Pixel.
   *
   * @return the Color object for this Pixel.
   */
  Color getColor();
}
