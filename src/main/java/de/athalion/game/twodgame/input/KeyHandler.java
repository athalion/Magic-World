package de.athalion.game.twodgame.input;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.sound.Sound;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public ControllerManager controllers = new ControllerManager();

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        controllers.initSDLGamepad();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        KeyState keyState = new KeyState()
                .setMenuUpPressed(code == KeyEvent.VK_W)
                .setMenuDownPressed(code == KeyEvent.VK_S)
                .setMenuLeftPressed(code == KeyEvent.VK_A)
                .setMenuRightPressed(code == KeyEvent.VK_D)
                .setMenuOKPressed(code == KeyEvent.VK_ENTER)
                .setMenuBackPressed(code == KeyEvent.VK_ESCAPE)
                .setMoveUpPressed(code == KeyEvent.VK_W)
                .setMoveDownPressed(code == KeyEvent.VK_S)
                .setMoveLeftPressed(code == KeyEvent.VK_A)
                .setMoveRightPressed(code == KeyEvent.VK_D)
                .setAttackPressed(code == KeyEvent.VK_ENTER)
                .setInventoryKeyPressed(code == KeyEvent.VK_E)
                .setEscapePressed(code == KeyEvent.VK_ESCAPE);
        processInput(keyState, e);
    }

    public void checkControllerInput() {

        ControllerState currentState = controllers.getState(0);

        if (currentState.isConnected) {

            KeyState keyState = new KeyState()
                    .setMenuUpPressed(currentState.dpadUpJustPressed)
                    .setMenuDownPressed(currentState.dpadDownJustPressed)
                    .setMenuLeftPressed(currentState.dpadLeftJustPressed)
                    .setMenuRightPressed(currentState.dpadRightJustPressed)
                    .setMenuOKPressed(currentState.aJustPressed)
                    .setMenuBackPressed(currentState.bJustPressed)
                    .setMoveUpPressed(currentState.leftStickY >= 0.7f)
                    .setMoveDownPressed(currentState.leftStickY <= -0.7f)
                    .setMoveLeftPressed(currentState.leftStickX <= -0.7f)
                    .setMoveRightPressed(currentState.leftStickX >= 0.7f)
                    .setAttackPressed(currentState.aJustPressed)
                    .setInventoryKeyPressed(currentState.xJustPressed)
                    .setEscapePressed(currentState.startJustPressed);
            processInput(keyState, null);

        }

    }

    private void processInput(KeyState keyState, KeyEvent e) {
        //logo state
        if (gamePanel.gameState == GameState.LOGO) {
            logoState(keyState);
        }

        //title state
        if (gamePanel.gameState == GameState.TITLE) {
            titleState(keyState, e);
        }

        //play state
        else if (gamePanel.gameState == GameState.PLAY) {
            playState(keyState);
        }

        //pause state
        else if (gamePanel.gameState == GameState.PAUSE) {
            pauseState(keyState);
        }

        //dialog state
        else if (gamePanel.gameState == GameState.DIALOG) {
            dialogState(keyState);
        }

        //message state
        else if (gamePanel.gameState == GameState.MESSAGE) {
            messageState(keyState);
        }

        //inventory state
        else if (gamePanel.gameState == GameState.INVENTORY) {
            inventoryState(keyState);
        }

        //cutScene state
        else if (gamePanel.gameState == GameState.CUTSCENE) {
            cutSceneState(keyState);
        }
    }

    private void cutSceneState(KeyState keyState) {

        if (keyState.isMenuOKPressed()) {
            enterPressed = true;
        }

    }

    private void logoState(KeyState keyState) {

        if (keyState.isEscapePressed()) {
            gamePanel.logoTimer = 900;
            gamePanel.stopSoundEffect();
        }

    }

    private void titleState(KeyState keyState, KeyEvent e) {
        gamePanel.ui.menuManager.acceptInput(keyState, e);
    }

    public void playState(KeyState keyState) {

        if (keyState.isMoveUpPressed()) {
            upPressed = true;
        }
        if (keyState.isMoveLeftPressed()) {
            leftPressed = true;
        }
        if (keyState.isMoveDownPressed()) {
            downPressed = true;
        }
        if (keyState.isMoveRightPressed()) {
            rightPressed = true;
        }
        if (keyState.isEscapePressed()) {
            gamePanel.gameState = GameState.PAUSE;
        }
        if (keyState.isInventoryKeyPressed()) {
            gamePanel.gameState = GameState.INVENTORY;
        }
        if (keyState.isAttackPressed()) {
            enterPressed = true;
        }

    }

    public void pauseState(KeyState keyState) {

        if (keyState.isEscapePressed()) {
            gamePanel.gameState = GameState.PLAY;
        }

    }

    public void dialogState(KeyState keyState) {

        if (keyState.isMenuOKPressed()) {
            enterPressed = true;
        }
        if (keyState.isMenuUpPressed()) {
            gamePanel.ui.commandNum--;
            if (gamePanel.ui.commandNum < 0) gamePanel.ui.commandNum = gamePanel.ui.responses - 1;
        }
        if (keyState.isMenuDownPressed()) {
            gamePanel.ui.commandNum++;
            if (gamePanel.ui.commandNum > gamePanel.ui.responses - 1) gamePanel.ui.commandNum = 0;
        }

    }

    public void messageState(KeyState keyState) {

        if (keyState.isMenuOKPressed()) {
            enterPressed = true;
        }

    }

    public void inventoryState(KeyState keyState) {

        if (keyState.isInventoryKeyPressed()) {
            gamePanel.gameState = GameState.PLAY;
        }
        if (keyState.isMenuUpPressed()) {
            if (gamePanel.ui.slotRow != 0) {
                gamePanel.ui.slotRow--;
                gamePanel.playSoundEffect(Sound.EFFECT_CURSOR);
            }
        }
        if (keyState.isMenuLeftPressed()) {
            if (gamePanel.ui.slotCol != 0) {
                gamePanel.ui.slotCol--;
                gamePanel.playSoundEffect(Sound.EFFECT_CURSOR);
            }
        }
        if (keyState.isMenuDownPressed()) {
            if (gamePanel.ui.slotRow != 3) {
                gamePanel.ui.slotRow++;
                gamePanel.playSoundEffect(Sound.EFFECT_CURSOR);
            }
        }
        if (keyState.isMenuRightPressed()) {
            if (gamePanel.ui.slotCol != 4) {
                gamePanel.ui.slotCol++;
                gamePanel.playSoundEffect(Sound.EFFECT_CURSOR);
            }
        }
        if (keyState.isMenuOKPressed()) {
            gamePanel.player.selectItem();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }

    public void quitGamepad() {
        controllers.quitSDLGamepad();
    }

}
