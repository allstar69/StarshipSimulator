package com.starshipsim.files;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;

import com.starshipsim.application.Driver;

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
	public static AudioClip loadSound(String filePath) {
		AudioClip sound = null;
		URL soundFile= FileIO.class.getResource(filePath);
		sound = Applet.newAudioClip(soundFile);

		return sound;
	}
	
	public static void serialize(Object o) {
		try {
			FileOutputStream fileOut = new FileOutputStream("object.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(o);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object deserialize(File file) {
		Object o = null;
		
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			o = in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return o;
	}

}
