

public class Terrain {
	
	private int height;
	private GameNPC occupiedBy;
	private boolean difficultTerrain;
	private int x;
	private int y;
	
	public Terrain() {
		this.height = 0;
		this.occupiedBy = null;
		this.difficultTerrain = false;
		this.x = 0;
		this.y = 0;
	}
	public Terrain(int x, int y) {
		this.height = 0;
		this.occupiedBy = null;
		this.difficultTerrain = false;
		this.x = x;
		this.y = y;
	}
	public Terrain(int h, GameNPC gc, boolean dt, int x, int y) {
		this.height = h;
		this.occupiedBy = gc;
		this.difficultTerrain = dt;
		this.x = x;
		this.y = y;
	}
	
	public int getHeight() {
		return this.height;
	}
	public GameNPC getOccupyingCharacter() {
		return this.occupiedBy;
	}
	public boolean getDifficultTerrain() {
		return this.difficultTerrain;
	}
	public int[] getLocation() {
		int[] a = {this.getXCoordinate(), this.getYCoordinate()};
		return a;
	}
	public int getXCoordinate() {
		return this.x;
	}
	public int getYCoordinate() {
		return this.y;
	}
	public int setOccupyingCharacter(GameNPC gc) {
		this.occupiedBy = gc;
		if (gc != null)
			gc.setTile(this);
		return 0;
	}
	public void setDifficultTerrain(boolean diff) {
		this.difficultTerrain = diff;
	}
	public boolean isOccupied() {
		return this.getOccupyingCharacter() != null;
	}
}
