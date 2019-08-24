package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danilo on 27/05/15.
 */

public class NumberOfPlayersPanel extends JPanel {
    private JButton buttons[];
    private JButton sortButton;
    private int numberOfPlayers;
    private int min_players;
    private int max_players;

    NumberOfPlayersPanel(int nPLayers, int minPLayers, int maxPlayers, ActionListener[] btnListener, ActionListener btnSortListener)
    {
        super(null);
        setLayout(new GridBagLayout());
        final int MIN_PLAYERS = minPLayers;
        final int MAX_PLAYERS = maxPlayers;
        this.min_players = MIN_PLAYERS;
        this.max_players = MAX_PLAYERS;
        //final NumberOfPlayersPanel t = this;

        numberOfPlayers = nPLayers;
        buttons = new JButton[1 + MAX_PLAYERS - MIN_PLAYERS];

        sortButton = new JButton("Sortear cores");
        sortButton.addActionListener(btnSortListener);

        JPanel leftPanel = new JPanel(null);
        GridBagLayout leftLayout = new GridBagLayout();
        leftPanel.setLayout(leftLayout);
        JLabel lbl = new JLabel("Numero de Jogadores:");
        GridBagConstraints c4 = new GridBagConstraints();
        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.weighty = 1.0;
        c4.insets = new Insets(5, 5, 5, 5);  //top padding
        c4.gridx = 0;
        c4.gridy = 0;
        c4.ipady = 0;
        c4.ipadx = 0;
        c4.gridwidth = 1;
        leftPanel.add(lbl, c4);
        GridBagConstraints c3 = new GridBagConstraints();
        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.weighty = 1.0;   //request any extra vertical space
        c3.insets = new Insets(5, 5, 5, 5);  //top padding
        c3.gridx = 1;
        c3.gridy = 0;
        c3.ipady = 0;
        c3.ipadx = 0;
        c3.gridwidth = 1;

        //buttons;
            JPanel buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));

            for (int i = MIN_PLAYERS; i <= MAX_PLAYERS; i++) {
                final int j = i;
                final JButton btn = new JButton(String.valueOf(i));
                btn.setFont(Font.getFont(Font.SANS_SERIF));
                btn.addActionListener(btnListener[i - MIN_PLAYERS]);
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numberOfPlayers = j;
                        fixButtons();;
                    }
                });


                buttons[i - MIN_PLAYERS] = btn;
                buttonsPanel.add(btn);
            }

            leftPanel.add(buttonsPanel, c3);

            GridBagConstraints c2 = new GridBagConstraints();
            c2.fill = GridBagConstraints.HORIZONTAL;

            add(leftPanel, c2);
 

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        add(sortButton, c);
        fixButtons();
    }

    public void fixButtons()
    {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(i + min_players != numberOfPlayers);
        }
    }
}