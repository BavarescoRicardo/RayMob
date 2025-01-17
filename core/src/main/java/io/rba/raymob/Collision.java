package io.rba.raymob;

public class Collision {
    public Collision() {
    }

    /**
     * Verifica se há uma colisão em uma posição específica no mapa.
     * Considera uma margem de segurança para evitar que o jogador atravesse paredes.
     *
     * @param x    Coordenada X do jogador (em pixels).
     * @param y    Coordenada Y do jogador (em pixels).
     * @param map  Objeto Cenario que contém a matriz do mapa e o tamanho dos tiles.
     * @return     true se houver colisão, false caso contrário.
     */
    public boolean colide(int gridX, int gridY, Cenario map) {

        // Verificar limites do mapa
        if (gridX < 0 || gridX >= map.getWidth() || gridY < 0 || gridY >= map.getHeight()) {
            return true; // Fora do mapa é considerado colisão
        }

        // Verificar colisão com paredes
        if (map.getMatriz()[gridY][gridX] == 1) {
            return true; // Colisão com uma parede
        }

        return false;
    }

}
