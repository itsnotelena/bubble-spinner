package game;

public class HardHexagonStrategy implements HexagonStrategy {
    @Override
    public void setupUpHexagon(HexagonController hex) {
        for (int y = -6; y <= 6; y++) {
            for (int x = -6; x <= 6; x++) {
                if (!((x == 0 && y == 0)
                        || (y == -6 && Math.abs(x) >= 1)
                        ||(y == -5 && Math.abs(x) >= 3)
                        ||(y == -4 && Math.abs(x) >= 5)
                        ||(y == 4 && Math.abs(x) >= 6)
                        ||(y == 5 && Math.abs(x) >= 4) || (y == 6 && Math.abs(x) >= 2))) {

                    hex.positionBubble(x, y);
                }
            }
        }
    }
}
