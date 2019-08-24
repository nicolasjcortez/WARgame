package controller;

public class Ponto {

    private Double x;
    private Double y;

    public Ponto(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Ponto(int x, int y)
    {
    	this.x = (double) x;
        this.y = (double) y;
	}

	public double get(String s) {

        if (s.equals("x")) {
            return this.x;
        } else if (s.equals("y")) {
            return this.y;
        }

        return 0;
    }
    
    public double getX()
    {
    	return x;
    }
    
    public double getY()
    {
    	return y;
    }
}
