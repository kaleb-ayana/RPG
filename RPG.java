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
                    System.out.print("Enter a new location: forest, mountain, or village: ");
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
        switch (location.toLowerCase()) {
            case "forest":
                System.out.println("You have arrived at the dark forest and stand before a giant who tells you to follow the spiders and speak with the queen");
                if(Math.random() > 0.5) {
                    System.out.println("The spider queen bestows upon you some new loot");
                    gameState.addItemToInventory("Sticky Net");
                } else {
                    System.out.println("Your presence is displeasing to the queen and she orders her spiders to attack");
                    gameState.setPlayerHealth(gameState.getPlayerHealth() - 20);
                }
                break;
            case "mountain":
                System.out.println("You have arrived at the top of the lonely mountain");
                if(Math.random() > 0.5) {
                    System.out.println("You stumble upon an empty cave filled with riches");
                    gameState.addItemToInventory("Golden Chestplate");
                } else {
                    System.out.println("You stubmle into a cave filled with gold only to find a might dragon protecting the riches");
                    gameState.setPlayerHealth(gameState.getPlayerHealth() - 30);
                }
                break;
            case "village":
            System.out.println("You have arrived at a nearby village");  
            if(Math.random() > 0.5) {
                System.out.println("The villagers greet you with a feast and send you on your way with additional supplies");
                gameState.addItemToInventory("Food");
                gameState.addItemToInventory("Water");
            } else {
                System.out.println("The villagers are wary of an outsider and chase you away with pitchforks");
                gameState.setPlayerHealth(gameState.getPlayerHealth() - 20);
            }       
            default:
            System.out.println("You moved to " + location);
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
