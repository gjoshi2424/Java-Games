import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpaceShip extends GameObject {

	public SpaceShip(double x, double y, double wid, double ht) {
		super(x, y, wid, ht);
		getImage();
		// TODO Auto-generated constructor stub
	}
	
	public void getImage() { 
		try {
			URL url = getClass().getResource("/images/newSpaceShip.png");
			setImg(ImageIO.read(url));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void moveLeft(){
		this.setX(getX()-30);
	}
	public void moveRight(){
		this.setX(getX()+30);
	}
//	public void moveUp(){
//		this.setY(getY()-20);
//	}
//	public void moveDown(){
//		this.setY(getY()+20);
//	}
//	

}
