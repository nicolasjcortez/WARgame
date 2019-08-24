package game;

import controller.Mapa;
import controller.Territorio;

public class Goal5 implements Goal
{

	@Override
	public boolean check() {
		for(Territorio t : Mapa.getInstance().getListaTerritorios())
		{
			if( t.getContinente() == "America do Sul" || t.getContinente() == "Oceania" || t.getContinente() == "America do Norte" )
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
		return "Conquistar, na totalidade, a America do Norte,\n America do Sul e a Oceania";
	}

}
