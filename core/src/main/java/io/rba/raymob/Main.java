package io.rba.raymob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture image, playerImg;
    private Player player;
    private Hud hud;
    private Cenario cenario;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
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

        // Renderizar o mapa
        batch.begin();
        cenario.drawMap(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        // Definir ângulo de visão do jogador e campo de visão
        double playerAngle = Math.toRadians(player.getTurnAngle()); // Supondo que player.getAngle() retorna o ângulo em graus
        double fov = Math.toRadians(60); // Campo de visão de 60 graus
        int rayCount = 60; // Quantidade de raios no campo de visão

        // Calcular raios no campo de visão
        for (int i = 0; i <= rayCount; i++) {
            // Distribuir raios dentro do campo de visão
            double rayAngle = playerAngle - fov / 2 + (fov / rayCount) * i;
            Ray ray = new Ray(player.getX(), player.getY(), rayAngle, cenario);
            ray.render(shapeRenderer);
        }

    shapeRenderer.end();

        // Renderizar o jogador
        batch.begin();
        batch.draw(playerImg, player.getIntegerX(), player.getIntegerY(), 60, 60);
        hud.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();        
    }
}
