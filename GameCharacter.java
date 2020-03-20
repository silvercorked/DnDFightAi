

public abstract class GameCharacter<T, K, F> implements HasMovement, HasTurn, HasAction {
	protected int hp;
	protected int ac;
	protected int statBonus;
	protected int proficiency;
	protected Dice attackDice;
	protected F tile;
	protected int movement;
	protected int team;
	
	protected abstract T findTarget(K map);
	
	public abstract int attack(T target);
	
	public abstract void die();
	
	public abstract boolean isDead();
	
	public int getHP() {
		return this.hp;
	}
	public int getAC() {
		return this.ac;
	}
	public int getStatBonus() {
		return this.statBonus;
	}
	public int getProficiency() {
		return this.proficiency;
	}
	public Dice getAttackDice() {
		return this.attackDice;
	}
	public F getTile() {
		return this.tile;
	}
	public int getMovement() {
		return this.movement;
	}
	public int getTeam() {
		return this.team;
	}
	public void setHP(int hp) {
		this.hp = hp;
	}
	public void setAC(int ac) {
		this.ac = ac;
	}
	public void setStatBonus(int statBonus) {
		this.statBonus = statBonus;
	}
	public void setProficiency(int proficiency) {
		this.proficiency = proficiency;
	}
	public void setAttackDice(String diceString) {
		this.attackDice = new Dice(diceString);
	}
	public void setAttackDice(Dice newDice) {
		this.attackDice = newDice;
	}
	public void setTile(F tile) {
		this.tile = tile;
	}
	public void setMovement(int movement) {
		this.movement = movement;
	}
	public void setTeam(int team) {
		this.team = team;
	}
}
