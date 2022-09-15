package imageprocessing.view;

/**
 * Represents operations which should be offered by a view for an image processing program.
 */
public interface ImageProcessingView {

  /**
   * Render the given message in some way. The specific form of rendition is dependent on the
   * implementation of the view.
   *
   * @param message the message to render.
   */
  void renderMessage(String message);
}
