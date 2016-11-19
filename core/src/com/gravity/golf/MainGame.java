package com.gravity.golf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {
	private AssetManager manager;

	public AssetManager getManager(){
		return manager;
	}

	public GameOverScreen gameoverscreen;
	public GameScreen gamescreen;
	public MenuScreen menuScreen;
	public LoadingScreen loadingScreen;

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("imagenes/floor.png", Texture.class);
		manager.load("imagenes/player.png", Texture.class);
		manager.load("imagenes/spike.png", Texture.class);
		manager.load("imagenes/logo.png", Texture.class);
		manager.load("imagenes/overfloor.png", Texture.class);
		manager.load("imagenes/gameover.png", Texture.class);
		manager.load("canciones/die.ogg", Sound.class);
		manager.load("canciones/jump.ogg", Sound.class);
		manager.load("canciones/song.ogg", Music.class);

		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void finishLoading() {
		gameoverscreen = new GameOverScreen(this);
		gamescreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

}
