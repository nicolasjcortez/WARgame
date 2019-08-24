package game;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.Mapa;
import controller.Territorio;


public class AttackDialog extends JDialog
{
	private JSpinner _armySpinner;
	private SpinnerNumberModel _spinnerControl = new SpinnerNumberModel(0,0,4,1);
	private JComboBox<String> _enemiesCombo;
	private JLabel _defDataLabel;
	public AttackDialog(JFrame parent, final Territorio attCountry, int maxArmy)
	{
		super(parent);
		String title = "Ataque a partir do(a) "+ attCountry.getNome() +" que contem " + attCountry.getNumExercitos() + " exercitos";
		setTitle( title );
		JPanel mainPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		JPanel spinPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		comboPanel.setLayout(new BoxLayout(comboPanel,BoxLayout.X_AXIS));
		spinPanel.setLayout(new BoxLayout(spinPanel,BoxLayout.X_AXIS));		
		
		_enemiesCombo = new JComboBox<String>();
		int numEnimies = 0;
        for (Territorio t : attCountry.getListaTerritoriosFrontreira())
        {
        	if( t.getDonoIndex() != GameData.getInstance().getCurrentPlayer())
        	{
        		_enemiesCombo.addItem(t.getNome());
        		numEnimies++;
        	}
        }
		if(numEnimies == 0)
		{
			JOptionPane.showMessageDialog(null,"Esse territorio nao pode atacar ninguem!");
		}
        
		String defCountryName = (String)_enemiesCombo.getSelectedItem();
		final Territorio defCountry =  Mapa.getInstance().getTerritorio(defCountryName);
		System.out.println("Defesa: " + defCountry.getNome() + " " + defCountry.getNumExercitos());
		if( attCountry.getNumExercitos() > 1)
		{
			if(attCountry.getNumExercitos() > 3)
			{
				_spinnerControl.setMaximum(3);
			}
			else
			{
				_spinnerControl.setMaximum( attCountry.getNumExercitos() - 1);
			}
		}
		else if( attCountry.getNumExercitos() == 1)
		{
			_spinnerControl.setMaximum(0);
		}
		//SpinnerNumberModel _spinnerControl = new SpinnerNumberModel(0,0,maxArmy,1);
		_armySpinner = new JSpinner(_spinnerControl);
		_armySpinner.setValue(0);
		
		//torna o spiner n�o editavel
		_armySpinner.setEditor(new JSpinner.DefaultEditor(_armySpinner));
        
		spinPanel.add(new JLabel("Numero de exercitos:   "));
		spinPanel.add(_armySpinner);
		
		comboPanel.add(new JLabel("Territorios inimigos:   "));
		comboPanel.add(_enemiesCombo);
		_enemiesCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{	
				
				_armySpinner.setValue(0);
				String defCountryName = (String)_enemiesCombo.getSelectedItem();
				Territorio defCountry =  Mapa.getInstance().getTerritorio(defCountryName);	
				if( attCountry.getNumExercitos() > 1)
				{
					if(attCountry.getNumExercitos() > 3)
					{
						_spinnerControl.setMaximum(3);
					}
					else
					{
						_spinnerControl.setMaximum(attCountry.getNumExercitos() - 1);
					}
				}
				else if( attCountry.getNumExercitos() == 1)
				{
					_spinnerControl.setMaximum(0);
				}
				
				if(defCountry.getNumExercitos() > 3)
				{
					_defDataLabel.setText( defCountryName +" tem " + defCountry.getNumExercitos()+ " entao defende com 3" + " dados");
				}
				else
				{
					_defDataLabel.setText( defCountryName +" tem " + defCountry.getNumExercitos() + " entao  defende com " + defCountry.getNumExercitos() + " dados");
				}
			}
		});
		
		JButton diceButton = new JButton("Jogar Dados");	
		diceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if((Integer)_armySpinner.getValue() == 0)
				{
					JOptionPane.showMessageDialog(null,"Numero de dados de ataque igual a 0, nada ocorreu!");
					return;
				}
				int defNumDices;
				if(defCountry.getNumExercitos() > 3)
				{
					defNumDices = 3;
				}
				else
				{
					defNumDices = defCountry.getNumExercitos();
				}
                DiceDialog diceDialog = new DiceDialog( MainFrame.getInstance(), (Integer)_armySpinner.getValue(), defNumDices, attCountry.getNome(), (String)_enemiesCombo.getSelectedItem()  );
                diceDialog.setVisible(true);
				
			}
		});			
		
		mainPanel.add(comboPanel);
		mainPanel.add(spinPanel);
		if(defCountry.getNumExercitos() > 3)  {
			_defDataLabel = new JLabel( defCountryName +" tem " + defCountry.getNumExercitos()+ " entao defende com 3" + " dados");
		} else {
			_defDataLabel = new JLabel( defCountryName +" tem " + defCountry.getNumExercitos() + " entao  defende com " + defCountry.getNumExercitos() + " dados");
		}
		mainPanel.add(_defDataLabel);
		mainPanel.add(diceButton);
		setSize(new Dimension(500, 165));
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(parent.getRootPane());
		setContentPane(mainPanel);
	}
	
}


