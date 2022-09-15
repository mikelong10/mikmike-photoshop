package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;

/**
 * Represents a filter with a kernel that can be applied to an image.
 */
public class Filter implements Operation {

  protected final double[][] kernel;

  /**
   * Construct a filter with the given kernel.
   *
   * @param kernel the kernel to apply to the image.
   * @throws IllegalArgumentException if the given kernel is null.
   */
  protected Filter(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("kernel cannot be null");
    }
    this.kernel = kernel;
  }

  /**
   * Modifies the given image by applying this filter's kernel to it.
   *
   * @param img the image create the modified image from.
   * @return a filtered version of the image.
   */
  @Override
  public ManipulableImage modify(ManipulableImage img) {
    Pixel[][] result = new Pixel[img.getHeight()][img.getWidth()];
    Pixel[][] origPixels = img.getPixels();

    for (int r = 0; r < img.getHeight(); r++) { // loop through the pixels of the image
      for (int c = 0; c < img.getWidth(); c++) {

        int[] channels = origPixels[r][c].getChannels();
        double red = 0;
        double green = 0;
        double blue = 0;

        // overlay the kernel and calculate the channel values for red, green, and blue
        for (int i = r - this.kernel.length / 2; i <= r + this.kernel.length / 2; i += 1) {
          for (int j = c - this.kernel[0].length / 2; j <= c + this.kernel[0].length / 2; j += 1) {
            if (i >= 0 && j >= 0 && i < img.getHeight() && j < img.getWidth()) {
              int[] rgb = origPixels[i][j].getChannels();
              double kernelValue = // get the appropriate kernel value
                  kernel[i - (r - kernel.length / 2)][j - (c - kernel[0].length / 2)];
              red += kernelValue * rgb[0]; // compute and add to the rgb values
              green += kernelValue * rgb[1];
              blue += kernelValue * rgb[2];
            }
          }
        }
        channels[0] = clamp(red);
        channels[1] = clamp(green);
        channels[2] = clamp(blue);
        result[r][c] = img.createPixel(channels);
      }
    }
    return new PixelGridImage(result);
  }

  /**
   * Round and clamp the given value between 0 and 255.
   *
   * @param value the value to clamp.
   * @return an int representing the clamped value.
   */
  private int clamp(double value) {
    return Math.max(0, Math.min((int) Math.rint(value), 255));
  }
}
