package imageprocessing.controller;

import imageprocessing.filehandler.GenericFileHandler;
import imageprocessing.filehandler.PPMHandler;
import imageprocessing.model.ManipulableImage;
import imageprocessing.operations.Operation;
import imageprocessing.view.ImageProcessingGuiView;
import java.io.File;
import java.io.IOException;

/**
 * Represents a basic implementation of a graphical image processing controller.
 */
public class SimpleIPGuiController implements ImageProcessingGuiController {

  private final ImageProcessingGuiView view; // the view to send images and messages to

  /**
   * Construct a graphical controller with the given view.
   *
   * @param view the given view.
   */
  public SimpleIPGuiController(ImageProcessingGuiView view) {
    this.view = view;
  }

  @Override
  public void load(File file) {
    ManipulableImage img;
    String fileType = getExtension(file);

    try {
      if (fileType.equals("ppm")) {
        img = new PPMHandler().load(file.getPath());
      } else {
        img = new GenericFileHandler().load(file.getPath());
      }
      view.setImage(img);
      view.renderMessage("Successfully loaded image " + file.getPath());
    } catch (IOException e) { // catch exceptions and tell the user to try again
      view.renderMessage(e.getMessage() + " Try again.");
    } catch (NullPointerException e) {
      view.renderMessage("No image to load. Try again.");
    }
  }

  @Override
  public void save(File file, ManipulableImage image) {
    String fileType = getExtension(file);

    try {
      if (fileType.equals("ppm")) {
        new PPMHandler().save(image, file.getPath());
      } else {
        new GenericFileHandler().save(image, file.getPath());
      }
      view.renderMessage("Successfully saved current image to " + file.getPath());
    } catch (IOException e) { // tell the user to try again
      view.renderMessage(e.getMessage() + " Try again.");
    } catch (NullPointerException ne) {
      view.renderMessage("No image to save!");
    }
  }

  @Override
  public void applyOperation(ManipulableImage image, Operation op) {
    if (image == null) {
      view.renderMessage("No image to modify");
      return;
    }
    ManipulableImage result = op.modify(image);
    view.setImage(result);
    view.renderMessage("Successfully applied operation");
  }

  /**
   * Detect the extension of the given file from the filepath.
   *
   * @param file the file to get the extension from.
   * @return a String representing the file extension (i.e. jpg, png, bmp, ppm)
   */
  private String getExtension(File file) {
    return file.getPath().substring(file.getPath().lastIndexOf('.') + 1);
  }
}
