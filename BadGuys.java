import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BadGuys extends GameObject {
	int number;
	public BadGuys(double x, double y, double wid, double ht) {
		super(x, y, wid, ht);
		number +=1;
		getImage();
		// TODO Auto-generated constructor stub
	}
	public void getImage() { 
		try {
			URL url = getClass().getResource("/images/badSpaceShip.png");
			setImg(ImageIO.read(url));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getNumber(){
		return number;
	}
	

}
