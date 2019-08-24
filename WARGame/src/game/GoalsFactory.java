package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class GoalsFactory
{
	static public ArrayList<Goal> makeGoalsList( int numberOfPlayers )
	{
		ArrayList<Goal> listOfGoals= new ArrayList<Goal>();
		HashSet<Integer> notSelectedSet = new HashSet<Integer>();
		notSelectedSet.add(0);
		notSelectedSet.add(1);
		notSelectedSet.add(2);
		notSelectedSet.add(3);
		notSelectedSet.add(4);
		notSelectedSet.add(5);
		notSelectedSet.add(6);
		
		for(int i =0; i< numberOfPlayers; i++)
		{
			boolean isGoalSelected = true;
			Integer sortedInx = 0;
			while( isGoalSelected )
			{
				sortedInx = (Integer) new Random().nextInt(7);
				if( notSelectedSet.contains( (Integer)sortedInx ) )
				{
					isGoalSelected = false;
				}				
			}
			if(sortedInx == 0 )
			{
				listOfGoals.add(new Goal1());				
			}
			else if(sortedInx == 1 )
			{
				listOfGoals.add(new Goal2());
			}
			else if(sortedInx == 2 )
			{
				listOfGoals.add(new Goal3());
			}
			else if(sortedInx == 3 )
			{
				listOfGoals.add(new Goal4());
			}
			else if(sortedInx == 4 )
			{
				listOfGoals.add(new Goal5());
			}
			else if(sortedInx == 5 )
			{
				listOfGoals.add(new Goal6());
			}
			else if(sortedInx == 6 )
			{
				listOfGoals.add(new Goal7());
			}
			
			notSelectedSet.remove(sortedInx);
			
		}
		
		return listOfGoals;
	}
}
