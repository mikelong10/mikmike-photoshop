import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import imageprocessing.model.Pixel;
import imageprocessing.model.RGBPixel;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for RGB pixels.
 */
public class RGBPixelTest {

  private Pixel white;
  private Pixel black;
  private Pixel orange;


  @Before
  public void init() {
    white = new RGBPixel(255, 255, 255);
    black = new RGBPixel(0, 0, 0);
    orange = new RGBPixel(234, 85, 16);
  }

  @Test
  public void testInvalidConstructor() {
    Pixel bad;
    try {
      bad = new RGBPixel(-4, 0, 0);
      fail("did not throw exception when given negative red value");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      bad = new RGBPixel(0, -1, 0);
      fail("did not throw exception when given negative green value");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      bad = new RGBPixel(0, 0, -159);
      fail("did not throw exception when given negative blue value");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      bad = new RGBPixel(269, 0, 0);
      fail("did not throw exception when given too big red value");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      bad = new RGBPixel(0, 3000, 0);
      fail("did not throw exception when given too big green value");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
    try {
      bad = new RGBPixel(0, 0, 304);
      fail("did not throw exception when given too big blue value");
    } catch (IllegalArgumentException e) {
      assertEquals("RGB values must be between 0 and 255", e.getMessage());
    }
  }

  @Test
  public void getChannels() {
    assertArrayEquals(new int[]{255, 255, 255}, white.getChannels());
    assertArrayEquals(new int[]{0, 0, 0}, black.getChannels());
    assertArrayEquals(new int[]{234, 85, 16}, orange.getChannels());
  }

  @Test
  public void getValue() {
    assertEquals(255, white.getValue());
    assertEquals(0, black.getValue());
    assertEquals(234, orange.getValue());
  }

  @Test
  public void getLuma() {
    assertEquals(255, white.getLuma());
    assertEquals(0, black.getLuma());
    assertEquals(112, orange.getLuma());
  }

  @Test
  public void getIntensity() {
    assertEquals(255, white.getIntensity());
    assertEquals(0, black.getIntensity());
    assertEquals(112, orange.getIntensity());
  }

  @Test
  public void testGetColor() {
    assertEquals(new Color(255, 255, 255), white.getColor());
    assertEquals(new Color(0, 0, 0), black.getColor());
    assertEquals(new Color(234, 85, 16), orange.getColor());
  }

  @Test
  public void testToString() {
    assertEquals("255 255 255", white.toString());
    assertEquals("0 0 0", black.toString());
    assertEquals("234 85 16", orange.toString());
  }

  @Test
  public void testEquals() {
    assertNotEquals(white, black);
    assertNotEquals("wacky", white);
    assertEquals(orange, new RGBPixel(234, 85, 16));
  }

  @Test
  public void testHashCode() {
    assertEquals(new RGBPixel(0, 0, 0).hashCode(), black.hashCode());
    assertNotEquals(orange.hashCode(), white.hashCode());
  }
}