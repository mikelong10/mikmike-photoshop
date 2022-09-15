package imageprocessing.view;

import imageprocessing.model.ManipulableImage;

/**
 * Represents a graphical view for the image processing program.
 */
public interface ImageProcessingGuiView extends ImageProcessingView {

  /**
   * Set the view's currently displayed image to the given image.
   *
   * @param img the current image.
   */
  void setImage(ManipulableImage img);
}
