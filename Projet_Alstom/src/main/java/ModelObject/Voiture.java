package ModelObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Voiture {

	protected List<Parametre> donnees = null;
	
	public  Voiture(){
		donnees = new ArrayList<Parametre>();
		
	}
	
	public List<Parametre> getDonnees(){
		return donnees;
	}
	
	
}
