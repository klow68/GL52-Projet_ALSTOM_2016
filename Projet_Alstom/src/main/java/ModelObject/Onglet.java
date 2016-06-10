package ModelObject;

import java.util.ArrayList;

public class Onglet {
	
	private String name = null;
	private ArrayList<Parametre> onglets = null;
	
	public Onglet(String _name){
		name = _name;
		onglets = new ArrayList<Parametre>();
	}
	
	public void addParametre(Parametre _para){
		onglets.add(_para);
		
	}
	
	public String getName(){
		return name;
	}
	
	public ArrayList<Parametre> getParametres(){
		return onglets;
	}

	
	

}
