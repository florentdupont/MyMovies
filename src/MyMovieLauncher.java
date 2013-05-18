import javax.swing.SwingUtilities;


public class MyMovieLauncher {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// positionne les variables systèmes
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MyMovie");
				
				
				new MyMovie().setVisible(true);
			}
		});
	}
}
