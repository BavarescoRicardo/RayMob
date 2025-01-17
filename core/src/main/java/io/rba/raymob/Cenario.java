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
        {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private int tileSize;

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public Cenario() {
        // Initialize any additional resources if needed
    }

    public int[][] getMatriz() {
        // return this.matriz;
        return this.invertMatriz(this.matriz);
    }

    private int[][] invertMatriz(int[][] originalMatriz){
        int rows = originalMatriz.length; // Número de linhas
        int cols = originalMatriz[0].length; // Número de colunas
        int[][] invertedMatriz = new int[rows][cols]; // Nova matriz com linhas invertidas
    
        // Inverte a ordem das linhas
        for (int i = 0; i < rows; i++) {
            invertedMatriz[i] = originalMatriz[rows - 1 - i];
        }
    
        return invertedMatriz;       
    }

    public int getWidth() {
        return this.matriz[0].length;
    }

    public int getHeight() {
        return this.matriz.length;
    }

    public void drawMap(SpriteBatch batch) {
        float tileWidth = (float) Gdx.graphics.getWidth() / getWidth();
        float tileHeight = (float) Gdx.graphics.getHeight() / getHeight();
        this.setTileSize((int) tileHeight);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                if (matriz[y][x] == 1) {
                    shapeRenderer.setColor(Color.DARK_GRAY); // Cor das paredes
                } else {
                    shapeRenderer.setColor(Color.LIGHT_GRAY); // Cor do chão
                }
    
                shapeRenderer.rect(
                    x * tileWidth,
                    Gdx.graphics.getHeight() - (y + 1) * tileHeight,
                    tileWidth, tileHeight
                );
            }
        }
    
        shapeRenderer.end();
    }
}
