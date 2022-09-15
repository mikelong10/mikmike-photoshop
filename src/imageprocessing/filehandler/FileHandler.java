package imageprocessing.filehandler;

import imageprocessing.model.ManipulableImage;
import java.io.IOException;

/**
 * Represents a file handler to interact with files of different types and convert them back and
 * forth to ManipulableImages for the image processing program.
 */
public interface FileHandler {

  /**
   * Load an image file from a given file path and return the Image representation of the file.
   *
   * @param path the path of the file.
   * @throws IOException if there is an error reading the file.
   */
  ManipulableImage load(String path) throws IOException;

  /**
   * Save a given image to the given path.
   *
   * @param img  the image to be saved.
   * @param path the filepath of the image.
   * @throws IOException if there is an error writing to the file.
   */
  void save(ManipulableImage img, String path) throws IOException;
}
