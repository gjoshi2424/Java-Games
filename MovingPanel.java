import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class MovingPanel extends JPanel {
	Dimension defaultDim = new Dimension(800,2000);
	GameMap gm;
	
	int x;
	int y;
	
	
	

	private Timer t;
	private Timer other;
	


	private void makeGameMap() {
		gm = new SpaceMap();// let the map know what dim i
		t = new Timer(45, new ActionListener() {// fires off every 10 ms
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gm.tick();// I tell the GameMap to tick... do what
					// you do every time the clock goes off.
				repaint();// naturally, we want to see the new view
			}
				
		});
//		other = new Timer(4500, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				gm.generate();// I tell the GameMap to tick... do what
//					// you do every time the clock goes off.
//				repaint();// naturally, we want to see the new view
//			}
//				
//		});
		// this semicolon is here because it is the end of the new Timer construction...
	}
		
	

	
	

	public MovingPanel() {
		this(new Dimension(550, 800));
		// TODO Auto-generated constructor stub
	}



	public MovingPanel(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		this.setBackground(new Color(0,0,0));
		makeGameMap();
		setUpKeyMappings();
		t.start();
	}




	@Override

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gm.draw(g);
		
	}
	private void setUpKeyMappings() {

		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "shoot");
		this.getActionMap().put("shoot", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				gm.shoot();
				// just add shoot here
				repaint();
				
			}
			
		}
		);
		

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		this.getActionMap().put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				gm.left();

				repaint();
				
			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		this.getActionMap().put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.right();

				repaint();
			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
		this.getActionMap().put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.up();

				repaint();
			}
		});

		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		this.getActionMap().put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gm.down();

				repaint();
			}
		});


	}

}
