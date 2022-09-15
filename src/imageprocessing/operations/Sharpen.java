package imageprocessing.operations;

/**
 * Represents a filter with a kernel that, when applied, sharpens an image.
 */
public class Sharpen extends Filter {

  /**
   * Creates a Sharpen filter object with the specified sharpen kernel matrix.
   */
  public Sharpen() {
    super(new double[][]{
        new double[]{-0.125, -0.125, -0.125, -0.125, -0.125},
        new double[]{-0.125, 0.25, 0.25, 0.25, -0.125},
        new double[]{-0.125, 0.25, 1, 0.25, -0.125},
        new double[]{-0.125, 0.25, 0.25, 0.25, -0.125},
        new double[]{-0.125, -0.125, -0.125, -0.125, -0.125}});
  }
}
