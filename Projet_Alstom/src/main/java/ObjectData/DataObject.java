package ObjectData;

import ModelObject.Parametre;

public class DataObject {

	Object value;
	Parametre parametre;
	
	public DataObject(Object value, Parametre parametre) {
		this.value = value;
		this.parametre = parametre;
	}

	public Object getValue() {
		return value;
	}

	public Parametre getParametre() {
		return parametre;
	}
	
	public void addValue(Object _data){
		value = _data;
	}
	
	
}
