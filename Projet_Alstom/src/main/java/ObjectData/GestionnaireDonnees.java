package ObjectData;

import java.io.File;
import java.io.FileReader;
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
	
	public GestionnaireDonnees(){
		objets = new ArrayList<ObjectClass>();
		
	}

	public ArrayList<ObjectClass> getObjets() {
		return objets;
	}
	
	public void run(GestionnaireConfig GC){
		fichier = new File("src/main/resources/data/data.json");
		
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
			
			ObjectClass objet = new ObjectClass(idObject,typeObject);
			
			
			JSONArray tabDonnees = (JSONArray) ob.get("data");
			for(Object obj : tabDonnees){
				JSONObject job = (JSONObject) obj;
				
				//On récupere l'ID
				int idData = Integer.parseInt(job.get("idConfig").toString());
				
				//On regarde à quoi correspond l'ID
				Parametre p = GC.getParametre(idData);
				if(p.getTypePara()==typeParametre.COMBO){
					//On s'attend à avoir un autre parametre contenant l'id du select
					// J'ai changer par value -.-
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
	

}
