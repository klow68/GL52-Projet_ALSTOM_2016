package ObjectData;

import ModelObject.Parametre;
import ModelObject.ParametreCombo;
import ModelObject.ParametreInput;

public class DataObject {

	Object value;
	Parametre parametre;

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

	public Object getValue() {
		return value;
	}

	public Parametre getParametre() {
		return parametre;
	}

	public void addValue(Object _data) {
		value = _data;
	}

}
