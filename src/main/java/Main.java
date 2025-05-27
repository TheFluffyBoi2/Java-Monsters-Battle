import database.DatabaseInitializer;
import entities.Player.Player;
import input.InputHandler;
import ui.GameWindow;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
        } catch (Exception e) {
            System.out.println("Eroare la initializarea bazei de date");
        }
        InputHandler inputHandler = new InputHandler();
        Player player = new Player();
        GameWindow gameWindow = new GameWindow(inputHandler);
        gameWindow.setVisible(true);
    }
}
