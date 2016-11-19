package com.gravity.golf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gravity.golf.entities.PlanetaEntity;
import com.gravity.golf.entities.PlayerEntity;
import com.justagame.entities.FloorEntity;
import com.justagame.entities.PlayerEntity;
import com.justagame.entities.SpikeEntity;
import com.justagame.Constants;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends PantallaBase {

    private Stage stage;
    private World world;
    public PlayerEntity player;
    private List<PlanetaEntity> floorList = new ArrayList<PlanetaEntity>();
    private Texture playerTexture, planetaTexture;
    private Sound jumpSound, dieSound;
    private Music gameMusic;
    private Vector3 position;

    public GameScreen(final MainGame game) {
        super(game);
        playerTexture = game.getManager().get("imagenes/player.png");
        planetaTexture = game.getManager().get("imagenes/floor.png");
        jumpSound = game.getManager().get("canciones/jump.ogg");
        dieSound = game.getManager().get("canciones/die.ogg");
        gameMusic = game.getManager().get("canciones/song.ogg");
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0,0), true);

        position = new Vector3(stage.getCamera().position);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) || (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact, "player", "floor")){
                    player.setJumping(false);
                }

                if(areCollided(contact, "player", "spike")){
                    if(player.isAlive()) {
                        player.setAlive(false);
                        dieSound.play();
                        gameMusic.stop();
                        stage.addAction(Actions.sequence(
                                Actions.delay(1f),
                                Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        game.setScreen(game.gameoverscreen);
                                    }
                                })
                        ));
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {
        player = new PlayerEntity(world, playerTexture,jumpSound, dieSound, new Vector2(1.5f, 1.5f));

        floorList.add(new FloorEntity(world, floorTexture,overfloorTexture ,0, 1000, 1));
        floorList.add(new FloorEntity(world, floorTexture,overfloorTexture ,12, 10, 2));
        spikeList.add(new SpikeEntity(world, spikeTexture, 60, 1));

        stage.addActor(player);
        for (FloorEntity floor : floorList){
            stage.addActor(floor);
        }
        for (SpikeEntity spike : spikeList) {
            stage.addActor(spike);
        }

        stage.getCamera().position.set(position);
        stage.getCamera().update();

        gameMusic.setVolume(0.60f);
        gameMusic.play();
    }

    @Override
    public void hide() {
        gameMusic.stop();
        player.detach();
        player.remove();
        for (FloorEntity floor : floorList){
            floor.detach();
        }
        for (SpikeEntity spike : spikeList) {
            spike.detach();
        }
        floorList.clear();
        spikeList.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta,6,2);
        if(player.getX() > 150 && player.isAlive()) {
            stage.getCamera().translate(player.getVelocityX() * delta * Constants.PIXEL_IN_METER, 0, 0);
        }
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();

    }
}
