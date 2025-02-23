package de.athalion.game.twodgame.event;

import de.athalion.game.twodgame.main.Direction;
import de.athalion.game.twodgame.main.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {

    GamePanel gamePanel;
    List<Event> eventList = new ArrayList<>();

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkEvent() {

        eventList.forEach(event -> {

            //check if player is more than 1 tile from event away
            int xDistance = Math.abs(gamePanel.player.worldX - event.getWorldX());
            int yDistance = Math.abs(gamePanel.player.worldY - event.getWorldY());
            int distance = Math.max(xDistance, yDistance);
            if (distance > gamePanel.tileSize) event.canTrigger = true;

            if (hit(event)) event.trigger();

        });

    }

    public boolean hit(Event event) {

        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        if (gamePanel.player.solidArea.intersects(event.getEventRectangle())) {
            if (gamePanel.player.direction.equals(event.getRequiredDirection()) || event.requiredDirection.equals(Direction.ANY)) {
                hit = true;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return hit;

    }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEvents() {
        return eventList;
    }

}
