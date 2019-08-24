package game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by danilo on 27/05/15.
 */
public class PlayerColorsPanel extends JPanel {
    private ArrayList<ColorSlot> colors = new ArrayList<ColorSlot>();

    PlayerColorsPanel() {
        super(new GridLayout(2, 3));
        createAreas();
    }

    private void createAreas() {
        int i = 1;
        if (colors == null) {
            return;
        }

        for (ColorSlot colorSlot : colors) {
            final int j = i;
            JPanel p = new JPanel() {
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawString(String.valueOf(j), getWidth() / 2, getHeight() / 2);
                }
            };
            p.setBackground(colorSlot.color);
            add(p);
            i++;
        }
    }

    @Override
    public void repaint() {
        removeAll();
        createAreas();
        revalidate();
        super.repaint();
    }

    public void setColors(ArrayList<ColorSlot> listColors) {
        colors.clear();
        if (listColors != null) {
            colors.addAll(listColors);
        }
        GameData.getInstance().setPlayersColorsList(colors);
        repaint();
    }
}
