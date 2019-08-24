package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by danilo on 26/05/15.
 */
public class GameOptionsPanel extends JPanel {
    //Assert: no one will change it;
	
	static private GameOptionsPanel _instance = null;
	
    public static HashMap<String, ColorSlot> colors;
    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 6;

    private int numberOfPlayers = MIN_PLAYERS;
    private ArrayList<ColorSlot> freeColors = new ArrayList<ColorSlot>();
    private ArrayList<ColorSlot> sortedColors = new ArrayList<ColorSlot>();
    private PlayerColorsPanel players;

    //initialize colors
    {
        ColorSlot c;
        colors = new HashMap<String, ColorSlot>();
        c = new ColorSlot("Azul", Color.getHSBColor(232.0f / 360f, 70.0f / 100f, 56.0f / 100f));
        colors.put(c.name, c);
        c = new ColorSlot("Vermelho", Color.getHSBColor(3.0f / 360f, 77.0f / 100f, 93.0f / 100f));
        colors.put(c.name, c);
        c = new ColorSlot("Amarelo", Color.getHSBColor(29.0f / 360f, 80.0f / 100f, 96.0f / 100f));
        colors.put(c.name, c);
        c = new ColorSlot("Ciano", Color.getHSBColor(199.0f / 360f, 80.0f / 100f, 87.0f / 100f));
        colors.put(c.name, c);
        c = new ColorSlot("Verde", Color.getHSBColor(152.0f / 360f, 100.0f / 100f, 41.0f / 100f));
        colors.put(c.name, c);
        c = new ColorSlot("Roxo", Color.getHSBColor(274.0f / 360f, 69.0f / 100f, 56.0f / 100f));
        colors.put(c.name, c);
    }

    static GameOptionsPanel getInstance(JButton nextBtn)
    {
    	if(_instance == null)
    	{
    		_instance = new GameOptionsPanel(nextBtn);
    	}
    	return _instance;
    }
    //Constructor
    private GameOptionsPanel(JButton nextBtn)
    {
        setLayout(new GridBagLayout());

        NumberOfPlayersPanel np = new NumberOfPlayersPanel(numberOfPlayers, MIN_PLAYERS, MAX_PLAYERS, createListeners(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortColors();
            }
        });

        {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            add(np, c);
        }

        players = new PlayerColorsPanel();

        {

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 0.8;
            c.weightx = 1;
            c.gridheight = 5;
            add(players, c);
        }

        {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 7;
            add(nextBtn, c);
        }
        sortColors();
    }

    private void resetColors() {
        players.setColors(null);
    }

    private ActionListener CreateActionList(int i) {
        final GameOptionsPanel g = this;
        final int j = i;
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.numberOfPlayers = j;
                g.resetColors();
                sortColors();
            }
        };
    }

    private ActionListener[] createListeners() {
        ActionListener listeners[] = new ActionListener[(MAX_PLAYERS - MIN_PLAYERS) + 1];
        for (int i = 0; i < listeners.length; i++) {
            listeners[i] = CreateActionList(MIN_PLAYERS + i);
        }
        return listeners;
    }

    public void sortColors() {
        ColorSlot color;
        ArrayList<ColorSlot> colorList = new ArrayList<ColorSlot>();
        sortedColors.clear();

        // restore each color to avaiable and add to list
        for (ColorSlot c : colors.values()) {
            c.avaiable = true;
            colorList.add(c);
        }

        //sorting over array inxes
        for (int i = 0; i < numberOfPlayers; i++) {
            int sortedInx = new Random().nextInt(colorList.size());
            color = colorList.get(sortedInx);
            colorList.remove(sortedInx);
            color.avaiable = false;
            sortedColors.add(color);
        }

        freeColors.clear();
        freeColors.addAll(colorList);
        players.setColors(sortedColors);
    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }
    public ArrayList<ColorSlot> getSortedColors()
    {
    	return sortedColors;
    }


}
