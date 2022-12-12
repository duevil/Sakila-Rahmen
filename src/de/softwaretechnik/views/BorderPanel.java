package de.softwaretechnik.views;

import java.awt.*;

public class BorderPanel extends Panel {
    private boolean drawBorder = true;
    private int borderOffset = 1;

    public void setBorderVisible( boolean b ) {
        if ( b != drawBorder ) {
            drawBorder = b;
            repaint();
        }
    }

    public boolean isBorderVisible() {
        return drawBorder;
    }

    public void setBorderOffset( int i ) {
        borderOffset = i;
        repaint();
    }

    public int getBorderOffset() {
        return borderOffset;
    }

    protected Rectangle getBorderBounds() {
        int x = borderOffset;
        int y = borderOffset;
        int width = getSize().width - borderOffset * 2;
        int height = getSize().height - borderOffset * 2;
        Rectangle bounds = new Rectangle( x, y, width, height );
        return bounds;
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.setColor(getBackground());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.fillRect( 0, 0, getSize().width, getSize().height );
        g2d.setColor(MainWindow.getInstance().gold);
        Rectangle border = getBorderBounds();
        g2d.drawRect(border.x, border.y, border.width, border.height);
    }

    /*public static void main( String[] args ) {
        Frame f = new Frame("BorderPanel Test");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit( 0 );
            }
        });

        BorderPanel p = new BorderPanel();
        p.add(new Label("Label"));
        f.add(p);
        f.setSize(300, 300);
        f.show();
    }*/
}