package imageprocessing.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import imageprocessing.controller.ImageProcessingGuiController;
import imageprocessing.controller.SimpleIPGuiController;
import imageprocessing.model.ManipulableImage;
import imageprocessing.operations.Blur;
import imageprocessing.operations.Brighten;
import imageprocessing.operations.FlipHorizontal;
import imageprocessing.operations.FlipVertical;
import imageprocessing.operations.Grayscale;
import imageprocessing.operations.GrayscaleB;
import imageprocessing.operations.GrayscaleG;
import imageprocessing.operations.GrayscaleIntensity;
import imageprocessing.operations.GrayscaleLuma;
import imageprocessing.operations.GrayscaleR;
import imageprocessing.operations.GrayscaleValue;
import imageprocessing.operations.Sepia;
import imageprocessing.operations.Sharpen;

/**
 * Represents a graphical image processing view implementation using Java Swing.
 */
public class SimpleIPGuiView extends JFrame implements ImageProcessingGuiView {

  private final ImageProcessingGuiController controller;

  private final Map<String, JMenuItem> menuItems; // the menu items representing operations
  private final JMenuBar menuBar; // the menu bar
  private final JMenu file; // the 'file' menu
  private final JMenu edit; // the 'edit' menu
  private final JMenu help; // the 'help' menu
  private final JMenu filter; // the Filters submenu
  private final JMenu color; // the Color Transform submenu
  private final JMenu component; // the Component Visualization submenu
  private final JMenu flip; // the Flips submenu
  private final JMenu other; // submenu for miscellaneous operations (i.e. brighten)

  private final JScrollPane imagePane; // the scrollable pane for displaying the loaded image
  private final HistogramPanel redHistogram; // the red component histogram
  private final HistogramPanel greenHistogram; // the green component histogram
  private final HistogramPanel blueHistogram; // the blue component histogram
  private final HistogramPanel intensityHistogram; // the intensity component histogram
  private final JLabel messageLabel; // the label for displaying messages to the user
  private ManipulableImage currentImage; // the currently loaded image

  /**
   * Construct a graphical image processing view.
   */
  public SimpleIPGuiView() {
    // set up the window
    super("MikMike BogoPhotoShop");
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.controller = new SimpleIPGuiController(this); // make the controller

    Font defaultFont = new Font("plain", Font.PLAIN, 16);
    Border defaultBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, false);

    JPanel content = new JPanel(new BorderLayout()); // organize everything into this panel

    // create the menu at the top of the screen
    this.menuItems = new HashMap<>();
    this.menuBar = new JMenuBar();
    this.file = new JMenu("File");
    this.edit = new JMenu("Edit");
    this.help = new JMenu("Help");
    this.filter = new JMenu("Filter");
    this.color = new JMenu("Color Transform");
    this.component = new JMenu("Component Visualization");
    this.flip = new JMenu("Flip");
    this.other = new JMenu("Other");
    this.initializeMenuItems();
    content.add(this.createMenuBar(), BorderLayout.PAGE_START);

    // create the scrollable image, appearing in the center
    this.imagePane = new JScrollPane(new JLabel(new ImageIcon()));
    this.imagePane.setBorder(defaultBorder);
    content.add(imagePane, BorderLayout.CENTER);

    // create the histograms, appearing on the right
    JPanel histogramPanel = new JPanel();
    histogramPanel.setLayout(new BoxLayout(histogramPanel, BoxLayout.PAGE_AXIS));

    this.redHistogram = new HistogramPanel(currentImage, "red");
    redHistogram.setBackground(new Color(255, 239, 239));
    redHistogram.setBorder(defaultBorder);
    JLabel redHistogramLabel = new JLabel("RED");
    redHistogramLabel.setFont(defaultFont);
    redHistogram.add(redHistogramLabel);
    histogramPanel.add(redHistogram);

    this.greenHistogram = new HistogramPanel(currentImage, "green");
    greenHistogram.setBackground(new Color(240, 255, 240));
    greenHistogram.setBorder(defaultBorder);
    JLabel greenHistogramLabel = new JLabel("GREEN");
    greenHistogramLabel.setFont(defaultFont);
    greenHistogram.add(greenHistogramLabel);
    histogramPanel.add(greenHistogram);

    this.blueHistogram = new HistogramPanel(currentImage, "blue");
    blueHistogram.setBackground(new Color(238, 241, 255));
    blueHistogram.setBorder(defaultBorder);
    JLabel blueHistogramLabel = new JLabel("BLUE");
    blueHistogramLabel.setFont(defaultFont);
    blueHistogram.add(blueHistogramLabel);
    histogramPanel.add(blueHistogram);

    this.intensityHistogram = new HistogramPanel(currentImage, "intensity");
    intensityHistogram.setBackground(new Color(255, 250, 240));
    intensityHistogram.setBorder(defaultBorder);
    JLabel intensityHistogramLabel = new JLabel("INTENSITY");
    intensityHistogramLabel.setFont(defaultFont);
    intensityHistogram.add(intensityHistogramLabel);
    histogramPanel.add(intensityHistogram);
    content.add(histogramPanel, BorderLayout.LINE_END);

    // create the message label at the bottom of the screen
    this.messageLabel = new JLabel(" Messages will appear here");
    this.messageLabel.setFont(defaultFont);
    this.messageLabel.setPreferredSize(new Dimension(800, 32));
    content.add(messageLabel, BorderLayout.PAGE_END);
    this.setContentPane(content);
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void setImage(ManipulableImage img) {
    Objects.requireNonNull(img, "Provided image cannot be null");
    this.currentImage = img;
    this.redHistogram.setImage(img);
    this.redHistogram.repaint();
    this.greenHistogram.setImage(img);
    this.greenHistogram.repaint();
    this.blueHistogram.setImage(img);
    this.blueHistogram.repaint();
    this.intensityHistogram.setImage(img);
    this.intensityHistogram.repaint();
    if (currentImage != null) {
      imagePane.setViewportView(new JLabel(new ImageIcon(currentImage.toBufferedImage(2)
              .getScaledInstance(imagePane.getWidth(), -1, 0))));
    }
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    this.messageLabel.setText(" " + message);
  }

  /**
   * Create the individual menu items representing operations, and add action listeners to them
   * which will delegate to the controller once clicked.
   */
  private void initializeMenuItems() {
    JMenuItem item = new JMenuItem("Load");
    JFileChooser fileChooser = new JFileChooser();
    item.addActionListener(e -> {
      int i = fileChooser.showOpenDialog(this);
      if (i == JFileChooser.APPROVE_OPTION) {
        controller.load(fileChooser.getSelectedFile());
      }
    });
    menuItems.put("load", item);

    item = new JMenuItem("Save");
    item.addActionListener(e -> {
      int i = fileChooser.showSaveDialog(this);
      if (i == JFileChooser.APPROVE_OPTION) {
        controller.save(fileChooser.getSelectedFile(), this.currentImage);
      }
    });
    menuItems.put("save", item);

    item = new JMenuItem("Show");
    item.addActionListener(e ->
            JOptionPane.showMessageDialog(imagePane,
                    "\nLOAD: click File, Load, and choose an image from your computer\n\n"
                            + "Once an image is loaded, modify the image through our plethora of " +
                            "operations found in the Edit menu in the menu bar!\n\n"
                            + "SAVE: click File, Save, navigate to where you would like to "
                            + "save the image on your computer,\ngive your image a name with a " +
                            "proper file extension, and click Save",
                    "Instruction Manual", JOptionPane.INFORMATION_MESSAGE));
    menuItems.put("help", item);

    item = new JMenuItem("Blur");
    item.addActionListener(e -> controller.applyOperation(currentImage, new Blur()));
    menuItems.put("blur", item);

    item = new JMenuItem("Sharpen");
    item.addActionListener(e -> controller.applyOperation(currentImage, new Sharpen()));
    menuItems.put("sharpen", item);

    item = new JMenuItem("Grayscale");
    item.addActionListener(e -> controller.applyOperation(currentImage, new Grayscale()));
    menuItems.put("grayscale", item);

    item = new JMenuItem("Sepia");
    item.addActionListener(e -> controller.applyOperation(currentImage, new Sepia()));
    menuItems.put("sepia", item);

    item = new JMenuItem("Red component");
    item.addActionListener(e -> controller.applyOperation(currentImage, new GrayscaleR()));
    menuItems.put("red-component", item);

    item = new JMenuItem("Green component");
    item.addActionListener(e -> controller.applyOperation(currentImage, new GrayscaleG()));
    menuItems.put("green-component", item);

    item = new JMenuItem("Blue component");
    item.addActionListener(e -> controller.applyOperation(currentImage, new GrayscaleB()));
    menuItems.put("blue-component", item);

    item = new JMenuItem("Value component");
    item.addActionListener(e -> controller.applyOperation(currentImage, new GrayscaleValue()));
    menuItems.put("value-component", item);

    item = new JMenuItem("Luma component");
    item.addActionListener(e -> controller.applyOperation(currentImage, new GrayscaleLuma()));
    menuItems.put("luma-component", item);

    item = new JMenuItem("Intensity component");
    item.addActionListener(e -> controller.applyOperation(currentImage, new GrayscaleIntensity()));
    menuItems.put("intensity-component", item);

    item = new JMenuItem("Vertical flip");
    item.addActionListener(e -> controller.applyOperation(currentImage, new FlipVertical()));
    menuItems.put("vertical-flip", item);

    item = new JMenuItem("Horizontal flip");
    item.addActionListener(e -> controller.applyOperation(currentImage, new FlipHorizontal()));
    menuItems.put("horizontal-flip", item);

    item = new JMenuItem("Brighten");
    item.addActionListener(e -> {
      String input = JOptionPane.showInputDialog(this, "Enter a brightening value");
      try {
        int value = Integer.parseInt(input);
        controller.applyOperation(currentImage, new Brighten(value));
      } catch (NumberFormatException nfe) {
        this.renderMessage("Invalid brightening value provided. Please provide an integer value.");
      }
    });
    menuItems.put("brighten", item);
  }

  /**
   * Add all the menu items to the menu bar and return it.
   *
   * @return the menu bar with all menu items added to it.
   */
  private JMenuBar createMenuBar() {
    // build 'file' menu, add load and save menu items
    file.add(menuItems.get("load"));
    file.add(menuItems.get("save"));

    // build 'edit' menu, add all operation menu items
    // filters
    filter.add(menuItems.get("blur"));
    filter.add(menuItems.get("sharpen"));
    edit.add(filter);

    // color transforms
    color.add(menuItems.get("grayscale"));
    color.add(menuItems.get("sepia"));
    edit.add(color);

    // component visualization
    component.add(menuItems.get("red-component"));
    component.add(menuItems.get("green-component"));
    component.add(menuItems.get("blue-component"));
    component.add(menuItems.get("luma-component"));
    component.add(menuItems.get("intensity-component"));
    component.add(menuItems.get("value-component"));
    edit.add(component);

    // flips
    flip.add(menuItems.get("vertical-flip"));
    flip.add(menuItems.get("horizontal-flip"));
    edit.add(flip);

    // miscellaneous
    other.add(menuItems.get("brighten"));
    edit.add(other);

    // help menu
    help.add(menuItems.get("help"));

    // add the menus
    menuBar.add(file);
    menuBar.add(edit);
    menuBar.add(help);

    return menuBar;
  }
}
