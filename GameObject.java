import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class GameObject implements MovingObject {
	private double speed, x,y,width, height, health, damage;

	private int direction;
	private Color color;
	private Image img;
	private Rectangle rectangle; 
	private int colour;
	public static final int NORTH = 1, SOUTH = 3, EAST = 2,WEST = 4;


	public GameObject(double x, double y, double wid, double ht) {
		speed = 0;
		this.setX(x);
		this.setY(y);
		this.setWidth(wid);
		this.setHeight(ht);
		this.setRectangle();
	}
	public double getSpeed() {
		return speed;
	}

	public boolean hit(GameObject g) {
		return this.rectangle.intersects(g.getBoundingRect());
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle() {
		setUpRectangle();
	}

	

@Override
public void move() {
	if(direction == 1) {
		x-=speed;
	}
	else if(direction == 2) {
		x+=speed;
	}
	this.setRectangle();
	

	// there should be some checking that takes place

}

public void setUpRectangle() { 
	 rectangle = new Rectangle((int)x, (int)y, (int)width-20, (int)height-15);
}
@Override
public Rectangle getBoundingRect() {
	return rectangle; 
}
public void removeBoundingRectangle(){
	rectangle = new Rectangle((int)x-100000, (int)y-1000000, (int)width-20, (int)height-15);
}

public double getHeight() {
	return height;
}

public void setHeight(double height) {
	this.height = height;
}

@Override
public void draw(Graphics g) {
	g.drawImage(getImg(), (int)getX(), (int)getY(),(int) this.getWidth(),(int) this.getHeight(), null);
	//System.out.println(this);
}
public Image getImg() {
	return img;
}
public void setImg(Image img) {
	this.img = img;
}
public int getColour() {
	return colour;
}
public void setColour(int colour) {
	this.colour = colour;
}

}
