package ObjectData;

import java.util.ArrayList;

public class ObjectClass {

	int id;
	String typeClass;
	ArrayList<DataObject> donnees;
	String URLImage = "";

	public ObjectClass(int _id, String _typeClass, String URL) {
		id = _id;
		typeClass = _typeClass;
		URLImage = URL;
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

	public void addData(DataObject da) {
		donnees.add(da);
	}

	public void setParametres(ArrayList<DataObject> _donnees) {
		donnees = _donnees;
	}

	public void setURL(String URL) {
		URLImage = URL;
	}

	public String getURL() {
		return URLImage;
	}

}
