package io.rba.raymob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Ray {
    private Cenario cenario; // Reference to the map
    private Player player; // Reference to the player
    private float rayAngle; // Ray angle in radians
    private float distance; // Distance to the wall
    private Texture wallTexture; // Texture for walls

    public Ray(Cenario cenario, Player player, float rayAngle, Texture wallTexture) {
        this.cenario = cenario;
        this.player = player;
        this.rayAngle = rayAngle;
        this.wallTexture = wallTexture;
    }

    public void setRayAngle(float rayAngle) {
        this.rayAngle = rayAngle;
    }

    public void wallRender3d(SpriteBatch spriteBatch, int columnIndex) {
        double[] wallHit = cast();
        double distance = Math.hypot(wallHit[0] - player.getX(), wallHit[1] - player.getY());
    
        // Debug: Log distance
        // Gdx.app.log("Ray", "Distance: " + distance);
    
        double correctedDistance = distance * Math.cos(rayAngle - Math.toRadians(player.getTurnAngle()));
        if (Double.isNaN(correctedDistance) || Double.isInfinite(correctedDistance)) {
            Gdx.app.error("Ray", "Invalid correctedDistance: " + correctedDistance);
            return;
        }
    
        double projectionPlaneDistance = (Gdx.graphics.getWidth() / 2.0) / Math.tan(Math.toRadians(60) / 2.0);
        double wallHeight = (cenario.getTileSize() / correctedDistance) * projectionPlaneDistance;
    
        // Debug: Log wall height
        // Gdx.app.log("Ray", "Wall Height: " + wallHeight);
    
        float columnWidth = Gdx.graphics.getWidth() / 60.0f;
        float columnX = columnIndex * columnWidth;
        float columnY = (float) ((Gdx.graphics.getHeight() / 2.0) - (wallHeight / 2.0));
    
        // Debug: Log column position
        // Gdx.app.log("Ray", "Column X: " + columnX + ", Column Y: " + columnY);
    
        spriteBatch.draw(
            wallTexture,
            columnX,           // X inicial
            columnY,           // Y inicial
            columnWidth,       // Largura da coluna
            (float) wallHeight // Altura da coluna
        );
    }

    public double[] cast() {
        double interceptX, interceptY;
        double stepX, stepY;
        double startX = player.getX();
        double startY = player.getY();
        this.rayAngle += player.getTurnAngle();

        double normalizedRayAngle = rayAngle % (2 * Math.PI);
        if (normalizedRayAngle < 0) {
            normalizedRayAngle += 2 * Math.PI;
        }

        boolean down = normalizedRayAngle > 0 && normalizedRayAngle < Math.PI;
        boolean left = normalizedRayAngle > Math.PI / 2 && normalizedRayAngle < 3 * Math.PI / 2;

        // Configuração do tamanho do tile
        int tileSize = cenario.getTileSize();
        // int tileSize = 60;

        // Interseções horizontais
        boolean hitHorizontal = false;
        interceptY = Math.floor(startY / tileSize) * tileSize;
        if (down) {
            interceptY += tileSize;
        }
        interceptX = startX + (interceptY - startY) / Math.tan(normalizedRayAngle);

        stepY = down ? tileSize : -tileSize;
        stepX = stepY / Math.tan(normalizedRayAngle);

        double nextXH = interceptX;
        double nextYH = interceptY;
        double wallHitXHorizontal = 0, wallHitYHorizontal = 0;

        while (!hitHorizontal) {
            int gridX = (int) Math.floor(nextXH / tileSize);
            int gridY = down ? (int) Math.floor(nextYH / tileSize) : (int) Math.floor((nextYH - 1) / tileSize);

            if (gridX < 0 || gridY < 0 || gridX >= cenario.getWidth() || gridY >= cenario.getHeight() || cenario.getMatriz()[gridY][gridX] == 1) {
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
        interceptY = startY + (interceptX - startX) * Math.tan(normalizedRayAngle);

        stepX = left ? -tileSize : tileSize;
        stepY = stepX * Math.tan(normalizedRayAngle);

        double nextXV = interceptX;
        double nextYV = interceptY;
        double wallHitXVertical = 0, wallHitYVertical = 0;

        while (!hitVertical) {
            int gridX = left ? (int) Math.floor((nextXV - 1) / tileSize) : (int) Math.floor(nextXV / tileSize);
            int gridY = (int) Math.floor(nextYV / tileSize);

            if (gridX < 0 || gridY < 0 || gridX >= cenario.getWidth() || gridY >= cenario.getHeight() || cenario.getMatriz()[gridY][gridX] == 1) {
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
}