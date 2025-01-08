package io.rba.raymob;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Ray {
    private Cenario map;
    private Player player;
    private double rayAngle; // Ângulo do raio

    public Ray(Cenario map, Player player, double rayAngle) {
        this.map = map;
        this.player = player;
        this.rayAngle = rayAngle;
    }

    public double[] cast() {
        double interceptX, interceptY;
        double stepX, stepY;
        double startX = player.getX();
        double startY = player.getY();
        this.rayAngle += player.getTurnAngle();

        boolean down = rayAngle > 0 && rayAngle < Math.PI;
        boolean left = rayAngle > Math.PI / 2 && rayAngle < 3 * Math.PI / 2;

        // Configuração do tamanho do tile
        int tileSize = 60;

        // Interseções horizontais
        boolean hitHorizontal = false;
        interceptY = Math.floor(startY / tileSize) * tileSize;
        if (down) {
            interceptY += tileSize;
        }
        interceptX = startX + (interceptY - startY) / Math.tan(rayAngle);

        stepY = down ? tileSize : -tileSize;
        stepX = stepY / Math.tan(rayAngle);

        double nextXH = interceptX;
        double nextYH = interceptY;
        double wallHitXHorizontal = 0, wallHitYHorizontal = 0;

        while (!hitHorizontal) {
            int gridX = (int) Math.floor(nextXH / tileSize);
            int gridY = down ? (int) Math.floor(nextYH / tileSize) : (int) Math.floor((nextYH - 1) / tileSize);

            if (gridX < 0 || gridY < 0 || gridX >= map.getWidth() || gridY >= map.getHeight() || map.getMatriz()[gridY][gridX] == 1) {
                hitHorizontal = true;
                wallHitXHorizontal = nextXH;
                wallHitYHorizontal = nextYH;
            } else {
                nextXH += stepX;
                nextYH += stepY;
            }
        }

        // Interseções verticais
        boolean hitVertical = false;
        interceptX = Math.floor(startX / tileSize) * tileSize;
        if (!left) {
            interceptX += tileSize;
        }
        interceptY = startY + (interceptX - startX) * Math.tan(rayAngle);

        stepX = left ? -tileSize : tileSize;
        stepY = stepX * Math.tan(rayAngle);

        double nextXV = interceptX;
        double nextYV = interceptY;
        double wallHitXVertical = 0, wallHitYVertical = 0;

        while (!hitVertical) {
            int gridX = left ? (int) Math.floor((nextXV - 1) / tileSize) : (int) Math.floor(nextXV / tileSize);
            int gridY = (int) Math.floor(nextYV / tileSize);

            if (gridX < 0 || gridY < 0 || gridX >= map.getWidth() || gridY >= map.getHeight() || map.getMatriz()[gridY][gridX] == 1) {
                hitVertical = true;
                wallHitXVertical = nextXV;
                wallHitYVertical = nextYV;
            } else {
                nextXV += stepX;
                nextYV += stepY;
            }
        }

        // Escolher a interseção mais próxima
        double distHorizontal = Math.hypot(wallHitXHorizontal - startX, wallHitYHorizontal - startY);
        double distVertical = Math.hypot(wallHitXVertical - startX, wallHitYVertical - startY);

        if (distHorizontal < distVertical) {
            return new double[]{wallHitXHorizontal, wallHitYHorizontal};
        } else {
            return new double[]{wallHitXVertical, wallHitYVertical};
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        double[] endPoint = cast();
        shapeRenderer.line((float) player.getX(), (float) player.getY(), (float) endPoint[0], (float) endPoint[1]);
    }
}
