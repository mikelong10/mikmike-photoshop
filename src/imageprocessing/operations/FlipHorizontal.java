package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;

/**
 * Represents an {@code Operation} that horizontally flips an image.
 */
public class FlipHorizontal implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    Pixel[][] origPixels = img.getPixels();
    Pixel[][] newPixels = new Pixel[img.getHeight()][img.getWidth()];

    for (int r = 0; r < img.getHeight(); r++) {
      for (int c = 0; c < img.getWidth(); c++) {
        newPixels[r][c] = origPixels[r][img.getWidth() - 1 - c];
      }
    }

    return new PixelGridImage(newPixels);
  }
}
