import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import imageprocessing.filehandler.FileHandler;
import imageprocessing.filehandler.PPMHandler;
import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBPixel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the PPM file handler.
 */
public class PPMHandlerTest {

  private FileHandler handler;

  @Before
  public void init() {
    handler = new PPMHandler();
  }

  @Test
  public void load() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      assertEquals(img, handler.load("test/testLoadImage"));
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }

  @Test
  public void save() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      handler.save(img, "test/testSaveImage");
    } catch (IOException e) {
      fail("threw i/o exception");
    }
    try {
      String output = Files.readString(Path.of("test/testSaveImage"));
      assertEquals("P3" + System.lineSeparator()
              + "3 3" + System.lineSeparator()
              + "255" + System.lineSeparator()
              + "255 0 0 255 255 80 255 0 255 16 234 227 234 85 16 0 0 0 125 16 234 0 15 175 46 "
              + "206 35 ",
          output);
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }
}