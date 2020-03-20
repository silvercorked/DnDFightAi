

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameNPC extends GameCharacter<GameNPC, Map, Terrain> {
	
	protected Map map;
	protected String name;
	
	public GameNPC(Map map, String name, int hp, int ac, int statBonus, int prof, String attackDiceString, Terrain tile, int movement, int team) {
		this.map = map;
		this.name = name;
		this.setHP(hp);
		this.setAC(ac);
		this.setStatBonus(statBonus);
		this.setProficiency(prof);
		this.setAttackDice(attackDiceString);
		this.setTile(tile);
		this.setMovement(movement);
		this.setTeam(team);
	}
	
	public double distanceTo(GameNPC target) {
		return distanceFormula(this.getTile().getXCoordinate(), this.getTile().getYCoordinate()
				, target.getTile().getXCoordinate(), target.getTile().getYCoordinate());
	}
	public boolean canHit(GameNPC target) {
		return (this.distanceTo(target) < 2 && this.distanceTo(target) > -2);
	}
	
	@Override
	public int move() {
		GameNPC enemy = this.findTarget(this.map);
		if (enemy != null) {
			System.out.println("NPC " + this.name + " tried to move toward " + enemy.name +"!");
			return moveOnce(this.getMovement(), enemy);
		}
		System.out.println("" + this.name + " couldn't find a target!");
		return -1;
	}
	private int moveOnce(int movementLeft, GameNPC target) {
		if (movementLeft <= 0 || this.canHit(target)) {
			return 0; //finished moving
		}
		List<Terrain> possibleTiles = new ArrayList<Terrain>();
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if ((this.getTile().getXCoordinate() + i >= 0 && this.getTile().getXCoordinate() + i < this.map.getMaxWidth())
					&& (this.getTile().getYCoordinate() + j >= 0 && this.getTile().getYCoordinate() + j < this.map.getMaxHeight())
					&& !this.map.getTile(this.getTile().getXCoordinate() + i, this.getTile().getYCoordinate() + j).isOccupied())
					possibleTiles.add(this.map.getTile(this.getTile().getXCoordinate() + i, this.getTile().getYCoordinate() + j));
		double min = distanceFormula(0, 0, this.map.getMaxWidth(), this.map.getMaxHeight());
		int minIndex = 0;
		for (int i = 0; i < possibleTiles.size(); i++) {
			double possibleMin = distanceFormula(
					possibleTiles.get(i).getXCoordinate(),
					possibleTiles.get(i).getYCoordinate(),
					target.getTile().getXCoordinate(),
					target.getTile().getYCoordinate());
			if (possibleMin < min) {
				min = possibleMin;
				minIndex = i;
			}
		}
		if (possibleTiles.size() != 0) {
			Terrain tileToMoveTo = possibleTiles.get(minIndex);
			System.out.println("" + this.name + " is about to move to " + tileToMoveTo.getXCoordinate() + "," + tileToMoveTo.getYCoordinate()
					+ " from " + this.getTile().getXCoordinate() + "," + this.getTile().getYCoordinate() + "!");
			this.map.moveCharacter(this, tileToMoveTo.getXCoordinate(), tileToMoveTo.getYCoordinate());
		}
		else {
			return -1; // nowhere to move!
		}
		return moveOnce(--movementLeft, target);
	}
	@Override
	public int startTurn() {
		System.out.println("NPC " + this.name + "'s turn starts!");
		int moveResult = this.move();
		int actionResult = this.doAction();
		return moveResult + actionResult;
	}
	@Override
	public int doAction() {
		GameNPC target = this.findTarget(this.map);
		if (target != null) {
			if (distanceTo(target) < 2) {
				int attackResult = this.attack(target);
				return attackResult;
			}
			System.out.println("" + this.name + " is too far from " + target.name + " to attack!");
		}
		return -1;
	}
	@Override
	protected GameNPC findTarget(Map map) {
		LinkedList<GameNPC> characters = map.getAllCharacters();
		int minIndex = -1;
		double min = map.getWidth() * map.getHeight();
		for (int i = 0; i < characters.size(); i++)
			if (characters.get(i).team != this.team) {
				double a = this.distanceTo(characters.get(i));
				if (a < min) {
					min = a;
					minIndex = i;
				}
			}
		return minIndex == -1 ? null : characters.get(minIndex);
	}
	protected static double distanceFormula(double x, double y, double otherX, double otherY) {
		return Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
	}

	@Override
	public int attack(GameNPC target) {
		int rollToHit = Game.getD20().rollDice() + this.getToHit();
		if (rollToHit >= target.getAC()) { // meets beats
			System.out.println("NPC " + this.name + " hit " + target.name + "!");
			int rollDamage = this.getAttackDice().rollDice() + this.getStatBonus();
			int enemyHP = target.getHP();
			target.setHP(enemyHP - rollDamage);
			return 0;
		}
		System.out.println("NPC " + this.name + " misses against " + target.name + "!");
		return -1; // don't hit
	}
	public int getToHit() {
		return this.statBonus + this.proficiency;
	}
	@Override
	public void setHP(int hp) {
		this.hp = hp;
		if (hp <= 0)
			die();
	}
	@Override
	public void die() {
		this.getTile().setOccupyingCharacter(null);
		this.setTile(null);
		System.out.println("" + this.name + " Died!");
		// died successfully
	}

	@Override
	public boolean isDead() {
		return this.getHP() <= 0;
	}
}
