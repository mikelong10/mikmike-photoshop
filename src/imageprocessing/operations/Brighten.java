package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Brightens or darkens an image based on the given factor. A positive factor makes the image
 * brighter, while a negative one darkens it.
 */
public class Brighten implements Operation {

  private final int factor;

  /**
   * Create a brightening operation with the given brightening factor.
   *
   * @param factor the factor to brighten the image by.
   */
  public Brighten(int factor) {
    this.factor = factor;
  }

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    return img.applyToAll(p -> {
      int[] channels = p.getChannels();
      channels[0] = smartClamp(p.getChannels(), factor)[0];
      channels[1] = smartClamp(p.getChannels(), factor)[1];
      channels[2] = smartClamp(p.getChannels(), factor)[2];
      return img.createPixel(channels);
    });
  }

  /**
   * Returns the new RGB values for a pixel after being brightened by the given factor. This
   * brightening method clamps the RGB values to be between 0 and 255, and stops
   * brightening/darkening as soon as one of the channels goes below 0 or exceeds 255, preserving
   * the hue of the pixel.
   *
   * @param channels the RBG values of the pixel being modified.
   * @param factor   the desired factor that the image currently being modified should be brightened
   *                 by.
   * @return an int array of the new RBG values for the pixel after being brightened.
   */
  private int[] smartClamp(int[] channels, int factor) {
    int rWithFactor = channels[0] + factor;
    int gWithFactor = channels[1] + factor;
    int bWithFactor = channels[2] + factor;
    int newFactor = factor;
    if (rWithFactor > 255 || gWithFactor > 255 || bWithFactor > 255) {
      int maxOver255 = Math.max(rWithFactor - 255, Math.max(gWithFactor - 255,
          bWithFactor - 255));
      newFactor = factor - maxOver255;
    } else if (rWithFactor < 0 || gWithFactor < 0 || bWithFactor < 0) {
      int maxUnder0 = Math.max(0 - rWithFactor, Math.max(0 - gWithFactor, 0 - bWithFactor));
      newFactor = factor + maxUnder0;
    }
    return new int[]{channels[0] + newFactor, channels[1] + newFactor, channels[2] + newFactor};
  }
}
