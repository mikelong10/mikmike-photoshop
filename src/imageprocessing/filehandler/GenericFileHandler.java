package imageprocessing.filehandler;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBAPixel;

/**
 * A file handler for converting between ManipulableImages and image file types supported by the
 * javax.imageio.ImageIO library.
 */
public class GenericFileHandler implements FileHandler {

  @Override
  public ManipulableImage load(String path) throws IOException {
    BufferedImage img = ImageIO.read(new File(path));
    Pixel[][] grid = new Pixel[img.getHeight()][img.getWidth()];

    for (int r = 0; r < img.getHeight(); r++) {
      for (int c = 0; c < img.getWidth(); c++) {
        Color col = new Color(img.getRGB(c, r), true);
        grid[r][c] = new RGBAPixel(col.getRed(), col.getGreen(), col.getBlue(), col.getAlpha());
      }
    }

    return new PixelGridImage(grid);
  }

  @Override
  public void save(ManipulableImage img, String path) throws IOException {
    String fileType = path.substring(path.lastIndexOf('.') + 1);
    int imgType = BufferedImage.TYPE_INT_RGB;
    if (fileType.equalsIgnoreCase("png")) {
      imgType = BufferedImage.TYPE_INT_ARGB;
    }
    if (!ImageIO.write(img.toBufferedImage(imgType), fileType, new File(path))) {
      throw new IOException("Tried to save image to an unsupported file type");
    }
  }
}
