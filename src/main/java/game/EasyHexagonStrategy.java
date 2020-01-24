package game;

public class EasyHexagonStrategy implements HexagonStrategy {

    @Override
    public void setupUpHexagon(HexagonController hex) {
        for (int y = -2; y <= 2; y++) {
            for (int x = -2; x <= 2; x++) {
                boolean shouldPlace = !(Math.abs(y) == 2 && Math.abs(x) == 2)
                        && !(y == -2 && Math.abs(x) == 1)
                        && !(y == 0 && x == 0);
                if (shouldPlace) {
                    hex.positionBubble(x, y);
                }
            }
        }
    }
}
