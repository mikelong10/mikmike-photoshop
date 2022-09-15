ASSIGNMENT 6 - June 24 2022<br/>
Running the program:

- To run the program with the default GUI view, run the JAR file in the res/ folder without any
  command-line arguments  (i.e. java -jar program.jar).
- To run the program through the command line and enter inputs yourself, run the JAR file and
  provide the -text flag (i.e. java -jar program.jar -text)
- To run the program with a script file as input, run the JAR and provide the -file flag provided by
  the path  (i.e. java -jar program.jar -file aDir/aFile.txt)

GUI Instructions:<br/>

- All operations can be accessed through the 'file' and 'edit' menus located at the top of the
  screen.
- The program will display messages at the bottom of the screen.
- Images will appear in the center of the screen once loaded and can be scrolled if they are larger
  than the window.
- Histograms for red, green, blue, and intensity components will appear on the right side of the
  screen once an image is loaded.

Loading and saving images:<br/>

- To load an image: file -> load -> select the file you would like to load from the popup menu.
- To save an image: file -> save -> select the file you would like to save the currently loaded
  image as from the popup menu.

Modifying images:<br/>

- Operations are grouped by type (filter, color transform, component visualization, flip, and
  other.)
- To apply an operation: edit -> select operation type -> click on your desired operation.
- For instance, to apply a sharpening filter: edit -> filter -> sharpen.
- To apply a brightening/darkening operation: edit -> other -> brighten -> input your desired
  brightening value as an integer into the popup menu. A positive value brightens an image, and a
  negative value darkens
  it.

ASSIGNMENT 5 - June 17 2022<br/>
Running the program:<br/>
To run the program in interactive mode (user-entered commands), simply run the program without
any command line arguments. To run the program with a script file, as input, provide the -file flag
followed by the path to the script file (i.e. -file res/script).

General:<br/>
A help menu is available in the program by typing 'h' or 'help'. You can quit at any time
by pressing 'q' or 'quit'.

Loading images:<br/>
To load an image, type "load", followed by the path of the file to load and the name the image
should be referred to as. (For example: load someDir/someImage.bmp anImage)

Performing operations:<br/>
To see a list of available operations, see the help menu. Operations are generally run by typing
the operation name, followed by the image name you want to perform the operation on, and finally
the name the resulting image should be referred to as. (For example: sepia someImage anotherImage.)
The brighten operation takes an additional integer parameter representing how much to brighten (or
darken if given a negative value) the image. (For example: brighten -25 someImage someImage.)

Saving images:
To save an image, type "save", followed by the image to save, and the file path to save the image
to. The file path should include the name of the image and the image format.
(For example: save thisImage aDirectory/anotherDirectory/image.png).