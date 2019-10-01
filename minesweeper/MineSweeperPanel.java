package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class MineSweeperPanel extends JPanel {



	private static final int SQ = 25,// the width and height of a square
			H_PADDING = 10;

	private static final int TOP_PADDING = 23,
			BOTTOM_PADDING = 10;

	Image unClickedSquare, flaggedSquare, bomb, clicked, one, two, three, four, five, six, seven, eight; // Images to be displayed

	// this contains the mines.  zero means no mine, 1 means mine
	// using zeros and ones instead of booleans will make it easier 
	// to add up the number of mines surrounding a square.
	// when we know the size (when this panel is constructed) we will
	// create the grid to be the correct size and then fill it randomly
	// with the correct number of mines
	int[][] mineGrid;
	int[][] counterGrid;
	int[][] clickState;// 0 if unclicked, 1 if clicked, 2 if flagged

	// this will store the numbers that will be displayed when a 
	// square is clicked.  This will be created after creating the 
	// mineGrid, so that the correct numbers can be placed into it
	int[][] numbers;

	// This is how you make a 2D array and initialize it all in one step
	// this is a 2x3 matrix.  This first row represents the number of rows
	// for any gameboard, and the second represents the corresponding cols
	public final int[][] ROW_COL = {{10, 30, 50},
			{10, 16, 50}};
	// percentage of spaces with mines
	final double[] DIFF = {.125, .25, .6};
	// this is the sizeIndex that causes the user to input the dimensions
	final int CUSTOM_INDEX = 3;

	// more instance variables will be created!




	public MineSweeperPanel(int diffIndex, int sizeIndex) {
		// See below for the proper way to open images and other types of
		// resources
		openImages();

		// this uses the two ints below to access ROW_COL and 
		// create the 2D array of ints
		createGrid(diffIndex, sizeIndex);

		// base the size of this JPanel upon the dimension of the 2D array
		// which has just been initialized by the method above
		setPreferredSize(new Dimension(mineGrid[0].length*SQ+H_PADDING*2,mineGrid.length*SQ + TOP_PADDING+BOTTOM_PADDING));

		// See the method below for the BEST way to set up ways to interact with 
		// mouse events.  Similar to interact with dragging and moving mouse
		setUpClickListener();
	}


	private void setUpClickListener() {
		// Whoever has focus is who can interact with mouse and keyboard, etc
		this.requestFocusInWindow();

		// similar to having an entity ready to interact with the Mouse
		this.addMouseListener(new MouseListener() {
			/*
			 * If you want to detect mouse dragging, then use a mouseMotionListener
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// I like to avoid using this one because if you are moving
				// the mouse while you are trying to click, it sometimes doesn't
				// register the click.  A click is a press and release at the 
				// same location

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// when the mouse enters the panel, this is called

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// duh...

			}

			@Override
			public void mousePressed(MouseEvent click) {
				// use this method when you wish to detect a click
				System.out.println(click);

				// here are some things you can do with a MouseEvent
				System.out.println(click.getX());
				System.out.println(click.getPoint());
				System.out.println(click.getButton());// try clicking left and right buttons

				//// So... What should we do when we detect a click?
				// This is a good way to isolate the clicking and the behavior
				// to be done when the JPanel is clicked
				clickedAt(click);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// use this to find out when the mouse was released

			}

		});

	}


	protected void clickedAt(MouseEvent click) {
		// This is called when the panel is clicked.  What should we do?
		if(click.getX() < H_PADDING || click.getY()<TOP_PADDING ||
				click.getX() > this.getWidth()-H_PADDING ||
				click.getY() > this.getHeight()-BOTTOM_PADDING)
			return;

		int col = (click.getX()-H_PADDING)/SQ;
		int row = (click.getY()-TOP_PADDING)/SQ;
		System.out.println("row: "+row+" col: "+col);

		if(click.getButton()==1) {
			leftClick(row, col);
		}
		else if(click.getButton()==3){
			rightClick(row,col);
		}
		repaint();
	}


	private void rightClick(int row, int col) {
		if(clickState[row][col]==0)
			clickState[row][col]=2;
		else if(clickState[row][col]==2)
			clickState[row][col]=0;

	}


	private void leftClick(int row, int col) {

		if(clickState[row][col]!=0)
			return;

		// they have clicked something that should be revealed
		clickState[row][col]=1;

		if(mineGrid[row][col]==1){
			
		//	blowUp();
		}
		else {
			int minesAround = checkNeighbors(row, col);
			if(minesAround == 0)
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {

						if (!(i == 0 && j == 0)) {
							int new_r = row + i;
							int new_c = col + j;
							if (new_r >= mineGrid.length || new_r < 0 || 
									new_c >= mineGrid[0].length || new_c < 0) {
								continue;
							} 

							leftClick(new_r, new_c);

						}
					}
				}
		}

	}







	private void createGrid(int diffIndex, int sizeIndex) {
		int[] row_col;
		if(sizeIndex == this.CUSTOM_INDEX) {
			row_col = createCustomSize();
		}
		else {
			row_col = new int[] {ROW_COL[0][sizeIndex], ROW_COL[1][sizeIndex]};
		}
		mineGrid = new int[row_col[0]][row_col[1]];

		clickState = new int[row_col[0]][row_col[1]];

		System.out.println(mineGrid.length+"  by  "+mineGrid[0].length);

		int rows = mineGrid.length,
				cols = mineGrid[0].length;

		int mines = (int) (DIFF[diffIndex]*rows*cols);

		placeMines(mines);
		this.placeNumbers();

	}
	private void placeMines(int mines) {

		int rows = mineGrid.length,
				cols = mineGrid[0].length;
		for(int i = 0; i<mines; i++) {
			int r = (int) (Math.random()*rows);
			int c = (int) (Math.random()*cols);

			if(mineGrid[r][c] == 0) {
				mineGrid[r][c] = 1;
			}
			else {
				i--;
			}
		}
		//printMines();

	}


//	private void printMines() {
//
//		for(int r = 0; r < mineGrid.length; r++) {
//			for(int c = 0; c < mineGrid[r].length; c++) {
//				if(mineGrid[r][c] == 1){
//							
//				System.out.print(mineGrid[r][c]);
//			}
//			System.out.println();
//		}
//		}


	


	private int[] createCustomSize() {
		int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows"));
		int cols = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of cols"));
		return new int[]{rows,cols};
	}

	//public void checkMines(){
	//	
	//}





	@Override
	public void paintComponent(Graphics g) {
		/*
		 * PLEASE, PLEASE, PLEASE, when you use Java Graphics, have this method be 
		 * the "trigger" for ALL drawing to be done should begin HERE, in this method!!!!!
		 * 
		 * All code needed to redraw this panel from scratch goes here.  JPanels
		 * are double-buffered by default, so don't worry about extra things that are redrawn
		 */

		//This is how you draw images
		super.paintComponent(g);

		
		
		
		for(int r = 0; r < mineGrid.length; r++)
			for(int c = 0; c< mineGrid[0].length;c++) {
				 
				if(clickState[r][c] == 0)
					g.drawImage(this.unClickedSquare, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
				else if(clickState[r][c] == 2)
					g.drawImage(this.flaggedSquare, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
				else if(clickState[r][c] == 1) {
					
					if(mineGrid[r][c] == 1){
						blowUp();
						System.out.println("bomb");
						g.drawImage(this.bomb, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						blowUp();
						//JOptionPane.showMessageDialog(null, "u suck", "u suck", JOptionPane.ERROR_MESSAGE);

						//System.exit(0);



					}
					if(mineGrid[r][c] == 0){

						int mineCounter = checkNeighbors(r, c);
						System.out.println(mineCounter + " row: "+r+" col: "+c);

						if(mineCounter == 0){
							g.drawImage(this.clicked, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);	
						}
						else if (mineCounter == 1){
							g.drawImage(this.one, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if (mineCounter == 2){
							g.drawImage(this.two, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if(mineCounter == 3){
							g.drawImage(this.three, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if(mineCounter == 4){
							g.drawImage(this.four, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if(mineCounter == 5){
							g.drawImage(this.five, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if(mineCounter == 6){
							g.drawImage(this.six, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if(mineCounter == 7){
							g.drawImage(this.seven, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}
						else if(mineCounter == 8){
							g.drawImage(this.eight, H_PADDING + c*SQ,TOP_PADDING + SQ*r,SQ, SQ,null);
						}


					}



				}
			}

	}

	private void placeNumbers(){
		int counter = 0;
		counterGrid = new int[mineGrid.length][mineGrid[0].length];
		for(int r = 0; r < mineGrid.length; r++){
			for(int c = 0; c< mineGrid[0].length;c++) {


				//if(clickState[r][c] == 1) {
				//counter = 0;
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {

						if (!(i == 0 && j == 0)) {
							int new_r = r + i;
							int new_c = c + j;
							if (new_r >= mineGrid.length || new_r < 0 || 
									new_c >= mineGrid[0].length || new_c < 0) {
								continue;
							} 

							if (mineGrid[new_r][new_c] == 1){

								//counter++; 
								counterGrid[r][c]++;
								System.out.println(counter);
								System.out.println(new_r);
								System.out.println(new_c);

							}

						}
					}
					//clickState[r][c] = 0;
				}
				//	}//ends clickState
			}//ends col check
		}
	}
	public int checkNeighbors(int rows, int cols) {


		return counterGrid[rows][cols];
	}



	//g.drawImage(this.flaggedSquare, 100,10,null);

	//
	//		// you can draw shapes
	//		g.setColor(Color.red);
	//		g.drawLine(200, 30, 80, 200);
	//
	//		// you can make your own colors
	//		g.setColor(new Color(68, 200, 100));
	//		// (x,y) of the upper left hand corner, width = 25, height = 50
	//		g.fillRect(200, 50, 25, 50);



	private void openImages() {

		/*
		 * FOLLOW THE EXAMPLE BELOW FOR OPENING RESOURCES!!!!!!!!!!!!
		 */
		try {
			URL url = getClass().getResource("res/images/unclicked.png");
			unClickedSquare = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the unclicked.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/flagged.png");
			flaggedSquare = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the unclicked.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/bomb.png");
			bomb = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the bomb.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/clicked.png");
			clicked = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the clicked.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 1.png");
			one = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 1.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 2.png");
			two = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 2.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 3.png");
			three = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 3.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 4.png");
			four = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 4.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 5.png");
			five = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 5.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 6.png");
			six = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 6.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 7.png");
			seven = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 7.png");
			e.printStackTrace();
		}
		try {
			URL url = getClass().getResource("res/images/Number 8.png");
			eight = ImageIO.read(url);
		} catch (IOException e) {
			System.out.println("Problem opening the Number 8.png");
			e.printStackTrace();
		}
	}


private void blowUp() {
	mineGrid = new mineGrid[0][0];
	for(int r = 0; r < mineGrid.length; r++)
		for(int c = 0; c< mineGrid[0].length;c++) {
			if(mineGrid[r][c] == 1){
				clickState[r][c] = 1; 
				
				
				//JOptionPane.showMessageDialog(null, "u suck", "u suck", JOptionPane.ERROR_MESSAGE);

				//System.exit(0);

		}

}
}}

