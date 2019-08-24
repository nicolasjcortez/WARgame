package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by danilo on 27/05/15.
 * <p/>
 * <p/>
 * Classe não usada!! Tentativa removida
 */
public class PlayerInfoPanel extends JPanel {
    JLabel lblPlayerName;
    JTextField txtPlayerName;
    JLabel lblColor;
    JComboBox<String> cbbColor;
    int comboInx;

    PlayerInfoPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder(Color.black, Color.darkGray));


        {
            lblPlayerName = new JLabel("Nome:");
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 0;
            c.weighty = 1.0;
            c.insets = new Insets(5, 5, 5, 5);  //top padding
            c.gridx = 0;
            c.gridy = 0;
            add(lblPlayerName, c);
        }

        {
            txtPlayerName = new JTextField();
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weighty = 1.0;
            c.insets = new Insets(5, 5, 5, 5);
            c.gridx = 1;
            c.gridwidth = 5;
            c.gridy = 0;
            c.ipady = 8;
            c.ipadx = 8;
            add(txtPlayerName, c);
        }

        {
            lblColor = new JLabel("Cor:");
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weighty = 1.0;   //request any extra vertical space
            c.insets = new Insets(5, 5, 5, 5);  //top padding
            c.gridx = 0;
            c.gridy = 1;
            c.ipady = 0;
            c.ipadx = 0;
            c.gridwidth = 1;
            add(lblColor, c);
        }

        {
            cbbColor = new JComboBox<String>(getColors());
            cbbColor.setSelectedIndex(comboInx);
            cbbColor.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    JComboBox cb = (JComboBox) e.getSource();
                    String st = (String) e.getItem();
                    String curr = (String) cb.getSelectedItem();
                }
            });

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weighty = 1.0;   //request any extra vertical space
            c.insets = new Insets(5, 5, 5, 5);  //top padding
            c.gridx = 1;       //aligned with button 2
            c.gridwidth = 5;   //2 columns wide
            c.gridy = 1;       //third row
            c.weightx = 1.0;   //request any extra vertical space
            c.ipady = 8;
            add(cbbColor, c);
        }


    }

    private String[] getColors() {
        String res[] = new String[1];
        return res;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}