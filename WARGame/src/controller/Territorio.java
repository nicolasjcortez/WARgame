package controller;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class Territorio {

    private String Nome;
    private String _continente;
    private GeneralPath poligono;
    private List<Territorio> _listaDeTerritoriosFronteira = new ArrayList<Territorio>();
    private int _jogadorDono; 
    private int _numExercitos = 1;

    public Territorio(String nome,String continente, Ponto p[], float x, float y,List<Territorio> listaDeTerritoriosFronteira ) 
    {
        super();
        
        //Na verdade percorrer� uma lista especifica passada como parametro 
//        if (listaDe)
//        for(Territorio t : listaDeTerritoriosFronteira)
//        {
//        	_listaDeTerritoriosFronteira.add(t);
//        }
        _continente = continente;
        this.Nome = nome;

        GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        gp.moveTo(p[0].get("x") + (x), p[0].get("y") + (y));

        for (int i = 1; i < p.length; i++) {
            gp.lineTo(p[i].get("x") + (x), p[i].get("y") + (y));
        }

        gp.closePath();

        this.poligono = gp;
    }
    
    public Territorio(String nome,String continente, Ponto p[]){
    	this(nome,continente, p, 0f, 0f, null);
    }
    
    public List<Territorio> getListaTerritoriosFrontreira()
    {
    	return _listaDeTerritoriosFronteira;
    }

    public GeneralPath getPoligono() {
        return this.poligono;
    }

    public String getNome() {
        return Nome;
    }
    
    public String getContinente() {
        return _continente;
    }
    
    public void setDono(int playerIndex)
    {
    	_jogadorDono = playerIndex;
    }
    
    public int getDonoIndex()
    {
    	return _jogadorDono;
    }
    
    public void setNumExercitos(int numberOfArmys)
    {
    	_numExercitos = numberOfArmys ;
    }
    
    public int getNumExercitos()
    {
    	return _numExercitos;
    }
    
    public void setTerritoriosFronteira(Territorio fronteiras[]) {
    	this._listaDeTerritoriosFronteira = new ArrayList<Territorio>(fronteiras.length);
    	for (Territorio t: fronteiras) {
    		_listaDeTerritoriosFronteira.add(t);
    	}
    }
}
