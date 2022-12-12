package de.softwaretechnik.views;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImgCanvas extends Canvas {
    private boolean drawBorder = true;
    private int borderOffset = 1;

    public ImgCanvas() {
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) screenWidth;
        int height = (int) ((int) (screenHeight / 10) * 1.5);
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(47, 47, 47));
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage img;
        try {
             img = ImageIO.read(new File("src/de/softwaretechnik/views/resources/Movie Database.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setBackground(new Color(47, 47, 47));
        g2d.fillRect( 0, 0, getSize().width, getSize().height );
        g2d.setColor(MainWindow.getInstance().gold);
        Rectangle border = getBorderBounds();
        g2d.drawRect(border.x, border.y, border.width, border.height);
        g2d.drawImage(
                img,
                (int) ((Toolkit.getDefaultToolkit()
                        .getScreenSize()
                        .getWidth() / 2) -
                        (img.getWidth() / (double) 2)),
                (int) ((((Toolkit.getDefaultToolkit()
                        .getScreenSize()
                        .getHeight() / 10) * 1.5) / 2) -
                        (img.getHeight() / (double) 2)),
                null);

    }

    protected Rectangle getBorderBounds() {
        int x = borderOffset;
        int y = borderOffset;
        int width = getSize().width - borderOffset * 2;
        int height = getSize().height - borderOffset * 2;
        Rectangle bounds = new Rectangle( x, y, width, height );
        return bounds;
    }
}
