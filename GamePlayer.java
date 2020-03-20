

import java.util.Scanner;

public class GamePlayer extends GameNPC {
	
	private int remainingMovement;
	private boolean remainingAction;
	private Scanner scan;

	public GamePlayer(Map map, String name, int hp, int ac, int statBonus, int prof, String attackDiceString, Terrain tile, int movement, int team) {
		super(map, name, hp, ac, statBonus, prof, attackDiceString, tile, movement, team);
		this.remainingMovement = this.getMovement();
		this.remainingAction = true;
		scan = new Scanner(System.in);
	}
	
	@Override
	public int move() {
		for (int i = 0; i < this.getRemainingMovement(); i++) {
			System.out.println("Place the Coordinates for the place you'd like to move to next. (x,y) or (end movement)");
			String answer = this.scan.next("^([0-9]+,[0-9]+)|(end turn)$");
			if (answer.toLowerCase().equals("end movement"))
				return -1; // turn ended early
			int[] coords = this.parseMoveString(answer);
			double distance = distanceFormula(this.getTile().getXCoordinate(),
					this.getTile().getYCoordinate(),
					coords[0],
					coords[1]);
			if (distance < 2 && distance > -2) {
				this.map.moveCharacter(this, coords[0], coords[1]);
			}
			else {
				i--;
				System.out.println("You can only move 1 space at a time!");
			}
		}
		System.out.println("Movement Complete!");
		return 0;
	}
	@Override
	public int startTurn() {
		this.remainingMovement = this.getMovement();
		this.remainingAction = true;
		boolean turnActive = true;
		while (turnActive) {
			System.out.println("What would you like to do?");
			System.out.println(" -- 1. attack");
			System.out.println(" -- 2. move");
			System.out.println("-------------------------");
			String action = this.scan.next("^(1|2)|(attack|move)$");
			if (action.equals("1") || action.equals("attack")) {
				doAction();
			}
			else if (action.equals("2") || action.equals("move")) {
				move();
			}
			System.out.println("Would you like to end your turn? (y/n)");
			String endTurn = this.scan.next("^(y|n|yes|no)$");
			if (endTurn.equals("y") || endTurn.equals("yes")) {
				turnActive = false;
			}
		}
		return 0;
	}
	@Override
	public int doAction() {
		if (this.getRemainingAction()) {
			System.out.println("Give the coordinates of the target of your attack! (x,y) or (end attack)");
			String result = scan.next("^([0-9]+,[0-9]+)|(end attack)$");
			if (result.toLowerCase().equals("end attack"))
				return -1;
			int[] coords = parseMoveString(result);
			double distance = distanceFormula(this.getTile().getXCoordinate(),
					this.getTile().getYCoordinate(),
					coords[0],
					coords[1]);
			if (distance < 2 && distance > -2) {
				attack(this.map.getTile(coords[0], coords[1]).getOccupyingCharacter());
				this.remainingAction = false;
				return 0;
			}
			else {
				System.out.println("Target is not close enough!");
				return -1;
			}
		}
		else {
			System.out.println("You've already used your Action!");
			return -1;
		}
	}
	
	
	private int[] parseMoveString(String movementString) {
		int[] coords = new int[2];
		coords[0] = Integer.parseInt(movementString.substring(0, movementString.indexOf(',')), 10);
		coords[1] = Integer.parseInt(movementString.substring(movementString.indexOf(','), movementString.length()), 10);
		return coords;
	}
	
	public int getRemainingMovement() {
		return this.remainingMovement;
	}
	public boolean getRemainingAction() {
		return this.remainingAction;
	}
	
}
