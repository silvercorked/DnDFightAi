

import java.util.LinkedList;
import java.util.Scanner;

public class Game {

	public Dice d20;
	public Map map;
	public int turn;
	
	public Game() {
		this.map = new Map(10);
		System.out.println(this.map.toString());
	}
	public Game(int width, int height) {
		this.map = new Map(width, height);
		System.out.println(this.map.toString());
	}
	
	public int playGame(boolean turnByTurn) {
		Scanner scan = new Scanner(System.in);
		while(!this.checkWin()) {
			if (turnByTurn) {
				if (scan.nextInt() == 1) {
					System.out.println("Running turn");
					this.doTurn();
					System.out.println(this.toString());
				}
			}
			else {
				this.doTurn();
				System.out.println(this.toString());
			}
		}
		scan.close();
		return 0;
	}
	
	public int addCharacter(GameNPC a, int x, int y) {
		return this.map.placeCharacter(a, x, y);
	}
	
	public int doTurn() {
		this.turn++;
		System.out.println("--------------------------------------------- Turn " + this.turn + " ---------------------------------------------");
		LinkedList<GameNPC> characters = this.map.getAllCharacters();
		for(GameNPC character : characters) {
			if (!character.isDead()) {
				character.startTurn();
			}
		}
		return 0;
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public boolean checkWin() {
		LinkedList<GameNPC> characters = this.getMap().getAllCharacters();
		if (!characters.isEmpty()) {
			int team = characters.get(0).getTeam();
			for (GameNPC character : characters)
				if (character.getTeam() != team)
					return false;
			System.out.println("Team " + team + " Wins!");
		}
		return true;
	}
	
	public static Dice getD20() {
		return new Dice("1d20:1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20");
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}
}
