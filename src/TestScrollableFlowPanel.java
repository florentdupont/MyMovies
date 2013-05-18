import java.awt.*;
import javax.swing.*;
 
public class TestScrollableFlowPanel extends JFrame {
	public static void main( String[] args ) {
		Runnable doEDT = new Runnable() {
			public void run() {
				new TestScrollableFlowPanel();
			}
		};
		SwingUtilities.invokeLater( doEDT );
	}
 
	public TestScrollableFlowPanel() {
		try {
			jbInit();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		this.setSize( 300, 300 );
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		this.setVisible( true );
	}
 
	private void jbInit() throws Exception {
		ScrollableFlowPanel panel = new ScrollableFlowPanel();
		for ( int k = 0; k < 120; k++ )
			panel.add( new JButton( "Button"  + k ) );
		JScrollPane scroll = new JScrollPane( panel );
		this.getContentPane().setLayout( new BorderLayout() );
		this.getContentPane().add( scroll, BorderLayout.CENTER );
	}
 
	static public class ScrollableFlowPanel extends JPanel implements Scrollable {
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
	}
}