package imageprocessing.operations;

/**
 * Represents a filter with a kernel that, when applied, blurs an image.
 */
public class Blur extends Filter {

  /**
   * Creates a Blur filter object with the specified blur kernel matrix.
   */
  public Blur() {
    super(new double[][]{
        new double[]{1 / 16d, 1 / 8d, 1 / 16d},
        new double[]{1 / 8d, 1 / 4d, 1 / 8d},
        new double[]{1 / 16d, 1 / 8d, 1 / 16d}});
  }
}