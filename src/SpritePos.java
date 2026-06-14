import java.awt.Dimension;
import java.util.Random;

public class SpritePos {
    double x = 0;
    double y = 0;

    SpritePos randNewPos(double curr_x, double curr_y, Dimension size){
        SpritePos newp = new SpritePos();

        double max_width = size.getWidth();
        double max_height = size.getHeight();

        //Get the change in either axis at a max of half the window size
        Random rand = new Random();
        double delta_x = rand.nextDouble(max_width/2);
        double delta_y = rand.nextDouble(max_width/2);

        //Check validity of both changes
        if(curr_x + delta_x < max_width) newp.x = curr_x + delta_x;
        else if(curr_x + delta_x > 0) newp.x = curr_x + delta_x;

        if(curr_y + delta_y < max_height) newp.y = curr_y + delta_y;
        else if(curr_y + delta_y > 0) newp.y = curr_y + delta_y;

        //return result
        return newp;
    }
}
