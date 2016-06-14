package ObjectData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ModelObject.GestionnaireConfig;
import ModelObject.Parametre;
import ModelObject.Parametre.typeParametre;

public class GestionnaireDonnees {

	ArrayList<ObjectClass> objets;
	File fichier;
	
	GestionnaireConfig GC = null;
	
	public GestionnaireDonnees(){
		objets = new ArrayList<ObjectClass>();
		
	}

	public ArrayList<ObjectClass> getObjets() {
		return objets;
	}
	
	public void run(GestionnaireConfig GC){
		fichier = new File("src/main/resources/data/data.json");
		objets = new ArrayList<ObjectClass>();
		this.GC = GC;
		
		JSONParser parser = new JSONParser();

		JSONArray a = null;
		
		try {
			a = (JSONArray) parser.parse(new FileReader(fichier));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Object o : a) {
			//On parcourt chaque objet qui peut etre loco/wagon
			JSONObject ob = (JSONObject) o;
			
			String typeObject = ob.get("typeObject").toString();
			int idObject = Integer.parseInt(ob.get("idObject").toString());
			String URL = ob.get("URLImage").toString();
			
			ObjectClass objet = new ObjectClass(idObject,typeObject,URL);
			
			
			JSONArray tabDonnees = (JSONArray) ob.get("data");
			for(Object obj : tabDonnees){
				JSONObject job = (JSONObject) obj;
				
				//On récupere l'ID
				int idData = Integer.parseInt(job.get("idConfig").toString());
				
				//On regarde à quoi correspond l'ID
				Parametre p = GC.getParametre(idData);
				if(p.getTypePara()==typeParametre.COMBO){
					//On s'attend à avoir un autre parametre contenant l'id du select
					// J'ai changé par value -.-
					int idReference = Integer.parseInt(job.get("values").toString());
					objet.addData(new DataObject(idReference,p));
				}
				else{
					objet.addData(new DataObject(job.get("values"),p));
				}
			}
			objets.add(objet);
		}
	}
	
	public void test(){
		//SEULE TA FOI T AIDERA MON FILS
		
		for(ObjectClass oc : getObjets()){
			//T ES ENCORE EN VIE MECREANT ?
			for(DataObject dob : oc.getDonnees()){
				System.out.println(dob.getValue());
				//BAM T MORT !
			}
		}
		
	}
	
	public void sauvegarde(int id, String type, ArrayList<String[]> donnees){
		// map Name ne contien que le name et donnees ne contient PAS le name dans ça liste
		boolean existe = false;
		ObjectClass objet = null;
		int index = 0;
		String document;
		
		for(ObjectClass oc : this.objets){
			if(oc.getId() == id ){
				existe = true;
				objet = oc;
				index = objets.indexOf(oc);
				break;
			}
		}
		
		if(!existe){
			//On le crée
			for(ObjectClass oc : objets){
				if(id < oc.getId()) id = oc.getId();
			}
			id++;
			objet = new ObjectClass(id,type,"");			
			
		}else{
			//On mets à jour la liste
			objets.remove(index);
		}
		
		
		ArrayList<DataObject> data = new ArrayList<DataObject>();

		
		
		for(String[] ligne : donnees){
			data.add(new DataObject(ligne[1],GC.getParametre(Integer.parseInt(ligne[0]))));
		}
		objet.setParametres(data);
		objets.add(objet);
		
		document = "[";
		for(ObjectClass o : objets){
			
			document += "{\"typeObject\":\"" + o.getTypeClass() + "\"\n,";
			document += "\"idObject\":\"" + o.getId() + "\"\n,";
			document += "\"URLImage\":\"" + o.getURL() + "\"\n,";
			document += "\"data\"\n:[";
			for(DataObject d : o.getDonnees()){
				document += "{\"idConfig\":\"" + d.getParametre().getId() + "\",";
				document += "\"values\":\"" + d.getValue() + "\"}\n,";
			}
			document += "]},";
			
		}
		document += "]";
		FileWriter writer;
		try {
			writer = new FileWriter(fichier);
			writer.write(document);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	public ObjectClass getObject(int id){
		
		ObjectClass o = null;
		for(ObjectClass ite : objets){
			if(ite.getId()==id) o =ite;
		}
		return o;
	}

	

}
