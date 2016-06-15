package ModelObject;

/**
 * The Class Parametre.
 */
public abstract class Parametre {

	/** The id. */
	protected int id = -1;

	/** The classe. */
	protected String classe = null;

	/** The label. */
	protected String label = null;

	/**
	 * The Enum typeParametre.
	 */
	public enum typeParametre {

		/** The combo. */
		COMBO,
		/** The select. */
		SELECT,
		/** The input. */
		INPUT
	}

	/** The type para. */
	protected typeParametre typePara = null;

	/**
	 * Instantiates a new parametre.
	 *
	 * @param _id
	 *            the _id
	 * @param _classe
	 *            the _classe
	 * @param _label
	 *            the _label
	 * @param _type
	 *            the _type
	 */
	public Parametre(int _id, String _classe, String _label, typeParametre _type) {
		id = _id;
		classe = _classe;
		label = _label;
		typePara = _type;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the classe.
	 *
	 * @return the classe
	 */
	public String getClasse() {
		return classe;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Gets the type para.
	 *
	 * @return the type para
	 */
	public typeParametre getTypePara() {
		return typePara;
	}

	/**
	 * Convert type para.
	 *
	 * @param para
	 *            the para
	 * @return the type parametre
	 */
	public static typeParametre convertTypePara(String para) {
		typeParametre ret = null;
		switch (para) {
		case ("combo"):
			ret = typeParametre.COMBO;
			break;
		case ("select"):
			ret = typeParametre.SELECT;
			break;
		case ("input"):
			ret = typeParametre.INPUT;
			break;
		}
		return ret;
	}

}
