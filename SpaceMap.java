import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SpaceMap extends GameMap {
	Color backGround = new Color(0, 0, 0);
	SpaceShip spaceShip;
	BadGuys badGuy;
	SpaceBackground spaceBackground;
	int shots = 0;
	blueLazer boarderHOne;
	blueLazer boarderHTwo;
	verticalLazer vOne;
	verticalLazer vTwo;
	double oldX;
	double oldY;
	int badGuyTracker = 0;
	int bulletCounter = 0;
	int powerUpLength= 1;
	int finalScore =0;
	
	
	List<Bullet> lazer =  new ArrayList<Bullet>();
	List<BadGuys> aliens =  new ArrayList<BadGuys>();
	List<badBullets> redBullets =  new ArrayList<badBullets>();
	List<Lives> life =  new ArrayList<Lives>();
	List<Pill> powerUp = new ArrayList<Pill>();
	List<Asteroids> roc = new ArrayList<Asteroids>();




	public SpaceMap(){
		makeCharacters();	
		tick();
	}
	private void makeCharacters() {
		spaceBackground = new SpaceBackground(0,0,1500, 1800);
		this.add(spaceBackground);

		spaceShip = new SpaceShip(225, 500, 70, 70);
		this.add(spaceShip);

//		boundry = new blackBoundry(50, 500, 50, 30);
//		this.add(boundry);
		
		double alienX = (Math.random()*50)*12;
		aliens.add(new BadGuys(alienX, 0, 70, 70));
		this.add(aliens.get(aliens.size()-1));
		
		double pillX = (Math.random()*50)*11;
		powerUp.add(new Pill(pillX, 530, 35, 20));
		this.add(powerUp.get(powerUp.size()-1));
		
		double rockRandom = (Math.random()*50)*11;
		roc.add(new Asteroids(rockRandom, 0, 40, 40));
		this.add(roc.get(roc.size()-1));
		
		
		
		
		
		makeLives();
		makeBoarders();
		moveAll();
	}
	
	
	private void bulletAlienCollision(){
		
			for(Bullet b: lazer){
				for(BadGuys x: aliens)
					if(b.hit(x)){
						x.removeBoundingRectangle();
						super.remove(x);
						b.removeBoundingRectangle();
						super.remove(b);
						finalScore += 50;
				}
			
			for(Asteroids a : roc){
				if(b.hit(a)){
					a.removeBoundingRectangle();
					super.remove(a);
					b.removeBoundingRectangle();
					super.remove(b);
					finalScore += 10;
				}
			}
		}
	}
	private void generateRocks(){
		if(roc != null){
			if(roc.get(roc.size()-1).getY() >200){
				double rock = (Math.random()*25)*12;
				Asteroids rk = new Asteroids(rock, 0, 40, 40);
				roc.add(rk);
				this.add(rk);		
			}
			
		}
	}                               
	private void lifeLost(){
		if(aliens.size() != 0){
			
		for(BadGuys b: aliens){
			if(b.hit(boarderHOne)){
				if(life.size() != 0){
					super.remove(life.remove(0));
					b.removeBoundingRectangle();
					super.remove(b);
				}
				
			}
			else if(life.size() == 0){
				//super.remove(life.remove(0));
				JFrame parent = new JFrame();
				JOptionPane.showMessageDialog(parent, "You Lose!\n Your final score is:" + finalScore);
			}
			
		}
		}
	}
	private void RockMovement(){
		if(roc!=null){
			for(Asteroids r: roc){
				r.setY(r.getY()+3);
			}
		}
	}
	private void shipAlienColl(){
		
		if(aliens != null){
			
			//for(BadGuys a: aliens){
			for(int i = 0; i < aliens.size(); i++){
				
				if(aliens.get(i).hit(spaceShip)){
					if(life.size() != 0){
						super.remove(life.remove(0));
						aliens.get(i).removeBoundingRectangle();
						super.remove(aliens.get(i));
						
						
					}
				}
						else if(life.size() == 0){
					//super.remove(life.remove(0));
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, "You Lose!\n Your final score is:" + finalScore);
				}
				

				}
			}
		}
	private void rockShipColl(){
		for(int j = 0; j < roc.size(); j++){
			if(roc.get(j).hit(spaceShip)){
				if(life.size() != 0){
					super.remove(life.remove(0));
					roc.get(j).removeBoundingRectangle();
					super.remove(roc.get(j));
			}
		}
			else if(life.size() == 0){
				//super.remove(life.remove(0));
				JFrame parent = new JFrame();
				JOptionPane.showMessageDialog(parent, "You Lose!\n Your final score is:" + finalScore);
			}
		}
	}
	
//private void anotherLifeLost(){
//	for(int i = 0; i < aliens.size(); i++){
//		if(life.size() != 0){
//		if(aliens.get(i).hit(boundry)){
//			if(life.size() != 0){
//				super.remove(life.remove(0));
//			}
//		}
//			else if(life.size()== 0){
//				JFrame parent = new JFrame();
//				JOptionPane.showMessageDialog(parent, "You Lose!\n Your final score is:" + finalScore);	
//			}
//		}
//	}
//	}
	
	private void shipPillColl(){
		for(int i = 0; i < powerUp.size(); i++){
		if(spaceShip.hit(powerUp.get(i))){
			powerUp.get(i).removeBoundingRectangle();
			super.remove(powerUp.remove(i));
			bulletCounter = 10;
			powerUpLength--;
		}
	}
}


	private void bounceBack(){
		if(spaceShip.hit(vOne)){
			spaceShip.setX(oldX+10);
			spaceShip.setY(oldY);
		}
		else if(spaceShip.hit(vTwo)){
			spaceShip.setX(oldX-10);
			spaceShip.setY(oldY);
		}
		else if(spaceShip.hit(boarderHOne)){
			spaceShip.setX(oldX);
			spaceShip.setY(oldY-10);
		}
		else if(spaceShip.hit(boarderHTwo)){
			spaceShip.setX(oldX);
			spaceShip.setY(oldY+10);
		}
	}

	
	private void makeLives(){
		int x= 450;
		for(int i = 0; i < 3; i++){
			x+=20;
			life.add(new Lives(x, 20, 20,20));
			this.add(life.get(i));
		}
	}
	private void makePill(){
		if((bulletCounter == 0 && powerUpLength == 0)){
			double pillX = (Math.random()*50)*10;
			powerUp.add(new Pill(pillX, 530, 35, 20));
			this.add(powerUp.get(powerUp.size()-1));
			powerUpLength++;
		}
	}
	private void badGuyShooters(){
		if(aliens != null){
			for(BadGuys b: aliens){
				if(b.getY() == 200){
					redBullets.add(new badBullets(b.getX(), b.getY(), 50,50));
					this.add(b);

				}
			}
		}
	}
	private void badBulletMovement(){
		if(redBullets != null){
			for(int i = 0; i < redBullets.size(); i++){
				redBullets.get(i).setY(redBullets.get(i).getY()-15);
			}

		}
	}
	private void makeBoarders(){
		boarderHOne = new blueLazer(0,680,1500,200);
		this.add(boarderHOne);
		boarderHTwo = new blueLazer(0,-180,1500,200);
		this.add(boarderHTwo);
		vOne = new verticalLazer(-180,0,200,1800);
		this.add(vOne);
		vTwo = new verticalLazer(520,0,200,1800);
		this.add(vTwo);
	}
	private void CreateNewLaser(){
		if(bulletCounter > 0){
		lazer.add(new Bullet(spaceShip.getX()+10, spaceShip.getY()-40, 50, 50));
		this.add(lazer.get(lazer.size()-1));
		bulletCounter--;
		}
	}
	private void badGuyMovement(){
		if(aliens != null){
			for(BadGuys b: aliens){
				b.setY(b.getY()+7);
			}
		}
	}
	private void generateAliens(){
		if(aliens != null){
			if(aliens.get(aliens.size()-1).getY() >200){
				double alienX = (Math.random()*25)*12;
				aliens.add(new BadGuys(alienX, 0, 70, 70));
				this.add(aliens.get(aliens.size()-1));	
				badGuyTracker++;
			}

		}
	}
	
	@Override
	public void openBackgroundImage() {
		
	}


	@Override
	public void shoot() {

		CreateNewLaser();
		super.moveAll();

	}
	@Override
	public void left() {

		spaceShip.moveLeft();
	}
	@Override
	public void right() {

		spaceShip.moveRight();

	}
	@Override
	public void up(){

		//spaceShip.moveUp();
	}
	@Override
	public void down(){

		//spaceShip.moveDown();
	}
	
	private void lazerMovement(){
		if(lazer != null){
			for(int i = 0; i < lazer.size(); i++){
				lazer.get(i).setY(lazer.get(i).getY()-15);
			}

		}
	}
	public void tick() {
		oldX = spaceShip.getX();
		oldY = spaceShip.getY();
		bounceBack();
		lazerMovement();
		bulletAlienCollision();
		shipPillColl();
		badGuyMovement();
		shipAlienColl();
		generateAliens();
		badGuyShooters();
		badBulletMovement();
		makePill();
		RockMovement();
		generateRocks();
		rockShipColl();
		lifeLost();
	//	anotherLifeLost();
		
		moveAll(); 
	}
	

}
