import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;

import com.sun.crypto.provider.DESCipher;


public class MovieInformationPanel extends JXPanel {

	private static final long serialVersionUID = 1L;

	JLabel title;
	JLabel image;
	JLabel director;
	JLabel actors;
	JLabel description;
	
	
	public MovieInformationPanel() {
		super();
		
		setBackgroundPainter(createBackgroundPainter());
		
		title = new JLabel("Le labyrinthe de Pan");
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		title.setForeground(new Color(255,255,102));
		
		
		image = new JLabel(new ImageIcon(ImageHelper.createEmptyImage(100,140)));
		
		director = new JLabel("réalisé par Guillermo del Toro");
		director.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		director.setForeground(Color.LIGHT_GRAY);
		
		actors = new JLabel("avec Ivana Baquero, Sergi Lopez, Doug Jones");
		actors.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		actors.setForeground(Color.LIGHT_GRAY);
		
		description = new JLabel("Espagne, 1944. Fin de la guerre. Carmen, récemment remariée,");
		description.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		description.setForeground(Color.WHITE);
		
		image.setBounds(10,10,100,140);
		title.setBounds(20,160,200,20);
		director.setBounds(30,180, 200, 20);
		actors.setBounds(30,200,200,20);
		description.setBounds(20, 230, 300, 200);
		description.setVerticalAlignment(JLabel.TOP);
		
		setLayout(null);
		add(title);
		add(image);
		add(director);
		add(actors);
		add(description);
		
		setPreferredSize(new Dimension(300,600));
		//add(new JLabel("<html><b><font style=\"color:white\">MovieInformationPanel</b></font>"));
	}
	
	@SuppressWarnings("unchecked")
	public Painter createBackgroundPainter() {
		
		Dimension size = new Dimension(600,600);
		
		Point p1 = new Point(size.width/2,0);
		Point p2 = new Point(size.width/2, size.height);
		
		Color lightColor = new Color(40,40,40); //Color.DARK_GRAY;
		Color darkColor = Color.BLACK;
		Color stripesColor = new Color(255,255,255,20);
		
		GradientPaint gp = new GradientPaint(p1, lightColor, p2, darkColor);
				
		MattePainter mp = new MattePainter(gp, true);
		
		PinstripePainter pp = new PinstripePainter(stripesColor, 45, 1, 10);
		
		CompoundPainter cp = new CompoundPainter(mp, pp);
		return cp;
	}
	
	public void setMovieInformation(Movie movie) {
		
		
		
	}
}
