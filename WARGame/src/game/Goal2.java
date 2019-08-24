package game;

import controller.Mapa;
import controller.Territorio;

public class Goal2 implements Goal
{

	@Override
	public boolean check() {
		for(Territorio t : Mapa.getInstance().getListaTerritorios())
		{
			if( t.getContinente() == "America do Sul" || t.getContinente() == "Asia" )
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
		return "Conquistar, na totalidade, a America do Sul\n e a Asia";
	}

}
