package game;

import controller.Mapa;
import controller.Territorio;

public class Goal1 implements Goal
{

	@Override
	public boolean check() {
		for(Territorio t : Mapa.getInstance().getListaTerritorios())
		{
			if( t.getContinente() == "Africa" || t.getContinente() == "Oceania" || t.getContinente() == "Europa" )
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
		return "Conquistar, na totalidade, a Africa\n a Oceania e Europa";
	}

}
