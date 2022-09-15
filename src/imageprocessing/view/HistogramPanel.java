package imageprocessing.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Map;

import javax.swing.JPanel;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;

/**
 * Represents a custom JPanel class that displays data about a ManipulableImage dynamically
 * through a histogram.
 */
public class HistogramPanel extends JPanel {
  private ManipulableImage img;
  private final String type;

  /**
   * Creates a Histogram panel for a specific image, displaying information about a specific
   * attribute of the image.
   *
   * @param img the current image.
   * @param type the attribute this histogram will display.
   */
  public HistogramPanel(ManipulableImage img, String type) {
    super();
    this.img = img;
    this.type = type;
    this.setPreferredSize(new Dimension(266, 200));
  }

  /**
   * Sets the current image this histogram is displaying information about to the given image.
   *
   * @param img the image to be set.
   */
  public void setImage(ManipulableImage img) {
    this.img = img;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.img != null) {
      if (this.type.equalsIgnoreCase("red")) {
        g.setColor(new Color(237, 15, 15));
        drawHistogram(img.histogram(pixel -> pixel.getChannels()[0]), g);
      }
      else if (this.type.equalsIgnoreCase("green")) {
        g.setColor(new Color(17, 234, 49));
        drawHistogram(img.histogram(pixel -> pixel.getChannels()[1]), g);
      }
      else if (this.type.equalsIgnoreCase("blue")) {
        g.setColor(new Color(12, 54, 220));
        drawHistogram(img.histogram(pixel -> pixel.getChannels()[2]), g);
      }
      else if (this.type.equalsIgnoreCase("intensity")) {
        g.setColor(new Color(117, 85, 20));
        drawHistogram(img.histogram(Pixel::getIntensity), g);
      }
    }
  }

  /**
   * Draws the histogram representation from a given map of data.
   *
   * @param histogram the given data.
   * @param g the Graphics object for drawing.
   */
  private void drawHistogram(Map<Integer, Integer> histogram, Graphics g) {
    double maxFreq = 0;
    // find the maximum frequency for a single value for this image
    for (int i = 0; i < 256; i += 1) {
      double freq = histogram.getOrDefault(i, 0);
      maxFreq = Math.max(freq, maxFreq);
    }
    // draw the histogram
    for (int i = 10; i < 266; i += 1) {
      // get the frequency for each value from 0 to 255
      double freq = histogram.getOrDefault(i - 10, 0);
      // start drawing at i - 5 to give some buffer area for the histogram
      // to make ends of histogram visible
      g.drawLine(i - 5, getHeight() - (int) (getHeight() * (freq / (maxFreq * 1.2))),
              i - 5, getHeight());
    }
  }
}
