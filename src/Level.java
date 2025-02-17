import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;

public class Level {
    private int difficulty;
    private World world;
    private final static int ppm = 20;
    Level(int difficulty, World world) {
        // difficulty  can be between 1 and 3
        this.difficulty = difficulty;
        this.world = world;
    }

    private void easy() {
        // easy should have a max of 10 walls and 2-3 enemies

    }

    private void medium() {
        // medium should have a max of 15 walls and 3-6 enemies
    }

    private void hard() {
        // hard should have a max of 25 walls and 4-8 enemies
    }

    public void generateBreakableWalls(int x, int y, int width, int height, boolean gravityEffected, String image) {
        // Walls that can be knocked over by the player
        Shape bodyShape = new BoxShape((float) width /2, (float) height /2);
        DynamicBody body = new DynamicBody(world, bodyShape);
        body.setPosition(new Vec2(x,y));
        body.addImage(new BodyImage(image, height));
        if (gravityEffected) {
            body.setGravityScale(1);
        } else {
            body.setGravityScale(0);
        }
    }

    public void generateUnbreakableWalls(float width, float height, float x, float y) {
        // Walls that CANNOT move
        Shape floor = new BoxShape(pixelToMetre(width) /2, pixelToMetre(height) / 2);
        StaticBody floorBody = new StaticBody(world, floor);
        floorBody.setPosition(new Vec2(pixelToMetre(x)/2, pixelToMetre(y) /2));
    }

    public void buildLevel(int resolutionX, int resolutionY) {
        // build floor
        this.generateUnbreakableWalls(resolutionX, 20.0f, 0, -resolutionY);
        // Build bounce back walls (these should be out of the frame)
        for(int i = -1; i <= 1; i+=2) {
            this.generateUnbreakableWalls(1.0f, resolutionY+1200, i*(resolutionX), 0);
        }
        // build a high ceiling (way outside the frame
        this.generateUnbreakableWalls(resolutionX, 1.0f, 0, resolutionY+1200);
        // generate safety walls
        switch (difficulty) {
            case 1:
                easy();
            case 2:
                medium();
            case 3:
                hard();
            default:
                easy();
        }
    }


    public static float pixelToMetre(float pixels) {
        return pixels / ppm;
    }
}
