package game;

public class MediumHexagonStrategy implements HexagonStrategy {
    @Override
    public void setupUpHexagon(HexagonController hex) {
        for (int y = -4; y <= 4; y++) {
            for (int x = -4; x <= 4; x++) {
                if(!((y == 0 && x == 0) || (y == -4 && Math.abs(x) >= 1)
                        || (y == -4 && Math.abs(x) >= 1)
                        || (y == -3 && Math.abs(x) >= 3) ||(y == 4 && Math.abs(x)>= 2)
                        || (Math.abs(x) == 4 && y == 3))) {
                    hex.positionBubble(x, y);
                }
            }
        }
    }
}
