package game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controller.Territorio;

public class RedistributeDialog extends JDialog
{
	private JSpinner _armySpinner;
	private JComboBox<String> _aliadosFronteiraCombo;
	private SpinnerNumberModel _spinnerControl = new SpinnerNumberModel(0,0,3,1);
    private final GameData _gameData = GameData.getInstance();

	public RedistributeDialog(JFrame parent, final Territorio territorio)
	{
		super(parent);
		setTitle( "Redistribuir a partir do(a) "+ territorio.getNome() + " que contem " + territorio.getNumExercitos() + " exercitos");
		JPanel mainPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		JPanel spinPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		comboPanel.setLayout(new BoxLayout(comboPanel,BoxLayout.X_AXIS));
		spinPanel.setLayout(new BoxLayout(spinPanel,BoxLayout.X_AXIS));
		
		_spinnerControl.setMaximum(territorio.getNumExercitos() - 1);
		_spinnerControl.setMinimum(1);
		_armySpinner = new JSpinner(_spinnerControl);
		

		_armySpinner.setEditor(new JSpinner.DefaultEditor(_armySpinner));
		_aliadosFronteiraCombo = new JComboBox<String>();
        for (Territorio t : territorio.getListaTerritoriosFrontreira())
        {
        	if( t.getDonoIndex() == GameData.getInstance().getCurrentPlayer() )
        	{
        		_aliadosFronteiraCombo.addItem(t.getNome());
        	}
        }
		
		 	
		spinPanel.add(new JLabel("Numero de exercitos:"));
		spinPanel.add(_armySpinner);
		
		comboPanel.add(new JLabel("Territorios de Remanejamento:"));
		comboPanel.add(_aliadosFronteiraCombo);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GameData.getInstance().finishRedistribute((int) _armySpinner.getValue(), territorio.getNome(), (String) _aliadosFronteiraCombo.getSelectedItem());
                setVisible(false);
            }
        });
		
		mainPanel.add(comboPanel);
		mainPanel.add(spinPanel);
		mainPanel.add(okButton);
		setSize(new Dimension(500, 165));
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(parent.getRootPane());
		setContentPane(mainPanel);
	}
	
	public JSpinner getSpinner()
	{
		return _armySpinner;
	}
	
	public JComboBox<String> getCombo()
	{
		return _aliadosFronteiraCombo;
	}
	
	public SpinnerNumberModel getSpinnerControl()
	{
		return _spinnerControl;
	}
}
