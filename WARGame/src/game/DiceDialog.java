package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class DiceDialog extends JDialog {
    private JButton _closeButton = new JButton("Fechar");
    //private final DicePanel _attackerDice = new DicePanel(DiceType.ATTACKER);
    //private final DicePanel _defenderDice = new DicePanel(DiceType.DEFENDER);
    private ArrayList<Integer> _attackerDiceList = new ArrayList<Integer>();
    private ArrayList<Integer> _defenderDiceList = new ArrayList<Integer>();

    public DiceDialog(JFrame parent, Integer numDicesAtt, Integer numDicesDef, final String attCountryName, final String defCountryName)
    {
        super(parent);
        setTitle("Dados");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel dicesPanel = new JPanel();
        dicesPanel.setLayout(new BoxLayout(dicesPanel, BoxLayout.LINE_AXIS));

        for(int i=0; i<numDicesAtt; i++)
        {
        	DicePanel attDice = new DicePanel(DiceType.ATTACKER);
 	        dicesPanel.add(attDice);
	        _attackerDiceList.add( (Integer)attDice.getNumber());
        }
        for(int i=0; i<numDicesDef; i++)
        {
       		DicePanel defDice = new DicePanel(DiceType.DEFENDER);
	        dicesPanel.add(defDice);  
	        _defenderDiceList.add( (Integer)defDice.getNumber());
        }
       			
        mainPanel.add(dicesPanel, BorderLayout.CENTER);
        mainPanel.add(_closeButton, BorderLayout.PAGE_END);

        final int _numDicesAtt = numDicesAtt;
        final int _numDicesDef = numDicesDef;
        _closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
            	int numAttVencedores = 0;
            	
            	Collections.sort(_attackerDiceList);
            	Collections.sort(_defenderDiceList);
            	int shotestListSize, longestListSize;
            	if(_attackerDiceList.size() < _defenderDiceList.size())
            	{
            		shotestListSize = _attackerDiceList.size();
            		longestListSize =_defenderDiceList.size();
            	}
            	else
            	{
            		shotestListSize = _defenderDiceList.size();
            		longestListSize =_attackerDiceList.size();
            	}
            	
            	//atualizar� uma variavel que indica o numero de exercitos vencedores
            	System.out.println("ForSize = " +shotestListSize);
            	for(int sht = shotestListSize-1, lg = longestListSize-1; sht >= 0; sht--, lg--)
            	{
            		//System.out.println(_attackerDiceList.get() + " > " + _defenderDiceList.get(i)+ " ? " );
            		if(_attackerDiceList.size() < _defenderDiceList.size())
                	{
	            		if( _attackerDiceList.get(sht) > _defenderDiceList.get(lg) )
	            		{
	            			numAttVencedores++;
	            		}
                	}
            		else
            		{
	            		if( _attackerDiceList.get(lg) > _defenderDiceList.get(sht) )
	            		{
	            			numAttVencedores++;
	            		}
            		}
            	}            	
            	int numDefVencedores = shotestListSize - numAttVencedores;
            	System.out.println("atqueWin = " + numAttVencedores + "defWin = " + numDefVencedores);
            	GameData.getInstance().finishAttack(numAttVencedores,numDefVencedores, attCountryName, defCountryName);
                setVisible(false);
            }
        });
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(parent.getRootPane());
        setSize(new Dimension( (_defenderDiceList.size() + _attackerDiceList.size())*170/2, 165));
        setContentPane(mainPanel);
    }
}
