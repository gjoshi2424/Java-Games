import javax.swing.JFrame;

public class spaceFrame extends JFrame {
	OpeningScreen opener;
	
	public spaceFrame(){
		super("Lets Play!");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		this.getContentPane().setLayout(null);
		loadSpacePanel();
		
	}
	protected void loadSpacePanel() {
		opener = new OpeningScreen();
		this.add(opener);
		
	}
	

}
