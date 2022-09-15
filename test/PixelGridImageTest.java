import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBAPixel;
import imageprocessing.model.RGBPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the PixelGridImage class.
 */
public class PixelGridImageTest {

  private ManipulableImage twoX2;
  private ManipulableImage oneX2;

  @Before
  public void init() {
    Pixel[] row1 = new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80)};
    Pixel[] row2 = new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16)};
    Pixel[][] pixels = new Pixel[][]{row1, row2};
    twoX2 = new PixelGridImage(pixels);
    oneX2 = new PixelGridImage(
            new Pixel[][]{new Pixel[]{new RGBPixel(156, 226, 66), new RGBPixel(15, 49, 208)}});
  }

  @Test
  public void applyToAll() {
    // when given a function that divides each channel by 2, should produce the correct image
    Pixel[][] expected2x2 =
      new Pixel[][]{new Pixel[]{new RGBPixel(127, 0, 0), new RGBPixel(127, 127, 40)},
        new Pixel[]{new RGBPixel(8, 117, 113), new RGBPixel(117, 42, 8)}};
    assertEquals(new PixelGridImage(expected2x2), twoX2.applyToAll(
        p -> new RGBPixel(p.getChannels()[0] / 2,
        p.getChannels()[1] / 2, p.getChannels()[2] / 2)));

    // when given a function that adds 4 to each channel, should produce the correct image
    Pixel[][] expected1x2 =
      new Pixel[][]{new Pixel[]{new RGBPixel(160, 230, 70), new RGBPixel(19, 53, 212)}};
    assertEquals(new PixelGridImage(expected1x2), oneX2.applyToAll(
        p -> new RGBPixel(p.getChannels()[0] + 4,
        p.getChannels()[1] + 4, p.getChannels()[2] + 4)));
  }

  @Test
  public void getMaxValue() {
    assertEquals(255, twoX2.getMaxValue());
    assertEquals(226, oneX2.getMaxValue());
  }

  @Test
  public void getWidth() {
    assertEquals(2, twoX2.getWidth());
    assertEquals(2, oneX2.getWidth());
  }

  @Test
  public void getHeight() {
    assertEquals(2, twoX2.getHeight());
    assertEquals(1, oneX2.getHeight());
  }

  @Test
  public void getPixels() {
    Pixel[][] expected2x2 =
      new Pixel[][]{new Pixel[]{new RGBPixel(255, 0, 0), new RGBPixel(255, 255, 80)},
        new Pixel[]{new RGBPixel(16, 234, 227), new RGBPixel(234, 85, 16)}};
    assertArrayEquals(expected2x2, twoX2.getPixels());

    Pixel[][] expected1x2 =
      new Pixel[][]{new Pixel[]{new RGBPixel(156, 226, 66), new RGBPixel(15, 49, 208)}};
    assertArrayEquals(expected1x2, oneX2.getPixels());
  }

  @Test
  public void testEquals() {
    assertEquals(twoX2, new PixelGridImage(
        new Pixel[][]{
          new Pixel[]{new RGBPixel(255, 0, 0),
            new RGBPixel(255, 255, 80)},
          new Pixel[]{new RGBPixel(16, 234, 227),
            new RGBPixel(234, 85, 16)}}));
    assertEquals(oneX2, new PixelGridImage(
        new Pixel[][]{
          new Pixel[]{new RGBPixel(156, 226, 66),
            new RGBPixel(15, 49, 208)}}));
  }

  @Test
  public void testHashCode() {
    assertEquals(twoX2.hashCode(), new PixelGridImage(
        new Pixel[][]{
          new Pixel[]{new RGBPixel(255, 0, 0),
            new RGBPixel(255, 255, 80)},
          new Pixel[]{new RGBPixel(16, 234, 227),
            new RGBPixel(234, 85, 16)}}).hashCode());
    assertEquals(oneX2.hashCode(), new PixelGridImage(
        new Pixel[][]{
          new Pixel[]{new RGBPixel(156, 226, 66),
            new RGBPixel(15, 49, 208)}}).hashCode());
  }

  @Test
  public void testCreatePixel() {
    assertEquals(new RGBPixel(1, 2, 3), twoX2.createPixel(1, 2, 3));
    assertEquals(new RGBAPixel(1, 2, 3, 255), twoX2.createPixel(1, 2, 3, 255));
    try {
      twoX2.createPixel(0, 0);
      twoX2.createPixel(100, 100, 100, 100, 100);
      fail("did not throw exception as expected");
    } catch (IllegalArgumentException e) {
      assertEquals("Pixel must be created with 3 or 4 channel values.", e.getMessage());
    }
  }

  @Test
  public void testToBufferedImage() {
    BufferedImage test = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
    test.setRGB(0, 0, new Color(255, 0, 0).getRGB());
    test.setRGB(1, 0, new Color(255, 255, 80).getRGB());
    test.setRGB(0, 1, new Color(16, 234, 227).getRGB());
    test.setRGB(1, 1, new Color(234, 85, 16).getRGB());
    BufferedImage actual = twoX2.toBufferedImage(BufferedImage.TYPE_INT_RGB);
    assertEquals(test.getWidth(), actual.getWidth());
    assertEquals(test.getHeight(), actual.getHeight());
    assertEquals(test.getRGB(0, 0), actual.getRGB(0, 0));
    assertEquals(test.getRGB(1, 0), actual.getRGB(1, 0));
    assertEquals(test.getRGB(0, 1), actual.getRGB(0, 1));
    assertEquals(test.getRGB(1, 1), actual.getRGB(1, 1));
    assertEquals(test.getType(), actual.getType());
  }

  @Test
  public void testHistogram() {
    Map<Integer, Integer> redHistogram = twoX2.histogram(pixel -> pixel.getChannels()[0]);
    Map<Integer, Integer> redExpected = new HashMap<>();
    redExpected.put(16, 1);
    redExpected.put(234, 1);
    redExpected.put(255, 2);
    assertEquals(redExpected, redHistogram);
    Map<Integer, Integer> greenHistogram = twoX2.histogram(pixel -> pixel.getChannels()[1]);
    Map<Integer, Integer> greenExpected = new HashMap<>();
    greenExpected.put(0, 1);
    greenExpected.put(85, 1);
    greenExpected.put(234, 1);
    greenExpected.put(255, 1);
    assertEquals(greenExpected, greenHistogram);
    Map<Integer, Integer> blueHistogram = twoX2.histogram(pixel -> pixel.getChannels()[2]);
    Map<Integer, Integer> blueExpected = new HashMap<>();
    blueExpected.put(0, 1);
    blueExpected.put(16, 1);
    blueExpected.put(80, 1);
    blueExpected.put(227, 1);
    assertEquals(blueExpected, blueHistogram);
    Map<Integer, Integer> intensityHistogram = twoX2.histogram(Pixel::getIntensity);
    Map<Integer, Integer> intensityExpected = new HashMap<>();
    intensityExpected.put(85, 1);
    intensityExpected.put(112, 1);
    intensityExpected.put(159, 1);
    intensityExpected.put(197, 1);
    assertEquals(intensityExpected, intensityHistogram);
  }
}