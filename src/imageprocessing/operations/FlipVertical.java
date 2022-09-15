package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;

/**
 * Represents an {@code Operation} that vertically flips an image.
 */
public class FlipVertical implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    Pixel[][] origPixels = img.getPixels();
    Pixel[][] newPixels = new Pixel[img.getHeight()][img.getWidth()];
    for (int r = 0; r < img.getHeight(); r++) {
      newPixels[r] = origPixels[img.getHeight() - 1 - r];
    }
    return new PixelGridImage(newPixels);
  }
}
