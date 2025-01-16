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
    public boolean colide(double x, double y, Cenario map) {
        int tileSize = map.getTileSize();
        int gridX = (int) Math.floor(x / tileSize);
        int gridY = (int) Math.floor(y / tileSize);

        // Verificar limites do mapa
        if (gridX < 0 || x >= map.getWidth() || gridY < 0 || y >= map.getHeight()) {
            return true; // Fora do mapa é considerado colisão
        }

        // Verificar colisão com paredes
        if (map.getMatriz()[gridY][gridX] == 1) {
            return true; // Colisão com uma parede
        }

        // Margem de segurança para evitar "travar" em quinas ou atravessar paredes
        double margin = 4.0; // Tamanho da margem (ajustável)

        // Verificar colisões ao redor do jogador
        boolean collisionTopLeft = isWall(x - margin, y - margin, map);
        boolean collisionTopRight = isWall(x + margin, y - margin, map);
        boolean collisionBottomLeft = isWall(x - margin, y + margin, map);
        boolean collisionBottomRight = isWall(x + margin, y + margin, map);

        return collisionTopLeft || collisionTopRight || collisionBottomLeft || collisionBottomRight;
    }

    /**
     * Verifica se uma posição específica (em pixels) corresponde a uma parede no mapa.
     *
     * @param x    Coordenada X em pixels.
     * @param y    Coordenada Y em pixels.
     * @param map  Objeto Cenario que contém a matriz do mapa e o tamanho dos tiles.
     * @return     true se a posição for uma parede, false caso contrário.
     */
    private boolean isWall(double x, double y, Cenario map) {
        int tileSize = map.getTileSize();
        int gridX = (int) Math.floor(x / tileSize);
        int gridY = (int) Math.floor(y / tileSize);

        // Verificar se a posição está dentro dos limites do mapa
        if (gridX < 0 || gridX >= map.getWidth() || gridY < 0 || gridY >= map.getHeight()) {
            return true; // Fora do mapa é considerado colisão
        }

        return map.getMatriz()[gridY][gridX] == 1; // Verificar se é uma parede
    }
}
