package io.rba.raymob;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture wallTexture;
    private Cenario cenario;
    private Player player;
    private Hud hud;
    private List<Ray> rays; // List to store rays

    @Override
    public void create() {
        batch = new SpriteBatch();
        wallTexture = new Texture("wallpaint.png");
        cenario = new Cenario();
        player = new Player(150, 150, cenario);
        hud = new Hud(player);

        // Initialize rays only once during object creation
        rays = new ArrayList<>();
        double playerAngle = Math.toRadians(player.getTurnAngle());
        double fov = Math.toRadians(60); // Field of view in radians
        int numColumns = 60; // Number of columns to render

        for (int i = 0; i < numColumns; i++) {
            // Calculate the angle of the current ray
            double rayAngle = playerAngle - fov / 2 + (fov / numColumns) * i;

            // Create and add the ray to the list
            rays.add(new Ray(cenario, player, (float) rayAngle, wallTexture));
        }

        // Configure touch events
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
        render3DWorld(batch);
        hud.render(batch);
        batch.end();
    }

    private void render3DWorld(SpriteBatch batch) {
        double playerAngle = player.getTurnAngle();
        double fov = Math.toRadians(60); // Field of view in radians
        int numColumns = 60; // Number of columns to render

        for (int i = 0; i < numColumns; i++) {
            // Calculate the angle of the current ray
            double rayAngle = playerAngle - fov / 2 + (fov / numColumns) * i;

            // Update and render the ray
            rays.get(i).setRayAngle((float)rayAngle); // Update the ray angle
            rays.get(i).wallRender3d(batch, i); // Render the ray
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        wallTexture.dispose();
    }
}