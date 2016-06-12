package ObjectData;

import java.util.ArrayList;

public class ObjectClass {

	int id;
	String typeClass;
	ArrayList<DataObject> donnees;
	
	public ObjectClass(int _id, String _typeClass){
		id = _id;
		typeClass = _typeClass;
		donnees = new ArrayList<DataObject>();
	}

	public ArrayList<DataObject> getDonnees() {
		return donnees;
	}

	public int getId() {
		return id;
	}

	public String getTypeClass() {
		return typeClass;
	}
	
	public void addData(DataObject da){
		donnees.add(da);
	}
	
}
