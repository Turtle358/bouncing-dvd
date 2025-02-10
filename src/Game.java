import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.swing.JFrame;
import java.util.Random;


/**
 * Your main game entry point
 */
public class Game {
    static int xResolution = 1920;
    static int yResolution = 1080;
    static int ppm = 20;
    int bigX = 8;
    int bigY = 8;
    /** Initialise a new Game. */
    public Game() {

        //1. make an empty game world and level
        World world = new World();
        Level level = new Level(1, world);

        //2. populate it with bodies (ex: platforms, collectibles, characters)
        level.generateUnbreakableWalls(xResolution, 1, 0, yResolution);
        level.generateUnbreakableWalls(1, yResolution, xResolution, 0);
        level.generateUnbreakableWalls(xResolution, 1, 0, -yResolution);
        level.generateUnbreakableWalls(1, yResolution, -xResolution, 0);
        //make a character (with an overlaid image)

        Shape bodyShape = new BoxShape((float) 4, (float) 2);
        DynamicBody body = new DynamicBody(world, bodyShape);
        body.setPosition(new Vec2(0,0));
        body.addImage(new BodyImage("data/dvd.png", 8));
        body.setGravityScale(0);
        body.setLinearVelocity(new Vec2(bigX, bigY));
        body.addCollisionListener(new CollisionListener() {

            @Override
            public void collide(CollisionEvent collisionEvent) {
                float a = collisionEvent.getNormal().x;
                float b = collisionEvent.getNormal().y;
                if(a==0.0) {
                    bigY *= -1;
                    body.setLinearVelocity(new Vec2(bigX, bigY));
                }
                if(b==0.0) {
                    bigX *= -1;
                    body.setLinearVelocity(new Vec2(bigX, bigY));
                }
                if(b==0.0 && a == 0.0) {
                    generateDVD(world);
                }
            }
        });
        //3. make a view to look into the game world
        UserView view = new UserView(world, xResolution, yResolution);



        //4. create a Java window (frame) and add the game
        //   view to it
        final JFrame frame = new JFrame("DVD bouncer");
        frame.add(view);

        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        //optional: uncomment this to make a debugging view
//         JFrame debugView = new DebugViewer(world, 800, 800);

        // start our game world simulation!
        world.start();
    }

    public static void generateDVD(World world) {
        final int[] bigX = {8};
        final int[] bigY = {8};
        int[] arr = {-1, 1};
        Shape bodyShape = new BoxShape((float) 4, (float) 2);
        DynamicBody body = new DynamicBody(world, bodyShape);
        body.setPosition(new Vec2(0,0));
        body.addImage(new BodyImage("data/dvd.png", 8));
        body.setGravityScale(0);
        body.setLinearVelocity(new Vec2(bigX[0]*getRandom(arr), bigY[0]));
        body.addCollisionListener(new CollisionListener() {

            @Override
            public void collide(CollisionEvent collisionEvent) {
                float a = collisionEvent.getNormal().x;
                float b = collisionEvent.getNormal().y;
                if(a==0.0) {
                    bigY[0] *= -1;
                    body.setLinearVelocity(new Vec2(bigX[0], bigY[0]));
                }
                if(b==0.0) {
                    bigX[0] *= -1;
                    body.setLinearVelocity(new Vec2(bigX[0], bigY[0]));
                }
                if(b==0.0 && a == 0.0) {
                    generateDVD(world);
                }
            }
        });
    }
    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }


    /** Run the game. */
    public static void main(String[] args) {

        new Game();
    }
}