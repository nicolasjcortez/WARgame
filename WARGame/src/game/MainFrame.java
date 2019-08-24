/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


import controller.Mapa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author ncortez
 *         implements ComponentListener
 */
public class MainFrame extends JFrame {
    static private MainFrame _instance = null;
    private final CardLayout _mainLayout = new CardLayout();
    private final JPanel _contentPane = new JPanel(_mainLayout);
    //painel que contem o mapa
    private final Mapa _mapPanel = Mapa.getInstance();
    //painel que contem controles do jogo (pertence ao segundo painel principal)
    private JPanel _controlsPanel;
    //painel que contem os dados da rodada do jogo
    private JPanel _gameStatePanel;
    //label e indice do jogador corrente (pertence ao segundo painel principal)
    private final Label _currentPlayerName = new Label();
    private final ButtonGroup _gameStageButtonGroup = new ButtonGroup();
    private final JButton _startGameBtn = new JButton("Iniciar Jogo!");
    private final GameOptionsPanel _firstMainPanel = GameOptionsPanel.getInstance(_startGameBtn);;
    private final JPanel _secondMainPanel = new JPanel(new BorderLayout());
    private final JRadioButton _distributeRadioButton = new JRadioButton("Distribuir");
    private final JRadioButton _attackRadioButton = new JRadioButton("Atacar");
    private final JRadioButton _redistributeRadioButton = new JRadioButton("Redistribuir");

    private GameData _gameData;

    static public MainFrame getInstance() {
        if (_instance == null) {
            _instance = new MainFrame();
        }
        return _instance;
    }

    private MainFrame() {
        super(" War ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //painel de opcoes de jogo,ex: numero de jogadores
        _contentPane.add(_firstMainPanel, "GameOptions");

        //Painel principal do jogo
        createSecondMainPanel();
        _contentPane.add(_secondMainPanel, "Game");

        //exibe o painel de opcoes de jogo primeiramente
        _mainLayout.show(_contentPane, "GameOptions");

        setMinimumSize(new Dimension(672, 480));
        setContentPane(_contentPane);
        _mapPanel.repaint();
        addStartGameBtnListener();

    }


    private void createSecondMainPanel() {
        _controlsPanel = createButtonsPanel();
        createGameStatePanel();
        _secondMainPanel.add(_gameStatePanel, BorderLayout.NORTH);
        _secondMainPanel.add(_mapPanel, BorderLayout.CENTER);
        _secondMainPanel.add(_controlsPanel, BorderLayout.PAGE_END);
    }

    private void createGameStatePanel() {
        Box box;
        _gameStatePanel = new JPanel(new BorderLayout());
        JPanel auxStagePanel = new JPanel();
        //_gameStatePanel.setLayout(new BoxLayout(_gameStatePanel,new BorderLayout));
        auxStagePanel.setLayout(new BoxLayout(auxStagePanel, BoxLayout.X_AXIS));
        _currentPlayerName.setSize(new Dimension(160, _currentPlayerName.getHeight()));
        _currentPlayerName.setFont(new Font("Serif", Font.PLAIN, 16));
        _currentPlayerName.setForeground(Color.WHITE);
        box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalGlue());
        box.add(_currentPlayerName);
        box.add(Box.createHorizontalGlue());
        _gameStatePanel.add(box, BorderLayout.NORTH);
        //Pra centralizar
        box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalGlue());
        box.add(auxStagePanel);
        box.add(Box.createHorizontalGlue());
        _gameStatePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        _gameStageButtonGroup.add(_distributeRadioButton);
        _gameStageButtonGroup.add(_attackRadioButton);
        _gameStageButtonGroup.add(_redistributeRadioButton);
        auxStagePanel.add(_distributeRadioButton);
        auxStagePanel.add(_attackRadioButton);
        auxStagePanel.add(_redistributeRadioButton);
        _gameStatePanel.add(box, BorderLayout.CENTER);
        _distributeRadioButton.setSelected(true);
        _gameStatePanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1, true));
    }

    private void addRadioButtonListeners() {
        _gameData.setGameState(GameState.Distribute);
        _distributeRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _gameData.setGameState(GameState.Distribute);
            }
        });
        _attackRadioButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (_gameData.getNumArmysToDistribute() > 0) {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "Voce precisa distribuir exercitos primeiro!");
                    _distributeRadioButton.setSelected(true);
                } else {

                    _gameData.setGameState(GameState.Attack);
                }

            }
        });
        _redistributeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_gameData.getNumArmysToDistribute() > 0) {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "Voce precisa distribuir exercitos primeiro!");
                    _distributeRadioButton.setSelected(true);
                } else {
                    _gameData.setGameState(GameState.Redistribute);
                }
            }
        });
    }

    private JPanel createButtonsPanel() {
        Dimension size;
        JPanel p = new JPanel();
        JButton nextPlayerBtn = new JButton("Proximo Jogador");
        nextPlayerBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (_gameData.getNumArmysToDistribute() > 0) {
                    JOptionPane.showMessageDialog(MainFrame.getInstance(), "Voce precisa distribuir exercitos primeiro!");
                    _distributeRadioButton.setSelected(true);
                    return;
                }
                //verifica se o objetivo esta concluido
                if (GameData.getInstance().isTheEnd()) {
                    //O Jogo termina
                    MainFrame.getInstance().setVisible(false);
                    String endStr = "Voce " + MainFrame.getInstance().getCurrentPlayerName() + " venceu!!";
                    JOptionPane.showMessageDialog(null, endStr);
                }

                //atualiza na interface o proximo jogador
                _gameData.nextPlayer();
                _currentPlayerName.setText("Jogador " + (_gameData.getCurrentPlayer() + 1) + " - " + _gameData.getCurrentPlayerColorSlot().name);
                _gameStatePanel.setBackground(_gameData.getCurrentPlayerColor());
                _currentPlayerName.setBackground(_gameData.getCurrentPlayerColor());
                //s_currentPlayerName.getFont().

                //atualiza na base no data o proximo jogador
                GameData.getInstance().iniciarRodada();
                _distributeRadioButton.setSelected(true);
            }
        });

        final MainFrame thisWindow = this;

        JButton seeGoalBtn = new JButton("Visualizar Objetivo");
        seeGoalBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GoalDialog goalDialog = new GoalDialog(thisWindow);
                goalDialog.setVisible(true);
            }
        });
        Insets in = p.getInsets();
        size = nextPlayerBtn.getPreferredSize();
        nextPlayerBtn.setBounds(5, 5, size.width, size.height);
        size = seeGoalBtn.getPreferredSize();
        seeGoalBtn.setBounds(in.right + 40, 5, size.width, size.height);


        JButton seeCards = new JButton("Visualizar Cartas");
        size = seeCards.getPreferredSize();
        seeCards.setBounds(in.right + 75, 5, size.width, size.height);
        seeCards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeeCardsDialog modal = new SeeCardsDialog(thisWindow, _gameData.getCurrentPlayerCards());
                modal.pack();
                modal.setVisible(true);
            }
        });
        p.add(seeCards);
        p.add(seeGoalBtn);
        p.add(nextPlayerBtn);

        return p;
    }


    private void addStartGameBtnListener() {
        _startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //inicia o jogo em termos de dados(data e nao dice)
                _mainLayout.show(_contentPane, "Game");
                _gameData = GameData.getInstance();
                addRadioButtonListeners();
                _mapPanel.addListener();
                _mapPanel.repaint();
                _currentPlayerName.setText("Jogador " + (_gameData.getCurrentPlayer() + 1) + " - " + _gameData.getCurrentPlayerColorSlot().name);
                _gameStatePanel.setBackground(_gameData.getCurrentPlayerColor());
                _currentPlayerName.setBackground(_gameData.getCurrentPlayerColor());
                _gameData.startGame();
            }
        });
    }

    public int getNumberOfPlayers() {
        return _firstMainPanel.getNumberOfPlayers();
    }

    public ArrayList<ColorSlot> getPlayesColors() {
        return _firstMainPanel.getSortedColors();
    }

    public String getCurrentPlayerName() {
        return _currentPlayerName.getText();
    }

    public void setDistributeRadio(boolean isSelected) {
        _distributeRadioButton.setSelected(isSelected);
    }

    public void setAttackRadio(boolean isSelected) {
        _attackRadioButton.setSelected(isSelected);
    }
}
