import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {

	enum Action {

		// Standard actions to be received from command file
		DEPOSIT, SELECT, CANCEL

	}

	public static void main(String[] args) {

		String fileName;

		// test cases

		// t1
		// int[] drinkStock = { 10, 8, 1, 2 };
		// int[] coinStock = { 5, 5, 5 };
		// fileName = "t1.txt";

		// t2
		// int[] drinkStock = { 2, 5, 4, 0 };
		// int[] coinStock = { 1, 2, 0 };
		// fileName = "t2.txt";

		// t3
		// int[] drinkStock = { 2, 1, 4, 1 };
		// int[] coinStock = { 80, 2, 0 };
		// fileName = "t3.txt";

		// t4
		// int[] drinkStock = { 1, 1, 1, 1 };
		// int[] coinStock = { 0, 0, 0 };
		// fileName = "t4.txt";

		// t5
		// int[] drinkStock = { 1, 1, 1, 1 };
		// int[] coinStock = { 5, 5, 5 };
		// fileName = "t5.txt";

		// custom values for testing
		// VendingMachine vendingMachine = new VendingMachine(drinkStock, coinStock);

		// instance with default values
		fileName = "input.txt";
		VendingMachine vendingMachine = new VendingMachine();

		try {

			// open file and read line by line

			File in = new File(fileName);

			Scanner sc = new Scanner(in);
			while (sc.hasNextLine()) {

				// split line by comma
				String[] txt = sc.nextLine().split(",");

				// parse the enum type, if no match print error
				Action mAction = parseAction(txt[0]);

				if (mAction == null) {
					System.out.println("Invalid input\n");
					continue;
				}

				System.out.println(mAction);

				// Cases for each action
				switch (mAction) {

					case DEPOSIT:

						// catch invalid input
						if (!inputValid(txt)) {
							break;
						}

						// read in coin and deposit
						VendingMachine.Coin coin = parseCoin(txt[1]);

						// catch invalid enum type
						if (coin == null) {
							System.out.println("Invalid coin\n");
							break;
						}

						vendingMachine.deposit(coin);

						break;

					case SELECT:

						// catch invalid input
						if (!inputValid(txt)) {
							break;
						}

						VendingMachine.Drink drink = parseDrink(txt[1]);

						// catch invalid enum type
						if (drink == null) {
							System.out.println("Invalid drink\n");
							break;
						}

						vendingMachine.select(drink);

						break;

					case CANCEL:

						vendingMachine.cancel();

						break;

				}
			}

			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error - file not found. Must be named \"" + fileName + "\"");
		}

	}

	// parse the enum type, if no match print error

	private static Action parseAction(String txt) {

		Action inAction;

		try {
			inAction = Action.valueOf(txt);

		} catch (IllegalArgumentException e) {
			return null;
		}

		return inAction;

	}

	private static VendingMachine.Drink parseDrink(String txt) {

		VendingMachine.Drink inDrink;

		try {
			inDrink = VendingMachine.Drink.valueOf(txt);

		} catch (IllegalArgumentException e) {
			return null;
		}

		return inDrink;

	}

	private static VendingMachine.Coin parseCoin(String txt) {

		VendingMachine.Coin inCoin;

		try {
			inCoin = VendingMachine.Coin.valueOf(txt);

		} catch (IllegalArgumentException e) {
			return null;
		}

		return inCoin;

	}

	// check if array is length of 2 after splitting string by comma

	private static boolean inputValid(String[] s) {

		if (s.length != 2) {
			System.out.println("Invalid input\n");
			return false;
		}

		return true;

	}

}