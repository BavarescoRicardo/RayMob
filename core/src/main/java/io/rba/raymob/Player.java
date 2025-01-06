package io.rba.raymob;

public class Player {
    private double x, y; // Posição do jogador
    private double turnAngle; // Ângulo de rotação
    private int move; // Direção de movimento (1 = frente, -1 = trás, 0 = parado)
    private int turn; // Direção de rotação (1 = direita, -1 = esquerda, 0 = parado)
    private Collision collision;
    private Cenario map;

    private final double moveSpeed = 3.0; // Velocidade de movimento (pixels por ciclo)
    private final double turnSpeed = Math.PI / 60; // Velocidade de rotação (radianos por ciclo)

    public Player(double x, double y, Cenario map) {
        this.x = x;
        this.y = y;
        this.turnAngle = 0.0;
        this.move = 0;
        this.turn = 0;
        this.collision = new Collision();
        this.map = map;
    }

    public double convertDegrees(double angle) {
        return angle * (Math.PI / 180);
    }

    public double distanceBetween(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    // Métodos de controle de movimento
    public void moveUp() {
        this.move = 1;
    }

    public void moveDown() {
        this.move = -1;
    }

    public void moveRight() {
        this.turn = 1;
    }

    public void moveLeft() {
        this.turn = -1;
    }

    public void releaseMove() {
        this.move = 0;
    }

    public void releaseTurn() {
        this.turn = 0;
    }

    // Atualização da posição do jogador
    public void updateMovement() {
        // Calcula a nova posição do jogador
        double newX = this.x + (this.move * Math.cos(this.turnAngle) * this.moveSpeed);
        double newY = this.y + (this.move * Math.sin(this.turnAngle) * this.moveSpeed);

        // Converte as novas coordenadas para índices de matriz
        int gridX = (int) Math.floor(newX / map.getWidth());
        int gridY = (int) Math.floor(newY / map.getHeigth());


        // Verifica colisão com o mapa
        if (!collision.colide(gridX, gridY, this.map)) {
            // Atualiza a posição apenas se não houver colisão
            this.x = newX;
            this.y = newY;
        }

        // Atualiza o ângulo de rotação
        this.turnAngle += this.turn * this.turnSpeed;
        if (this.turnAngle > 2 * Math.PI) {
            this.turnAngle = 0;
        }
        if (this.turnAngle < 0) {
            this.turnAngle += 2 * Math.PI;
        }
    }

    // Métodos getter para posição e ângulo
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIntegerX() {
        return (int) this.x;
    }

    public int getIntegerY() {
        return (int) this.y;
    }    

    public double getTurnAngle() {
        return turnAngle;
    }
}