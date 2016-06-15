package ModelObject;

import java.util.ArrayList;

import ModelObject.Parametre.typeParametre;

/**
 * The Class Onglet.
 */
public class Onglet {

	/** The name. */
	private String name = null;

	/** The onglets. */
	private ArrayList<Parametre> onglets = null;

	/**
	 * Instantiates a new onglet.
	 *
	 * @param _name
	 *            the _name
	 */
	public Onglet(String _name) {
		name = _name;
		onglets = new ArrayList<Parametre>();
	}

	/**
	 * Adds the parametre.
	 *
	 * @param _para
	 *            the _para
	 */
	public void addParametre(Parametre _para) {
		onglets.add(_para);

	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the parametres.
	 *
	 * @return the parametres
	 */
	public ArrayList<Parametre> getParametres() {
		return onglets;
	}

	/**
	 * Gets the parametre.
	 *
	 * @param id
	 *            the id
	 * @return the parametre
	 */
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
