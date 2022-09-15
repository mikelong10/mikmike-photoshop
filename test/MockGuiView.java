import imageprocessing.model.ManipulableImage;
import imageprocessing.view.ImageProcessingGuiView;

/**
 * A mock gui view for testing the controller, which adds to log when its methods are called.
 */
public class MockGuiView implements ImageProcessingGuiView {

  private final StringBuilder log;

  /**
   * Construct a mock view with the given log.
   *
   * @param log the stringbuilder log.
   */
  public MockGuiView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setImage(ManipulableImage img) {
    log.append("Successfully set image\n");
  }

  @Override
  public void renderMessage(String message) {
    log.append(message);
  }
}
