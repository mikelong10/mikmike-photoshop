package imageprocessing.operations;

/**
 * Apply a grayscale color transformation.
 */
public class Grayscale extends ColorTransform {

  /**
   * Construct a grayscale operation using the luma component.
   */
  public Grayscale() {
    super(new double[][]{
        new double[]{0.2126, 0.7152, 0.0722},
        new double[]{0.2126, 0.7152, 0.0722},
        new double[]{0.2126, 0.7152, 0.0722},
    });
  }
}
