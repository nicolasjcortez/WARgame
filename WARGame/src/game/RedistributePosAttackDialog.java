package game;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controller.Territorio;

public class RedistributePosAttackDialog extends RedistributeDialog
{
	private JSpinner _armySpinner;
	private JComboBox<String> _oTerritorioConquistadoCombo;
	private SpinnerNumberModel _spinnerControl;
	public RedistributePosAttackDialog(JFrame parent, Territorio territorio, Territorio territorioConquistado)
	{
		super(parent, territorio);
		 setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		_oTerritorioConquistadoCombo = getCombo();
		_oTerritorioConquistadoCombo.removeAllItems();
		_oTerritorioConquistadoCombo.addItem(territorioConquistado.getNome());
		_armySpinner = getSpinner();
		_armySpinner.setValue(1);
		_spinnerControl = getSpinnerControl();
		if(territorio.getNumExercitos() > 3)
		{
			_spinnerControl.setMaximum(3);
		}
		else
		{
			_spinnerControl.setMaximum(territorio.getNumExercitos() - 1);
		}
		_spinnerControl.setMinimum(1);
	}

}
