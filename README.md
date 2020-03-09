## Mosaic Generator
This Java program will take an input image, then use a collection of miscellaneous nature images to generate a mosaic.

## Screenshots
Pictures can be found on my [Project Website](http://krishanhewitt.ca/projectPage.php#mosaicDiv).
  
## Features
   -User can specify how accurate they want the images to match  
   -User can provide their own input/library of images by specifying file path  
   
## Installation/Compilation
To create a mosaic using this program the 'base' variable will need to point to your input image
```
File base = new File("tiles/input.jpg");
```
You will also need to specify a folder of assorted images to use in the 'getImagesFromTiles' parameter
```
ArrayList<Tile> tileImages = getImagesFromTiles(new File("tiles")); 
```
