import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class GameState implements Serializable {
    private String location = "Starting Area";
    private int playerHealth = 100;
    private String playerName;
    private String playerClass;
    private ArrayList<String> inventory = new ArrayList<>();
    private Stack<String> previousLocations = new Stack<>();

    public void initializePlayer(String name, String playerClass) {
        this.playerName = name;
        this.playerClass = playerClass;
        System.out.println("Welcome, " + playerName + " the " + playerClass + "!");
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int health) {
        this.playerHealth = health;
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void addItemToInventory(String item) {
        inventory.add(item);
    }

    public boolean useItem(String item) {
        if (inventory.contains(item)) {
            inventory.remove(item);
            if (item.equals("Health Potion")) {
                playerHealth = Math.min(playerHealth + 20, 100);
                System.out.println("You regained health! " + "Your health is now: " + playerHealth);
            }
            return true;
        }
        return false;
    }

    public void saveState() {
        previousLocations.push(location);
    }

    public void undo() {
        if (!previousLocations.isEmpty()) {
            location = previousLocations.pop();
            System.out.println("Undo successful! Back at: " + location);
        } else {
            System.out.println("No actions to undo.");
        }
    }
}
