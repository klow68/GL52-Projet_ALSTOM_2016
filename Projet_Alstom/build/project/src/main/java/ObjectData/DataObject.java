package ObjectData;

import ModelObject.Parametre;
import ModelObject.ParametreCombo;
import ModelObject.ParametreInput;

/**
 * The Class DataObject. Permet d'associer une valeur à un paramètre
 */
public class DataObject {

	/** The value. */
	Object value;

	/** The parametre. */
	Parametre parametre;

	/**
	 * Instantiates a new data object.
	 *
	 * @param _value
	 *            the _value
	 * @param _parametre
	 *            the _parametre
	 */
	public DataObject(Object _value, Parametre _parametre) {

		switch (_parametre.getTypePara()) {
		case INPUT:

			this.parametre = (ParametreInput) _parametre;
			switch (((ParametreInput) parametre).getType()) {
			case INTEGER:
				value = new Integer(_value.toString());
				break;
			case BOOLEAN:
				value = new Boolean(_value.toString());
				break;
			case STRING:
				value = _value.toString();
				break;
			case DOUBLE:
				value = new Double(_value.toString());
				break;
			default:
				break;
			}
			break;
		case COMBO:
			parametre = (ParametreCombo) _parametre;
			value = new Integer(_value.toString());
			break;
		default:
			break;
		}
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the parametre.
	 *
	 * @return the parametre
	 */
	public Parametre getParametre() {
		return parametre;
	}

	/**
	 * Adds the value.
	 *
	 * @param _data
	 *            the _data
	 */
	public void addValue(Object _data) {
		value = _data;
	}

}
