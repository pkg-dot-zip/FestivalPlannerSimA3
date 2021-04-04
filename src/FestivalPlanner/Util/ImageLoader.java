package FestivalPlanner.Util;

import FestivalPlanner.GUI.AgendaGUI.PopUpGUI.AbstractDialogPopUp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Static class that loads and splits the NPC sprites
 * <p>
 * This class stores these Images so these only need to be loaded and split once.
 */
public class ImageLoader {

    private static String[] characterFiles = {"char_1", "char_2"};
    private static final int npcTileX = 4;
    private static final int npcTileY = 3;

    private static ArrayList<ArrayList<BufferedImage>>[] lists = new ArrayList[characterFiles.length];

    /**
     * Loads all the NPC spriteSheets and stores them.
     */
    public static void loadImages() {
        for (int i = 0, characterFilesLength = characterFiles.length; i < characterFilesLength; i++) {
            String characterFile = characterFiles[i];

            ArrayList<ArrayList<BufferedImage>> allFourSpriteSheets = new ArrayList<>(4);

            try {
                BufferedImage image = ImageIO.read(ImageLoader.class.getResourceAsStream("/characters/" + characterFile + ".png"));

                //Initialise values.
                ArrayList<BufferedImage> spritesLeft = new ArrayList<>(4);
                ArrayList<BufferedImage> spritesDown = new ArrayList<>(4);
                ArrayList<BufferedImage> spritesUp = new ArrayList<>(4);
                ArrayList<BufferedImage> spritesRight = new ArrayList<>(4);

                //Calculate width and height per sprite / tile.
                int width = image.getWidth() / npcTileX;
                int height = image.getHeight() / npcTileY;

                //Split up the images.
                splitImages(image, spritesLeft, 0, width, height);
                splitImages(image, spritesDown, 1, width, height);
                splitImages(image, spritesUp, 2, width, height);
                splitImages(image, spritesRight, 3, width, height);

                //Add the images to the ArrayList.
                allFourSpriteSheets.add(spritesLeft);
                allFourSpriteSheets.add(spritesDown);
                allFourSpriteSheets.add(spritesUp);
                allFourSpriteSheets.add(spritesRight);

                lists[i] = allFourSpriteSheets;
            } catch (IOException e) {
                AbstractDialogPopUp.showExceptionPopUp(e);
            }
        }
    }

    /**
     * This method wil split up one imagesSheet into all the sprites.
     * @param image  The image to split up
     * @return  All the sprites
     */
    public static ArrayList<ArrayList<BufferedImage>> loadImage(BufferedImage image) {
        ArrayList<ArrayList<BufferedImage>> allFourSpriteSheets = new ArrayList<>(4);

        try {

            //Initialise values.
            ArrayList<BufferedImage> spritesLeft = new ArrayList<>(4);
            ArrayList<BufferedImage> spritesDown = new ArrayList<>(4);
            ArrayList<BufferedImage> spritesUp = new ArrayList<>(4);
            ArrayList<BufferedImage> spritesRight = new ArrayList<>(4);

            //Calculate width and height per sprite / tile.
            int width = image.getWidth() / npcTileX;
            int height = image.getHeight() / npcTileY;

            //Split up the images.
            splitImages(image, spritesLeft, 0, width, height);
            splitImages(image, spritesDown, 1, width, height);
            splitImages(image, spritesUp, 2, width, height);
            splitImages(image, spritesRight, 3, width, height);

            //Add the images to the ArrayList.
            allFourSpriteSheets.add(spritesLeft);
            allFourSpriteSheets.add(spritesDown);
            allFourSpriteSheets.add(spritesUp);
            allFourSpriteSheets.add(spritesRight);

             return allFourSpriteSheets;
        } catch (Exception e) {
            AbstractDialogPopUp.showExceptionPopUp(e);
            return lists[(int)(Math.random())];
        }
    }

    private static void splitImages(BufferedImage image, ArrayList<BufferedImage> list, int x, int width, int height){
        int xToSplit = x * width;
        list.add(image.getSubimage(xToSplit, 0, width, height));
        list.add(image.getSubimage(xToSplit, height, width, height));
        list.add(image.getSubimage(xToSplit, 0, width, height));
        list.add(image.getSubimage(xToSplit, 2 * height, width, height));
    }

    /**
     * Returns <code>lists[spritesheet]</code>, with spritesheet being the place to look for given as a parameter.
     * @param spriteSheet  place to look for in the list
     * @return  ArrayList with BufferedImages
     */
    public static ArrayList<ArrayList<BufferedImage>> getLists(int spriteSheet) {
        return lists[spriteSheet];
    }
}
