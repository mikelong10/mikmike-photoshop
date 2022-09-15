package imageprocessing.operations;

import imageprocessing.model.ManipulableImage;

/**
 * Represents an operation that can be applied to an image to change its appearance in some way.
 */
public interface Operation {

  /**
   * Return a modified version of the given image. The type of modification depends on the specific
   * implementation of this interface. NOTE: This method does NOT mutate the given image.
   *
   * @param img the image create the modified image from.
   * @return a modified version of the given image.
   */
  ManipulableImage modify(ManipulableImage img);
}
