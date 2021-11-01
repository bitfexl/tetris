package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
    private InputCb callback;

    public InputListener(InputCb cb) {
        callback = cb;
    }

    public void keyPressed(KeyEvent e) {
        Input input = switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> Input.LEFT;
            case KeyEvent.VK_RIGHT -> Input.RIGHT;
            case KeyEvent.VK_UP -> Input.ROTATE;
            case KeyEvent.VK_DOWN -> Input.DOWN;
            case KeyEvent.VK_SPACE -> Input.HARD_DROP;
            default -> null;
        };

        if(input != null) {
            callback.cb(input);
        }
    }

    public void keyTyped(KeyEvent e) { }

    public void keyReleased(KeyEvent e) { }
}