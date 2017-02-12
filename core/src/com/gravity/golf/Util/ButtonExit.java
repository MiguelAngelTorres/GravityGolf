package com.gravity.golf.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.gravity.golf.Util.Constants;
import com.gravity.golf.MainGame;

public class ButtonExit extends Button { // Botón que permitirá salir del juego

    private MainGame game;

    public ButtonExit(int x, int y, int width, int height, MainGame game) {
        super(x, y, width, height);
        this.game = game;
        cadena = Constants.SALIR;
    }

    @Override
    protected void funcionamiento() {
        Gdx.app.exit();
    }
}