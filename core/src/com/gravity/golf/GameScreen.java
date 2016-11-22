package com.gravity.golf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.gravity.golf.Constants;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class GameScreen extends PantallaBase {

    private Stage stage;
    private World world;
    public PlayerEntity player;
    private List<PlanetaEntity> planetaList = new ArrayList<PlanetaEntity>();
    private Texture playerTexture, planetaTexture;
    private Sound dieSound;
    private Music gameMusic;
    private Vector3 position;



    public GameScreen(final MainGame game) {
        super(game);
        playerTexture = game.getManager().get("img/nave.png");
        planetaTexture = game.getManager().get("img/mercurio.png");
        dieSound = game.getManager().get("sound/die.ogg");
        stage = new Stage(new FitViewport(640, 420));
        world = new World(new Vector2(0,0), true);

        position = new Vector3(stage.getCamera().position);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) || (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if(areCollided(contact, "player", "planet")){
                    if(player.isAlive()) {
                        //player.setAlive(false);
                        dieSound.play();
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
        player = new PlayerEntity(world, playerTexture, dieSound, 0.5f, 1, new Vector2(0f, -6f), new Vector2(-6,4));

        /////////////////////////////////////////////////////////
        /////   AÑADIR OBJETOS SELECCIONADOS PARA LA MISION  ////
        /////////////////////////////////////////////////////////

        planetaList.add(new PlanetaEntity(world, planetaTexture ,0, 0, 2));
        planetaList.add(new PlanetaEntity(world, planetaTexture ,0, 15, 2));

        stage.addActor(player);
        for (PlanetaEntity p : planetaList){
            stage.addActor(p);
        }

        stage.getCamera().position.set(position);
        stage.getCamera().update();

//        gameMusic.setVolume(0.60f);
//        gameMusic.play();
    }

    @Override
    public void hide() {
//        gameMusic.stop();
        player.detach();
        player.remove();
        for (PlanetaEntity p : planetaList){
            p.detach();
        }
        planetaList.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(delta,6,2);

        ((OrthographicCamera)stage.getCamera()).zoom = 1.5f;
        /////////////////////////////    CÁMARA SIGUE AL JUGADOR Y LE HACE ZOOM SI HA SIDO LANZADO      ///////////////////////
        if(player.isAlive()) {
            ((OrthographicCamera)stage.getCamera()).position.x = player.getX();
            ((OrthographicCamera)stage.getCamera()).position.y = player.getY();
           // ((OrthographicCamera)stage.getCamera()).zoom -= (float) ((((OrthographicCamera)stage.getCamera()).zoom - 0.6) * 0.04f);


            //////////////////////////////////////////////    MOTOR     DE     GRAVEDAD     //////////////////////////////////////
            Vector2 aux = new Vector2(0,0);
            for(PlanetaEntity planet : planetaList){
                float dcubo = (float) pow((planet.body.getPosition().x - player.body.getPosition().x)*(planet.body.getPosition().x - player.body.getPosition().x) + (planet.body.getPosition().y - player.body.getPosition().y)*(planet.body.getPosition().y - player.body.getPosition().y),1.5f);
            //    System.out.println(planet.getX() + " " + planet.getY() + " " + player.getX() + " " + player.getX() );
                aux.x += planet.getMass()*(planet.body.getPosition().x - player.body.getPosition().x) / dcubo;
                aux.y += planet.getMass()*(planet.body.getPosition().y - player.body.getPosition().y) / dcubo;
            }
            System.out.println(aux.x);
            player.applyGravity(aux);
        }else{
            stage.getCamera().lookAt(position);   ////  POSICION INICIAL DE LA CAMARA EN EL NIVEL  ////
            player.setAlive(true);
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
