## Mosaic Generator
This program will take an input image, then use a repo of misc. nature images to generate a mosaic.

## Screenshots
Pictures can be found on my [Project Website](http://krishanhewitt.ca/projectPage.php#mosaicDiv).

## Tech/Libraries used
  -Java8  
  -EclipseIDE
  
## Features
   -User can specify how accurate they want the images to match  
   -User can provide their own library of images by specifying file path  
   
## Installation/Compilation
To create a mosaic using this program the 'base' variable will need to point to your input image
```
File base = new File("tiles/input.jpg");
```
You will also need to specify a folder of assorted images to use in the 'getImagesFromTiles' parameter
```
ArrayList<Tile> tileImages = getImagesFromTiles(new File("tiles")); 
```
