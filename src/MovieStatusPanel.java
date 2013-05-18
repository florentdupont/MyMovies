import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;


public class MovieStatusPanel extends JXPanel {

	private static final long serialVersionUID = 1L;

	MyMovie myMovie;
	
	MovieStatusPanel(MyMovie pmyMovie) {
		super();
		
		this.myMovie = pmyMovie;
		
		JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL,
                10, 100, 75);
		
		zoomSlider.getModel().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				myMovie.changeZoom(((DefaultBoundedRangeModel)arg0.getSource()).getValue());			
			}
		});
		setPreferredSize(new Dimension(700,30));
		setLayout(new BorderLayout());
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(350, 10));
		add(emptyPanel, BorderLayout.WEST);
		add(new JLabel("6 Eléments"), BorderLayout.CENTER);
		
		
		JPanel zoomPanel = new JPanel();
		zoomPanel.setPreferredSize(new Dimension(300,20));
		zoomPanel.add(new JLabel(ImageHelper.createSmallMovieIcon()));
		zoomPanel.add(zoomSlider);
		zoomPanel.add(new JLabel(ImageHelper.createMovieIcon()));
		zoomPanel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		zoomPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		
		add(zoomPanel, BorderLayout.EAST);
		
		
		
		
		
		
	}
}
