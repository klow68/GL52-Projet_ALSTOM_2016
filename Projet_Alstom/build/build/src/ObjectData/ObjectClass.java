package ObjectData;

import java.util.ArrayList;

/**
 * The Class ObjectClass. Permet d'instancier Type d'object
 * (Locomotive,Wagon...)
 */
public class ObjectClass {

	/** The id. */
	int id;

	/** The type class. */
	String typeClass;

	/** The donnees. */
	ArrayList<DataObject> donnees;

	/** The URL image. */
	String URLImage = "";

	/**
	 * Instantiates a new object class.
	 *
	 * @param _id
	 *            the _id
	 * @param _typeClass
	 *            the _type class
	 * @param URL
	 *            the url
	 */
	public ObjectClass(int _id, String _typeClass, String URL) {
		id = _id;
		typeClass = _typeClass;
		URLImage = URL;
		donnees = new ArrayList<DataObject>();
	}

	/**
	 * Gets the donnees.
	 *
	 * @return the donnees
	 */
	public ArrayList<DataObject> getDonnees() {
		return donnees;
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
	 * Gets the type class.
	 *
	 * @return the type class
	 */
	public String getTypeClass() {
		return typeClass;
	}

	/**
	 * Adds the data.
	 *
	 * @param da
	 *            the da
	 */
	public void addData(DataObject da) {
		donnees.add(da);
	}

	/**
	 * Sets the parametres.
	 *
	 * @param _donnees
	 *            the new parametres
	 */
	public void setParametres(ArrayList<DataObject> _donnees) {
		donnees = _donnees;
	}

	/**
	 * Sets the url.
	 *
	 * @param URL
	 *            the new url
	 */
	public void setURL(String URL) {
		URLImage = URL;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getURL() {
		return URLImage;
	}

}
