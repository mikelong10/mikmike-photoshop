import imageprocessing.model.ManipulableImage;
import imageprocessing.operations.Operation;
import java.io.IOException;

/**
 * Mock operation to confirm inputs.
 */
public class ConfirmInputsOperation implements Operation {

  private final Appendable log;

  /**
   * Construct a confirm inputs operation w/ the given StringBuilder log.
   *
   * @param log the log.
   */
  public ConfirmInputsOperation(Appendable log) {
    this.log = log;
  }

  @Override
  public ManipulableImage modify(ManipulableImage img) {
    try {
      log.append("Successfully received image").append(img.toString());
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    return img; // we don't want the method to actually do anything to the image.
  }
}
