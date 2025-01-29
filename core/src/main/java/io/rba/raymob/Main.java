package io.rba.raymob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture wallTexture;
    private Cenario cenario;
    private Player player;
    private Hud hud;

    @Override
    public void create() {
        batch = new SpriteBatch();
        wallTexture = new Texture("wallpaint.png");
        cenario = new Cenario();
        player = new Player(150, 150, cenario);
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
        render3DWorld();
        hud.render(batch);
        batch.end();
    }

    private void render3DWorld() {
        double playerAngle = Math.toRadians(player.getTurnAngle());
        double fov = Math.toRadians(60);
        int rayCount = 60;
    
        for (int i = 0; i < rayCount; i++) {
            double rayAngle = playerAngle - fov / 2 + (fov / rayCount) * i;
            // Gdx.app.log("Ray", "Ray Angle: " + rayAngle); // Debug log
            Ray ray = new Ray(cenario, player, rayAngle, wallTexture);
            ray.wallRender3d(batch, i);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallTexture.dispose();
    }
}