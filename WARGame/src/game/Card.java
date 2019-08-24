package game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Territorio;

public class Card {
	private Territorio _territorio;
	private Image _imagem;
	private CardType _type;
	
	public Card(Territorio t) {
		if (t != null) {
			setImagem(loadImage(fixName(t)));
		} else {
			setImagem(loadImage("war_carta_coringa"));
		}
		_territorio = t;
		discoverType();
	}

	private String fixName(Territorio t) {
		String res = "war_carta_";

		switch (t.getContinente()) {
		case "America do Norte":
			res += "an";
			break;
		case "America do Sul": 
			res += "asl";
			break;
		case "Africa":
			res += "af";
			break;
		case "Asia": 
			res += "as";
			break;			
		case "Europa":
			res += "eu";
			break;
		case "Oceania": 
			res += "oc";
			break;
		default:
			System.out.println("\tProblema @ Class 'Card':" + t.getContinente());
			break;
		}
		
		return res +"_"+t.getNome().replace("_", "").replace(" ", "").toLowerCase();
	}
	private Image loadImage(String name) {
		Image i = null;
		String filename = "images/cartas/"+name+".png";
		try {
			i = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("@Class 'Card': " + e.getMessage());
			System.out.println("\t File: "+filename);
			System.exit(1);
		}
		int width = i.getWidth(null);
		int height = i.getHeight(null);
		

		return i;
	}


	public Territorio getTerritorio() {
		return _territorio;
	}


	public Image getImagem() {
		return _imagem;
	}


	public CardType getType() {
		return _type;
	}

	private void discoverType() {
		if (_territorio == null) {
			_type = CardType.Joker;
			return;
		}

		switch (_territorio.getNome()) {
			case "Africa do Sul":
			case "Egito":
			case "Alasca":
			case "Texas":
			case "Vancouver":
			case "Coreia do Sul":
			case "India":
			case "Iraque":
			case "Peru":
			case "Venezuela":
			case "Mongolia":
			case "Russia":
			case "Tailandia":
			case "Turquia":
			case "Franca":
			case "Polonia":
			case "Romenia":
			case "Australia":
			case "Indonesia":
				_type = CardType.Triangle;
			break;

			case "Argelia":
			case "Nigeria":
			case "Calgary":
			case "Groelandia":
			case "Quebec":
			case "Arabia Saudita":
			case "Bangladesh":
			case "Cazaquistao":
			case "Estonia":
			case "Japao":
			case "Brasil":
			case "Paquistao":
			case "Espanha":
			case "Reino Unido":
			case "Ucrania":
			case "Perth":
				_type = CardType.Circle;

			break;

			case "Angola":
			case "Somalia":
			case "California":
			case "Mexico":
			case "NovaYork":
			case "China":
			case "Coreia do Norte":
			case "Ira":
			case "Jordania":
			case "Argentina":
			case "Letonia":
			case "Siberia":
			case "Siria":
			case "Italia":
			case "Suecia":
			case "Nova Zelandia":
				_type = CardType.Rectangle;

			break;

			default:
				System.out.print("Algo deu errado!");
			break;
		}
	}



	private void setImagem(Image _imagem) {
		this._imagem = _imagem;
	}
}
