

import java.util.LinkedList;

public class Map {

	private Terrain[][] gameMap;
	private int MAX_WIDTH;
	private int MAX_HEIGHT;
	
	public Map(int squareSize) {
		this.initializeGameMap(squareSize, squareSize);
	}
	public Map(int width, int height) {
		this.initializeGameMap(width, height);
	}
	
	private void initializeGameMap(int width, int height) {
		this.MAX_WIDTH = width;
		this.MAX_HEIGHT = height;
		this.gameMap = new Terrain[width][height];
		for (int i = 0; i < this.gameMap.length; i++) {
			for (int j = 0; j < this.gameMap[i].length; j++) {
				this.gameMap[i][j] = new Terrain(i, j);
			}
		}
	}
	public int placeCharacter(GameNPC a, int x, int y) {
		this.checkCoordinates(x, y);
		return this.gameMap[x][y].setOccupyingCharacter(a);
	}
	public int moveCharacter(GameNPC a, int x, int y) {
		this.checkCoordinates(x, y);
		if (!this.gameMap[x][y].isOccupied()) {
			a.getTile().setOccupyingCharacter(null);
			a.setTile(this.getTile(x, y));
			a.getTile().setOccupyingCharacter(a);
			return 0; // successful move
		}
		return -1; // tile is occupied!
	}
	
	public int toggleDifficultTerrain(int x, int y) {
		this.checkCoordinates(x, y);
		this.gameMap[x][y].setDifficultTerrain(!this.gameMap[x][y].getDifficultTerrain());
		return 0;
	}
	
	public Terrain getTile(int x, int y) {
		this.checkCoordinates(x, y);
		//System.out.println("Attempting to get Tile: " + x + " " + y);
		return this.gameMap[x][y];
	}
	
	public LinkedList<GameNPC> getAllCharacters() {
		LinkedList<GameNPC> characters = new LinkedList<GameNPC>();
		for (int i = 0; i < this.gameMap.length; i++)
			for (int j = 0; j < this.gameMap[i].length; j++)
				if (this.gameMap[i][j].getOccupyingCharacter() != null)
					characters.add(this.gameMap[i][j].getOccupyingCharacter());
		return characters;
	}
	
	private void checkCoordinates(int x, int y) {
		if (!(x < this.gameMap.length || y < this.gameMap[x].length))
			throw new IndexOutOfBoundsException("X: " + x + " | Y: " + y + " | Board Width: " + this.gameMap.length + " | Board Height: " + this.gameMap[0].length + ". X and Y don't fit!");
	}
	public int getWidth() {
		return this.gameMap.length;
	}
	public int getHeight() {
		return this.gameMap[0].length;
	}
	
	@Override
	public String toString() {
		StringBuilder a = new StringBuilder("             |");
		for (int i = 0; i < this.gameMap.length; i++) {
			a.append("      " + i + "      |");
		}
		a.append('\n');
		for (int j = 0; j < this.gameMap[0].length; j++) {
			a = this.addRow(a);
			a.append("      " + j + "      |");
			for (int i = 0; i < this.gameMap.length; i++) {
				a.append(String.format("%13s|", (this.getTile(i, j).isOccupied()) ? this.getTile(i, j).getOccupyingCharacter().name : "" + i + "," + j));
				//a.append("     " + i + "," + j + "     |");
			}
			a.append('\n');
		}
		return a.toString();
	}
	private StringBuilder addRow(StringBuilder a) {
		for (int i = 0; i < this.gameMap.length + 1; i++) {
			a.append("-------------|");
		}
		a.append('\n');
		return a;
	}
	
	public int getMaxWidth() {
		return this.MAX_WIDTH;
	}
	public int getMaxHeight() {
		return this.MAX_HEIGHT;
	}
}
