import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import imageprocessing.controller.ImageProcessingGuiController;
import imageprocessing.controller.SimpleIPGuiController;
import imageprocessing.filehandler.GenericFileHandler;
import imageprocessing.filehandler.PPMHandler;
import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBPixel;
import imageprocessing.operations.Brighten;
import imageprocessing.operations.GrayscaleB;
import imageprocessing.view.ImageProcessingGuiView;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the gui controller.
 */
public class SimpleIPGuiControllerTest {

  private ImageProcessingGuiController controller;
  private StringBuilder log;

  @Before
  public void init() {
    log = new StringBuilder();
    ImageProcessingGuiView mockView = new MockGuiView(log);
    controller = new SimpleIPGuiController(mockView);
  }

  @Test
  public void loadSuccessPPM() {
    controller.load(new File("test/testImage.ppm"));
    assertEquals("Successfully set image\nSuccessfully loaded image test\\testImage.ppm",
        log.toString());
  }

  @Test
  public void loadSuccessOther() {
    controller.load(new File("test/testImage.bmp"));
    assertEquals("Successfully set image\nSuccessfully loaded image test\\testImage.bmp",
        log.toString());
  }

  @Test
  public void loadNull() {
    controller.load(new File("test/bad.xyz"));
    assertEquals("No image to load. Try again.", log.toString());
  }

  @Test
  public void loadIOException() {
    controller.load(new File("nonexistent.file"));
    assertEquals("Can't read input file! Try again.", log.toString());
  }


  @Test
  public void saveSuccessPPM() {
    try {
      ManipulableImage img = new GenericFileHandler().load("test/testImage.jpg");
      controller.save(new File("test/testImage.ppm"), img);
      assertEquals("Successfully saved current image to test\\testImage.ppm", log.toString());
    } catch (IOException e) {
      fail("threw exception");
    }
  }

  @Test
  public void saveSuccessOther() {
    try {
      ManipulableImage img = new PPMHandler().load("test/testImage.ppm");
      controller.save(new File("test/testImage.png"), img);
      assertEquals("Successfully saved current image to test\\testImage.png", log.toString());
    } catch (IOException e) {
      fail("threw exception");
    }
  }

  @Test
  public void testSaveNull() {
    controller.save(new File("test/testNull.png"), null);
    assertEquals("No image to save!", log.toString());
  }

  @Test
  public void testSaveIO() {
    try {
      ManipulableImage img = new GenericFileHandler().load("test/testImage.jpg");
      controller.save(new File("test/xyz.bad"), img);
      assertEquals("Unsupported file type. Try again.", log.toString());
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }

  @Test
  public void applyOperation() {
    Pixel[] grayscaleBRow1 = new Pixel[]{new RGBPixel(0, 0, 0),
        new RGBPixel(80, 80, 80)};
    Pixel[] grayscaleBRow2 = new Pixel[]{new RGBPixel(227, 227, 227),
        new RGBPixel(16, 16, 16)};
    Pixel[][] grayscaleBPixels = new Pixel[][]{grayscaleBRow1, grayscaleBRow2};
    try {
      ManipulableImage img = new GenericFileHandler().load("test/testImage.bmp");
      controller.applyOperation(img, new GrayscaleB());
      assertEquals("Successfully set image\n"
          + "Successfully applied operation", log.toString());
      assertEquals(new PixelGridImage(grayscaleBPixels), new GrayscaleB().modify(img));
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }

  @Test
  public void testApplyToNull() {
    controller.applyOperation(null, new Brighten(44));
    assertEquals("No image to modify", log.toString());
  }
}