package io.rba.raymob;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

public class Cenario {
    private int[][] matriz = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public Cenario() {
        // Initialize any additional resources if needed
    }

    public int[][] getMatriz() {
        return this.matriz;
    }

    public int getWidth() {
        return this.matriz[0].length;
    }

    public int getHeight() {
        return this.matriz.length;
    }

    public void drawMap(SpriteBatch batch) {
        ShapeRenderer shapeRenderer = new ShapeRenderer(); // Create a ShapeRenderer instance
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                if (matriz[y][x] == 1) {
                    shapeRenderer.setColor(Color.DARK_GRAY); // Wall color
                } else {
                    shapeRenderer.setColor(Color.LIGHT_GRAY); // Floor color
                }

                // Draw each tile
                shapeRenderer.rect(
                    x * 60, 
                    Gdx.graphics.getHeight() - (y + 1) * 60,
                    60, 60
                );
            }
        }

        shapeRenderer.end();
    }
}
