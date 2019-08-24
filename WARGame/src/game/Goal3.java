package game;

import controller.Mapa;
import controller.Territorio;

public class Goal3 implements Goal
{

	@Override
	public boolean check() {
		for(Territorio t : Mapa.getInstance().getListaTerritorios())
		{
			if( t.getContinente() == "Oceania" || t.getContinente() == "Asia" )
			{
				if( t.getDonoIndex() == GameData.getInstance().getCurrentPlayer())
				{
					//ira returnar true no final se sempre entrar aqui
				}
				else
				{
					return false;
				}
			}		
		}
		return true;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return "Conquistar, na totalidade, a Oceania\n e a Asia";
	}

}
