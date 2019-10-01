import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OpeningScreen extends JPanel{
	//public boolean legitGame = false;
	Dimension defaultDim = new Dimension(800,2000);
	private Image backgroundImage;
	
	public OpeningScreen() {
		this(new Dimension(550, 800));
		// TODO Auto-generated constructor stub
		
	}



	public OpeningScreen(Dimension dim) {
		defaultDim = dim;
		this.setPreferredSize(defaultDim);
		this.setBackground(new Color(0,0,0));
		this.setLayout(null);
		openBackgroundImage();
		//makeImage();
		makeButtons();
		
	}
	public void openBackgroundImage() {
		// TODO Auto-generated method stub
		
	
			try {
				System.out.println("Background Loaded");
				//java.net.URL url = getClass().getResource("res/images/spaceInvaderBackground.png");
				backgroundImage = new ImageIcon("res/images/spaceInvaderBackground.png").getImage();
				
				
			} catch (Exception e) {
				System.out.println("Problem opening background image");
				e.printStackTrace();
			}
		
		}
		
	
	 public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, 800,2000, this);	
		}
	
	
	public void music() {
		  try {
		         // Open an audio input stream.
		         java.net.URL url = this.getClass().getClassLoader().getResource("humble.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         clip.start();
		      } catch (UnsupportedAudioFileException e) {
		         e.printStackTrace();
		      } catch (IOException e) {
		         e.printStackTrace();
		      } catch (LineUnavailableException e) {
		         e.printStackTrace();
		      }
		   }
	
		


	
	private void makeButtons(){
//		Button start =new Button("Start"); 
//		
//		start.setBounds(300, 400, 50, 50);
//		this.add(start);
		JButton  instruct = new JButton(); 
		JPanel panel = new JPanel(); 
		instruct.setLayout(null);
		instruct.setBounds(200,600,100,25);
		instruct.setText("Instructions");
		instruct.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
			music();
			JFrame other = new JFrame();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			Instructions  instruct = new Instructions();
			other.add(instruct);
			other.pack();
			other.setVisible(true);
			other.setDefaultCloseOperation(other.EXIT_ON_CLOSE);		    //gameFrame.add(play);
			}
		});;
		JButton start = new JButton();
		start.setLayout(null);
		start.setBounds(300, 600, 75, 25);
		start.setText("Start");
		start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				music();
				JFrame gameFrame = new JFrame();
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				MovingPanel mop = new MovingPanel();
				gameFrame.add(mop);
				gameFrame.pack();
				gameFrame.setVisible(true);
				gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
				
				
			}
		});;
	this.add(start);
	this.add(instruct);
	}
	

}
