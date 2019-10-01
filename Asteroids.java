import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Asteroids extends GameObject{
	public Asteroids(double x, double y, double wid, double ht) {
		super(x, y, wid, ht);
		getImage();
		// TODO Auto-generated constructor stub
	}
	
	public void getImage() { 
		try {
			URL url = getClass().getResource("/images/legitAsteroid.png");
			setImg(ImageIO.read(url));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}


}
