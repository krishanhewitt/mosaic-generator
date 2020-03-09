import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class MosaicMaker {

	public static void main(String[] args) throws IOException{
		System.out.println("Reading tiles...");
		ArrayList<Tile> tileImages = getImagesFromTiles(new File("tiles")); 	

		System.out.println("Read the base image");
		File base = new File("tiles/input.jpg");
		BufferedImage img = ImageIO.read(base);

		int scale = 1;
		BufferedImage newImage = new BufferedImage(10000, 10000, BufferedImage.TYPE_3BYTE_BGR); 	//instantiate an output image
		Tile chunkArray[][] = new Tile[(img.getHeight()/60) * scale][(img.getWidth()/80) * scale];
		chunkArray = splitImage(img, scale, chunkArray); 	//divide input image into chunks
		createImage(newImage, scale, chunkArray, tileImages);
	}
	
	//iterate through input image chunks and compare the average rgb of them to the pics in the arrayList
	public static void createImage(BufferedImage newImage, int scale, Tile[][] chunkArray, ArrayList<Tile> tileImages) throws IOException {
		Graphics2D pic = newImage.createGraphics();
		Tile bestFit = null;
		for(int i = 0; i < chunkArray.length; i++) {
			for(int j = 0; j < chunkArray[i].length; j++) {
				for(int k = 0; k < tileImages.size(); k++) {
					int closest = 40; 	//determines how close we want the rgb to match
					if(chunkArray[i][j].difference(tileImages.get(k)) < closest){
						closest = chunkArray[i][j].difference(tileImages.get(k));
						bestFit = tileImages.get(k);
						pic.drawImage(bestFit.image, j * (80/scale), i * (60/scale), 80/scale, 60/scale, null); 	//replace chunk with image from arraylist
						System.out.println("Swapped " + i + ", " + j + " for " + k);
						break;
					}else {
						pic.drawImage(chunkArray[i][j].image, j * (80/scale), i * (60/scale), 80/scale, 60/scale, null);
					}
				}
			}
		}
		File output = new File("tiles/output.jpg");
		ImageIO.write(newImage, "jpg", output);
		System.out.println("Mosaic created");
	}

	//create an arrayList with a (given) library of random images
	private static ArrayList<Tile> getImagesFromTiles(File tilesDir) throws IOException{
		ArrayList<Tile> tileImages = new ArrayList<Tile>();
		File[] files = tilesDir.listFiles();
		for(File file : files){
			BufferedImage img = ImageIO.read(file);
			System.out.println("ArrayList contents: " + tilesDir);
			if (img != null){
				System.out.println("Reading " + file);
				tileImages.add(new Tile(img));
			} else {
				System.err.println("null image for file " + file.getName());
			}
		}
		return tileImages;
	}

	//divide input image into chunks and return a 2d array of them
	public static Tile[][] splitImage(BufferedImage input, int scale, Tile[][] chunkArray) {
		for(int i = 0; i < (input.getHeight()/60) * scale; i++) {
			for(int j = 0; j < (input.getWidth()/80) * scale; j++) {
				BufferedImage subImage = input.getSubimage(j * (80/scale), i * (60/scale), 80/scale, 60/scale);
				chunkArray[i][j] = new Tile(subImage);
			}
		}
		return chunkArray;
	}
}

class Tile {
	public int averageRed;
	public int averageGreen;
	public int averageBlue;
	public BufferedImage image;

	Tile(BufferedImage i){
		image = i;
		calculateAverage();
		System.out.println("Average RGB " + averageRed + ", " + averageGreen + ", " + averageBlue);
	}
	
	//iterate through each pixel and calculate the average rgb values for the image
	private void calculateAverage(){
		int width = image.getWidth();
		int height = image.getHeight();
		long red = 0, green = 0, blue = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color pixel = new Color(image.getRGB(x, y));
				red += pixel.getRed();
				green += pixel.getGreen();
				blue += pixel.getBlue();
			}
		}
		int numPixels = width * height;
		averageRed = (int)(red / numPixels);
		averageGreen = (int)(green / numPixels);
		averageBlue = (int)(blue / numPixels);
	}

	//compares tile passed in to the current tile
	public int difference(Tile tile) {
		int d = Math.abs(averageRed - tile.averageRed);
		d += Math.abs(averageGreen - tile.averageGreen);
		d += Math.abs(averageBlue - tile.averageBlue);
		return d;
	}
}