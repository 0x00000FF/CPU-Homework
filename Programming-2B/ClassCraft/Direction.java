public enum Direction {
    left(1),
    right(2),
    up(3),
    down(4);

    private int direction;

    Direction(int _direction) {
        direction = _direction;
    }

    public int getDirection() {
        return direction;
    }
}
