package ModelObject;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ParametreSelect extends Parametre {
	
	private ArrayList<Parametre> parametres = null;


	public ParametreSelect(int _id, String _classe, String _label, typeParametre _type, ArrayList<Parametre> _parametres) {
		super(_id, _classe, _label, _type);
		parametres = _parametres;
	}
	@SuppressWarnings("unchecked")
	public ParametreSelect(int _id, String _classe, String _label, typeParametre _type,JSONArray tableau) {
		super(_id, _classe, _label, _type);
		//Nous devons étudier la chose que nous avons recu...
		//Le tab est composé des parametres que doit afficher le select une fois choisi 
		parametres = new ArrayList<Parametre>();
		if (tableau!=null ){
			if(tableau.size()>0){
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
						parametres.add( new ParametreInput(id,classe,label,typo,job.get("input").toString()));
						break;
					case("combo"):
						//Un combo ...
						id = Integer.parseInt(job.get("id").toString());
						classe = job.get("class").toString();
						label = job.get("label").toString();
						typo = typeParametre.COMBO;
						
						JSONArray tabCombo = (JSONArray) job.get("select");
						parametres.add(new ParametreCombo(id,classe,label,typo,tabCombo));
						
						
						break;
					default:
						System.out.println("ERREUR INTERNE : mauvaise config");
						break;
					}
				}
			}
		}
	}


	public ArrayList<Parametre> getParametres() {
		return parametres;
	}
	
	public Parametre getParametre(int id){
		
		Parametre tmp = null;
		
		for(Parametre p : parametres){

			if(p.getId()==id){ return p;}
			
			switch(p.getTypePara()){
			case COMBO:
				tmp = ((ParametreCombo) p).getParametre(id);
				if(tmp!=null)return tmp;
				break;
			default:
				break;
			}
		}
		
		return tmp;
	}
	

}
