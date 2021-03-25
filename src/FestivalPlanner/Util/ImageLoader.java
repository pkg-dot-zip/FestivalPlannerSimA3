package FestivalPlanner.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageLoader {

    private static String[] characterFiles = {"char_1", "char_2"};
    private static final int npcTileX = 4;
    private static final int npcTileY = 3;

//    private static ArrayList<BufferedImage> spritesLeft = new ArrayList<>(4);
//    private static ArrayList<BufferedImage> spritesDown = new ArrayList<>(4);
//    private static ArrayList<BufferedImage> spritesUp = new ArrayList<>(4);
//    private static ArrayList<BufferedImage> spritesRight = new ArrayList<>(4);

    private static ArrayList<ArrayList<ArrayList<BufferedImage>>> lists = new ArrayList<>(characterFiles.length);

    public static void loadImages() {
        ArrayList<ArrayList<BufferedImage>> allFourSpriteSheets = new ArrayList<>(4);
        for (int i = 0; i < characterFiles.length; i++) {
            try {
                BufferedImage image = ImageIO.read(ImageLoader.class.getResourceAsStream("/characters/" + characterFiles[i] + ".png"));

                ArrayList<BufferedImage> spritesLeft = new ArrayList<>(4);
                ArrayList<BufferedImage> spritesDown = new ArrayList<>(4);
                ArrayList<BufferedImage> spritesUp = new ArrayList<>(4);
                ArrayList<BufferedImage> spritesRight = new ArrayList<>(4);

                int width = image.getWidth() / npcTileX;
                int height = image.getHeight() / npcTileY;

                splitImages(image, spritesLeft, 0, width, height);
                splitImages(image, spritesDown, 1, width, height);
                splitImages(image, spritesUp, 2, width, height);
                splitImages(image, spritesRight, 3, width, height);

                allFourSpriteSheets.add(spritesLeft);
                allFourSpriteSheets.add(spritesDown);
                allFourSpriteSheets.add(spritesUp);
                allFourSpriteSheets.add(spritesRight);

                lists.add(allFourSpriteSheets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void splitImages(BufferedImage image, ArrayList<BufferedImage> list, int x, int width, int height){
        int xToSplit = x * width;
        list.add(image.getSubimage(xToSplit, 0, width, height));
        list.add(image.getSubimage(xToSplit, height, width, height));
        list.add(image.getSubimage(xToSplit, 0, width, height));
        list.add(image.getSubimage(xToSplit, 2 * height, width, height));
    }

    public static ArrayList<ArrayList<BufferedImage>> getLists(int spriteSheet) {
        return lists.get(spriteSheet);
    }
}
