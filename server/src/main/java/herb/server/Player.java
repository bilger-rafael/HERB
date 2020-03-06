package herb.server;

public class Player {
	private String name;
	private Integer rank;
	
	public Player() { }
	
	public Player(String name, Integer rank) {
		this.setName(name);
		this.setRank(rank);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
