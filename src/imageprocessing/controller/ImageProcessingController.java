package imageprocessing.controller;

/**
 * Represents the operations which should be offered by an image processing controller. The
 * controller works with models of images and controls how they are processed through user input,
 * which can be provided in a variety of ways, and works with a view to display output.
 */
public interface ImageProcessingController {

  /**
   * Runs the image processing program, using models of images to apply modifications on them of
   * according to user input, and uses a view to transmit the produced output of the game as the
   * user makes inputs.
   */
  void processImage();
}
