package io.rba.raymob;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Ray {
    private double startX, startY; // Posição inicial do raio
    private double directionX, directionY; // Direção do raio
    private Cenario map;

    public Ray(double startX, double startY, double angle, Cenario map) {
        this.startX = startX;
        this.startY = startY;
        this.directionX = Math.cos(angle); // Direção baseada no ângulo
        this.directionY = Math.sin(angle);
        this.map = map;
    }

    public double[] cast() {
        double x = startX;
        double y = startY;

        // Incrementos por grid (usando o passo do raycasting)
        double stepSize = 1.0; // Pode ajustar conforme necessário

        while (true) {
            int gridX = (int) Math.floor(x / 60); // Cada célula do mapa tem tamanho 60
            int gridY = (int) Math.floor(y / 60);

            // Verificar se o raio está fora do mapa
            if (gridX < 0 || gridY < 0 || gridX >= map.getWidth() || gridY >= map.getHeight()) {
                break;
            }

            // Verificar colisão com uma parede
            if (map.getMatriz()[gridY][gridX] == 1) {
                return new double[]{x, y};
            }

            // Avançar o raio
            x += directionX * stepSize;
            y += directionY * stepSize;
        }

        return new double[]{x, y}; // Caso não haja colisão, retorna a borda
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line((float) startX, (float) startY, (float) cast()[0], (float) cast()[1]);
    }
}
