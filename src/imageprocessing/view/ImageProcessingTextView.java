package imageprocessing.view;

import java.io.IOException;

/**
 * Represents a text-based implementation of an image processing view.
 */
public class ImageProcessingTextView implements ImageProcessingView {

  private final Appendable dest;

  /**
   * Constructs a text view with the given appendable destination.
   *
   * @param dest the appendable destination.
   * @throws IllegalArgumentException if the destination is null.
   */
  public ImageProcessingTextView(Appendable dest) throws IllegalArgumentException {
    if (dest == null) {
      throw new IllegalArgumentException("destination cannot be null");
    }
    this.dest = dest;
  }

  /**
   * Transmits the given message to the appendable destination.
   *
   * @param message the message to transmit.
   */
  @Override
  public void renderMessage(String message) {
    try {
      this.dest.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
