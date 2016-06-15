package ModelObject;

import java.util.ArrayList;

import ModelObject.Parametre.typeParametre;

public class Onglet {

	private String name = null;
	private ArrayList<Parametre> onglets = null;

	public Onglet(String _name) {
		name = _name;
		onglets = new ArrayList<Parametre>();
	}

	public void addParametre(Parametre _para) {
		onglets.add(_para);

	}

	public String getName() {
		return name;
	}

	public ArrayList<Parametre> getParametres() {
		return onglets;
	}

	public Parametre getParametre(int id) {

		Parametre tmp = null;
		for (Parametre p : onglets) {
			if (p.getId() == id)
				tmp = p;
			;

			if (p.getTypePara() == typeParametre.COMBO)
				tmp = ((ParametreCombo) p).getParametre(id);

		}

		return tmp;
	}

}
