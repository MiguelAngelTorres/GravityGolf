package com.gravity.golf.Util;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gravity.golf.Util.Constants;
import com.gravity.golf.MainGame;

public class ButtonPlay extends Button {

    private MainGame game;

    public ButtonPlay(int x, int y, int width, int height, MainGame game) {
        super(x, y, width, height);
        this.game = game;
        cadena = Constants.JUGAR;
    }

    @Override
    protected void funcionamiento() {
        game.setScreen(game.gamescreen);
    }
}