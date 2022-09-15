package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Constructs a grayscale version of an image from the luma values of its pixels.
 */
public class GrayscaleLuma implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    return img.applyToAll(p -> {
      int[] channels = p.getChannels();
      channels[0] = p.getLuma();
      channels[1] = p.getLuma();
      channels[2] = p.getLuma();
      return img.createPixel(channels);
    });
  }
}
