package com.gravity.golf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gravity.golf.Util.Button;
import com.gravity.golf.Util.ButtonExit;
import com.gravity.golf.Util.ButtonPlay;
import com.gravity.golf.Util.Constants;


public class MenuScreen extends PantallaBase {

    private Stage stage;
    private Texture fondo;
    private Button exit, play;

    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(Constants.GAME_WIDTH, Constants.GAME_HEIGHT));
        ((OrthographicCamera)stage.getCamera()).zoom = 1;
        play = new ButtonPlay((int) 0, (int) Constants.GAME_HEIGHT*8/10, (int) Constants.GAME_WIDTH/2, (int) Constants.GAME_HEIGHT/8, game);
        exit = new ButtonExit(0, 0, 0, 0, game);

        fondo = game.getManager().get("img/Estrella_2.png");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        play.update();
        exit.update();

        stage.getBatch().begin();
        stage.getBatch().draw(fondo, -100, -100, 2000, 2000);
        play.draw(stage.getBatch());
        exit.draw(stage.getBatch());
        stage.getBatch().end();


        stage.act();
        stage.draw();
    }
}
