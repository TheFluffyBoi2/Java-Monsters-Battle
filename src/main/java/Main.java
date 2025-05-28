import database.DatabaseInitializer;
import input.InputHandler;
import ui.GameWindow;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputHandler inputHandler = new InputHandler();
        GameWindow gameWindow = new GameWindow(inputHandler);
        gameWindow.setVisible(true);
    }
}
