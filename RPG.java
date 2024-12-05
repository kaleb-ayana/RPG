import java.util.Scanner;

public class RPG {
    private static GameState gameState = new GameState();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("Welcome to Advanced RPG!");

        // Character creation
        System.out.print("Enter your character's name: ");
        String playerName = scanner.nextLine();
        System.out.println("Choose your class: Warrior or Mage");
        String playerClass = scanner.nextLine().toLowerCase();

        gameState.initializePlayer(playerName, playerClass);

        while (isRunning) {
            System.out.println("\nLocation: " + gameState.getLocation());
            System.out.println("Health: " + gameState.getPlayerHealth());
            System.out.println("Inventory: " + gameState.getInventory());
            System.out.println("Commands: move, search, fight, inventory, save, load, undo, exit");
            System.out.print("What would you like to do? ");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "move":
                    System.out.print("Enter a new location: ");
                    String newLocation = scanner.nextLine();
                    gameState.saveState(); // Save state for undo
                    gameState.setLocation(newLocation);
                    handleLocation(newLocation);
                    break;
                case "search":
                    handleSearch();
                    break;
                case "fight":
                    handleFight();
                    break;
                case "inventory":
                    handleInventory(scanner);
                    break;
                case "save":
                    SaveManager.saveGame(gameState);
                    break;
                case "load":
                    gameState = SaveManager.loadGame();
                    break;
                case "undo":
                    gameState.undo();
                    break;
                case "exit":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
                    break;
            }
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static void handleLocation(String location) {
        if (location.equalsIgnoreCase("secret cave")) {
            System.out.println("You found the Secret Cave! A hidden treasure appears!");
            gameState.addItemToInventory("Golden Sword");
        } else {
            System.out.println("You moved to " + location + ".");
        }
    }

    private static void handleSearch() {
        System.out.println("You search the area and find a health potion!");
        gameState.addItemToInventory("Health Potion");
    }

    private static void handleFight() {
        System.out.println("An enemy appears!");
        if (Math.random() > 0.5) {
            System.out.println("You defeated the enemy and found a treasure!");
            gameState.addItemToInventory("Silver Shield");
        } else {
            System.out.println("You were injured during the fight!");
            gameState.setPlayerHealth(gameState.getPlayerHealth() - 10);
        }
    }

    private static void handleInventory(Scanner scanner) {
        System.out.println("Your Inventory: " + gameState.getInventory());
        System.out.println("Type 'use <item>' to use an item or 'back' to go back.");
        String input = scanner.nextLine();
        if (input.startsWith("use ")) {
            String item = input.substring(4);
            if (gameState.useItem(item)) {
                System.out.println("You used the " + item + ".");
            } else {
                System.out.println("Item not found.");
            }
        }
    }
}
