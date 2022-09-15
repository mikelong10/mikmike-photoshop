package imageprocessing.filehandler;

import imageprocessing.model.ManipulableImage;
import imageprocessing.model.Pixel;
import imageprocessing.model.PixelGridImage;
import imageprocessing.model.RGBPixel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a file handler for ASCII PPM images. It can load ppm files and turn them into
 * ManipulableImages and save them back into ppm files onto the user's computer.
 */
public class PPMHandler implements FileHandler {

  @Override
  public ManipulableImage load(String path) throws IOException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IOException(e.getMessage());
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    if (!sc.next().equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    sc.nextInt(); // skip over max value

    Pixel[][] grid = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        grid[i][j] = new RGBPixel(r, g, b);
      }
    }
    return new PixelGridImage(grid);
  }

  @Override
  public void save(ManipulableImage img, String path) throws IOException {
    StringBuilder contents = new StringBuilder();

    // make the header
    contents.append("P3").append(System.lineSeparator());
    contents.append(img.getWidth()).append(" ").append(img.getHeight())
        .append(System.lineSeparator());
    contents.append(img.getMaxValue()).append(System.lineSeparator());

    // make contents
    for (Pixel[] row : img.getPixels()) {
      for (Pixel p : row) {
        contents.append(p.toString()).append(" ");
      }
    }

    File outFile = new File(path);
    FileWriter writer = new FileWriter(outFile);
    writer.write(contents.toString());
    writer.close();
  }
}
