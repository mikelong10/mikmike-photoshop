ASSIGNMENT 6 - June 24 2022<br/>

-- ADDITIONS AND DESIGN CHANGES -- <br/>
Model:<br/>
To support the new histogram feature for the GUI view, we added one method,
histogram(Function<Pixel, Integer> data) to the ManipulableImage interface to provide the view
of the data needed to display the histogram.

View:<br/>
Using Java Swing, we built an interactive GUI Image Processing program. Our program has a
convenient, easy-to-use menu bar at the top for loading and saving images, applying filters and
other image modifications, and displaying a help menu for users. The main panel of the JFrame is
where the loaded image is displayed, and will dynamically update whenever operations are applied.
To the right of the image is where the histograms are displayed, constantly showing the red,
green, blue, and intensity distributions for the current image. Helpful information/error
messages are displayed at the bottom of the screen.<br/>
To create the histograms, we implemented a custom class that extends JPanel that overrides the
paintComponent method to draw histograms. Otherwise, our previous design worked seamlessly with
Java Swing and our operations with ActionListeners.

Controller:<br/>
We implemented another controller class for the GUI because the functionality the controllers
are quite different, as the GUI controller must respond to user inputs in the form of mouse
clicks rather than text/script commands.

-- IMAGE CITATION --<br>
Image retrieved from https://www.travelandleisure.com/travel-guide/boston

ASSIGNMENT 5 - June 17 2022<br/>

-- SCRIPT UPDATE --<br/>
The relative path to the script file with respect to the JAR file is script. Provide the script as
a command line argument by using the -file flag followed by the path (i.e. ImageProcessing.java
-file script). The script will demonstrate the functionality of the program by running all
operations and saving resulting images in the scriptOut folder.

-- ADDITIONS AND DESIGN CHANGES -- <br/>
Model:<br/>
Our model had to be updated slightly to add support for transparency in images, and for saving
images using Java's ImageIO library. To add transparency, we implemented a new Pixel, RGBAPixel,
which extends RGBPixel and offers support for a transparency (alpha) channel. For loading and saving
images with transparency, we added a method to Pixel called getColor, which returns a
java.awt.paint.Color representation of a pixel. We added a method to our ManipulableImage called
createPixel(), which takes in an array of channel values and creates either an RGB or RGBA pixel
depending on the given array. We also added a method called toRenderedImage(), which returns a
java.awt.RenderedImage representation of a ManipulableImage.

View:<br/>
Our view did not need to be changed or updated for this assignment.

Controller:<br/>
To add support for different file formats, we had to update our controller slightly. We no longer
have a FileHandler as a field of the class, but rather, we dynamically create FileHandler objects
depending on what kinds of files the user wants to load or save. We also put four more entries
into the map of known commands, representing blur, sharpen, sepia, and grayscale operations.

Operations:<br/>
To add support for filtering and color transformations, we created new implementations of Operation
called Filter and ColorTransform. These base classes provide the functionality for applying
filtering kernels and color transform matrices on images. To implement specific filters and color
transformations, we simply extended Filter and ColorTransform and called their constructors with
the specific filter's or color transformation's kernel or matrix. This is how we implemented the
blur, sharpen, grayscale, and sepia operations.

FileHandler:<br/>
To add support for more conventional file formats, we created a new implementation of FileHandler
called GenericFileHandler, which uses Java's ImageIO library.

ASSIGNMENT 4 - June 10 2022<br/>

-- DESIGN OVERVIEW -- <br/>
Main method:<br/>
The main method is located in the imageprocessing package, named ImageProcessing.java.

Model:<br/>
The model consists of two interfaces found in the imageprocessing.model package: ManipulableImage,
which represents an image consisting of a series of pixels, and Pixel, which represents a pixel.
Both interfaces contain general utility methods related to images and pixels. We currently have one
implementation of ManipulableImage, called PixelGridImage, which represents an image as a 2D array
of pixels, and one implementation of Pixel, RGBPixel, representing a standard 24-bit color pixel.

View:<br/>
The view interface is found in the imageprocessing.view package. The view has one method,
renderMessage(), which renders the given String message. We currently have one implementation of
the view named TextView, which takes an appendable destination as a constructor parameter and
transmits text-based messages to it.

Controller:<br/>
The controller interface is found in the imageprocessing.controller package. The controller has just
one method, processImage(), which reads input and executes commands based on the input. We currently
have one implementation of the controller named SimpleIPController, which takes a Readable input
source, a FileHandler, and a view as constructor parameters.

Operations:<br/>
Our Operation interface, located in the imageprocessing.operations package, represents an operation
which can be applied to a ManipulableImage. The interface has just one method, modify(), which takes
a given ManipulableImage and returns a new, modified version of it. Each implementing class of this
interface represents a specific operation, such as brightening, flipping in a direction, or
grayscaling based on a component of an image.

FileHandler:<br/>
Our FileHandler interface, in the imageprocessing.filehandler package, represents an object which
performs operations between ManipulableImages and images of different file types. The interface has
two methods, load(), which takes a given file path to an image and returns a ManipulableImage
representation of the image, and save(), which takes a given ManipulableImage and file path, and
saves the ManipulableImage as a file at the path. Each implementation of FileHandler represents an
image file type. We currently have one implementing class, PPMHandler, which can convert between
ManipulableImages and ASCII PPM files.

-- SCRIPT INSTRUCTIONS --<br/>
A script file named script has been provided in this folder. To run the script, provide its path
("script") as a command line argument when running the main method. The script will load the
res/oasis.ppm file, run some operations on it, and save resulting images in the scriptOut folder.

-- IMAGE CITATION --<br/>
The image used for the res/ folder is our own photograph, and we authorize its use in this project.
