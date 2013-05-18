/*
Swing Hacks Tips and Tools for Killer GUIs
By Joshua Marinacci, Chris Adamson
First Edition June 2005  
Series: Hacks
ISBN: 0-596-00907-0
Pages: 542
website: http://www.oreilly.com/catalog/swinghks/
*/

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

public class JGradientViewport extends JViewport {
  
    private static final long serialVersionUID = 1L;

    
    private int gradientLength = 100;
    private int gradientOffset = 20;
    private double progressiveGradientLength = 100;
    private Color bgColor = Color.BLACK;

    public JGradientViewport() {
        super();
    }

    public void paintComponent(Graphics g) {
        // do the superclass behavior first
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(bgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());

    }
    
    private Color getAlphaColor(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    
    /**
     * 
     * @param ratio, from 0.0 to 1.0
     * @return a value from 0 to 255
     */
    private int getAlphaFromRatio(double ratio) {
        int alpha = (int)(255*(ratio));
        alpha = (alpha > 255) ? 255 : alpha;
        alpha = (alpha < 0) ? 0 : alpha;
        return alpha;
    }

    public void paintChildren(Graphics g) {
        super.paintChildren(g);

        Graphics2D g2 = (Graphics2D)g;
        Point p = getViewPosition();
        Color transparent = getAlphaColor(bgColor, 0);
        
        // Top gradient ...
        double startRatio = p.getY()/progressiveGradientLength;
        Color startColor = getAlphaColor(bgColor, getAlphaFromRatio(startRatio));
        GradientPaint topPaint = new GradientPaint(getWidth()/2, gradientOffset, startColor, getWidth()/2, gradientLength, transparent);
        g2.setPaint(topPaint);
        g2.fillRect(0, 0, getWidth(), gradientLength);        

        // bottom gradient...
        double endRatio = (getViewSize().getHeight()-getHeight()-p.getY())/progressiveGradientLength;
        Color endColor = getAlphaColor(bgColor, getAlphaFromRatio(endRatio));
        GradientPaint bottomPaint = new GradientPaint(getWidth()/2,getHeight()-gradientLength, transparent, getWidth()/2, getHeight()-gradientOffset, endColor);
        g2.setPaint(bottomPaint);
        g2.fillRect(0, getHeight()-gradientLength, getWidth(), getHeight());

        g2.dispose();
    }

    public void setView(JComponent view) {
        view.setOpaque(false);
        super.setView(view);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();

        JTextArea ta = new JTextArea();
        for (int i = 0; i < 1000; i++) {
            ta.append(Integer.toString(i) + "  ");
        }

        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        // ta.setOpaque(false);

        JGradientViewport watermark = new JGradientViewport();
        watermark.setView(ta);

        JScrollPane scroll = new JScrollPane();
        scroll.setViewport(watermark);

        frame.getContentPane().add(scroll);
        frame.pack();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}