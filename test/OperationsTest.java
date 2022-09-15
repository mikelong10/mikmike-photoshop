import static org.junit.Assert.assertEquals;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBPixel;
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
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the operation interface.
 */
public class OperationsTest {

  private Operation redComp;
  private Operation greenComp;
  private Operation blueComp;
  private Operation luma;
  private Operation intensity;
  private Operation value;
  private Operation flipVert;
  private Operation flipHoriz;
  private Operation brighten40;
  private Operation darken40;
  private Operation blur;
  private Operation sharpen;
  private Operation grayscale;
  private Operation sepia;
  private ManipulableImage twoX2;

  @Before
  public void init() {
    redComp = new GrayscaleR();
    greenComp = new GrayscaleG();
    blueComp = new GrayscaleB();
    luma = new GrayscaleLuma();
    intensity = new GrayscaleIntensity();
    value = new GrayscaleValue();
    flipVert = new FlipVertical();
    flipHoriz = new FlipHorizontal();
    brighten40 = new Brighten(40);
    darken40 = new Brighten(-40);
    blur = new Blur();
    sharpen = new Sharpen();
    grayscale = new Grayscale();
    sepia = new Sepia();

    Pixel[] row1 = new Pixel[]{new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80)};
    Pixel[] row2 = new Pixel[]{new RGBPixel(16, 234, 227),
        new RGBPixel(234, 85, 16)};
    Pixel[][] pixels = new Pixel[][]{row1, row2};
    twoX2 = new PixelGridImage(pixels);
  }

  @Test
  public void redComp() {
    Pixel[] grayscaleRRow1 = new Pixel[]{new RGBPixel(255, 255, 255),
        new RGBPixel(150, 150, 150)};
    Pixel[] grayscaleRRow2 = new Pixel[]{new RGBPixel(16, 16, 16),
        new RGBPixel(234, 234, 234)};
    Pixel[][] grayscaleRPixels = new Pixel[][]{grayscaleRRow1, grayscaleRRow2};
    assertEquals(new PixelGridImage(grayscaleRPixels), redComp.modify(twoX2));
  }

  @Test
  public void greenComp() {
    Pixel[] grayscaleGRow1 = new Pixel[]{new RGBPixel(0, 0, 0),
        new RGBPixel(255, 255, 255)};
    Pixel[] grayscaleGRow2 = new Pixel[]{new RGBPixel(234, 234, 234),
        new RGBPixel(85, 85, 85)};
    Pixel[][] grayscaleGPixels = new Pixel[][]{grayscaleGRow1, grayscaleGRow2};
    assertEquals(new PixelGridImage(grayscaleGPixels), greenComp.modify(twoX2));
  }

  @Test
  public void blueComp() {
    Pixel[] grayscaleBRow1 = new Pixel[]{new RGBPixel(0, 0, 0),
        new RGBPixel(80, 80, 80)};
    Pixel[] grayscaleBRow2 = new Pixel[]{new RGBPixel(227, 227, 227),
        new RGBPixel(16, 16, 16)};
    Pixel[][] grayscaleBPixels = new Pixel[][]{grayscaleBRow1, grayscaleBRow2};
    assertEquals(new PixelGridImage(grayscaleBPixels), blueComp.modify(twoX2));
  }

  @Test
  public void lumaComp() {
    Pixel[] grayscaleLRow1 = new Pixel[]{new RGBPixel(54, 54, 54),
        new RGBPixel(220, 220, 220)};
    Pixel[] grayscaleLRow2 = new Pixel[]{new RGBPixel(187, 187, 187),
        new RGBPixel(112, 112, 112)};
    Pixel[][] grayscaleLPixels = new Pixel[][]{grayscaleLRow1, grayscaleLRow2};
    assertEquals(new PixelGridImage(grayscaleLPixels), luma.modify(twoX2));
  }

  @Test
  public void intensityComp() {
    Pixel[] grayscaleIRow1 = new Pixel[]{new RGBPixel(85, 85, 85),
        new RGBPixel(162, 162, 162)};
    Pixel[] grayscaleIRow2 = new Pixel[]{new RGBPixel(159, 159, 159),
        new RGBPixel(112, 112, 112)};
    Pixel[][] grayscaleIPixels = new Pixel[][]{grayscaleIRow1, grayscaleIRow2};
    assertEquals(new PixelGridImage(grayscaleIPixels), intensity.modify(twoX2));
  }

  @Test
  public void valueComp() {
    Pixel[] grayscaleVRow1 = new Pixel[]{new RGBPixel(255, 255, 255),
        new RGBPixel(255, 255, 255)};
    Pixel[] grayscaleVRow2 = new Pixel[]{new RGBPixel(234, 234, 234),
        new RGBPixel(234, 234, 234)};
    Pixel[][] grayscaleVPixels = new Pixel[][]{grayscaleVRow1, grayscaleVRow2};
    assertEquals(new PixelGridImage(grayscaleVPixels), value.modify(twoX2));
  }

  @Test
  public void testFlipVert() {
    Pixel[] flipVertRow1 = new Pixel[]{new RGBPixel(16, 234, 227),
        new RGBPixel(234, 85, 16)};
    Pixel[] flipVertRow2 = new Pixel[]{new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80)};
    Pixel[][] flipVertPixels = new Pixel[][]{flipVertRow1, flipVertRow2};
    assertEquals(new PixelGridImage(flipVertPixels), flipVert.modify(twoX2));
  }

  @Test
  public void testFlipHoriz() {
    Pixel[] flipHorizRow1 = new Pixel[]{new RGBPixel(150, 255, 80),
        new RGBPixel(255, 0, 0)};
    Pixel[] flipHorizRow2 = new Pixel[]{new RGBPixel(234, 85, 16),
        new RGBPixel(16, 234, 227)};
    Pixel[][] flipHorizPixels = new Pixel[][]{flipHorizRow1, flipHorizRow2};
    assertEquals(new PixelGridImage(flipHorizPixels), flipHoriz.modify(twoX2));
  }

  @Test
  public void testBrighten() {
    Pixel[] brighten40Row1 = new Pixel[]{new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80)};
    Pixel[] brighten40Row2 = new Pixel[]{new RGBPixel(37, 255, 248),
        new RGBPixel(255, 106, 37)};
    Pixel[][] brighten40Pixels = new Pixel[][]{brighten40Row1, brighten40Row2};
    assertEquals(new PixelGridImage(brighten40Pixels), brighten40.modify(twoX2));
    Pixel[] darken40Row1 = new Pixel[]{new RGBPixel(255, 0, 0),
        new RGBPixel(110, 215, 40)};
    Pixel[] darken40Row2 = new Pixel[]{new RGBPixel(0, 218, 211),
        new RGBPixel(218, 69, 0)};
    Pixel[][] darken40Pixels = new Pixel[][]{darken40Row1, darken40Row2};
    assertEquals(new PixelGridImage(darken40Pixels), darken40.modify(twoX2));
  }

  @Test
  public void testBlur() {
    Pixel[] row1 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row2 = new Pixel[]{
        new RGBPixel(16, 234, 227),
        new RGBPixel(234, 85, 16),
        new RGBPixel(120, 50, 250)};
    Pixel[] row3 = new Pixel[]{
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(200, 250, 50)};
    Pixel[][] pixels = new Pixel[][]{row1, row2, row3};
    ManipulableImage threeX3 = new PixelGridImage(pixels);

    Pixel[] blurredRow1 = new Pixel[]{
        new RGBPixel(99, 66, 39),
        new RGBPixel(113, 105, 71),
        new RGBPixel(61, 68, 80)};
    Pixel[] blurredRow2 = new Pixel[]{
        new RGBPixel(90, 114, 88),
        new RGBPixel(148, 149, 106),
        new RGBPixel(109, 99, 100)};
    Pixel[] blurredRow3 = new Pixel[]{
        new RGBPixel(48, 91, 77),
        new RGBPixel(106, 136, 77),
        new RGBPixel(98, 106, 55)};
    Pixel[][] blurredPixels = new Pixel[][]{blurredRow1, blurredRow2, blurredRow3};

    assertEquals(new PixelGridImage(blurredPixels), blur.modify(threeX3));
  }

  @Test
  public void testSharpen() {
    Pixel[] row1 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row2 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row3 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row4 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row5 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[][] pixels = new Pixel[][]{row1, row2, row3, row4, row5};
    ManipulableImage fiveX5 = new PixelGridImage(pixels);

    Pixel[] sharpRow1 = new Pixel[]{
        new RGBPixel(255, 58, 0),
        new RGBPixel(227, 229, 116),
        new RGBPixel(54, 255, 172),
        new RGBPixel(150, 255, 172),
        new RGBPixel(94, 171, 142)};
    Pixel[] sharpRow2 = new Pixel[]{
        new RGBPixel(255, 109, 0),
        new RGBPixel(255, 255, 164),
        new RGBPixel(104, 255, 231),
        new RGBPixel(194, 255, 255),
        new RGBPixel(138, 247, 181)};
    Pixel[] sharpRow3 = new Pixel[]{
        new RGBPixel(255, 65, 0),
        new RGBPixel(246, 209, 125),
        new RGBPixel(22, 255, 174),
        new RGBPixel(144, 255, 200),
        new RGBPixel(106, 190, 134)};
    Pixel[] sharpRow4 = new Pixel[]{
        new RGBPixel(255, 109, 0),
        new RGBPixel(255, 255, 164),
        new RGBPixel(104, 255, 231),
        new RGBPixel(194, 255, 255),
        new RGBPixel(138, 247, 181)};
    Pixel[] sharpRow5 = new Pixel[]{
        new RGBPixel(255, 58, 0),
        new RGBPixel(227, 229, 116),
        new RGBPixel(54, 255, 172),
        new RGBPixel(150, 255, 172),
        new RGBPixel(94, 171, 142)};
    Pixel[][] sharpPixels = new Pixel[][]{sharpRow1, sharpRow2, sharpRow3, sharpRow4, sharpRow5};

    assertEquals(new PixelGridImage(sharpPixels), sharpen.modify(fiveX5));
  }

  @Test
  public void testGrayscale() {
    Pixel[] row1 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row2 = new Pixel[]{
        new RGBPixel(16, 234, 227),
        new RGBPixel(234, 85, 16),
        new RGBPixel(120, 50, 250)};
    Pixel[] row3 = new Pixel[]{
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(200, 250, 50)};
    Pixel[][] pixels = new Pixel[][]{row1, row2, row3};
    ManipulableImage threeX3 = new PixelGridImage(pixels);

    Pixel[] grayRow1 = new Pixel[]{
        new RGBPixel(54, 54, 54),
        new RGBPixel(220, 220, 220),
        new RGBPixel(93, 93, 93)};
    Pixel[] grayRow2 = new Pixel[]{
        new RGBPixel(187, 187, 187),
        new RGBPixel(112, 112, 112),
        new RGBPixel(79, 79, 79)};
    Pixel[] grayRow3 = new Pixel[]{
        new RGBPixel(93, 93, 93),
        new RGBPixel(220, 220, 220),
        new RGBPixel(225, 225, 225)};
    Pixel[][] grayPixels = new Pixel[][]{grayRow1, grayRow2, grayRow3};

    assertEquals(new PixelGridImage(grayPixels), grayscale.modify(threeX3));
  }

  @Test
  public void testSepia() {
    Pixel[] row1 = new Pixel[]{
        new RGBPixel(255, 0, 0),
        new RGBPixel(150, 255, 80),
        new RGBPixel(50, 100, 150)};
    Pixel[] row2 = new Pixel[]{
        new RGBPixel(16, 234, 227),
        new RGBPixel(234, 85, 16),
        new RGBPixel(120, 50, 250)};
    Pixel[] row3 = new Pixel[]{
        new RGBPixel(50, 100, 150),
        new RGBPixel(150, 255, 80),
        new RGBPixel(200, 250, 50)};
    Pixel[][] pixels = new Pixel[][]{row1, row2, row3};
    ManipulableImage threeX3 = new PixelGridImage(pixels);

    Pixel[] sepRow1 = new Pixel[]{
        new RGBPixel(100, 89, 69),
        new RGBPixel(255, 241, 187),
        new RGBPixel(125, 111, 87)};
    Pixel[] sepRow2 = new Pixel[]{
        new RGBPixel(229, 204, 159),
        new RGBPixel(160, 143, 111),
        new RGBPixel(133, 118, 92)};
    Pixel[] sepRow3 = new Pixel[]{
        new RGBPixel(125, 111, 87),
        new RGBPixel(255, 241, 187),
        new RGBPixel(255, 250, 194)};
    Pixel[][] sepiaPixels = new Pixel[][]{sepRow1, sepRow2, sepRow3};

    assertEquals(new PixelGridImage(sepiaPixels), sepia.modify(threeX3));
  }
}
