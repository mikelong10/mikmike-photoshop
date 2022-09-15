import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import imageprocessing.model.Pixel;
import imageprocessing.model.RGBAPixel;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the RGBAPixel class.
 */
public class RGBAPixelTest {

  private Pixel transparent;
  private Pixel opaque;

  @Before
  public void init() {
    transparent = new RGBAPixel(255, 0, 255, 0);
    opaque = new RGBAPixel(0, 0, 0, 255);
  }

  @Test
  public void testInvalidConstructor() {
    try {
      Pixel bad = new RGBAPixel(1, 2, 3, -25);
      fail("didn't throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("alpha must be between 0 and 255", e.getMessage());
    }
    try {
      Pixel bad = new RGBAPixel(1, 2, 3, 266);
      fail("didn't throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("alpha must be between 0 and 255", e.getMessage());
    }
  }

  @Test
  public void getChannels() {
    assertArrayEquals(new int[]{255, 0, 255, 0}, transparent.getChannels());
    assertArrayEquals(new int[]{0, 0, 0, 255}, opaque.getChannels());
  }

  @Test
  public void getColor() {
    assertEquals(new Color(255, 0, 255, 0), transparent.getColor());
    assertEquals(new Color(0, 0, 0, 255), opaque.getColor());
  }

  @Test
  public void testEquals() {
    assertEquals(new RGBAPixel(255, 0, 255, 0), transparent);
    assertNotEquals(opaque, transparent);
  }

  @Test
  public void testHashCode() {
    assertEquals(new RGBAPixel(255, 0, 255, 0).hashCode(), transparent.hashCode());
    assertNotEquals(opaque.hashCode(), transparent.hashCode());
  }
}