package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Constructs a grayscale version of an image from the green components of its pixels.
 */
public class GrayscaleG implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    return img.applyToAll(
        p -> {
          int[] channels = p.getChannels();
          channels[0] = channels[1];
          channels[2] = channels[1];
          return img.createPixel(channels);
        });
  }
}
