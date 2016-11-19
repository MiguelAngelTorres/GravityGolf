package com.gravity.golf.entities;

        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.CircleShape;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.World;
        import com.badlogic.gdx.scenes.scene2d.Actor;

        import com.gravity.golf.Constants;

public class PlanetaEntity extends Actor {

    private Texture planeta;
    private World world;
    private Body body;
    private Fixture fixture;

    public PlanetaEntity(World world, Texture planeta, float x, float y, float rad) {
        this.world = world;
        this.planeta = planeta;

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        body = world.createBody(def);

        CircleShape circle = new CircleShape();
        circle.setRadius(rad);
        fixture = body.createFixture(circle, 1);
        fixture.setUserData("planet");  // pasar a las subclases cuando las haya
        circle.dispose();

        setSize(rad * 2 * Constants.PIXEL_IN_METER, Constants.PIXEL_IN_METER);
        setPosition((x - rad) * Constants.PIXEL_IN_METER, (y - rad) * Constants.PIXEL_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(planeta, getX(), getY(), getWidth(), getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}