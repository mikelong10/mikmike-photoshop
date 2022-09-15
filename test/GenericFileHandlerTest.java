import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import imageprocessing.filehandler.FileHandler;
import imageprocessing.filehandler.GenericFileHandler;
import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the generic file handler.
 */
public class GenericFileHandlerTest {

  private FileHandler handler;

  @Before
  public void init() {
    handler = new GenericFileHandler();
  }

  @Test
  public void loadJPG() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(253, 0, 0), new RGBPixel(253, 255, 87), new RGBPixel(253, 0, 252)},
        new Pixel[]{new RGBPixel(20, 235, 227), new RGBPixel(236, 81, 0), new RGBPixel(15, 3, 17)},
        new Pixel[]{new RGBPixel(111, 18, 220), new RGBPixel(1, 18, 186), new RGBPixel(38, 198, 38)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      assertEquals(img, handler.load("test/testImage.jpg"));
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }

  @Test
  public void loadPNG() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      assertEquals(img, handler.load("test/testImage.png"));
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }

  @Test
  public void loadBMP() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      assertEquals(img, handler.load("test/testImage.bmp"));
    } catch (IOException e) {
      fail("threw i/o exception");
    }
  }

  @Test
  public void saveJPG() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      handler.save(img, "test/saveImage.jpg");
    } catch (IOException e) {
      fail("threw i/o exception");
    }
    try {
      String type = Files.probeContentType(Path.of("test/saveImage.jpg"));
      assertEquals("image/jpeg", type);
    } catch (IOException e) {
      fail("threw i/o exception " + e.getMessage());
    }
  }

  @Test
  public void savePNG() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      handler.save(img, "test/saveImage.png");
    } catch (IOException e) {
      fail("threw i/o exception");
    }
    try {
      String type = Files.probeContentType(Path.of("test/saveImage.png"));
      assertEquals("image/png", type);
    } catch (IOException e) {
      fail("threw i/o exception " + e.getMessage());
    }
  }

  @Test
  public void saveBMP() {
    Pixel[][] grid = new Pixel[][]{
        new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80), new RGBPixel(255, 0, 255)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16), new RGBPixel(0, 0, 0)},
        new Pixel[]{new RGBPixel(125, 16, 234), new RGBPixel(0, 15, 175), new RGBPixel(46, 206, 35)}
    };
    ManipulableImage img = new PixelGridImage(grid);
    try {
      handler.save(img, "test/saveImage.bmp");
    } catch (IOException e) {
      fail("threw i/o exception");
    }
    try {
      String type = Files.probeContentType(Path.of("test/saveImage.bmp"));
      assertEquals("image/bmp", type);
    } catch (IOException e) {
      fail("threw i/o exception " + e.getMessage());
    }
  }

  @Test
  public void testFileTypeCheck() {
    String jpg = "random.long.file.jpg";
    String png = "also-very.png.LongFile.png";
    assertEquals("jpg", jpg.substring(jpg.lastIndexOf('.') + 1));
    assertEquals("png", png.substring(png.lastIndexOf('.') + 1));
  }
}