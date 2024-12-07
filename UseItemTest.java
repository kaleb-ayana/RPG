import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class UseItemTest {
  private GameState gameState;

  @Before
  public void setUp() {
    gameState = new GameState();
    gameState.initializePlayer("Gandalf", "Mage");
  }


  @Test
  public void testUseItem() {
    gameState.addItemToInventory("Health Potion");

    gameState.setPlayerHealth(80);

    gameState.useItem("Health Potion");

    assertEquals(100, gameState.getPlayerHealth());
  }

}
