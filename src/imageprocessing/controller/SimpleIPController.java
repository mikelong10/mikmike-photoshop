package imageprocessing.controller;

import imageprocessing.filehandler.FileHandler;
import imageprocessing.filehandler.GenericFileHandler;
import imageprocessing.filehandler.PPMHandler;
import imageprocessing.model.ManipulableImage;
import imageprocessing.operations.Blur;
import imageprocessing.operations.Brighten;
import imageprocessing.operations.FlipHorizontal;
import imageprocessing.operations.FlipVertical;
import imageprocessing.operations.Grayscale;
import imageprocessing.operations.GrayscaleB;
import imageprocessing.operations.GrayscaleG;
import imageprocessing.operations.GrayscaleIntensity;
import imageprocessing.operations.GrayscaleLuma;
import imageprocessing.operations.GrayscaleR;
import imageprocessing.operations.GrayscaleValue;
import imageprocessing.operations.Operation;
import imageprocessing.operations.Sepia;
import imageprocessing.operations.Sharpen;
import imageprocessing.view.ImageProcessingView;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Represents a controller which handles text-based inputs. It can take in any Readable input and
 * transmit output to a given view. It has the capability to handle a variety of file types through
 * the FileHandler and can apply a number of commands on images.
 */
public class SimpleIPController implements ImageProcessingController {

  private final Readable input; // the readable to read inputs from
  private final ImageProcessingView view; // the view to transmit to
  private final Map<String, ManipulableImage> images; // the currently loaded images
  private final Map<String, Function<Scanner, Operation>> commands;
  // the operations that can be applied to images

  /**
   * Construct a controller with the given Readable input, view, and file handler.
   *
   * @param input the readable input.
   * @param view  the view.
   * @throws IllegalArgumentException if any arguments are null.
   */
  public SimpleIPController(Readable input, ImageProcessingView view)
      throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("Readable cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.input = input;
    this.view = view;
    this.images = new HashMap<>();
    this.commands = setBaseCommands();
  }

  /**
   * Reads text-based user inputs to perform image processing functions.
   *
   * @throws IllegalStateException in the case of an i/o exception.
   */
  @Override
  public void processImage() throws IllegalStateException {
    Scanner scan = new Scanner(this.input);
    boolean quit = false;
    transmitMessage("Welcome to MikMike BogoPhotoShop\n");
    transmitMessage("Press 'h' or 'help' for help\n");
    transmitMessage("Press 'q' or 'quit' to quit\n");
    while (!quit) {
      Operation o;
      String in;
      try {
        in = scan.next();
      } catch (NoSuchElementException e) {
        transmitMessage("No inputs remaining!\n");
        return;
      }
      switch (in.toLowerCase()) {
        case "q":
        case "quit":
          transmitMessage("Goodbye!");
          quit = true;
          break;
        case "h":
        case "help":
          transmitMessage(helpMenu());
          break;
        case "load":
          transmitMessage("Loading...\n");
          String path;
          String name;
          try {
            path = scan.next();
            name = scan.next();
          } catch (NoSuchElementException e) {
            throw new IllegalStateException(e.getMessage());
          }
          FileHandler fileHandler = getFileHandler(path);
          ManipulableImage img;
          try {
            img = fileHandler.load(path);
            images.put(name, img);
            transmitMessage(
                "Loaded " + path + " as " + name + "\n" + "Width: " + img.getWidth() + " | Height: "
                    + img.getHeight() + " | Max value: " + img.getMaxValue() + "\n");
          } catch (IOException e) {
            transmitMessage("File " + path + " not found. Try again.\n");
          } catch (NullPointerException ne) {
            transmitMessage("Unsupported file format. Try again.\n");
          }
          break;
        case "save":
          try {
            name = scan.next();
            path = scan.next();
          } catch (NoSuchElementException e) {
            throw new IllegalStateException(e.getMessage());
          }
          img = images.get(name);
          if (img == null) {
            transmitMessage("Image " + name + " not found. Try again.\n");
          } else {
            try {
              fileHandler = getFileHandler(path);
              fileHandler.save(img, path);
              transmitMessage("Saved " + name + " to " + path + "\n");
            } catch (IOException e) {
              transmitMessage("File " + path + " could not be written to: " + e.getMessage()
                  + " Try again.\n");
            }
          }
          break;
        default:
          Function<Scanner, Operation> cmd = commands.getOrDefault(in, null);
          if (cmd == null) {
            transmitMessage("Invalid command provided. Try again.\n");
          } else {
            try {
              o = cmd.apply(scan);
            } catch (InputMismatchException ime) {
              transmitMessage("Invalid parameter. Try again.\n");
              scan.next();
              scan.next();
              scan.next();
              break;
            }
            String src;
            String dest;
            try {
              src = scan.next();
              dest = scan.next();
            } catch (NoSuchElementException e) {
              throw new IllegalStateException(e.getMessage());
            }
            img = images.get(src);
            if (img == null) {
              transmitMessage("Image " + src + " not found. Try again.\n");
            } else {
              images.put(dest, o.modify(img));
              transmitMessage(
                  "Successfully applied " + in + " to " + src + ", now named " + dest + "\n");
            }
          }
          break;
      }
    }
  }

  /**
   * Returns a FileHandler object of the correct type matching with the file extension of the given
   * file path.
   *
   * @param path the filepath of the file to be handled.
   * @return the correct FileHandler object.
   */
  private FileHandler getFileHandler(String path) {
    String fileType = path.substring(path.lastIndexOf('.') + 1);
    if (fileType.equals("ppm")) {
      return new PPMHandler();
    }
    return new GenericFileHandler();
  }

  /**
   * Returns a map of the available commands for this controller with String keys representing the
   * command names and Function object values representing the operations themselves.
   *
   * @return the map of commands.
   */
  private Map<String, Function<Scanner, Operation>> setBaseCommands() {
    Map<String, Function<Scanner, Operation>> commands = new HashMap<>();
    commands.put("red-component", s -> new GrayscaleR());
    commands.put("green-component", s -> new GrayscaleG());
    commands.put("blue-component", s -> new GrayscaleB());
    commands.put("value-component", s -> new GrayscaleValue());
    commands.put("intensity-component", s -> new GrayscaleIntensity());
    commands.put("luma-component", s -> new GrayscaleLuma());
    commands.put("brighten", s -> new Brighten(s.nextInt()));
    commands.put("horizontal-flip", s -> new FlipHorizontal());
    commands.put("vertical-flip", s -> new FlipVertical());
    commands.put("blur", s -> new Blur());
    commands.put("sharpen", s -> new Sharpen());
    commands.put("grayscale", s -> new Grayscale());
    commands.put("sepia", s -> new Sepia());
    return commands;
  }

  /**
   * Transmit a given message to the view, catching IOExceptions.
   *
   * @param message the message to transmit.
   */
  private void transmitMessage(String message) {
    view.renderMessage(message);
  }

  /**
   * Return a string representing the help menu for the controller to be displayed for the user.
   *
   * @return the help menu as a string.
   */
  private String helpMenu() {
    StringBuilder cmds = new StringBuilder();
    for (Map.Entry<String, Function<Scanner, Operation>> cmd : commands.entrySet()) {
      cmds.append("- ").append(cmd.getKey()).append("\n");
    }
    return "-- BOGOPHOTOSHOP HELP MENU --\n"
        + "Load an image: load <path> <name> (i.e. load images/koala.ppm koala)\n"
        + "Save an image: save <name> <path> (i.e. save koala images/koala.ppm)\n"
        + "Command syntax: <command> [param] <src> <dest> (i.e. brighten 10 koala koala-brighter; "
        + "red-component koala koalaR)\n"
        + "Available commands (brighten requires int brightening factor):\n" + cmds
        + "Help: h or help\n"
        + "Quit: q or quit\n";
  }
}
