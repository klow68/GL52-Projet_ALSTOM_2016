package ModelObject;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ModelObject.Parametre.typeParametre;
import ModelObject.ParametreInput.typeInput;

public class GestionnaireConfig {
	
	private ArrayList<Onglet> onglets = null;
	
	private File file = null; //= new File("src/main/resources/InterfaceAdmin/admin.json");

	public GestionnaireConfig(){
		
		onglets = new ArrayList<Onglet>();
	}
	
	public void run(){
		//On ouvre le fichier des configs
		file = new File("src/main/resources/configs/config.json");
		
		JSONParser parser = new JSONParser();

		JSONArray a = null;
		
		try {
			a = (JSONArray) parser.parse(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Nous allons parcourir chaque ligne du fichier selon une procédure
		
		//1er On récupère tous les onglets
		
		for (Object o : a) {
			JSONObject ob = (JSONObject) o;
			
			Parametre para = null;
			
			//si l'onglet n'existe pas
			Onglet onglet = null;
			if(!this.ClassExist(ob.get("class").toString())){
				onglets.add(new Onglet(ob.get("class").toString()));
			}
			
			onglet = getOnglet(ob.get("class").toString());
			
			int id;
			String label;
			typeParametre type = null;
			
			id = new Integer(ob.get("id").toString());
			label = ob.get("label").toString();
			type = Parametre.convertTypePara(ob.get("type").toString());
			//On va   de quel type de parametre on traite
			
			switch(type){
			case COMBO:
				
				//Idée : on boucle tant que tab
				//Dans le combo on aura QUE des select
				para = new ParametreCombo(id,onglet.getName(),label,typeParametre.COMBO);
				
				ArrayList<ParametreSelect> paraSelect = new ArrayList<ParametreSelect>();
				
				JSONArray array = (JSONArray) ob.get("select");
				Iterator<JSONObject> ite = array.iterator();
				while(ite.hasNext()){
					JSONObject job = ite.next();
					if(job.get("type").toString().equals("select"))	System.out.println(job.get("select").toString());
				}
			
				
				
				break;
			case INPUT:
				para = new ParametreInput(id,onglet.getName(),label,type,typeInput.INTEGER);
				onglet.addParametre(para);
				break;
			default:
				System.out.println("ERREUR INTERNE : mauvais type");
				break;
				
			}
		
			
		}
		
		
		
	}
	public ArrayList<Onglet> getOnglets() {
		return onglets;
	}
	
	public boolean ClassExist(String _name){
		
		for(Onglet onglet : onglets){
			if(onglet.getName().equals(_name)) return true;
		}
		return false;
	}
	
	public Onglet getOnglet(String name){
		for(Onglet o : onglets){
			if(o.getName().equals(name)) return o;
		}
		return null;
	}
	
	
	

}
