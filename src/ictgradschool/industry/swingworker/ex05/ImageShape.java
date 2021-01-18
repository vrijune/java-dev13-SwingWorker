package ictgradschool.industry.swingworker.ex05;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * A shape which is capable of loading and rendering images from files / Uris / URLs / etc.
 */

public class ImageShape extends Shape {

    private Image image1;

    public ImageShape(int x, int y, int deltaX, int deltaY, int width, int height, String fileName) throws MalformedURLException {
        this(x, y, deltaX, deltaY, width, height, new File(fileName).toURI());
    }

    public ImageShape(int x, int y, int deltaX, int deltaY, int width, int height, URI uri) throws MalformedURLException {
        this(x, y, deltaX, deltaY, width, height, uri.toURL());
    }

    public ImageShape(int x, int y, int deltaX, int deltaY, int width, int height, URL url) {
        super(x, y, deltaX, deltaY, width, height);

        SwingWorker<Image, Void> imageWorker = new SwingWorker<>() {

            @Override
            protected void done() {
                try{ImageShape.this.image1 =get();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected Image doInBackground() throws Exception {
                try {
                    Image image = ImageIO.read(url);
                    if (width == image.getWidth(null) && height == image.getHeight(null)) {
//                        image1 = image;
                        return image;
                    } else {
                        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        return image;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }


        };
        imageWorker.execute();

    }






    @Override
    public void paint(Painter painter) {
        if (image1 == null) {

            painter.setColor(Color.GRAY);
            painter.fillRect(fX,fY,fWidth,fHeight);
            painter.setColor(Color.RED);
            painter.drawRect(fX,fY,fWidth,fHeight);
            painter.drawCenteredText("Loading...", fX, fY, fWidth, fHeight);
        } else {
            painter.drawImage(image1, fX, fY, fWidth, fHeight);

        }
    }
}