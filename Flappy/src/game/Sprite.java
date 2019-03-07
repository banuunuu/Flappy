package game;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
	public static BufferedImage getSprite(String fileName) throws IOException {
		return ImageIO.read(Sprite.class.getResourceAsStream(fileName)); // Returns an image, images get packed when used as .jar
	}
	
}
