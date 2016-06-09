package ModelObject;

public class Parametre {
	
	String nom = null;
	Object donnee = null;
	String mesure = null;
	
	Parametre(String _nom, Object _donnee, String _mesure){
		nom = _nom;
		donnee = _donnee;
		mesure = _mesure;
	}
	
	
	Parametre(String _nom, Object _donnee){
		nom = _nom;
		donnee = _donnee;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public Object getDonnee() {
		return donnee;
	}


	public void setDonnee(Object donnee) {
		this.donnee = donnee;
	}


	public String getMesure() {
		return mesure;
	}


	public void setMesure(String mesure) {
		this.mesure = mesure;
	}
	
	
}
