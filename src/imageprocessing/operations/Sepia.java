package imageprocessing.operations;

/**
 * Apply a sepia tone color transformation.
 */
public class Sepia extends ColorTransform {

  /**
   * Construct a sepia operation.
   */
  public Sepia() {
    super(new double[][]{
        new double[]{0.393, 0.769, 0.189},
        new double[]{0.349, 0.686, 0.168},
        new double[]{0.272, 0.534, 0.131},
    });
  }
}
