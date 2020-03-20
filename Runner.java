

public class Runner {

	public static void main(String[] args) {
		Game game = new Game(10, 10);
		int teams = 0;
		
		for (int j = 0; j < game.getMap().getMaxHeight(); j++) {
			teams++;
			game.addCharacter(new GameNPC(game.getMap(), "Alex W 10" + j, 100 - j, 12, 0, 2, "1d6:1,2,3,4,5,6", null, 6, 1), 0, j);
			game.addCharacter(new GameNPC(game.getMap(), "Alex W 20" + j, 100 - j, 12, 0, 2, "1d6:1,2,3,4,5,6", null, 6, 2), 9, j);
		}
		//GameNPC first = new GameNPC(game.getMap(), "Alex W", 50, 12, 2, 2, "1d6:1,2,3,4,5,6", null, 6, 0);
		//game.addCharacter(first, 9, 0);
		//GameNPC second = new GameNPC(game.getMap(), "Bill H", 50, 12, 2, 2, "1d6:1,2,3,4,5,6", null, 6, 1);
		//game.addCharacter(second, 0, 9);
		//GameNPC third = new GameNPC(game.getMap(), "Pam Y", 50, 12, 2, 2, "1d6:1,2,3,4,5,6", null, 6, 2);
		//game.addCharacter(third, 5, 4);
		System.out.println(game.toString());
		game.playGame(false);
	}

}
