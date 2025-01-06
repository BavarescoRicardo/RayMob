package io.rba.raymob;

public class Collision {
    public Collision() {

    }

    public boolean colide(int x, int y, Cenario map) {
        // Valide se x e y estão dentro dos limites do mapa
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeigth()) {
            return true; // Considere como uma colisão fora do mapa
        }
        return map.getMatriz()[y][x] == 1; // Verifique se é uma parede
    }
    
}
