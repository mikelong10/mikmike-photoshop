package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Constructs a grayscale version of an image from the value components of its pixels.
 */
public class GrayscaleValue implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    return img.applyToAll(p -> {
      int[] channels = p.getChannels();
      channels[0] = p.getValue();
      channels[1] = p.getValue();
      channels[2] = p.getValue();
      return img.createPixel(channels);
    });
  }
}
