package ModelObject;

import java.util.ArrayList;

public class ParametreCombo extends Parametre {
	
	private ArrayList<Parametre> selects = null;

	public ParametreCombo(int _id, String _classe, String _label, typeParametre _type) {
		super(_id, _classe, _label, _type);
		selects = new ArrayList<Parametre>();
		
	}

	public void addSelect(Parametre para){
		if(para.typePara.equals(typeParametre.SELECT)){
			selects.add(para);
		}
		else{
			System.out.println("Erreur interne : mauvais select");
		}
	}
	public ArrayList<Parametre> getSelects() {
		return selects;
	}

}
