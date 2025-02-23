package de.athalion.game.twodgame.input;

public class KeyState {

    boolean menuUpPressed = false;
    boolean menuDownPressed = false;
    boolean menuLeftPressed = false;
    boolean menuRightPressed = false;
    boolean menuOKPressed = false;
    boolean menuBackPressed = false;

    boolean moveUpPressed = false;
    boolean moveDownPressed = false;
    boolean moveLeftPressed = false;
    boolean moveRightPressed = false;
    boolean attackPressed = false;

    boolean inventoryKeyPressed = false;

    boolean escapePressed = false;

    public KeyState setMenuUpPressed(boolean menuUpPressed) {
        this.menuUpPressed = menuUpPressed;
        return this;
    }

    public KeyState setMenuDownPressed(boolean menuDownPressed) {
        this.menuDownPressed = menuDownPressed;
        return this;
    }

    public KeyState setMenuLeftPressed(boolean menuLeftPressed) {
        this.menuLeftPressed = menuLeftPressed;
        return this;
    }

    public KeyState setMenuRightPressed(boolean menuRightPressed) {
        this.menuRightPressed = menuRightPressed;
        return this;
    }

    public KeyState setMenuOKPressed(boolean menuOKPressed) {
        this.menuOKPressed = menuOKPressed;
        return this;
    }

    public KeyState setMenuBackPressed(boolean menuBackPressed) {
        this.menuBackPressed = menuBackPressed;
        return this;
    }

    public KeyState setMoveUpPressed(boolean moveUpPressed) {
        this.moveUpPressed = moveUpPressed;
        return this;
    }

    public KeyState setMoveDownPressed(boolean moveDownPressed) {
        this.moveDownPressed = moveDownPressed;
        return this;
    }

    public KeyState setMoveLeftPressed(boolean moveLeftPressed) {
        this.moveLeftPressed = moveLeftPressed;
        return this;
    }

    public KeyState setMoveRightPressed(boolean moveRightPressed) {
        this.moveRightPressed = moveRightPressed;
        return this;
    }

    public KeyState setAttackPressed(boolean attackPressed) {
        this.attackPressed = attackPressed;
        return this;
    }

    public KeyState setInventoryKeyPressed(boolean inventoryKeyPressed) {
        this.inventoryKeyPressed = inventoryKeyPressed;
        return this;
    }

    public KeyState setEscapePressed(boolean escapePressed) {
        this.escapePressed = escapePressed;
        return this;
    }

    public boolean isMenuUpPressed() {
        return menuUpPressed;
    }

    public boolean isMenuDownPressed() {
        return menuDownPressed;
    }

    public boolean isMenuLeftPressed() {
        return menuLeftPressed;
    }

    public boolean isMenuRightPressed() {
        return menuRightPressed;
    }

    public boolean isMenuOKPressed() {
        return menuOKPressed;
    }

    public boolean isMenuBackPressed() {
        return menuBackPressed;
    }

    public boolean isMoveUpPressed() {
        return moveUpPressed;
    }

    public boolean isMoveDownPressed() {
        return moveDownPressed;
    }

    public boolean isMoveLeftPressed() {
        return moveLeftPressed;
    }

    public boolean isMoveRightPressed() {
        return moveRightPressed;
    }

    public boolean isAttackPressed() {
        return attackPressed;
    }

    public boolean isInventoryKeyPressed() {
        return inventoryKeyPressed;
    }

    public boolean isEscapePressed() {
        return escapePressed;
    }

}
