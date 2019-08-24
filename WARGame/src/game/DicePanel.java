package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by danilo on 07/06/15.
 */
public class DicePanel extends JPanel {
    private Image _img;
    private DiceType _type;
    private int _currentNumber = -1;

    DicePanel(DiceType tp) {
        _type = tp;
        loadDice();
    }

    public void loadDice() {
        int sortedInx = new Random().nextInt(6) + 1;
        String fileName = "";
        if (sortedInx != _currentNumber) {
            _currentNumber  = sortedInx;
            switch (_type) {
                case ATTACKER:
                    fileName = "images/dado_ataque_" + String.valueOf(sortedInx) + ".png";
                    break;
                case DEFENDER:
                    fileName = "images/dado_defesa_" + String.valueOf(sortedInx) + ".png";
                    break;
            }
            try {
                _img = ImageIO.read(new File(fileName));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
    }

    public int getNumber()
    {
    	return _currentNumber;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int diceSize = 80;
        Dimension ptDim = getParent().getSize();
        g.drawImage(_img, 2,2, diceSize, diceSize, null);
    }

}
