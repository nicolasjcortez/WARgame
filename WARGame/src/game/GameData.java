package game;

import controller.Mapa;
import controller.Territorio;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class GameData {
    private GameState _state;
    private static GameData _instance = null;
    private int _currentPlayer = 0;
    private int _numberOfPlayers;
    private int _numeroTroca = 0;
    private ArrayList<ColorSlot> _playersColorsList;
    private ArrayList<Territorio> _listaTerritorios;
    private ArrayList<Goal> _listOfGoals;
    private LinkedList<Card> _exchangedCards = new LinkedList<Card>();
    private LinkedList<Card> _cards = new LinkedList<Card>();
    private ArrayList<ArrayList<Card>> _listaCardsPorJogador = new ArrayList<ArrayList<Card>>();
    private boolean _canGetACard = false;

    //Deck<Card>
    //exercitos para distribuir do jogador corrente
    private int _armysToDistribute = 0;
    private Mapa _map;

    private GameData() {
    }

    //chamada no clique do botao Iniciar Jogo!
    public void startGame() {
        _numberOfPlayers = MainFrame.getInstance().getNumberOfPlayers();
        setPlayersColorsList(MainFrame.getInstance().getPlayesColors());
        _map = Mapa.getInstance();
        _listaTerritorios = (ArrayList<Territorio>) _map.getListaTerritorios();
        int i = 0;

        //divide os territ�rios, j� setando os seus donos
        for (Territorio t : _listaTerritorios) {
            _map.editarDonoPais(t.getNome(), getPlayersColorsList().get(i).getColor(), i);

            //dividindo igualmente os territorios
            i++;
            if (i >= _numberOfPlayers) {
                i = 0;
            }
            _cards.add(new Card(t));
        }

        // Coringas
        _cards.add(new Card(null));
        _cards.add(new Card(null));

        for (i = 0; i < _numberOfPlayers; i++) {
            _listaCardsPorJogador.add(new ArrayList<Card>());
        }
        Collections.shuffle(_cards);

        //cria objetivos para cada jogador
        _listOfGoals = GoalsFactory.makeGoalsList(_numberOfPlayers);

        iniciarRodada();
    }

    public void iniciarRodada() {

        //atualiza o jogador corrente
        _state = GameState.Distribute;
        //setCurrentPlayer(MainFrame.getInstance().getCurrentPlayerIndex());

        //Jogador corrente recebe exercitos
        _armysToDistribute = getPlayerNumberOfCountries(getCurrentPlayer()) / 2;


        //ataque acontecer� por tras dos panos
        //(A callback da janela Atack altera os dados dessa classe)


        //redistribuir acontecer� por tras dos panos
        //(A callback da Redistribute janela altera os dados dessa classe)

    }

    //recupera o numero de territorios que um jogador possue
    public int getPlayerNumberOfCountries(int playerIndex) {
        int i = 0;
        for (Territorio t : _listaTerritorios) {
            if (t.getDonoIndex() == playerIndex) {
                i++;
            }
        }
        return i;
    }

    public void finishAttack(int numArmyAttWinners, int numArmyDefWinners, String attCountryName, String defCountryName) {
        //essa funcao muda o dono do defCountryName caso o att ganhe
        //a combo regula o num de exercitos do ataque
        //deve se mudar territodio dono e a cor do marcador
        Territorio defCountry = _map.getTerritorio(defCountryName);
        Territorio attCountry = _map.getTerritorio(attCountryName);
        defCountry.setNumExercitos(defCountry.getNumExercitos() - numArmyAttWinners);
        attCountry.setNumExercitos(attCountry.getNumExercitos() - numArmyDefWinners);


        //muda o dono se for o caso
        if (defCountry.getNumExercitos() == 0) {
            _canGetACard = true;
            _map.editarDonoPais(defCountryName, getPlayersColorsList().get(attCountry.getDonoIndex()).getColor(), attCountry.getDonoIndex());
            RedistributePosAttackDialog redistWindow = new RedistributePosAttackDialog(MainFrame.getInstance(), attCountry, defCountry);
            redistWindow.setVisible(true);
            _map.repaint();
        }
    }

    public void finishRedistribute(int numOfArmys, String originCountryName, String destinyCountryName) {
        Territorio originCountry = _map.getTerritorio(originCountryName);
        Territorio destinyCountry = _map.getTerritorio(destinyCountryName);
        System.out.println("Origem antes: " + originCountry.getNumExercitos());
        System.out.println("Destino antes: " + destinyCountry.getNumExercitos());
        originCountry.setNumExercitos(originCountry.getNumExercitos() - numOfArmys);
        destinyCountry.setNumExercitos(destinyCountry.getNumExercitos() + numOfArmys);
        System.out.println("Origem depois: " + originCountry.getNumExercitos());
        System.out.println("Destino depois: " + destinyCountry.getNumExercitos());
    }

    static public GameData getInstance() {
        if (_instance == null) {
            _instance = new GameData();
        }

        return _instance;
    }

    public void setGameState(GameState state) {
        _state = state;
    }

    public GameState getGameState() {
        return _state;
    }

    public void setNumArmysToDistribute(int numOfArmys) {
        _armysToDistribute = numOfArmys;
    }

    public int getNumArmysToDistribute() {
        return _armysToDistribute;
    }

    public String getCurrentPlayerGoalMsg() {
        return _listOfGoals.get(getCurrentPlayer()).getMsg();
    }

    public boolean isTheEnd() {
        return _listOfGoals.get(getCurrentPlayer()).check();
    }

    public ArrayList<Card> getCurrentPlayerCards() {
        return _listaCardsPorJogador.get(getCurrentPlayer());
    }

    //controller verifica se pode, a assertiva é que pode
    public int exchangeCards(ArrayList<Card> cards) {
        for (Card c:cards) {
            _listaCardsPorJogador.get(getCurrentPlayer()).remove(c);
            _cards.remove(c);
            _exchangedCards.add(c);
        }
        int num = 0;
        switch (_numeroTroca){
            case 0:
                num = 4;
                break;
            case 1:
                num = 6;
                break;
            case 2:
                num =8;
                break;
            case 3:
                num=10;
                break;
            case 4:
                num=12;
                break;
            case 5:
                num=15;
                break;
            default:
                num = (_numeroTroca-5)*5 + 15;
                break;
        }

        if (_cards.size() == 0) {
            Collections.shuffle(_exchangedCards);
            _cards.addAll(_exchangedCards);
            _exchangedCards.clear();
        }
        _armysToDistribute += num;
        return num;
    }

    public ColorSlot getCurrentPlayerColorSlot() {
        return getPlayersColorsList().get(getCurrentPlayer());
    }

    public Color getCurrentPlayerColor() {
        return getPlayersColorsList().get(getCurrentPlayer()).getColor();
    }


    public int getCurrentPlayer() {
        return _currentPlayer;
    }

    public void setCurrentPlayer(int _currentPlayer) {
        this._currentPlayer = _currentPlayer;
    }

    public ArrayList<ColorSlot> getPlayersColorsList() {
        return _playersColorsList;
    }

    public void setPlayersColorsList(ArrayList<ColorSlot> _playersColorsList) {
        this._playersColorsList = _playersColorsList;
    }

    public void nextPlayer() {
        if (_canGetACard) {
            Card c = _cards.poll();
            _listaCardsPorJogador.get(getCurrentPlayer()).add(c);
            _canGetACard = false;
        }
        if ((_instance.getCurrentPlayer() + 1) < _instance._numberOfPlayers) {
            _instance.setCurrentPlayer(_instance.getCurrentPlayer() + 1);
        } else {
            _instance.setCurrentPlayer(0);
        }
    }
}
