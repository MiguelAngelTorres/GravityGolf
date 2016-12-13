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
		manager.load("img/mercurio.png", Texture.class);
		manager.load("img/Estrella_1.png", Texture.class);
		manager.load("img/Estrella_2.png", Texture.class);
		manager.load("img/tierra.png", Texture.class);
		manager.load("img/nave.png", Texture.class);
		manager.load("img/logo.png", Texture.class);
		manager.load("img/gameover.png", Texture.class);
		manager.load("sound/die.ogg",Sound.class);

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
