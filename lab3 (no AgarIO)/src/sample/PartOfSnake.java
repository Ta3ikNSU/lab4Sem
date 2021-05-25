package sample;

public class PartOfSnake {
    public int prevX, prevY; //предыдущие координаты
    directionMove myDir;
    directionMove predDir; //предыдущее направление
    GameModel gameModel;
    boolean isAmaOnTurn = false; // я поворачиваюсь?
    private int x, y; //кординаты местоположения

    public PartOfSnake(directionMove myDir, int x, int y, GameModel gameModel) {
        this.myDir = myDir;
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.gameModel = gameModel;
        this.gameModel.setSnake(x, y); //помечаем в мировых координатах клетку, как занятую
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(PartOfSnake next, boolean isTail) {
        if (myDir == directionMove.UP) {
            y--;
            if (y < 0) {
                y = gameModel.height - 1;
            }
        } else if (myDir == directionMove.DOWN) {
            y++;
            if (y >= gameModel.height) {
                y = 0;
            }
        } else if (myDir == directionMove.LEFT) {
            x--;
            if (x < 0) {
                x = gameModel.width - 1;
            }
        } else if (myDir == directionMove.RIGHT) {
            x++;
            if (x >= gameModel.width) {
                x = 0;
            }
        }
        if (next != null) {
            if (next.myDir != myDir) {
                isAmaOnTurn = true;
            } else {
                isAmaOnTurn = false;
            }
            predDir = myDir;
            myDir = next.myDir;
        }
    }

    public double getRotate(boolean IsAmaTail) {
        if (!isAmaOnTurn || IsAmaTail) {
            switch (myDir) {
                case UP:
                    return 0;
                case RIGHT:
                    return 90;
                case DOWN:
                    return 180;
                case LEFT:
                    return 270;
                default:
                    return 0;
            }
        } else { //ниже обработка поворотов для частей являющихся туловищем, когда змейка поворачивает в ту или иную сторону
            if (myDir == directionMove.RIGHT && predDir == directionMove.UP) {
                return 0;
            } else if (myDir == directionMove.LEFT && predDir == directionMove.UP) {
                return 90;
            } else if (myDir == directionMove.LEFT && predDir == directionMove.DOWN) {
                return 180;
            } else if (myDir == directionMove.RIGHT && predDir == directionMove.DOWN) {
                return 270;
            } else if (myDir == directionMove.UP && predDir == directionMove.RIGHT) {
                return 180;
            } else if (myDir == directionMove.DOWN && predDir == directionMove.RIGHT) {
                return 90;
            } else if (myDir == directionMove.UP && predDir == directionMove.LEFT) {
                return 270;
            } else if (myDir == directionMove.DOWN && predDir == directionMove.LEFT) {
                return 0;
            }
            return 0;
        }
    }

    enum directionMove {
        UP, DOWN, LEFT, RIGHT
    }
}
