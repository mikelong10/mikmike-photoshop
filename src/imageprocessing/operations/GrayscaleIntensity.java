package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Constructs a grayscale version of an image from the intensities of its pixels.
 */
public class GrayscaleIntensity implements Operation {

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    return img.applyToAll(p -> {
      int[] channels = p.getChannels();
      channels[0] = p.getIntensity();
      channels[1] = p.getIntensity();
      channels[2] = p.getIntensity();
      return img.createPixel(channels);
    });
  }
}
