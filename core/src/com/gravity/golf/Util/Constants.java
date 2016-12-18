package com.gravity.golf.Util;

import com.badlogic.gdx.Gdx;

public class Constants {
    public static final int W = (int)(640);
    public static final int H = (int)(480);
    public static final int WIDTH = W;
    public static final int HEIGHT = H*W/ Gdx.graphics.getWidth();  ///// OUT GAME
    public static final int GAME_HEIGHT = Gdx.graphics.getHeight()*W/Gdx.graphics.getWidth();   ///// IN GAME
    public static final int GAME_WIDTH = W;
    public static final float PIXEL_IN_METER = W/17f;

    public static final float DIV_PULSADO = 4f;
    public static final float DIV_SIN_PULSAR = 3.5f;

    public static final String CONTINUAR = "continuar";
    public static final String SALIR = "salir";
    public static final String MENU = "menu";
    public static final String JUGAR = "jugar";
    public static final String REINICIAR = "reiniciar";
}
