package controller;

import game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class Mapa extends JPanel {
    private final double BASE_WIDTH = 1024;
    private final double BASE_HEIGHT = 768;
    // deslocaX e deslocaY � utilizado para alinhar os pol�gonos criados em cima da imagem dos territorios.
    private final int deslocaX = 0;
    private final int deslocaY = 0;

    // Lista de territorios
    private ArrayList<Territorio> _listaDeTerritorios;
    private double imageRatio;
    private Image _img = null;
    private HashMap<String, Marker> _hashMarcadores = new HashMap<String, Marker>();
    private HashMap<String, Territorio> _hashTerritorios = new HashMap<String, Territorio>();

    private GameData _gameData;
    //private MainFrame _mainWindow  = MainFrame.getInstance();

    static private Mapa _instance = null;

    static public Mapa getInstance() {
        if (_instance == null) {
            _instance = new Mapa();
        }
        return _instance;
    }

    // Bloco de inicializa��o dos territ�rios
    // Estou assumindo que a classe territorio tem um nome e um poligono definindo sua área de clique.
    private void inicializarTerritorios() {
        // Adicionando os territorios na lista de territorios.
        _listaDeTerritorios.add(new Territorio("Alasca", "America do Norte", new Ponto[]{
                new Ponto(86.1, 124.1),
                new Ponto(134.6, 124.1),
                new Ponto(111.4, 167.3),
                new Ponto(58.7, 167.3),
                new Ponto(72.3, 141.8),
                new Ponto(77.3, 141.8)
        }));

        _listaDeTerritorios.add(new Territorio("Calgary", "America do Norte", new Ponto[]{
                new Ponto(148.7, 124.1),
                new Ponto(134.6, 124.1),
                new Ponto(126.4, 139.5),
                new Ponto(146.9, 177.0),
                new Ponto(223.0, 177.0),
                new Ponto(231.3, 163.2),
                new Ponto(245.5, 163.2),
                new Ponto(258.5, 139.5),
                new Ponto(284.1, 139.5),
                new Ponto(271.6, 116.9),
                new Ponto(241.7, 116.9),
                new Ponto(230.4, 137.8),
                new Ponto(212.9, 137.8),
                new Ponto(208.7, 130.1),
                new Ponto(152.4, 130.1)
        }));

        _listaDeTerritorios.add(new Territorio("Groelandia", "America do Norte", new Ponto[]{
                new Ponto(285.4, 94.6),
                new Ponto(271.6, 116.9),
                new Ponto(284.1, 139.5),
                new Ponto(328.7, 139.5),
                new Ponto(335.1, 149.5),
                new Ponto(350.8, 149.5),
                new Ponto(365.9, 122.1),
                new Ponto(371.4, 122.1),
                new Ponto(380.4, 108.5),
                new Ponto(371.0, 94.6)
        }));

        _listaDeTerritorios.add(new Territorio("Vancouver", "America do Norte", new Ponto[]{
                new Ponto(111.4, 167.3),
                new Ponto(119.3, 182.3),
                new Ponto(102.1, 211.9),
                new Ponto(107.5, 220.0),
                new Ponto(214.0, 220.0),
                new Ponto(231.7, 191.2),
                new Ponto(223.0, 177.0),
                new Ponto(146.90, 177.0),
                new Ponto(126.4, 139.5)
        }));

        _listaDeTerritorios.add(new Territorio("Quebec", "America do Norte", new Ponto[]{
                new Ponto(262.2, 190.8),
                new Ponto(231.7, 190.8),
                new Ponto(214.0, 220.0),
                new Ponto(296.9, 220.0),
                new Ponto(304.3, 207.1),
                new Ponto(316.1, 207.1),
                new Ponto(312.5, 215.3),
                new Ponto(325.1, 215.3),
                new Ponto(329.9, 204.5),
                new Ponto(322.8, 191.4),
                new Ponto(331.6, 177.8),
                new Ponto(337.1, 184.7),
                new Ponto(344.7, 172.2),
                new Ponto(340.5, 163.4),
                new Ponto(321.2, 163.4),
                new Ponto(318.8, 160.4),
                new Ponto(288.7, 160.4),
                new Ponto(281.9, 171.9),
                new Ponto(272.9, 171.9)
        }));

        _listaDeTerritorios.add(new Territorio("California", "America do Norte", new Ponto[]{
                new Ponto(107.5, 220.0),
                new Ponto(84.7, 258.0),
                new Ponto(89.9, 268.9),
                new Ponto(78.2, 289.0),
                new Ponto(90.4, 310.2),
                new Ponto(126.4, 310.2),
                new Ponto(178.5, 220.0)
        }));

        _listaDeTerritorios.add(new Territorio("Texas", "America do Norte", new Ponto[]{
                new Ponto(202.2, 248.2),
                new Ponto(231.7, 248.2),
                new Ponto(246.1, 220.0),
                new Ponto(178.5, 220.0),
                new Ponto(126.4, 310.2),
                new Ponto(145.9, 343.8)
        }));

        _listaDeTerritorios.add(new Territorio("NovaYork", "America do Norte", new Ponto[]{
                new Ponto(181.8, 310.2),
                new Ponto(165.7, 310.2),
                new Ponto(202.2, 248.2),
                new Ponto(231.7, 248.2),
                new Ponto(246.1, 220.0),
                new Ponto(296.9, 220.0),
                new Ponto(290.7, 231.7),
                new Ponto(279.4, 231.7),
                new Ponto(261.5, 265.1),
                new Ponto(243.7, 265.1),
                new Ponto(237.8, 279.1),
                new Ponto(231.3, 279.1),
                new Ponto(215.3, 306.5),
                new Ponto(207.3, 306.5),
                new Ponto(200.6, 318.9),
                new Ponto(211.5, 337.8),
                new Ponto(204.3, 349.4)
        }));

        _listaDeTerritorios.add(new Territorio("Mexico", "America do Norte", new Ponto[]{
                new Ponto(90.4, 310.2),
                new Ponto(83.1, 323.0),
                new Ponto(103.6, 359.2),
                new Ponto(110.6, 346.5),
                new Ponto(102.5, 330.6),
                new Ponto(105.7, 324.8),
                new Ponto(136.6, 377.6),
                new Ponto(145.7, 377.6),
                new Ponto(162.1, 408.2),
                new Ponto(172.2, 408.2),
                new Ponto(179.8, 421.9),
                new Ponto(188.8, 406.2),
                new Ponto(183.8, 398.0),
                new Ponto(178.5, 398.0),
                new Ponto(173.0, 389.4),
                new Ponto(173.0, 386.3),
                new Ponto(175.3, 384.3),
                new Ponto(167.1, 370.0),
                new Ponto(171.8, 360.7),
                new Ponto(164.3, 348.1),
                new Ponto(161.4, 355.3),
                new Ponto(152.3, 355.3),
                new Ponto(126.4, 310.2)
        }));

        _listaDeTerritorios.add(new Territorio("Venezuela", "America do Sul", new Ponto[]{
                new Ponto(193.5, 398.2),
                new Ponto(254.3, 398.2),
                new Ponto(196.8, 498.1),
                new Ponto(188.8, 482.2),
                new Ponto(176.2, 482.2),
                new Ponto(161.2, 455.8),
                new Ponto(179.8, 421.9),
                new Ponto(188.8, 406.2)
        }));

        _listaDeTerritorios.add(new Territorio("Peru", "America do Sul", new Ponto[]{
                new Ponto(196.8, 498.1),
                new Ponto(202.3, 507.9),
                new Ponto(216.1, 507.9),
                new Ponto(232.2, 538.5),
                new Ponto(226.8, 545.4),
                new Ponto(239.7, 564.8),
                new Ponto(274.9, 505.0),
                new Ponto(233.8, 433.8)
        }));

        _listaDeTerritorios.add(new Territorio("Brasil", "America do Sul", new Ponto[]{
                new Ponto(259.6, 407.2),
                new Ponto(285.3, 407.2),
                new Ponto(293.4, 423.9),
                new Ponto(305.7, 423.9),
                new Ponto(316.1, 445.2),
                new Ponto(335.4, 445.2),
                new Ponto(343.3, 461.0),
                new Ponto(343.3, 463.1),
                new Ponto(325.9, 491.3),
                new Ponto(333.8, 505.0),
                new Ponto(274.9, 505.0),
                new Ponto(233.8, 433.8),
                new Ponto(254.3, 398.2)
        }));

        _listaDeTerritorios.add(new Territorio("Argentina", "America do Sul", new Ponto[]{
                new Ponto(333.8, 505.0),
                new Ponto(302.4, 558.8),
                new Ponto(311.2, 573.0),
                new Ponto(281.6, 624.5),
                new Ponto(295.7, 650.2),
                new Ponto(278.1, 650.2),
                new Ponto(235.0, 572.4),
                new Ponto(239.7, 564.8),
                new Ponto(274.9, 505.0)
        }));

        _listaDeTerritorios.add(new Territorio("Africa do Sul", "Africa", new Ponto[]{new Ponto(528.4, 545.4),
                new Ponto(558.1, 600.2),
                new Ponto(597.1, 600.2),
                new Ponto(606.1, 584.7),
                new Ponto(613.1, 584.7),
                new Ponto(626.6, 559.1),
                new Ponto(617.6, 545.4)}));

        _listaDeTerritorios.add(new Territorio("Angola", "Africa", new Ponto[]{
                new Ponto(514.7, 493.2),
                new Ponto(519.9, 482.5),
                new Ponto(588.6, 482.5),
                new Ponto(599.4, 502.3),
                new Ponto(573.4, 545.4),
                new Ponto(528.4, 545.4),
                new Ponto(535.6, 531.4)}));

        _listaDeTerritorios.add(new Territorio("Argelia", "Africa", new Ponto[]{
                new Ponto(436.7, 337.5),
                new Ponto(473.2, 337.5),
                new Ponto(479.6, 350.3),
                new Ponto(492.5, 350.3),
                new Ponto(499.1, 364.1),
                new Ponto(539.8, 364.1),
                new Ponto(513.0, 410.1),
                new Ponto(425.3, 410.1),
                new Ponto(409.3, 384.5)}));

        _listaDeTerritorios.add(new Territorio("Egito", "Africa", new Ponto[]{
                new Ponto(588.6, 360.3),
                new Ponto(542.0, 360.3),
                new Ponto(539.8, 364.1),
                new Ponto(513.0, 410.1),
                new Ponto(547.6, 410.1),
                new Ponto(562.4, 436.6),
                new Ponto(623.6, 436.6),
                new Ponto(594.0, 385.3),
                new Ponto(597.8, 378.5)}));

        _listaDeTerritorios.add(new Territorio("Nigeria", "Africa", new Ponto[]{
                new Ponto(425.3, 410.1),
                new Ponto(448.1, 452.2),
                new Ponto(503.3, 452.2),
                new Ponto(519.9, 482.5),
                new Ponto(588.6, 482.5),
                new Ponto(562.4, 436.6),
                new Ponto(547.6, 410.1)}));

        _listaDeTerritorios.add(new Territorio("Somalia", "Africa", new Ponto[]{
                new Ponto(643.1, 502.0),
                new Ponto(653.0, 502.0),
                new Ponto(672.7, 464.6),
                new Ponto(639.3, 464.6),
                new Ponto(623.6, 436.6),
                new Ponto(562.4, 436.6),
                new Ponto(599.4, 502.3),
                new Ponto(573.4, 545.4),
                new Ponto(617.6, 545.4)}));

        _listaDeTerritorios.add(new Territorio("Espanha", "Europa", new Ponto[]{
                new Ponto(442.3, 266.0),
                new Ponto(461.0, 266.0),
                new Ponto(476.9, 292.5),
                new Ponto(475.0, 296.6),
                new Ponto(480.0, 296.6),
                new Ponto(470.7, 312.3),
                new Ponto(452.3, 312.3),
                new Ponto(456.4, 304.0),
                new Ponto(443.2, 304.0),
                new Ponto(440.2, 309.2),
                new Ponto(415.9, 309.2)}));

        _listaDeTerritorios.add(new Territorio("Franca", "Europa", new Ponto[]{
                new Ponto(526.8, 186.6),
                new Ponto(538.7, 210.4),
                new Ponto(508.1, 263.1),
                new Ponto(502.1, 263.1),
                new Ponto(494.4, 279.1),
                new Ponto(483.7, 279.1),
                new Ponto(476.9, 292.5),
                new Ponto(461.0, 266.0),
                new Ponto(466.7, 255.2),
                new Ponto(459.9, 244.7),
                new Ponto(452.3, 244.7),
                new Ponto(448.2, 235.7),
                new Ponto(488.6, 235.7),
                new Ponto(499.3, 219.1),
                new Ponto(516.2, 219.1),
                new Ponto(521.8, 207.7),
                new Ponto(517.8, 200.4)}));

        _listaDeTerritorios.add(new Territorio("Italia", "Europa", new Ponto[]{
                new Ponto(552.4, 210.4),
                new Ponto(538.7, 210.4),
                new Ponto(508.1, 263.1),
                new Ponto(516.6, 263.1),
                new Ponto(526.7, 281.8),
                new Ponto(533.7, 281.8),
                new Ponto(542.5, 298.2),
                new Ponto(542.5, 300.2),
                new Ponto(537.1, 308.2),
                new Ponto(549.2, 308.2),
                new Ponto(554.8, 297.3),
                new Ponto(556.1, 297.3),
                new Ponto(557.4, 299.5),
                new Ponto(563.9, 299.5),
                new Ponto(563.9, 297.7),
                new Ponto(552.8, 277.8),
                new Ponto(547.0, 277.8),
                new Ponto(539.1, 261.1),
                new Ponto(543.5, 253.4),
                new Ponto(552.8, 253.4),
                new Ponto(555.8, 259.4),
                new Ponto(567.5, 237.9)}));

        _listaDeTerritorios.add(new Territorio("Polonia", "Europa", new Ponto[]{
                new Ponto(575.6, 176.1),
                new Ponto(583.6, 176.0),
                new Ponto(600.8, 205.9),
                new Ponto(581.8, 237.9),
                new Ponto(567.5, 237.9),
                new Ponto(552.4, 210.4),
                new Ponto(564.1, 189.0),
                new Ponto(569.8, 189.0)}));

        _listaDeTerritorios.add(new Territorio("Reino Unido", "Europa", new Ponto[]{
                new Ponto(462.0, 146.2),
                new Ponto(481.6, 146.2),
                new Ponto(473.7, 156.6),
                new Ponto(480.8, 156.6),
                new Ponto(470.3, 174.2),
                new Ponto(482.7, 198.4),
                new Ponto(490.8, 198.4),
                new Ponto(484.0, 210.4),
                new Ponto(445.5, 210.4),
                new Ponto(451.3, 196.1),
                new Ponto(458.1, 196.1),
                new Ponto(463.2, 188.5),
                new Ponto(454.0, 174.2),
                new Ponto(459.4, 165.3),
                new Ponto(459.4, 163.2),
                new Ponto(450.8, 163.2)}));

        _listaDeTerritorios.add(new Territorio("Romenia", "Europa", new Ponto[]{
                new Ponto(567.5, 237.9),
                new Ponto(555.8, 259.4),
                new Ponto(555.8, 262.4),
                new Ponto(557.6, 264.2),
                new Ponto(566.1, 264.2),
                new Ponto(579.8, 288.6),
                new Ponto(574.4, 297.5),
                new Ponto(581.4, 308.2),
                new Ponto(593.2, 308.2),
                new Ponto(601.4, 297.4),
                new Ponto(595.1, 289.5),
                new Ponto(598.3, 283.7),
                new Ponto(607.9, 283.7),
                new Ponto(581.8, 237.9)}));

        _listaDeTerritorios.add(new Territorio("Suecia", "Europa", new Ponto[]{
                new Ponto(551.0, 96.6),
                new Ponto(582.5, 96.6),
                new Ponto(610.2, 144.5),
                new Ponto(602.0, 158.0),
                new Ponto(571.5, 158.0),
                new Ponto(578.7, 145.7),
                new Ponto(569.6, 145.7),
                new Ponto(579.6, 127.8),
                new Ponto(562.9, 127.8),
                new Ponto(552.1, 145.8),
                new Ponto(558.1, 156.3),
                new Ponto(552.0, 167.3),
                new Ponto(555.4, 172.1),
                new Ponto(548.3, 183.7),
                new Ponto(535.8, 183.7),
                new Ponto(530.4, 171.1),
                new Ponto(521.7, 171.1),
                new Ponto(514.0, 183.7),
                new Ponto(501.4, 183.7),
                new Ponto(492.1, 166.6),
                new Ponto(507.0, 140.6),
                new Ponto(522.0, 140.6),
                new Ponto(537.1, 111.5),
                new Ponto(543.0, 111.5)}));

        _listaDeTerritorios.add(new Territorio("Ucrania", "Europa", new Ponto[]{
                new Ponto(611.6, 254.4),
                new Ponto(620.1, 239.5),
                new Ponto(600.8, 205.9),
                new Ponto(581.8, 237.9),
                new Ponto(607.9, 283.7),
                new Ponto(619.0, 264.9)}));

        _listaDeTerritorios.add(new Territorio("Arabia Saudita", "Asia", new Ponto[]{
                new Ponto(646.2, 423.6),
                new Ponto(639.1, 434.7),
                new Ponto(649.1, 450.3),
                new Ponto(699.0, 450.3),
                new Ponto(726.6, 403.0),
                new Ponto(716.8, 387.3),
                new Ponto(680.5, 387.3),
                new Ponto(654.0, 342.4),
                new Ponto(627.4, 388.2)}));

        _listaDeTerritorios.add(new Territorio("Bangladesh", "Asia", new Ponto[]{
                new Ponto(885.3, 350.0),
                new Ponto(847.8, 350.0),
                new Ponto(828.7, 383.5),
                new Ponto(842.1, 410.9),
                new Ponto(848.1, 410.9),
                new Ponto(859.7, 432.6),
                new Ponto(854.7, 441.3),
                new Ponto(870.5, 470.8),
                new Ponto(879.3, 458.8),
                new Ponto(879.3, 456.0),
                new Ponto(872.2, 444.6),
                new Ponto(879.9, 432.5),
                new Ponto(859.2, 396.3)}));

        _listaDeTerritorios.add(new Territorio("Cazaquistao", "Asia", new Ponto[]{
                new Ponto(907.9, 201.9),
                new Ponto(920.4, 222.6),
                new Ponto(906.1, 246.4),
                new Ponto(784.1, 246.4),
                new Ponto(772.1, 224.1),
                new Ponto(724.7, 224.1),
                new Ponto(739.0, 201.9)}));

        _listaDeTerritorios.add(new Territorio("Mongolia", "Asia", new Ponto[]{
                new Ponto(906.1, 246.4),
                new Ponto(804.9, 246.4),
                new Ponto(822.0, 278.5),
                new Ponto(873.9, 278.5),
                new Ponto(894.6, 278.5),
                new Ponto(906.1, 300.4),
                new Ponto(916.3, 287.4),
                new Ponto(902.8, 264.5),
                new Ponto(910.3, 254.7)}));

        _listaDeTerritorios.add(new Territorio("China", "Asia", new Ponto[]{
                new Ponto(873.9, 278.5),
                new Ponto(822.0, 278.5),
                new Ponto(804.9, 246.5),
                new Ponto(784.1, 246.5),
                new Ponto(773.5, 264.0),
                new Ponto(764.7, 278.9),
                new Ponto(754.8, 297.2),
                new Ponto(785.5, 348.8),
                new Ponto(785.5, 350.5),
                new Ponto(811.9, 350.5),
                new Ponto(838.9, 302.5),
                new Ponto(887.9, 302.5)}));

        _listaDeTerritorios.add(new Territorio("Coreia do Norte", "Asia", new Ponto[]{
                new Ponto(839.0, 302.2),
                new Ponto(825.3, 326.1),
                new Ponto(914.9, 326.1),
                new Ponto(908.2, 314.2),
                new Ponto(894.1, 314.2),
                new Ponto(888.0, 302.2)}));

        _listaDeTerritorios.add(new Territorio("Coreia do Sul", "Asia", new Ponto[]{
                new Ponto(914.9, 326.1),
                new Ponto(922.1, 337.2),
                new Ponto(915.5, 350.0),
                new Ponto(811.9, 350.0),
                new Ponto(825.3, 326.1)}));

        _listaDeTerritorios.add(new Territorio("Estonia", "Asia", new Ponto[]{
                new Ponto(735.3, 123.8),
                new Ponto(659.5, 123.8),
                new Ponto(650.1, 144.1),
                new Ponto(630.0, 144.1),
                new Ponto(614.8, 117.0),
                new Ponto(628.2, 117.0),
                new Ponto(632.0, 124.2),
                new Ponto(644.4, 124.2),
                new Ponto(632.6, 101.5),
                new Ponto(607.8, 101.5),
                new Ponto(604.9, 96.6),
                new Ponto(582.5, 96.6),
                new Ponto(626.8, 173.1),
                new Ponto(706.8, 173.1)}));

        _listaDeTerritorios.add(new Territorio("India", "Asia", new Ponto[]{
                new Ponto(798.0, 450.3),
                new Ponto(763.8, 387.8),
                new Ponto(785.5, 351.2),
                new Ponto(785.5, 350.0),
                new Ponto(847.8, 350.0),
                new Ponto(808.0, 420.0),
                new Ponto(812.3, 427.5)}));

        _listaDeTerritorios.add(new Territorio("Ira", "Asia", new Ponto[]{
                new Ponto(716.8, 310.2),
                new Ponto(701.1, 310.2),
                new Ponto(691.0, 329.0),
                new Ponto(703.0, 351.2),
                new Ponto(716.5, 375.9),
                new Ponto(733.6, 375.9),
                new Ponto(739.9, 387.8),
                new Ponto(751.3, 370.1)}));

        _listaDeTerritorios.add(new Territorio("Iraque", "Asia", new Ponto[]{
                new Ponto(703.0, 351.2),
                new Ponto(694.0, 362.7),
                new Ponto(694.0, 364.8),
                new Ponto(706.5, 387.3),
                new Ponto(680.5, 387.3),
                new Ponto(654.0, 342.4),
                new Ponto(671.9, 310.2),
                new Ponto(701.1, 310.2),
                new Ponto(691.0, 329.0)}));

        _listaDeTerritorios.add(new Territorio("Japao", "Asia", new Ponto[]{
                new Ponto(937.3, 222.4),
                new Ponto(956.3, 254.3),
                new Ponto(953.7, 257.5),
                new Ponto(965.5, 276.8),
                new Ponto(955.4, 293.3),
                new Ponto(947.6, 293.3),
                new Ponto(940.0, 307.4),
                new Ponto(921.9, 307.4),
                new Ponto(930.2, 293.0),
                new Ponto(927.9, 288.6),
                new Ponto(933.8, 279.7),
                new Ponto(939.4, 279.7),
                new Ponto(943.7, 269.9),
                new Ponto(927.1, 239.4)}));

        _listaDeTerritorios.add(new Territorio("Jordania", "Asia", new Ponto[]{
                new Ponto(621.9, 378.5),
                new Ponto(612.8, 378.5),
                new Ponto(602.1, 357.1),
                new Ponto(615.7, 337.6),
                new Ponto(633.6, 337.6),
                new Ponto(649.1, 310.2),
                new Ponto(671.9, 310.2),
                new Ponto(654.0, 342.4),
                new Ponto(627.4, 388.2)}));

        _listaDeTerritorios.add(new Territorio("Letonia", "Asia", new Ponto[]{
                new Ponto(610.2, 144.5),
                new Ponto(591.0, 176.0),
                new Ponto(583.6, 176.0),
                new Ponto(611.2, 224.1),
                new Ponto(724.7, 224.1),
                new Ponto(739.0, 201.9),
                new Ponto(724.7, 173.1),
                new Ponto(626.8, 173.1)}));


        _listaDeTerritorios.add(new Territorio("Paquistao", "Asia", new Ponto[]{
                new Ponto(763.8, 387.8),
                new Ponto(739.9, 387.8),
                new Ponto(751.3, 370.1),
                new Ponto(708.9, 296.4),
                new Ponto(718.3, 278.9),
                new Ponto(764.7, 278.9),
                new Ponto(754.8, 297.2),
                new Ponto(785.5, 348.8),
                new Ponto(785.5, 351.2)}));

        _listaDeTerritorios.add(new Territorio("Russia", "Asia", new Ponto[]{
                new Ponto(744.4, 125.0),
                new Ponto(742.5, 129.9),
                new Ponto(748.2, 129.9),
                new Ponto(744.1, 136.7),
                new Ponto(728.4, 136.7),
                new Ponto(706.7, 173.1),
                new Ponto(724.7, 173.1),
                new Ponto(739.0, 201.9),
                new Ponto(825.0, 201.9),
                new Ponto(869.9, 125.0)}));

        _listaDeTerritorios.add(new Territorio("Siberia", "Asia", new Ponto[]{
                new Ponto(949.4, 204.5),
                new Ponto(956.8, 191.1),
                new Ponto(940.9, 162.5),
                new Ponto(947.1, 155.2),
                new Ponto(940.9, 144.4),
                new Ponto(954.0, 144.4),
                new Ponto(937.8, 117.0),
                new Ponto(875.3, 117.0),
                new Ponto(870.6, 124.9),
                new Ponto(825.0, 201.9),
                new Ponto(888.4, 201.9),
                new Ponto(877.3, 187.8),
                new Ponto(885.4, 176.1),
                new Ponto(907.5, 176.1),
                new Ponto(916.3, 159.9),
                new Ponto(930.5, 183.1),
                new Ponto(938.0, 183.1)}));

        _listaDeTerritorios.add(new Territorio("Siria", "Asia", new Ponto[]{
                new Ponto(660.8, 272.8),
                new Ponto(664.6, 278.9),
                new Ponto(718.3, 278.9),
                new Ponto(708.9, 296.4),
                new Ponto(716.8, 310.2),
                new Ponto(637.0, 310.2),
                new Ponto(637.0, 306.2),
                new Ponto(628.7, 306.6),
                new Ponto(619.6, 291.5),
                new Ponto(628.7, 275.8),
                new Ponto(646.1, 275.8),
                new Ponto(647.3, 272.8)}));

        _listaDeTerritorios.add(new Territorio("Tailandia", "Asia", new Ponto[]{
                new Ponto(915.5, 350.0),
                new Ponto(885.3, 350.0),
                new Ponto(859.2, 396.3),
                new Ponto(879.9, 432.5),
                new Ponto(887.3, 446.2),
                new Ponto(895.1, 446.2),
                new Ponto(901.8, 432.5),
                new Ponto(897.5, 426.0),
                new Ponto(901.8, 420.7),
                new Ponto(887.0, 398.1),
                new Ponto(892.9, 389.8),
                new Ponto(895.9, 395.1),
                new Ponto(908.7, 395.1),
                new Ponto(911.4, 387.6),
                new Ponto(919.1, 387.6),
                new Ponto(927.4, 370.3)}));

        _listaDeTerritorios.add(new Territorio("Turquia", "Asia", new Ponto[]{
                new Ponto(683.6, 278.9),
                new Ponto(691.0, 268.9),
                new Ponto(678.5, 247.4),
                new Ponto(668.1, 247.4),
                new Ponto(664.5, 240.9),
                new Ponto(651.9, 240.9),
                new Ponto(646.3, 251.2),
                new Ponto(639.4, 239.5),
                new Ponto(620.1, 239.5),
                new Ponto(611.2, 224.1),
                new Ponto(772.1, 224.1),
                new Ponto(784.1, 246.4),
                new Ponto(764.7, 278.9)}));

        _listaDeTerritorios.add(new Territorio("Australia", "Oceania", new Ponto[]{
                new Ponto(875.1, 539.4),
                new Ponto(885.9, 539.4),
                new Ponto(919.7, 598.0),
                new Ponto(911.5, 611.8),
                new Ponto(917.6, 623.4),
                new Ponto(901.8, 650.0),
                new Ponto(891.1, 650.0),
                new Ponto(877.6, 673.5),
                new Ponto(846.1, 673.5),
                new Ponto(837.9, 655.8),
                new Ponto(823.4, 655.8),
                new Ponto(816.7, 641.9)}));

        _listaDeTerritorios.add(new Territorio("Indonesia", "Oceania", new Ponto[]{
                new Ponto(850.9, 480.7),
                new Ponto(861.3, 500.4),
                new Ponto(880.7, 500.4),
                new Ponto(887.7, 486.3),
                new Ponto(902.5, 486.3),
                new Ponto(907.6, 498.3),
                new Ponto(925.2, 498.3),
                new Ponto(935.6, 517.9),
                new Ponto(943.8, 517.9),
                new Ponto(954.3, 536.6),
                new Ponto(928.3, 536.6),
                new Ponto(920.3, 522.0),
                new Ponto(907.2, 522.0),
                new Ponto(904.7, 528.9),
                new Ponto(890.3, 528.9),
                new Ponto(885.3, 517.5),
                new Ponto(849.0, 517.5),
                new Ponto(835.2, 490.0),
                new Ponto(841.0, 480.7)}));

        _listaDeTerritorios.add(new Territorio("Nova Zelandia", "Oceania", new Ponto[]{
                new Ponto(928.8, 601.5),
                new Ponto(936.9, 601.5),
                new Ponto(943.0, 613.7),
                new Ponto(939.8, 616.5),
                new Ponto(944.2, 616.5),
                new Ponto(950.3, 628.6),
                new Ponto(931.9, 661.5),
                new Ponto(926.6, 661.5),
                new Ponto(917.6, 678.5),
                new Ponto(900.1, 678.5),
                new Ponto(917.6, 644.5),
                new Ponto(922.9, 644.5),
                new Ponto(932.3, 627.8),
                new Ponto(928.0, 619.1),
                new Ponto(934.1, 610.0)}));

        _listaDeTerritorios.add(new Territorio("Perth", "Oceania", new Ponto[]{
                new Ponto(856.7, 535.5),
                new Ponto(839.2, 535.5),
                new Ponto(822.3, 565.8),
                new Ponto(799.1, 565.8),
                new Ponto(789.6, 584.3),
                new Ponto(781.3, 584.3),
                new Ponto(775.3, 595.7),
                new Ponto(775.3, 598.3),
                new Ponto(782.3, 608.6),
                new Ponto(782.3, 610.7),
                new Ponto(770.9, 630.1),
                new Ponto(763.5, 630.1),
                new Ponto(756.7, 639.9),
                new Ponto(766.4, 655.8),
                new Ponto(780.3, 655.8),
                new Ponto(787.3, 641.9),
                new Ponto(816.7, 641.9),
                new Ponto(861.6, 562.1),
                new Ponto(855.6, 550.2),
                new Ponto(862.4, 542.3)}));

        // Agora, vou preencher o hashMap
        for (Territorio t : _listaDeTerritorios) {
            _hashTerritorios.put(t.getNome(), t);
        }

        geraTerritoriosFronteira();


    }


    private void geraTerritoriosFronteira() {
        Territorio ter;
        ter = _hashTerritorios.get("Alasca");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Vancouver"),
                _hashTerritorios.get("Calgary"),
                _hashTerritorios.get("Siberia")
        });

        ter = _hashTerritorios.get("Calgary");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Groelandia"),
                _hashTerritorios.get("Alasca"),
                _hashTerritorios.get("Vancouver")
        });

        ter = _hashTerritorios.get("Groelandia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Calgary"),
                _hashTerritorios.get("Quebec"),
                _hashTerritorios.get("Reino Unido")
        });

        ter = _hashTerritorios.get("Vancouver");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Quebec"),
                _hashTerritorios.get("Alasca"),
                _hashTerritorios.get("Calgary"),
                _hashTerritorios.get("California"),
                _hashTerritorios.get("Texas")
        });

        ter = _hashTerritorios.get("Quebec");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Vancouver"),
                _hashTerritorios.get("NovaYork"),
                _hashTerritorios.get("Texas"),
                _hashTerritorios.get("Groelandia")
        });

        ter = _hashTerritorios.get("California");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Texas"),
                _hashTerritorios.get("Vancouver"),
                _hashTerritorios.get("Mexico")
        });

        ter = _hashTerritorios.get("Texas");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("California"),
                _hashTerritorios.get("Mexico"),
                _hashTerritorios.get("NovaYork"),
                _hashTerritorios.get("Quebec"),
                _hashTerritorios.get("Vancouver")
        });

        ter = _hashTerritorios.get("NovaYork");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Quebec"),
                _hashTerritorios.get("Texas")
        });

        ter = _hashTerritorios.get("Mexico");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Texas"),
                _hashTerritorios.get("California"),
                _hashTerritorios.get("Venezuela")
        });

        ter = _hashTerritorios.get("Venezuela");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Mexico"),
                _hashTerritorios.get("Brasil"),
                _hashTerritorios.get("Peru")
        });

        ter = _hashTerritorios.get("Peru");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Brasil"),
                _hashTerritorios.get("Argentina"),
                _hashTerritorios.get("Venezuela")
        });

        ter = _hashTerritorios.get("Brasil");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Peru"),
                _hashTerritorios.get("Venezuela"),
                _hashTerritorios.get("Argentina"),
                _hashTerritorios.get("Nigeria")
        });

        ter = _hashTerritorios.get("Argentina");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Brasil"),
                _hashTerritorios.get("Peru")
        });

        ter = _hashTerritorios.get("Africa do Sul");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Angola"),
                _hashTerritorios.get("Somalia")
        });

        ter = _hashTerritorios.get("Angola");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Africa do Sul"),
                _hashTerritorios.get("Somalia"),
                _hashTerritorios.get("Nigeria")
        });

        ter = _hashTerritorios.get("Argelia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Egito"),
                _hashTerritorios.get("Nigeria"),
                _hashTerritorios.get("Italia"),
                _hashTerritorios.get("Espanha")
        });

        ter = _hashTerritorios.get("Egito");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Nigeria"),
                _hashTerritorios.get("Argelia"),
                _hashTerritorios.get("Somalia"),
                _hashTerritorios.get("Jordania"),
                _hashTerritorios.get("Romenia")
        });

        ter = _hashTerritorios.get("Nigeria");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Angola"),
                _hashTerritorios.get("Somalia"),
                _hashTerritorios.get("Egito"),
                _hashTerritorios.get("Argelia"),
                _hashTerritorios.get("Brasil")
        });

        ter = _hashTerritorios.get("Somalia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Africa do Sul"),
                _hashTerritorios.get("Angola"),
                _hashTerritorios.get("Egito"),
                _hashTerritorios.get("Nigeria"),
                _hashTerritorios.get("Arabia Saudita")
        });

        ter = _hashTerritorios.get("Espanha");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Franca"),
                _hashTerritorios.get("Argelia")
        });

        ter = _hashTerritorios.get("Franca");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Italia"),
                _hashTerritorios.get("Espanha"),
                _hashTerritorios.get("Reino Unido"),
                _hashTerritorios.get("Suecia")
        });

        ter = _hashTerritorios.get("Italia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Romenia"),
                _hashTerritorios.get("Polonia"),
                _hashTerritorios.get("Franca"),
                _hashTerritorios.get("Argelia"),
                _hashTerritorios.get("Suecia")
        });

        ter = _hashTerritorios.get("Polonia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Romenia"),
                _hashTerritorios.get("Italia"),
                _hashTerritorios.get("Ucrania"),
                _hashTerritorios.get("Letonia")
        });

        ter = _hashTerritorios.get("Reino Unido");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Franca"),
                _hashTerritorios.get("Groelandia")
        });

        ter = _hashTerritorios.get("Romenia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Polonia"),
                _hashTerritorios.get("Ucrania"),
                _hashTerritorios.get("Italia"),
                _hashTerritorios.get("Egito")
        });

        ter = _hashTerritorios.get("Suecia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Letonia"),
                _hashTerritorios.get("Estonia"),
                _hashTerritorios.get("Franca"),
                _hashTerritorios.get("Italia")
        });

        ter = _hashTerritorios.get("Ucrania");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Polonia"),
                _hashTerritorios.get("Romenia"),
                _hashTerritorios.get("Letonia"),
                _hashTerritorios.get("Turquia")
        });

        ter = _hashTerritorios.get("Estonia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Russia"),
                _hashTerritorios.get("Letonia"),
                _hashTerritorios.get("Suecia")
        });
        ter = _hashTerritorios.get("Siria");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Turquia"),
                _hashTerritorios.get("Paquistao"),
                _hashTerritorios.get("Iraque"),
                _hashTerritorios.get("Ira"),
                _hashTerritorios.get("Jordania")
        });

        ter = _hashTerritorios.get("Jordania");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Arabia Saudita"),
                _hashTerritorios.get("Siria"),
                _hashTerritorios.get("Iraque"),
                _hashTerritorios.get("Egito")
        });

        ter = _hashTerritorios.get("Arabia Saudita");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Iraque"),
                _hashTerritorios.get("Jordania"),
                _hashTerritorios.get("Somalia")

        });
        ter = _hashTerritorios.get("Cazaquistao");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Russia"),
                _hashTerritorios.get("Turquia"),
                _hashTerritorios.get("Japao"),
                _hashTerritorios.get("Mongolia"),
                _hashTerritorios.get("Siberia"),
                _hashTerritorios.get("China"),
                _hashTerritorios.get("Letonia")

        });

        ter = _hashTerritorios.get("Paquistao");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("India"),
                _hashTerritorios.get("China"),
                _hashTerritorios.get("Turquia"),
                _hashTerritorios.get("Siria"),
                _hashTerritorios.get("Ira")
        });

        ter = _hashTerritorios.get("Ira");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Iraque"),
                _hashTerritorios.get("Paquistao"),
                _hashTerritorios.get("Siria"),
        });

        ter = _hashTerritorios.get("Iraque");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Ira"),
                _hashTerritorios.get("Siria"),
                _hashTerritorios.get("Arabia Saudita"),
                _hashTerritorios.get("Jordania")

        });

        ter = _hashTerritorios.get("Letonia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Cazaquistao"),
                _hashTerritorios.get("Russia"),
                _hashTerritorios.get("Estonia"),
                _hashTerritorios.get("Polonia"),
                _hashTerritorios.get("Suecia"),
                _hashTerritorios.get("Ucrania"),
                _hashTerritorios.get("Turquia")
        });


        ter = _hashTerritorios.get("Russia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Siberia"),
                _hashTerritorios.get("Cazaquistao"),
                _hashTerritorios.get("Letonia"),
                _hashTerritorios.get("Estonia")
        });
        ter = _hashTerritorios.get("Turquia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Cazaquistao"),
                _hashTerritorios.get("China"),
                _hashTerritorios.get("Paquistao"),
                _hashTerritorios.get("Siria"),
                _hashTerritorios.get("Ucrania"),
                _hashTerritorios.get("Letonia")

        });

        ter = _hashTerritorios.get("Siberia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Cazaquistao"),
                _hashTerritorios.get("Russia"),
                _hashTerritorios.get("Alasca")
        });

        ter = _hashTerritorios.get("Mongolia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("China"),
                _hashTerritorios.get("Japao"),
                _hashTerritorios.get("Cazaquistao")
        });

        ter = _hashTerritorios.get("China");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Mongolia"),
                _hashTerritorios.get("India"),
                _hashTerritorios.get("Paquistao"),
                _hashTerritorios.get("Coreia do Norte"),
                _hashTerritorios.get("Coreia do Sul"),
                _hashTerritorios.get("Turquia"),
                _hashTerritorios.get("Cazaquistao")
        });

        ter = _hashTerritorios.get("Japao");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Coreia do Norte"),
                _hashTerritorios.get("Mongolia"),
                _hashTerritorios.get("Cazaquistao")
        });

        ter = _hashTerritorios.get("Coreia do Norte");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("China"),
                _hashTerritorios.get("Japao"),
                _hashTerritorios.get("Coreia do Sul")
        });

        ter = _hashTerritorios.get("Coreia do Sul");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Tailandia"),
                _hashTerritorios.get("Bangladesh"),
                _hashTerritorios.get("India"),
                _hashTerritorios.get("China"),
                _hashTerritorios.get("Coreia do Norte")
        });


        ter = _hashTerritorios.get("India");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Indonesia"),
                _hashTerritorios.get("Bangladesh"),
                _hashTerritorios.get("Coreia do Sul"),
                _hashTerritorios.get("Paquistao"),
                _hashTerritorios.get("China")
        });


        ter = _hashTerritorios.get("Tailandia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Bangladesh"),
                _hashTerritorios.get("Coreia do Sul")
        });


        ter = _hashTerritorios.get("Bangladesh");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Indonesia"),
                _hashTerritorios.get("Tailandia"),
                _hashTerritorios.get("India"),
                _hashTerritorios.get("Coreia do Sul")
        });

        ter = _hashTerritorios.get("Australia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Indonesia"),
                _hashTerritorios.get("Perth"),
                _hashTerritorios.get("Nova Zelandia")
        });

        ter = _hashTerritorios.get("Indonesia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Australia"),
                _hashTerritorios.get("Nova Zelandia"),
                _hashTerritorios.get("India"),
                _hashTerritorios.get("Bangladesh")
        });

        ter = _hashTerritorios.get("Nova Zelandia");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Indonesia"),
                _hashTerritorios.get("Australia")

        });
        ter = _hashTerritorios.get("Perth");
        ter.setTerritoriosFronteira(new Territorio[]{
                _hashTerritorios.get("Australia")
        });
    }


    private Mapa() {
        _listaDeTerritorios = new ArrayList<Territorio>();
        inicializarTerritorios();
        construirMarcadores(getImgDimension());
//        _gameData = GameData.getInstance();
    }

    public void addListener() {
        _gameData = GameData.getInstance();
        this.addMouseListener(new MouseListener() {

            // Evento de click para detectar se o ponto clicado est� dentro da area do teritorio.
            @Override
            public void mouseClicked(MouseEvent e) {
                Dimension d = getImgDimension();

                double ratioX = d.width / BASE_WIDTH;
                double ratioY = d.height / BASE_HEIGHT;

                double dx = (e.getX() - (getWidth() * 1.0 - d.width) / 2.0) / ratioX;
                double dy = (e.getY() - (getHeight() * 1.0 - d.height) / 2.0) / ratioY;

                int x = (int) Math.round(dx);
                int y = (int) Math.round(dy);

                for (Territorio t : _listaDeTerritorios) {
                    if (t.getPoligono().contains(x, y)) {
                        if (_gameData.getGameState() == GameState.Distribute) {
                            if (t.getDonoIndex() == _gameData.getCurrentPlayer()) {
                                if (_gameData.getNumArmysToDistribute() > 0) {
                                    DistributeDialog distributeDialog = new DistributeDialog(MainFrame.getInstance(), t);
                                    distributeDialog.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Nao sobrou mais nada, indo para o modo de ataque!");
                                    MainFrame.getInstance().setAttackRadio(true);
                                    _gameData.setGameState(GameState.Attack);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Esse territorio nao pertence ao jogador da vez!");
                            }
                        } else if (_gameData.getGameState() == GameState.Attack) {
                            if (_gameData.getNumArmysToDistribute() != 0) {
                                JOptionPane.showMessageDialog(null, "Ainda ha exercitos para distribuicao!");
                                MainFrame.getInstance().setDistributeRadio(true);
                                _gameData.setGameState(GameState.Distribute);
                            } else if (t.getDonoIndex() == _gameData.getCurrentPlayer()) {
                                AttackDialog attackDialog = new AttackDialog(MainFrame.getInstance(), t, 3);
                                attackDialog.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Esse territorio nao pertence ao jogador da vez!");
                            }
                        } else if (_gameData.getGameState() == GameState.Redistribute) {
                            RedistributeDialog attackWindow = new RedistributeDialog(MainFrame.getInstance(), t);
                            attackWindow.setVisible(true);
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }


    private Image loadImage() {
        Image i = null;
        try {
            i = ImageIO.read(new File("images/war_tabuleiro_com_nomes.png"));
        } catch (IOException e) {
            System.out.println("@Class 'Mapa': " + e.getMessage());
            System.exit(1);
        }
        int width = i.getWidth(null);
        int height = i.getHeight(null);
        imageRatio = (double) width / (double) height;


        return i;
    }

    private Dimension getImgDimension() {
        Dimension d = getSize();

        double w = d.width;
        double h = d.height;


        if (w / h <= imageRatio) {
            h = w / imageRatio;
            //w = h/imageRatio;
        } else {
            w = h * imageRatio;
        }

        return new Dimension((int) Math.round(w), (int) Math.round(h));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (_img == null) {
            _img = loadImage();
        }

        Dimension d = getImgDimension();
        Dimension ptDim = getParent().getSize();
        g.drawImage(_img, (ptDim.width - d.width) / 2, 5, d.width, d.height, null);

        //construirMarcadores(d);
        double ratioX = d.width / BASE_WIDTH;
        double ratioY = d.height / BASE_HEIGHT;

        //desenhar marcadores
        Graphics2D g2d = (Graphics2D) g;
        Set<String> keys = _hashMarcadores.keySet();
        for (String nomePais : keys) {
            Marker marcador = _hashMarcadores.get(nomePais);
            double dx = marcador.getPonto().getX() * ratioX + (this.getSize().getWidth() * 1.0 - d.width) / 2;
            double dy = marcador.getPonto().getY() * ratioY + (this.getSize().getHeight() * 1.0 - d.height) / 2;
            new Ellipse2D.Double(dx, dy, 15, 15);
            g2d.setColor(marcador.getColor());
            g2d.fill(new Ellipse2D.Double(dx, dy, 15, 15));
            g2d.setColor(Color.black);
            g2d.draw(new Ellipse2D.Double(dx, dy, 15, 15));
        }
    }

    private void construirMarcadores(Dimension d) {
        _hashMarcadores.put("Brasil", new Marker(new Ponto(278.0, 446.0), Color.green));
        _hashMarcadores.put("Peru", new Marker(new Ponto(232.0, 487.0), Color.green));
        _hashMarcadores.put("Venezuela", new Marker(new Ponto(193, 456), Color.green));
        _hashMarcadores.put("Mexico", new Marker(new Ponto(133, 355), Color.green));
        _hashMarcadores.put("California", new Marker(new Ponto(126, 250), Color.green));
        _hashMarcadores.put("Vancouver", new Marker(new Ponto(154, 198), Color.green));
        _hashMarcadores.put("Calgary", new Marker(new Ponto(188, 151), Color.green));
        _hashMarcadores.put("Alasca", new Marker(new Ponto(99, 146), Color.green));
        _hashMarcadores.put("Groelandia", new Marker(new Ponto(326, 115), Color.green));
        _hashMarcadores.put("Quebec", new Marker(new Ponto(279, 201), Color.green));
        _hashMarcadores.put("Reino Unido", new Marker(new Ponto(467, 185), Color.green));
        _hashMarcadores.put("Suecia", new Marker(new Ponto(530, 146), Color.green));
        _hashMarcadores.put("Franca", new Marker(new Ponto(485, 250), Color.green));
        _hashMarcadores.put("Espanha", new Marker(new Ponto(438, 291), Color.green));
        _hashMarcadores.put("Italia", new Marker(new Ponto(532, 255), Color.green));
        _hashMarcadores.put("Ucrania", new Marker(new Ponto(601, 239), Color.green));
        _hashMarcadores.put("Estonia", new Marker(new Ponto(665, 152), Color.green));
        _hashMarcadores.put("Russia", new Marker(new Ponto(787, 151), Color.green));
        _hashMarcadores.put("Cazaquistao", new Marker(new Ponto(864, 227), Color.green));
        _hashMarcadores.put("Turquia", new Marker(new Ponto(720, 248), Color.green));
        _hashMarcadores.put("Mongolia", new Marker(new Ponto(874, 260), Color.green));
        _hashMarcadores.put("Japao", new Marker(new Ponto(946, 281), Color.green));
        _hashMarcadores.put("China", new Marker(new Ponto(798, 291), Color.green));
        _hashMarcadores.put("Paquistao", new Marker(new Ponto(741, 307), Color.green));
        _hashMarcadores.put("Siria", new Marker(new Ponto(680, 296), Color.green));
        _hashMarcadores.put("Jordania", new Marker(new Ponto(633, 353), Color.green));
        _hashMarcadores.put("Arabia Saudita", new Marker(new Ponto(657, 388), Color.green));
        _hashMarcadores.put("Iraque", new Marker(new Ponto(675, 342), Color.green));
        _hashMarcadores.put("Ira", new Marker(new Ponto(716, 343), Color.green));
        _hashMarcadores.put("Paquistao", new Marker(new Ponto(759, 350), Color.green));
        _hashMarcadores.put("India", new Marker(new Ponto(802, 370), Color.green));
        _hashMarcadores.put("Indonesia", new Marker(new Ponto(898, 506), Color.green));
        _hashMarcadores.put("Australia", new Marker(new Ponto(864, 601), Color.green));
        _hashMarcadores.put("Perth", new Marker(new Ponto(797, 618), Color.green));
        _hashMarcadores.put("Nova Zelandia", new Marker(new Ponto(934, 642), Color.green));
        _hashMarcadores.put("Nigeria", new Marker(new Ponto(524, 452), Color.green));
        _hashMarcadores.put("Letonia", new Marker(new Ponto(706, 205), Color.green));
        _hashMarcadores.put("Egito", new Marker(new Ponto(580, 412), Color.green));
        _hashMarcadores.put("Argelia", new Marker(new Ponto(456, 362), Color.green));
        _hashMarcadores.put("Africa do Sul", new Marker(new Ponto(581, 575), Color.green));
        _hashMarcadores.put("Angola", new Marker(new Ponto(556, 516), Color.green));
        _hashMarcadores.put("NovaYork", new Marker(new Ponto(250, 252), Color.green));
        _hashMarcadores.put("Argentina", new Marker(new Ponto(274, 554), Color.green));
        _hashMarcadores.put("Texas", new Marker(new Ponto(165, 274), Color.green));
        _hashMarcadores.put("Polonia", new Marker(new Ponto(577, 208), Color.green));
        _hashMarcadores.put("Somalia", new Marker(new Ponto(619, 492), Color.green));
        _hashMarcadores.put("Siberia", new Marker(new Ponto(883, 157), Color.green));
        _hashMarcadores.put("Tailandia", new Marker(new Ponto(896, 378), Color.green));
        _hashMarcadores.put("Bangladesh", new Marker(new Ponto(846, 382), Color.green));
        _hashMarcadores.put("Coreia do Sul", new Marker(new Ponto(871, 340), Color.green));
        _hashMarcadores.put("Coreia do Norte", new Marker(new Ponto(866, 313), Color.green));
        _hashMarcadores.put("Romenia", new Marker(new Ponto(583, 277), Color.green));


    }

    public void editarDonoPais(String nomePais, Color cor, int playerIndex) {
        //System.out.println("Aqui no mapa: " + nomePais);
        if (!_hashMarcadores.isEmpty() && _hashMarcadores.containsKey(nomePais)) {
            //edita a cor no marcador
            _hashMarcadores.get(nomePais).setColor(cor);

            //edita dono do Territorio
            for (Territorio t : _listaDeTerritorios) {
                if (t.getNome() == nomePais) {
                    t.setDono(playerIndex);
                }
            }
        } else {
            if (_hashMarcadores.isEmpty())
                System.out.println("Maracadors vazios!");
            else
                System.out.println("Nao ha marcador com chave " + nomePais);
        }
        Set<String> keys = _hashMarcadores.keySet();
//         for( String nomePaisit : keys)
//         {
//        	 System.out.print(nomePaisit +"\n" );
//         }
    }

    public List<Territorio> getListaTerritorios() {
        return _listaDeTerritorios;
    }

    public Territorio getTerritorio(String name) {
        return _hashTerritorios.get(name);
    }


}
