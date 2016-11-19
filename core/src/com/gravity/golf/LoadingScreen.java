package com.gravity.golf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen extends PantallaBase {

    /** Labels are also Actors. We will use Scene2D UI. */
    private Stage stage;

    /** This is the skin file (see GameOverScreen for more information on this). */
    private Skin skin;

    /** This is the label that we use to display some text on the screen. */
    private Label loading;

    public LoadingScreen(MainGame game) {
        super(game);

        // Set up the stage and the skin. See GameOverScreen for more comments on this.
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Create some loading text using this skin file and position it on screen.
        loading = new Label("Loading...", skin);
        loading.setPosition(320 - loading.getWidth() / 2, 180 - loading.getHeight() / 2);
        stage.addActor(loading);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.getManager().update()) {
            game.finishLoading();
        } else {
           int progress = (int) (game.getManager().getProgress() * 100);
            loading.setText("Loading... " + progress + "%");
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
