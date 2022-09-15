package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;

/**
 * Represents a color transformation operation with a given matrix.
 */
public class ColorTransform implements Operation {

  protected final double[][] matrix; // the matrix to apply to the image

  /**
   * Construct a color transform with the given 2D array matrix.
   *
   * @param matrix the matrix to modify images with.
   * @throws IllegalArgumentException if the matrix is null.
   */
  protected ColorTransform(double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("matrix cannot be null");
    }
    this.matrix = matrix;
  }

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    Pixel[][] result = new Pixel[img.getHeight()][img.getWidth()];
    Pixel[][] origPixels = img.getPixels();

    for (int r = 0; r < img.getHeight(); r++) {
      for (int c = 0; c < img.getWidth(); c++) {
        int[] rgb = origPixels[r][c].getChannels();
        int red = computeChannelVal(0, rgb);
        int green = computeChannelVal(1, rgb);
        int blue = computeChannelVal(2, rgb);
        rgb[0] = red;
        rgb[1] = green;
        rgb[2] = blue;
        result[r][c] = img.createPixel(rgb);
      }
    }
    return new PixelGridImage(result);
  }

  /**
   * Compute a channel value from the given channels and a given row in the color transform matrix.
   *
   * @param mRow     the row of the matrix to use.
   * @param channels the channels of the pixel to use in the transformation.
   * @return the resulting channel value.
   */
  private int computeChannelVal(int mRow, int[] channels) {
    int value = (int) Math.rint(channels[0] * matrix[mRow][0] + channels[1] * matrix[mRow][1]
        + channels[2] * matrix[mRow][2]);
    return Math.max(0, Math.min(value, 255));
  }
}
