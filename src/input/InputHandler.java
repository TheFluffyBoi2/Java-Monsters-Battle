package input;

import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import ui.KeyEventObserver;

public class InputHandler implements KeyListener {
    private final List<KeyEventObserver> observers = new ArrayList<>();

    public void addObserver(KeyEventObserver window) {
        observers.add(window);
    }

    public void removeObserver(KeyEventObserver window) {
        observers.remove(window);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for (KeyEventObserver observer : observers) {
            observer.onKeyEvent(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
