import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.jdesktop.swingx.graphics.GraphicsUtilities;


public class ImageHelper {

	public static Image getImageFromResources(String pathName, String imgName) {
		BufferedImage bufImage = null;
		URL url = null;
		try {
		    url = MovieHelper.class.getResource(pathName+imgName);
		    bufImage = ImageIO.read(url);
		} catch(IOException ioe)    {
		    System.out.println("Erreur de chargement! " + ioe);
		}
		
		return bufImage;
	}
	
	/**
	 * Convert an Image into a BufferedImage. 
	 * @param img, source image
	 * @return destination image.
	 */
	public static BufferedImage convertToBufferedImage(Image img) {
		
		if(img instanceof BufferedImage) {
			return (BufferedImage) img ;
		}
		
		/** On crée la nouvelle image */
		BufferedImage bufferedImage = new BufferedImage(
				img.getWidth(null),
				img.getHeight(null),
				BufferedImage.TYPE_INT_ARGB );
		Graphics g = bufferedImage.createGraphics();
		g.drawImage(img,0,0,null);
		g.dispose();
		
		return bufferedImage;
	}
	
	
	
	
	
	public static BufferedImage appendReflection(BufferedImage orig, BufferedImage reflection) {
		return appendReflection(orig, reflection, 1);
	}
	
	public static BufferedImage appendReflection(BufferedImage orig, BufferedImage reflection, int gap) {
		return appendReflection(orig, reflection, gap, 0);
	}
	
	/**
	 * Add a reflection to a given image.
	 * The gap and reflectionMinHeight can be used to change the layout of the image.
	 * 
	 * @param orig, Original image.
	 * @param reflection, reflection image.
	 * @param gap, gap (in pixel) between the image and the reflection.
	 * @param reflectionMinHeight, minimum height for the reflection area. 
	 * @return the appended image.
	 */
	public static BufferedImage appendReflection(BufferedImage orig, BufferedImage reflection, int gap, int reflectionMinHeight) {
		
		int reflectionHeight = Math.max(reflection.getHeight(), reflectionMinHeight);
		
		int resultWidth = orig.getWidth();
		int resultHeight = orig.getHeight() + reflectionHeight + gap;
		
		BufferedImage result = new BufferedImage(resultWidth, resultHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = result.createGraphics();
		
		g.drawImage(orig, 0, 0, null);
		
		int reflectionX = 0;
		int reflectionY = orig.getHeight() + gap;
		
		g.drawImage(reflection, reflectionX, reflectionY, null);
				
		g.dispose();	
		reflection.flush();
		
		return result;
	}
	
	
	public static BufferedImage createEmptyImage(int width, int height) {
		
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = result.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		
		float[] dashPattern = { 20, 10, 20, 10 };
		BasicStroke bs = new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5f,dashPattern, 10f);
		
	    g.setStroke(bs);
		g.setColor(new Color(255,255,255,50));
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(1,1, width-3, height-3, 20, 20);
        g.draw(roundedRectangle);
        
        g.setStroke(new BasicStroke());
		g.setColor(new Color(255,255,255,10));
		g.fillRoundRect(0,0, width, height, 20,20);
        
        return result;
	}
	
	public static ImageIcon createMovieIcon() {
		
		
		return new ImageIcon(createMovieImage());
		
	}
	
public static BufferedImage createMovieImage() {
		
		BufferedImage result = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = result.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(new Color(120,120,120));
		g.setStroke(new BasicStroke(3));
		g.drawRect(0, 0, 15, 15);
		
		g.fillOval(5, 5, 6, 6);
		
		Polygon p = new Polygon();
		p.addPoint(8, 8);
		p.addPoint(3, 15);
		p.addPoint(13, 15);
		
		g.fillPolygon(p);
		
		return result;
		
	}
	
	
public static ImageIcon createSmallMovieIcon() {
		
		BufferedImage result = GraphicsUtilities.createThumbnail(createMovieImage(), 8);
		
		return new ImageIcon(result);
		
	}
	
	
	public static BufferedImage createDvdJacket(BufferedImage img) {

		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = result.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
		
		g.drawImage(img, 0, 0, null);
		
		g.setStroke(new BasicStroke(3));
		g.setColor(new Color(20,20,20));
		g.drawRoundRect(-2, 1, img.getWidth(), img.getHeight()-3, 5, 5);
		
		
		g.setStroke(new BasicStroke());
		g.setColor(Color.BLACK);
		
		g.drawLine(0, 0, 0, 0);
		g.drawLine(0, img.getHeight()-1, 0, img.getHeight()-1);
		g.drawLine(img.getWidth()-1, img.getHeight()-1, img.getWidth()-1, img.getHeight()-1);
		
		g.setColor(new Color(255,255,255,30));
		g.drawRect(0,2,img.getWidth()-3, img.getHeight()-6);
		
		
		// gloss effect
		Ellipse2D ellipse = new Ellipse2D.Double(-img.getWidth() / 2.0,
				img.getHeight() / 2.7, img.getWidth() * 2.0,
				img.getHeight() * 2.0);

		Area gloss = new Area(ellipse);

		Area area = new Area(new Rectangle(0, 0,
				img.getWidth(), img.getHeight()));
		area.subtract(new Area(ellipse));
		gloss = area;

		
		g.setPaint(new Color(255,255,255,40));
		g.fill(gloss);
		
		
		return result;
	}
	
	public static BufferedImage createRoundedBorder(BufferedImage img) {
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = result.createGraphics();
		   g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(img, 0, 0, null);
		
		g.setStroke(new BasicStroke(4));
		g.setColor(new Color(51,102,255));
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(1, 1, img.getWidth()-3, img.getHeight()-3, 10, 10);
        g.draw(roundedRectangle);
		
		return result;
		
	}
	
	
	
}
