package controller;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Marker
{
	private Ponto _point;
    private Color _color;

    Marker(Ponto point, Color color)
    {
        _point = point;
        _color = color;
    }
    
    public Color getColor()
    {
    	return _color;
    }
    
    public void setColor(Color color)
    {
    	_color = color;
    }
    
    public Ponto getPonto()
    {
    	return _point;
	}
}
