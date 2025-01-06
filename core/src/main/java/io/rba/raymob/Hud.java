package io.rba.raymob;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Hud {
    private Player player;
    private Texture imgUp, imgDown, imgLeft, imgRight;
    private Rectangle buttonUp, buttonDown, buttonLeft, buttonRight;

    public Hud(Player player) {
        this.player = player;

        // Carregar as texturas dos botões
        this.imgUp = new Texture(Gdx.files.internal("arrow-up.png"));
        this.imgDown = new Texture(Gdx.files.internal("arrow-down.png"));
        this.imgLeft = new Texture(Gdx.files.internal("arrow-left.png"));
        this.imgRight = new Texture(Gdx.files.internal("arrow-right.png"));

        // Definir áreas clicáveis para os botões
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float buttonSize = screenWidth * 0.065f; // 10% da largura da tela
        float margin = screenWidth * 0.02f; // 2% de margem

        this.buttonUp = new Rectangle(screenWidth / 2 - buttonSize / 2, margin + buttonSize * 2, buttonSize, buttonSize);
        this.buttonDown = new Rectangle(screenWidth / 2 - buttonSize / 2, margin, buttonSize, buttonSize);
        this.buttonLeft = new Rectangle(screenWidth / 2 - buttonSize * 1.5f, margin + buttonSize, buttonSize, buttonSize);
        this.buttonRight = new Rectangle(screenWidth / 2 + buttonSize / 2, margin + buttonSize, buttonSize, buttonSize);
    }

    public void handleInput(float touchX, float touchY, boolean isRelease) {
        // Ajustar coordenadas para o sistema de coordenadas do LibGDX
        touchY = Gdx.graphics.getHeight() - touchY;
        player.updateMovement();

        if (buttonUp.contains(touchX, touchY)) {
            if (isRelease) {
                player.releaseMove();
            } else {
                player.moveUp();
            }
        } else if (buttonDown.contains(touchX, touchY)) {
            if (isRelease) {
                player.releaseMove();
            } else {
                player.moveDown();
            }
        } else if (buttonLeft.contains(touchX, touchY)) {
            if (isRelease) {
                player.releaseTurn();
            } else {
                player.moveLeft();
            }
        } else if (buttonRight.contains(touchX, touchY)) {
            if (isRelease) {
                player.releaseTurn();
            } else {
                player.moveRight();
            }
        }        
    }

    public void render(SpriteBatch batch) {
        // Desenhar os botões na tela
        batch.draw(imgUp, buttonUp.x, buttonUp.y, buttonUp.width, buttonUp.height);
        batch.draw(imgDown, buttonDown.x, buttonDown.y, buttonDown.width, buttonDown.height);
        batch.draw(imgLeft, buttonLeft.x, buttonLeft.y, buttonLeft.width, buttonLeft.height);
        batch.draw(imgRight, buttonRight.x, buttonRight.y, buttonRight.width, buttonRight.height);
    }
}
