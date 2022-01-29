package org.term2d.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import org.term2d.display.Display;
import org.term2d.iterator.ImageIterator;

public class Image implements Iterable<Integer> {
    private BufferedImage image;
    private int width;
    private int height;

    public Image(String path) {
        try {
            File input = new File(path);
            image  = ImageIO.read(input);
            width  = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void toGreyScale() throws IOException {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));

                int red   = (int) (c.getRed()   * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue  = (int) (c.getBlue()  * 0.114);

                Color newColor = new Color(
                    red + green + blue,
                    red + green + blue,
                    red + green + blue);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
    }
    
    public void resizeImage(int targetWidth, int targetHeight) {
        java.awt.Image resultingImage = image.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        this.image = outputImage;
        this.width = targetWidth;
        this.height = targetHeight;
    }

    public void scale(double scalar) {
        resizeImage((int) (width*scalar), (int) (height*scalar));
    }

    public void fitStretch(Display d) {
        int fitWidth  = (int) (width  * (d.width()  / (double) width));
        int fitHeight = (int) (height * (d.height() / (double) height));
        resizeImage(fitWidth, fitHeight);
    }

    public void fitScale(Display d) {
        double scalar = Math.min(
            d.width() / (double) width,
            d.height() / (double) height
        );
        scale(scalar);
    }

    public void save(String path) throws IOException {
        File f = new File(path);
        ImageIO.write(image, "jpg", f);
    }

    public int map(double intensity) {
        return (int) (intensity / (255.0/4.0));    
    }

    public double intensity(Color c) {
        return (c.getRed() + c.getGreen() + c.getBlue()) / 3.0;
    }

    @Override
    public ImageIterator iterator() {
        return new ImageIterator() {
            private long limit = (long) (width * height);
            private int index = -1;

            @Override
            public int rowIndex() {
                return index / width;
            }

            @Override
            public int colIndex() {
                return index - rowIndex() * width;
            }
            
            @Override
            public int get() {
                Color c = new Color(image.getRGB(colIndex(), rowIndex()));
                return map(intensity(c));
            }

            @Override
            public void set(int rgb) {
                image.setRGB(colIndex(), rowIndex(), rgb);
            }

            @Override
            public boolean hasNext() {
                return index + 1 < limit;
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                index++;
                return get();
            }
        };
    }
}
