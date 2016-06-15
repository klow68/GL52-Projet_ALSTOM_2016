package ModelObject;

/**
 * The Class ParametreInput.
 */
public class ParametreInput extends Parametre {

	/**
	 * The Enum typeInput.
	 */
	public enum typeInput {

		/** The integer. */
		INTEGER,
		/** The double. */
		DOUBLE,
		/** The string. */
		STRING,
		/** The boolean. */
		BOOLEAN
	}

	/** The type. */
	private typeInput type = null;

	/**
	 * Instantiates a new parametre input.
	 *
	 * @param _id
	 *            the _id
	 * @param _classe
	 *            the _classe
	 * @param _label
	 *            the _label
	 * @param _typePara
	 *            the _type para
	 * @param input
	 *            the input
	 */
	public ParametreInput(int _id, String _classe, String _label, typeParametre _typePara, String input) {
		super(_id, _classe, _label, _typePara);
		type = convertInput(input);
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public typeInput getType() {
		return type;
	}

	/**
	 * Convert input.
	 *
	 * @param input
	 *            the input
	 * @return the type input
	 */
	private typeInput convertInput(String input) {
		switch (input) {
		case ("double"):
			return typeInput.DOUBLE;
		case ("integer"):
			return typeInput.INTEGER;
		case ("string"):
			return typeInput.STRING;
		case ("boolean"):
			return typeInput.BOOLEAN;
		default:
			System.out.println("Erreur interne : mauvaise type input");
			return null;
		}
	}

}
