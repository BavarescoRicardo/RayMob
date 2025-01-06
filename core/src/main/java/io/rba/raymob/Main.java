package io.rba.raymob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image, playerImg;
    private Player player;
    private Hud hud;
    private Cenario cenario;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        playerImg = new Texture("dm.png");
        cenario = new Cenario();
        player = new Player(150, 150, cenario); // cordenadas iniciais do player e mapa por parametro para ver colisoes
        hud = new Hud(player);

        // Configurar eventos de toque
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                hud.handleInput(screenX, screenY, false);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                hud.handleInput(screenX, screenY, true);
                return true;
            }
        });

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        // poderia desenhar o fundo aqui // por exemplo o chao e o teto        
        // batch.draw(image, 140, 210);
        hud.render(batch);
        batch.draw(playerImg, player.getIntegerX(), player.getIntegerY());
        cenario.drawMap(batch);        
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();        
    }
}
