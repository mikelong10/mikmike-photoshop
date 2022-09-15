import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import imageprocessing.controller.ImageProcessingController;
import imageprocessing.controller.SimpleIPController;
import imageprocessing.view.ImageProcessingTextView;
import imageprocessing.view.ImageProcessingView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the image processing controller.
 */
public class SimpleIPControllerTest {

  private ImageProcessingController controller;
  private ImageProcessingView view;
  private Appendable out;

  @Before
  public void init() {
    out = new StringBuilder();
    view = new ImageProcessingTextView(out);
  }

  @Test
  public void testInvalidConstructor() {
    try {
      controller = new SimpleIPController(null, view);
      fail("did not throw exception when given null readable");
    } catch (IllegalArgumentException e) {
      assertEquals("Readable cannot be null", e.getMessage());
    }
    try {
      controller = new SimpleIPController(new StringReader(""), null);
      fail("did not throw exception when given null view");
    } catch (IllegalArgumentException e) {
      assertEquals("View cannot be null", e.getMessage());
    }
  }

  @Test
  public void testBadReadable() {
    Readable in = new BadReadable();
    controller = new SimpleIPController(in, view);
    try {
      controller.processImage();
      fail("did not throw exception");
    } catch (IllegalStateException e) {
      assertEquals("No inputs left", e.getMessage());
    }
  }

  @Test
  public void testNoMoreInputs() {
    controller = new SimpleIPController(new StringReader("haha"), view);
    try {
      controller.processImage();
      fail("did not throw exception");
    } catch (IllegalStateException e) {
      assertEquals("No inputs left", e.getMessage());
    }
  }

  @Test
  public void testStartupAndQuit() {
    controller = new SimpleIPController(new StringReader("quit"), view);
    controller.processImage();
    assertEquals("Welcome to MikMike BogoPhotoShop\n"
        + "Press 'h' or 'help' for help\n"
        + "Press 'q' or 'quit' to quit\n"
        + "Goodbye!", out.toString());
  }

  @Test
  public void testHelpMenu() {
    controller = new SimpleIPController(new StringReader("help q"), view);
    controller.processImage();
    assertEquals("-- BOGOPHOTOSHOP HELP MENU --\n"
        + "Load an image: load <path> <name> (i.e. load images/koala.ppm koala)\n"
        + "Save an image: save <name> <path> (i.e. save koala images/koala.ppm)\n"
        + "Command syntax: <command> [param] <src> <dest> (i.e. brighten 10 koala koala-brighter;"
        + " red-component koala koalaR)\n"
        + "Available commands (brighten requires int brightening factor):\n"
        + "- brighten\n"
        + "- sharpen\n"
        + "- green-component\n"
        + "- blur\n"
        + "- horizontal-flip\n"
        + "- intensity-component\n"
        + "- grayscale\n"
        + "- emboss\n"
        + "- sepia\n"
        + "- vertical-flip\n"
        + "- value-component\n"
        + "- blue-component\n"
        + "- luma-component\n"
        + "- ridge\n"
        + "- red-component\n"
        + "Help: h or help\n"
        + "Quit: q or quit\n"
        + "Goodbye!", out.toString().substring(90));
  }

  @Test
  public void testInvalidCommand() {
    controller = new SimpleIPController(new StringReader("badCommand q"), view);
    controller.processImage();
    assertEquals("Invalid command provided. Try again.\nGoodbye!", out.toString().substring(90));
  }

  @Test
  public void testInvalidParameter() {
    controller =
        new SimpleIPController(new StringReader("brighten foo abc abc q"), view);
    controller.processImage();
    assertEquals("Invalid parameter. Try again.\nGoodbye!", out.toString().substring(90));
  }

  @Test
  public void testNonexistentLoad() {
    controller = new SimpleIPController(new StringReader("load bad.ppm bad q"), view);
    controller.processImage();
    assertEquals("Loading...\nFile bad.ppm not found. Try again.\nGoodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testBadLoad() {
    controller = new SimpleIPController(new StringReader("load test/bad.xyz bad q"), view);
    controller.processImage();
    assertEquals("Loading...\nUnsupported file format. Try again.\nGoodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testNonexistentSave() {
    controller = new SimpleIPController(new StringReader("save bad bad.ppm q"), view);
    controller.processImage();
    assertEquals("Image bad not found. Try again.\nGoodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testBadSave() {
    controller = new SimpleIPController(
        new StringReader("load test/testImage.png bad save bad test/bad.xyz q"), view);
    controller.processImage();
    assertEquals("Loading...\n"
            + "Loaded test/testImage.png as bad\n"
            + "Width: 3 | Height: 3 | Max value: 255\n"
            + "File test/bad.xyz could not be written to: Unsupported file type. Try again.\n"
            + "Goodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testSuccessfulLoad() {
    controller =
        new SimpleIPController(new StringReader("load test/testImage.ppm img q"), view);
    controller.processImage();
    assertEquals("Loading...\n"
            + "Loaded test/testImage.ppm as img\n"
            + "Width: 3 | Height: 3 | Max value: 255\n"
            + "Goodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testSuccessfulSave() {
    controller = new SimpleIPController(new StringReader(
        "load test/testImage.jpg img save img test/testImage.ppm q"), view);
    controller.processImage();
    assertEquals("Loading...\n"
            + "Loaded test/testImage.jpg as img\n"
            + "Width: 3 | Height: 3 | Max value: 255\n"
            + "Saved img to test/testImage.ppm\n"
            + "Goodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testSuccessfulOperation() {
    controller = new SimpleIPController(new StringReader(
        "load test/testImage.png img luma-component img img-luma q"), view);
    controller.processImage();
    assertEquals("Loading...\n"
            + "Loaded test/testImage.png as img\n"
            + "Width: 3 | Height: 3 | Max value: 255\n"
            + "Successfully applied luma-component to img, now named img-luma\n"
            + "Goodbye!",
        out.toString().substring(90));
  }

  @Test
  public void testSuccessfulOperationsFromScriptFile() {
    try {
      controller = new SimpleIPController(new FileReader("test/test-script"), view);
    } catch (FileNotFoundException e) {
      fail("Controller could not parse script");
    }
    controller.processImage();
    assertEquals("Loading...\n"
            + "Loaded test/testImage.bmp as img\n"
            + "Width: 3 | Height: 3 | Max value: 255\n"
            + "Successfully applied luma-component to img, now named img-luma\n"
            + "Goodbye!",
        out.toString().substring(90));
  }
}