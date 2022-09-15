package imageprocessing.controller;

import imageprocessing.model.ManipulableImage;
import imageprocessing.operations.Operation;
import java.io.File;

/**
 * Represents an asynchronous graphical controller for an image processing program.
 */
public interface ImageProcessingGuiController {

  /**
   * Load the given image file, and send the resulting ManipulableImage to the GUI.
   *
   * @param file the file to be loaded.
   */
  void load(File file);

  /**
   * Save the given ManipulableImage to the given file.
   *
   * @param file  the file to save to.
   * @param image the image to save.
   */
  void save(File file, ManipulableImage image);

  /**
   * Apply the given operation to the given ManipulableImage, and send the resulting image back to
   * the GUI.
   *
   * @param image the image to modify.
   * @param op    the operation to apply to the image.
   */
  void applyOperation(ManipulableImage image, Operation op);
}
