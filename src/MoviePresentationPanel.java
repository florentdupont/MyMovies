import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.graphics.ReflectionRenderer;
import org.jdesktop.swingx.image.ColorTintFilter;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;




public class MoviePresentationPanel extends JXPanel implements Scrollable {

	private static final long serialVersionUID = 1L;

	private JXButton selectedButton;
	private MyMovie myMovie;
	
	private float zoom = 0.75f;
	
	List<JButton> buttons;
	Movie[] movies;
	
	
	public MoviePresentationPanel(MyMovie myMovie) {
		super();
		
		this.myMovie = myMovie;
		setBackgroundPainter(createBackgroundPainter());
		
		movies = MovieHelper.getMovieList();
		
		
		buttons = new ArrayList<JButton>();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		for(Movie movie : movies) {
			JButton btn = createButton(movie);
			add(btn);
			buttons.add(btn);
		}
		//setScrollableTracksViewportHeight(true);
		//setScrollableTracksViewportWidth(true);
		
		//add(new JLabel("MoviePresentationPanel"));
		
		addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent arg0) {
				//MouseWheelEvent.MOUSE_WHEEL;
				
			    int offmask = InputEvent.META_DOWN_MASK;
			    //System.out.println("scroll");
			
			    if(arg0.getModifiersEx() == offmask) {
			    	System.out.println("amount " + arg0.getScrollAmount());
					System.out.println("units " + arg0.getUnitsToScroll());
					if(arg0.getUnitsToScroll() > 0) {
						float zoom = getZoom();
						zoom+= 0.05f;
						
						if(zoom > 1)
							zoom = 1.0f;
						changeZoom(zoom);
					}
					else {
						float zoom = getZoom();
						zoom-= 0.05f;
						if(zoom < 0.1)
							zoom = 0.1f;
						changeZoom(zoom);
					}
				}	
			}
			
		});
		setPreferredSize(new Dimension(500,1000));
	}
	
	@SuppressWarnings("unchecked")
	public Painter createBackgroundPainter() {
		
		Dimension size = new Dimension(600,600);
		
		Point p1 = new Point(size.width/2,0);
		Point p2 = new Point(size.width/2, size.height);
		
		Color lightColor = new Color(40,40,40); //Color.DARK_GRAY;
		Color darkColor = Color.BLACK;
		Color stripesColor = new Color(255,255,255,20);
		
		//GradientPaint gp = new GradientPaint(p1, lightColor, p2, darkColor);
				
		MattePainter mp = new MattePainter(darkColor, true);
		
		//PinstripePainter pp = new PinstripePainter(stripesColor, 45, 1, 10);
		
		CompoundPainter cp = new CompoundPainter(mp/*, pp*/);
		return cp;
	}
	
	public void setBounds( int x, int y, int width, int height ) {
		super.setBounds( x, y, getParent().getWidth(), height );
	}

	public Dimension getPreferredSize() {
		return new Dimension( getWidth(), getPreferredHeight() );
	}

	public Dimension getPreferredScrollableViewportSize() {
		return super.getPreferredSize();
	}

	public int getScrollableUnitIncrement( Rectangle visibleRect, int orientation, int direction ) {
		int hundredth = ( orientation ==  SwingConstants.VERTICAL
				? getParent().getHeight() : getParent().getWidth() ) / 100;
		return ( hundredth == 0 ? 1 : hundredth ); 
	}

	public int getScrollableBlockIncrement( Rectangle visibleRect, int orientation, int direction ) {
		return orientation == SwingConstants.VERTICAL ? getParent().getHeight() : getParent().getWidth();
	}

	public boolean getScrollableTracksViewportWidth() {
		return true;
	}

	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	private int getPreferredHeight() {
		int rv = 0;
		for ( int k = 0, count = getComponentCount(); k < count; k++ ) {
			Component comp = getComponent( k );
			Rectangle r = comp.getBounds();
			int height = r.y + r.height;
			if ( height > rv )
				rv = height;
		}
		rv += ( (FlowLayout) getLayout() ).getVgap();
		return rv;
	}
	
	public void changeZoom(float zoom) {
		this.zoom = zoom;
		for(int i = 0; i<buttons.size(); i++) {
			JButton btn = buttons.get(i);
			btn.setIcon(new ImageIcon(createImage(movies[i])));
			
			BufferedImage img = createSelectedImage(movies[i]);
			btn.setSelectedIcon(new ImageIcon(img));
			btn.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		}
		repaint();
		
	}
	
	public float getZoom() {
		return this.zoom;
	}
	
	
	public BufferedImage createImage(Movie movie) {
		BufferedImage bufferedImage = ImageHelper.convertToBufferedImage(movie.image);

		BufferedImage jacketImage = ImageHelper.createDvdJacket(bufferedImage);
		
		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setOpacity(0.1f);
		renderer.setLength(0.3f);
		renderer.setBlurEnabled(false);
			
		BufferedImage reflection = renderer.createReflection(jacketImage);
						
		BufferedImage btnImage = ImageHelper.appendReflection(jacketImage, reflection, 5, 70);
		
		btnImage = GraphicsUtilities.createThumbnail(btnImage, (int)(250*zoom));
		
		return btnImage;
		
	}
	
	public BufferedImage createSelectedImage(Movie movie) {
		BufferedImage bufferedImage = ImageHelper.convertToBufferedImage(movie.image);
		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setOpacity(0.1f);
		renderer.setLength(0.3f);
		renderer.setBlurEnabled(false);
		
		BufferedImage roundedImg = ImageHelper.createRoundedBorder(bufferedImage);

		ColorTintFilter filter = new ColorTintFilter(new Color(51,102,255), 0.2f);


		BufferedImage dst = GraphicsUtilities.createCompatibleImage(roundedImg);
		filter.filter(roundedImg, dst);

		BufferedImage selectedReflection = renderer.createReflection(dst);

		BufferedImage selbtnImage = ImageHelper.appendReflection(dst, selectedReflection, 5, 70);

		selbtnImage = GraphicsUtilities.createThumbnail(selbtnImage, (int)(250*zoom));
		
		return selbtnImage;


	}
	

	public JButton createButton(final Movie movie) {

		
		final JXButton btn = new JXButton(new ImageIcon(createImage(movie)));
		
		BufferedImage btnselImage = createSelectedImage(movie);
		btn.setSelectedIcon(new ImageIcon(btnselImage));
		
		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(selectedButton != null){
					selectedButton.setSelected(false);
				}
				
				btn.setSelected(!btn.isSelected());
				selectedButton = (JXButton)e.getSource();
				myMovie.setSelectedMovie(movie);

			}
			
		});
		
		btn.setPreferredSize(new Dimension(btnselImage.getWidth()+10, btnselImage.getHeight()+10));
		btn.setVerticalAlignment(JButton.BOTTOM);
		btn.setBorderPainted(false);
		btn.setBorder(BorderFactory.createLineBorder(Color.white));

		return  btn;
	}
}
