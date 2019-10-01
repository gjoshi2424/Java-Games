import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Instructions extends JPanel {
	
		Dimension defaultDim = new Dimension(800,2000);
		public Instructions() {
			this(new Dimension(550, 800));
			// TODO Auto-generated constructor stub
			
		}



		public Instructions(Dimension dim) {
			defaultDim = dim;
			this.setPreferredSize(defaultDim);
			this.setBackground(new Color(0,0,0));
			this.setLayout(null);
			makeLabel();
			makeButtons();
			
			
		
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
		private void makeLabel(){
			JLabel label = new JLabel("My label");
			label.setBounds(40,100,500,500);
			label.setVerticalAlignment(JLabel.TOP);
			label.setHorizontalAlignment(JLabel.SOUTH_EAST);
			label.setText("<html><font color=white>The game is quite simple. <br>In order to move left or right, all you have to do is press the arrow keys. If you press space bar then you will be able to fire lasers at the enemy or incoming rocks. <br> If you are able to hit the rocks then you get 10 points but if you are able to hit the spaceship then you get 50 points!<br> Don't let the spaceship enemies get past you! Every ship that passes you, makes you lose a life. <br> Also, you only get 10 lasers until you run out and then you must acquire the power pill before you can fire at the enemies<br> Turn up the volume and GOOD LUCK!!</html>");
			this.add(label);
		}
		private void makeButtons(){
			JButton start = new JButton();
			start.setLayout(null);
			start.setBounds(320, 300, 75, 75);
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
		}

	}


