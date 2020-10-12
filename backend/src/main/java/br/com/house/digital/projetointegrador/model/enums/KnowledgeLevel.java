package br.com.house.digital.projetointegrador.model.enums;

public enum KnowledgeLevel {

	BASIC(1, "Basic"), 
	INTERMEDIATE(2, "Intermediate"), 
	ADVANCED(3, "Advanced");

	private int id;
	private String description;

	private KnowledgeLevel(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static KnowledgeLevel toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (KnowledgeLevel x : KnowledgeLevel.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id invalid:" + id);
	}

}
