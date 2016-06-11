package ModelObject;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ParametreSelect extends Parametre {
	
	private Parametre paraSelect = null;


	public ParametreSelect(int _id, String _classe, String _label, typeParametre _type, Parametre _parametre) {
		super(_id, _classe, _label, _type);
		paraSelect = _parametre;
	}
	public ParametreSelect(int _id, String _classe, String _label, typeParametre _type,JSONArray tableau) {
		super(_id, _classe, _label, _type);
		//Nous devons étudier la chose que nous avons recu...
		
		//Le tab est composé soit d'un input ou bien d'encore une combobox ...
		
		@SuppressWarnings("unchecked")
		Iterator<JSONObject> ite = tableau.iterator();
		while(ite.hasNext()){
			JSONObject job = ite.next();
			int id;
			String classe;
			String label;
			typeParametre typo;
			switch(job.get("type").toString()){
			case("input"):
				//On insère un type input et c'est fini
				id = Integer.parseInt(job.get("id").toString());
				classe = job.get("class").toString();
				label = job.get("label").toString();
				typo = typeParametre.INPUT;
				paraSelect = new ParametreInput(id,classe,label,typo,job.get("input").toString());
				break;
			case("combo"):
				//Un combo ...
				id = Integer.parseInt(job.get("id").toString());
				classe = job.get("class").toString();
				label = job.get("label").toString();
				typo = typeParametre.COMBO;
				
				JSONArray tabCombo = (JSONArray) job.get("select");
				paraSelect = new ParametreCombo(id,classe,label,typo);
				((ParametreCombo) paraSelect).addAllSelect(tabCombo);
				
				
				break;
			default:
				System.out.println("ERREUR INTERNE : mauvaise config");
				break;
			}
		}
	}


	public Parametre getParaSelect() {
		return paraSelect;
	}
	
	public typeParametre getTypeSelect(){
		return paraSelect.typePara;
	}

}
