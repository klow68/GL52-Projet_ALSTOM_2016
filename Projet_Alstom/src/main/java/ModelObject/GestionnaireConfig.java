package ModelObject;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
			System.out.println(ob.get("select"));
		}
		
		
	}
	public ArrayList<Onglet> getOnglets() {
		return onglets;
	}
	
	
	

}
