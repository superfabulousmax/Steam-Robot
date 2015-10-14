package com.mygdx.SteamRobot.CollisionDetection;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * A object to keep things that can collide
 * Created by calvin on 2014/09/05.
 */
public class Collidable {
    public Sprite sprite;
    public long[] mask;
    public boolean colliding;
    public Polygon bounding;

    public Collidable(String name) {
        // name is the filename, and will setup the sprite and mask
        sprite = new Sprite(new Texture(name));
        mask = newMask(new Pixmap(new FileHandle(name)));
        bounding = rectToPoly(sprite.getBoundingRectangle());
    }

    private Polygon rectToPoly(Rectangle r) {
        // Generates a polygon from a rectangle
        return new Polygon(new float[]{
                r.x + r.width, r.y,
                r.x + r.width, r.y + r.height,
                r.x, r.y + r.height,
                r.x, r.y
        });

    }

    public void setPolygon(float[] coord){
        // Allows setting of better-fitting polygons
        bounding = new Polygon(coord);
    }

    public long[] newMask(Pixmap map) {
        /* Creates a new mask from a pixmap*/
        int h = map.getHeight();
        int w = map.getWidth();

        long[] mask = new long[h];

        // Loop through each row of pixels
        for (int i = 0; i < h; i++) {
            long line = 0;
            // Create a binary number that represents that row
            for (int j = 0; j < w; j++) {
                if(line> Long.MAX_VALUE /2 -5){
                    // Checks for overflow
                    System.out.print("OVERFLOW!!!");
                }
                line = line * 2;
                if ((map.getPixel(j, i) & 0x000000ff) != 0) {
                    line += 1;
                }
            }
            // PixMaps have 0,0 top left, libgdx bottom left, this fixes that
            mask[h-1-i] = line;
        }
        testPrint(mask);
        return mask;

    }

    public void flip(boolean t) {
        //sprite.setFlip(t, false);
        // This does not work for pixel perfect collision
        // Would need to generate a mask for flipped sprite
        // Final game will not use pixel perfect collision
    }

    public static void testPrint(long[] mask){

       // Prints out masks for testing
        for (int i = (mask.length)-1; i >= 0; i--) {
            System.out.println(String.format(i + "%64s", Long.toBinaryString(mask[i])).replace(' ', '0'));
        }
        System.out.print("\n\n\n");
    }
}
