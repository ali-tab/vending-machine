public class VendingMachine {

    // enumerated types for standardization of drink & coin classes

    public enum Drink {

        // IDs correspond to array storage
        COLA(0, 25), DIET_COLA(1, 35), LIME_SODA(2, 25), WATER(3, 45);

        private int id;
        private int price;

        // getter method
        public int getPrice() {
            return this.price;
        }

        private Drink(int ID, int Price) {
            this.id = ID;
            this.price = Price;
        }

    }

    public enum Coin {

        // IDs correspond to array storage
        QUARTER(2, 25), DIME(1, 10), NICKEL(0, 5);

        private int id;
        private int amt;

        public int getAmount() {
            return this.amt;
        }

        private Coin(int ID, int Amount) {
            this.id = ID;
            this.amt = Amount;
        }

    }

    // array to keep track of drinks in stock, ID of each drink corresponds to its
    // array position

    // same system as drinks for coins held in vending machine
    public int[] drinkStock = { 0, 0, 0, 0 };
    public int[] coinStock = { 0, 0, 0 };

    // default values
    static final int[] DEFAULTDRINKSTOCK = { 10, 8, 0, 2 };
    static final int[] DEFAULTCOINSTOCK = { 5, 5, 5 };

    public int funds = 0;

    public VendingMachine(int[] inputtedDrinkStock, int[] inputtedCoinStock) {

        if (inputtedDrinkStock.length != 4) {
            System.out.println("Invalid array length for drinks");
            return;
        }
        if (inputtedCoinStock.length != 3) {
            System.out.println("Invalid array length for coins");
            return;
        }
        drinkStock = inputtedDrinkStock;
        coinStock = inputtedCoinStock;
        System.out.println("Welcome to the vending machine");
        printStatus();

    }

    public VendingMachine() {

        //default
        this(DEFAULTDRINKSTOCK, DEFAULTCOINSTOCK);

    }

    //deposit a coin
    public void deposit(Coin coin) {

        System.out.println("Inserting " + coin.name());
        // add to funds and vending machine
        funds = funds + coin.amt;
        coinStock[coin.id] = coinStock[coin.id] + 1;
        printStatus();

    }

    //select a drink and return change
    public void select(Drink drink) {

        System.out.println("You have selected: ");
        System.out.println(drink.name() + " for " + drink.price + " cents");

        //check if drink is in stock
        if (drinkStock[drink.id] == 0) {
            System.out.println("Sorry, this drink is out of stock.");
            printStatus();
            return;
        }
        System.out.println("You have a total balance of " + funds + " cents");

        //check if drink can be afforded
        if (drink.price > funds) {
            System.out.println("Insufficient funds");
        }
        //take price away from funds, reduce drink stock by 1, return change
        else {
            System.out.println("Successfully purchased " + drink.name() + ", dispensing drink...");
            funds = funds - drink.price;
            drinkStock[drink.id] = drinkStock[drink.id] - 1;
            giveChange();
        }
        printStatus();
        return;
    }

    //cancel purchase and return change
    public void cancel() {
        System.out.println("Cancelling purchase");
        giveChange();
        printStatus();
    }

    private void printStatus() {

        System.out.println("The machine currently has ");

        //iterate through all drink types, get amount of each in stock by ID
        Drink[] drinks = Drink.values();

        for (Drink drink : drinks) {
            System.out.print(drink.name() + ": " + drinkStock[drink.id] + " | ");

        }

        System.out.println("\nand is holding ");

        //iterate through all coin types, get amount of each in stock by ID
        Coin[] coins = Coin.values();

        for (Coin coin : coins) {
            System.out.print(coinStock[coin.id] + " " + coin.name() + "S | ");
        }
        System.out.println("\nYou have a total balance of " + funds + " cents remaining\n");

    }

    private void giveChange() {

        //stop if there are no funds
        if (funds == 0) {
            System.out.println("No funds to return!");
            return;
        }

        System.out.println("Returning balance of " + funds + " cents");

        //record amount of funds
        int oldfunds = funds;

        Coin[] coins = Coin.values();

        //array of returned coins, same ID system as machine's coins
        int[] returnedCoins = { 0, 0, 0 };

        //greedy algorithm to loop through each coin from biggest to smallest and deducting
        //as many as possible from the funds
        for (Coin coin : coins) {

            //only if fund is greater than or equal to the coin and coin is in stock
            while (funds - coin.amt >= 0 && coinStock[coin.id] > 0) {
                funds = funds - coin.amt;
                coinStock[coin.id] = coinStock[coin.id] - 1;
                returnedCoins[coin.id] = returnedCoins[coin.id] + 1;
            }

        }

        //calculate total value of returned coins
        int returnedSum = getSum(returnedCoins[Coin.QUARTER.id], returnedCoins[Coin.DIME.id],
                returnedCoins[Coin.NICKEL.id]);

        //if not equal with original amount of funds, machine must have run out of coins
        if (returnedSum != oldfunds) {

            System.out.print("Out of coins...returned ");
        }
        else {
            //otherwise returning proper amount
            System.out.print("in: ");

        }
        for (Coin coin : coins) {
            //lists all coins that were returned, whether they sum to total owed to customer or not
            if (returnedCoins[coin.id] != 0)
                System.out.print(returnedCoins[coin.id] + " " + coin.name() + "S ");
        }

        if (returnedSum == 0) {
            //print if no coins were returned and should have been
            System.out.println("nothing");
        }
        System.out.println();

    }

    //calculate sum of coins
    private int getSum(int nQ, int nD, int nN) {
        return (Coin.QUARTER.amt * nQ + Coin.DIME.amt * nD + Coin.NICKEL.amt * nN);
    }

}