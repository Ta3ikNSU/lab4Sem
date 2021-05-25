package sample;

import java.util.ArrayList;

public class PlayerSnake {
    ArrayList<PartOfSnake> parts;

    GameModel gameModel;

    public PlayerSnake(GameModel gameModel, int headX, int headY, String directionHead) {
        parts = new ArrayList<>(3);

        PartOfSnake.directionMove dir = getDirectionFromString(directionHead);

        parts.add(new PartOfSnake(dir, headX, headY, gameModel));
        int newX = headX;
        int newY = headY;
        if (dir == PartOfSnake.directionMove.UP) {
            headY++;
            if (headY >= gameModel.height) {
                headY = 0;
            }
            newY = headY + 1;
            if (newY >= gameModel.height) {
                newY = 0;
            }
            System.out.println(newY);
        } else if (dir == PartOfSnake.directionMove.LEFT) {
            headX++;
            if (headX >= gameModel.width) {
                headX = 0;
            }
            newX = headX + 1;
            if (newX >= gameModel.width) {
                newX = 0;
            }
        } else if (dir == PartOfSnake.directionMove.RIGHT) {
            headX--;
            if (headX < 0) {
                headX = gameModel.width - 1;
            }
            newX = headX - 1;
            if (newX < 0) {
                newX = gameModel.width - 1;
            }
        } else if (dir == PartOfSnake.directionMove.DOWN) {
            headY--;
            if (headY < 0) {
                headY = gameModel.height - 1;
            }
            newY = headY - 1;
            if (newY < 0) {
                newY = gameModel.height - 1;
            }
        }
        parts.add(new PartOfSnake(dir, headX, headY, gameModel));
        parts.add(new PartOfSnake(dir, newX, newY, gameModel));
        this.gameModel = gameModel;
    }

    private PartOfSnake.directionMove getDirectionFromString(String directionString) {
        if (directionString.equals("UP")) {
            return PartOfSnake.directionMove.UP;
        } else if (directionString.equals("DOWN")) {
            return PartOfSnake.directionMove.DOWN;
        } else if (directionString.equals("LEFT")) {
            return PartOfSnake.directionMove.LEFT;
        } else if (directionString.equals("RIGHT")) {
            return PartOfSnake.directionMove.RIGHT;
        } else {
            throw new RuntimeException("Error level setting.");
        }
    }

    public void logicProc() {
        for (int i = parts.size() - 1; i >= 0; i--) {
            if (i != 0 && i != parts.size() - 1) {
                parts.get(i).move(parts.get(i - 1), false);
            } else if (i == parts.size() - 1) {
                parts.get(i).move(parts.get(i - 1), true);
            } else {
                parts.get(i).move(null, false);
            }
        }
    }

}
