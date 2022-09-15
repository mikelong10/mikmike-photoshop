package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Constructs a grayscale version of an image from the red components of its pixels.
 */
public class GrayscaleR implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    return img.applyToAll(
        p -> {
          int[] channels = p.getChannels();
          channels[1] = channels[0];
          channels[2] = channels[0];
          return img.createPixel(channels);
        });
  }
}