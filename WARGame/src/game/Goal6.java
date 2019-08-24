package game;

import controller.Mapa;
import controller.Territorio;

public class Goal6 implements Goal
{

	@Override
	public boolean check() 
	{
		if( GameData.getInstance().getCurrentPlayer() == 2 )
		{
			int numTerritorioDe2 = 0;
			for(Territorio t : Mapa.getInstance().getListaTerritorios())
			{
				if( t.getDonoIndex() == 2)
				{
					numTerritorioDe2++;
				}
				else
				{
					//ira returnar true no final se sempre entrar aqui
				}	
			}
			if(numTerritorioDe2 >= 24)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			for(Territorio t : Mapa.getInstance().getListaTerritorios())
			{
				if( t.getDonoIndex() == 2)
				{
					return false;
				}
				else
				{
					//ira returnar true no final se sempre entrar aqui
				}	
			}
			
		}
		return true;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return "Destruir o Jogador 2        por completo, se for voce,conquiste 24 territorios";
	}

}
