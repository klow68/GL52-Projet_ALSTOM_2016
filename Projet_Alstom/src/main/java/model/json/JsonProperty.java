package model.json;

public class JsonProperty {

	private Integer id;
	private Integer idOnglet;
	private String property;
	private String type;

	public JsonProperty() {

	}

	public JsonProperty(Integer id, Integer idOnglet,String property, String type) {
		this.id = id;
		this.idOnglet = idOnglet;
		this.property = property;
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdOnglet() {
		return idOnglet;
	}

	public void setIdOnglet(Integer idOnglet) {
		this.idOnglet = idOnglet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
