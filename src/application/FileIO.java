package application;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileIO {

	public static Image loadImage(String filePath) {
		Image image = null;

		try {
			File file = new File(filePath);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Error: Cannot load " + filePath + ".");
		}

		return image;
	}

}
