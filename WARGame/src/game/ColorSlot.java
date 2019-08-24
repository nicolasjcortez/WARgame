package game;

import java.awt.*;

/**
 * Created by danilo on 27/05/15.
 */
public class ColorSlot {
	
	//tornar variaveis privadas, encapsulamento
    public String name;
    public Color color;
    public boolean avaiable = true;

    ColorSlot(String nm, Color c)
    {
        name = nm;
        color = c;
    }
    
    public Color getColor()
    {
    	return color;
    }
}
