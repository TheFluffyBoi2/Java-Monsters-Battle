import database.DatabaseInitializer;
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
        GameWindow gameWindow = new GameWindow(inputHandler);
        gameWindow.setVisible(true);
    }
}
