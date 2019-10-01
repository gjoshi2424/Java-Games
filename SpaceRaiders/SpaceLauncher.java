

	import java.awt.Dimension;
	import java.awt.Toolkit;
	import java.util.Map;

	import javax.swing.JFrame;
import javax.swing.JPanel;
	public class SpaceLauncher {
	

		public static void main(String[] args) {
			
//			JFrame gameFrame = new JFrame();
//			
//			Map<String,String> environMap= System.getenv();
//			System.out.println(environMap.keySet());
//			
//			
//			
//			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//			MovingPanel mop = new MovingPanel();
//			//OpeningScreen starter = new OpeningScreen(); 
//			gameFrame.add(mop);
//			gameFrame.pack();
//			gameFrame.setVisible(true);
//			gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
//			
			JFrame openingFrame = new JFrame();
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		OpeningScreen opener = new OpeningScreen();
			//OpeningScreen starter = new OpeningScreen(); 
			openingFrame.add(opener);
			openingFrame.pack();
			openingFrame.setVisible(true);
			openingFrame.setDefaultCloseOperation(openingFrame.EXIT_ON_CLOSE);
			
				
			//spaceFrame sf = new spaceFrame();
			//sf.setVisible(true);
			
		}

	}

