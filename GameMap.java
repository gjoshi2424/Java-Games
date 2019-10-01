import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public abstract class GameMap {
	private List<GameObject> movers;
	Image backgroundImage;


	public GameMap() {
		movers = new ArrayList();
		openBackgroundImage();
	}
	
//	public void setUpRect() { 
//		//check each movers
//		for(int i = 0; i<movers.size(); i++) {
//			movers.get(i).setUpRectangle( );
//		}
//	}

	
	public void moveAll(){
		for(GameObject mo:movers){
			//System.out.println("moving: "+mo); 
			mo.move();
			mo.setUpRectangle();
		}
		//setUpRect();
	}
	public abstract void openBackgroundImage();


	public void add(GameObject go) {
		movers.add(go);
	}
	public void remove(GameObject go) {
		movers.remove(go);
	}

	public abstract void shoot();// {
	//		// TODO Auto-generated method stub
	//		System.out.println("shoot"); 
	//	}
	public abstract void left();// {
	//		// TODO Auto-generated method stub
	//		System.out.println("left"); 
	//	}
	//
	public abstract void right();// {
	//		// TODO Auto-generated method stub
	//		System.out.println("right");
	//	}
	//
	public abstract void up();
	
	public abstract void down();
	public void draw(Graphics g) {
		for(GameObject m:movers){
			//System.out.println(((GameObject) m).getX());
			m.draw(g);
		}
	}

	public abstract void tick() ;

//	public abstract void generate();
}
