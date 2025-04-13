import input.InputHandler;
import ui.GameWindow;

public class Main {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        GameWindow gameWindow = new GameWindow(inputHandler);
        gameWindow.setVisible(true);
    }
}