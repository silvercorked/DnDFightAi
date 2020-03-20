// no for or while loops allowed!
public class Dice {

	private Die[] dice;
	
	public Dice(String parseString) {
		this.dice = new Die[0];
		handleDice(parseString);
	}
	
	private int handleDice(String parseString) {
		int end = parseString.indexOf('|');
		String oneType = parseString.substring(0, end <= -1 ? parseString.length() : end);
		parseString = parseString.substring(++end);
		handleOneType(oneType);
		if (end == 0) {
			return 0; // 0 for success
		}
		return handleDice(parseString);
	}
	private int handleOneType(String justOne) {
		int runs = Integer.parseInt(justOne.substring(0, justOne.indexOf('d')), 10);
		if (runs == 0)
			return 0; // 0 for success
		Die a = new Die(justOne.substring(1, justOne.length()));
		appendToArray(a);
		runs -= 1;
		justOne = "" + runs + justOne.substring(justOne.indexOf('d'), justOne.length());
		return handleOneType(justOne);
	}
	private void appendToArray(Die a) {
		int size = this.dice.length;
		Die[] newArray = new Die[size + 1];
		newArray = copyArrays(this.dice, newArray, 0);
		newArray[size] = a;
		this.dice = newArray;
	}
	private Die[] copyArrays(Die[] array, Die[] array2, int index) {
		if (index >= array.length || index >= array2.length)
			return array2;
		array2[index] = array[index];
		return copyArrays(array, array2, ++index);
	}
	
	public int rollDice() {
		return rollDiceRecrow(0, 0);
	}
	private int rollDiceRecrow(int index, int sum) {
		if (index >= this.getDice().length)
			return sum;
		sum += this.getDice()[index].rollDie();
		return rollDiceRecrow(++index, sum);
	}
	
	public Die[] getDice() {
		return this.dice;
	}
}
