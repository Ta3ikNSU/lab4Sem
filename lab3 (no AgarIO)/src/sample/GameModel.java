package sample;

public class GameModel {
    public static final int EMPTY = 0;
    public static final int FOOD = 1;
    public static final int WALL = 2;
    public static final int SNAKE = 3;

    public static final int ErrCodeSuccess = 0;
    public static final int ErrCodeStopGame = 1;
    private final Observer cntr;
    public int width;
    public int height;
    int[][] board; //память состояний клеток
    PlayerSnake playerSnake;
    private int countOfFood;

    public GameModel(int width, int height, Observer cntr) {
        this.width = width;
        this.height = height;
        board = new int[width][height];
        this.cntr = cntr;
    }

    public void addPlayerSnake(PlayerSnake playerSnake) {
        this.playerSnake = playerSnake;
    }

    public int workLogic() {
        playerSnake.logicProc();

        return recheckPosSnake(playerSnake); //меняет состояние мира (конкретно, учитывает положение змейки)
    }

    private int recheckPosSnake(PlayerSnake snake) {
        for (int i = 0; i < snake.parts.size(); i++) {
            if (i == 0) { //голова наткнулась на еду
                int state = getState(snake.parts.get(i).getX(), snake.parts.get(i).getY());
                if (state == FOOD) {
                    PartOfSnake pOS = new PartOfSnake(snake.parts.get(snake.parts.size() - 1).predDir, snake.parts.get(snake.parts.size() - 1).prevX, snake.parts.get(snake.parts.size() - 1).prevY, this);
                    snake.parts.add(pOS);
                    cntr.plusAlreadyFoodAndReNew();
                    genFood(); //+ новая еда на поле
                } else if (state == SNAKE || state == WALL) {
                    return ErrCodeStopGame;
                }
            }

            setEmpty(snake.parts.get(i).prevX, snake.parts.get(i).prevY);
            snake.parts.get(i).prevX = snake.parts.get(i).getX();
            snake.parts.get(i).prevY = snake.parts.get(i).getY();

            setSnake(snake.parts.get(i).getX(), snake.parts.get(i).getY());
        }
        return ErrCodeSuccess;
    }

    public int getState(int x, int y) {
        return this.board[x][y];
    }

    public void setFood(int x, int y) {
        this.board[x][y] = FOOD;
    }

    public void setEmpty(int x, int y) {
        this.board[x][y] = EMPTY;
    }

    public void setSnake(int x, int y) {
        this.board[x][y] = SNAKE;
    }

    public void setWall(int x, int y) {
        this.board[x][y] = WALL;
    }

    public void clearWorld() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.board[x][y] = EMPTY;
            }
        }
    }

    public void genFood() { //создаёт еду на поле
        boolean isFreeSpace = false;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (this.board[x][y] == EMPTY) {
                    isFreeSpace = true;
                    break;
                }
            }
            if (isFreeSpace) {
                break;
            }
        }

        if (!isFreeSpace) {
            return;
        }

        int x = Observer.random(0, width);
        int y = Observer.random(0, height);
        boolean shouldFindPlace = true;
        do {
            if (getState(x, y) == EMPTY) {
                shouldFindPlace = false;
                setFood(x, y);
                break;
            } else {
                x++;
                if (x >= width) {
                    x = 0;
                    y++;
                }
                if (y >= height) {
                    y = 0;
                    x = 0;
                }
            }
        } while (shouldFindPlace);
    }

    public int getCountOfFood() {
        return countOfFood;
    }

    public void setCountOfFood(int countOfFood) {
        this.countOfFood = countOfFood;
    }
}
