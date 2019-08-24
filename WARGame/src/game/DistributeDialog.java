package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.*;

public class DistributeDialog extends JDialog
{
	private JSpinner _spinner;
	private final GameData _gameData = GameData.getInstance();
	public DistributeDialog(JFrame parent, final Territorio territorio) {
		int maxArmy = _gameData.getNumArmysToDistribute();
		init(parent, territorio,maxArmy);
	}
	public DistributeDialog(JFrame parent, final Territorio territorio, int maxArmy)
	{
		super(parent);
		init(parent, territorio, maxArmy);

	}

	private void init(JFrame parent, final Territorio territorio, int maxArmy) {
		setTitle("Distribiur em " + territorio.getNome() + " que contem " + territorio.getNumExercitos() + " exercitos");
		JPanel mainPanel = new JPanel();
		JPanel spinPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		spinPanel.setLayout(new BoxLayout(spinPanel,BoxLayout.X_AXIS));
		SpinnerNumberModel spinnerControl = new SpinnerNumberModel(0,0,maxArmy,1);
		_spinner = new JSpinner(spinnerControl);

		//torna o spiner n�o editavel
		_spinner.setEditor(new JSpinner.DefaultEditor(_spinner));

		spinPanel.add(new JLabel("Numero de exercitos:   "));
		spinPanel.add(_spinner);
		JButton okButton = new JButton("OK");
		mainPanel.add(spinPanel);
		mainPanel.add(okButton);
		//setSize(new Dimension(300, 165));
		setBounds(0, 0, 500, 165);
		setContentPane(mainPanel);

		setResizable(false);
		setModal(true);
		setLocationRelativeTo(parent.getRootPane());

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int spinnerValue = ((Integer) _spinner.getValue()).intValue();
				territorio.setNumExercitos( territorio.getNumExercitos() + spinnerValue );
				GameData.getInstance().setNumArmysToDistribute( _gameData.getNumArmysToDistribute() -  spinnerValue );
				setVisible(false);
			}
		});
	}


}
