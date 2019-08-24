package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by danilo on 05/07/15.
 */
public class SeeCardsDialog extends JDialog {
    private final JButton _doChoose = new JButton("Efetuar Troca");
    private final ArrayList<Card> _selectedCards = new ArrayList<Card>();
    private final GameData _gameData = GameData.getInstance();
    private final HashMap<CardType, ArrayList<JCheckBox>> _checkboxes= new HashMap<CardType, ArrayList<JCheckBox>>();

    {
        _checkboxes.put(CardType.Circle, new ArrayList<JCheckBox>());
        _checkboxes.put(CardType.Joker, new ArrayList<JCheckBox>());
        _checkboxes.put(CardType.Triangle, new ArrayList<JCheckBox>());
        _checkboxes.put(CardType.Rectangle, new ArrayList<JCheckBox>());
    }

    private final HashMap<CardType, Boolean> _marked= new HashMap<CardType, Boolean>();
    {
        _marked.put(CardType.Circle, false);
        _marked.put(CardType.Joker, false);
        _marked.put(CardType.Triangle, false);
        _marked.put(CardType.Rectangle, false);
    }
    private final HashMap<CardType, Integer> _markedCount= new HashMap<CardType, Integer>();
    {
        _markedCount.put(CardType.Circle, 0);
        _markedCount.put(CardType.Joker, 0);
        _markedCount.put(CardType.Triangle, 0);
        _markedCount.put(CardType.Rectangle, 0);
    }

    public SeeCardsDialog(Container parent, ArrayList<Card> cards) {
        final JDialog dialog = this;

        final boolean triangle = false;
        final boolean circle = false;
        final boolean rectangle = false;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        setTitle("Vizualizar Cartas");
        JPanel contentPane = new JPanel(new BorderLayout());
        JPanel cardsGrid = new JPanel(new GridLayout(0,5));
        setSize(new Dimension(790, 570));
        setMinimumSize(getSize());
        cardsGrid.setSize(new Dimension(720, 432));

        int i =0;
        for(final Card c: cards) {
            final JButton btnChoose = _doChoose;
            if (i % 5 == 0) {
                i = 0;
            }

            JPanel cardPanel = new JPanel() {
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(c.getImagem(), 4, 30, 150, 280, this);
                }
            };

            {

                final JCheckBox troca = new JCheckBox("Trocar");
                _checkboxes.get(c.getType()).add(troca);


                troca.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (troca.isSelected()) {
                            _selectedCards.add(c);
                          _markedCount.put(c.getType(), _markedCount.get(c.getType()) + 1);
                        } else {
                            _selectedCards.remove(c);
                            _markedCount.put(c.getType(), _markedCount.get(c.getType()) - 1);
                        }
                        _marked.put(c.getType(), _markedCount.get(c.getType()) > 0);
                        btnChoose.setEnabled(verificaSePodeTrocar());
                    }
                });
                cardPanel.add(troca);
            }
            System.out.println("Novo teste");

            cardsGrid.add(cardPanel);
            i++;
        }

        //fixing layout

        for (; i < 5;i++ ) {
            cardsGrid.add(new Box(1));
        }

        contentPane.add(cardsGrid, BorderLayout.CENTER);


        Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalGlue());


        _doChoose.setEnabled(false);
        box.add(_doChoose);
        box.add(Box.createHorizontalGlue());
        _doChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int num  = _gameData.exchangeCards(_selectedCards);
                dialog.setVisible(false);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "Cartas trocadas", "Você tem "+num+" novos exercitos pra distribuir!", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton btnClose = new JButton("Fechar");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        box.add(Box.createHorizontalGlue());
        box.add(btnClose);
        box.add(Box.createHorizontalGlue());
        box.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
        contentPane.add(box, BorderLayout.PAGE_END);




        setResizable(false);
        setModal(true);
        setLocationRelativeTo(getRootPane().getTopLevelAncestor());
        setContentPane(contentPane);

    }
    public boolean verificaSePodeTrocar() {
        int total = 0;
        for (Integer i : _markedCount.values()) {
            total += i;
            if (total > 3) {
                return false;
            }
        }
        int amount = 3;
        if (_marked.get(CardType.Joker)) {
            amount = 2;
        }

        return (_marked.get(CardType.Joker) && ((_marked.get(CardType.Triangle) && _marked.get(CardType.Rectangle))
                || (_marked.get(CardType.Circle) && _marked.get(CardType.Rectangle))
                || (_marked.get(CardType.Circle) && _marked.get(CardType.Triangle))))
                || (_marked.get(CardType.Triangle) && _marked.get(CardType.Rectangle) && _marked.get(CardType.Circle))
                || _markedCount.get(CardType.Triangle) == amount
                || _markedCount.get(CardType.Rectangle) == amount
                || _markedCount.get(CardType.Circle) == amount
                ;
    }

}
