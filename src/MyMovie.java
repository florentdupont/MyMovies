import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.jdesktop.swingx.JXFrame;


public class MyMovie extends JXFrame {

	private static final long serialVersionUID = 1L;

	
	// 3 main Panels
	private MoviePresentationPanel presentationPanel;
	private MovieInformationPanel  informationPanel;
	private MovieStatusPanel       statusPanel;
	
	private Movie selectedMovie;
	
	
	public MyMovie() {
			
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		this.presentationPanel = new MoviePresentationPanel(this);
		this.informationPanel  = new MovieInformationPanel();
		this.statusPanel       = new MovieStatusPanel(this);
		
		JGradientViewport scrollPane = new JGradientViewport();
		
		scrollPane.setView(presentationPanel);
		
		JScrollPane scroll = new JScrollPane();
        scroll.setViewport(scrollPane);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationPanel, scroll);
		splitPane.setDividerLocation(300);
		
		splitPane.setBorder(BorderFactory.createEmptyBorder());
		
		contentPane.add(splitPane, BorderLayout.CENTER);
		contentPane.add(statusPanel, BorderLayout.SOUTH);

		setContentPane(contentPane);
		
		
		setDefaultCloseOperation(JXFrame.DISPOSE_ON_CLOSE);
		setSize(800,600);
	}
	
	public void changeZoom(int value) {
		presentationPanel.changeZoom(((float)value)/100f);
	}


	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}
	
	
}
