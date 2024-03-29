package com.gravity.golf.entities;

        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.physics.box2d.Body;
        import com.badlogic.gdx.physics.box2d.BodyDef;
        import com.badlogic.gdx.physics.box2d.CircleShape;
        import com.badlogic.gdx.physics.box2d.Fixture;
        import com.badlogic.gdx.physics.box2d.World;
        import com.badlogic.gdx.scenes.scene2d.Actor;

        import com.gravity.golf.Util.Constants;

public class PlanetaEntity extends Actor {

    private Texture planeta;
    private World world;
    public Body body;
    private Fixture fixture;
    private float radius;

    public PlanetaEntity(World world, Texture planeta, Vector2 pos, float rad) {
        this.world = world;
        this.planeta = planeta;

        BodyDef def = new BodyDef();
        def.position.set(pos.x, pos.y);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        CircleShape circle = new CircleShape();
        circle.setRadius(rad);
        radius = rad;
        fixture = body.createFixture(circle, 1);
        fixture.setUserData("planet");  // pasar a las subclases cuando las haya
        circle.dispose();

        setSize(rad * Constants.PIXEL_IN_METER, rad * Constants.PIXEL_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - radius*1.5f)* Constants.PIXEL_IN_METER, (body.getPosition().y - radius*1.2f) * Constants.PIXEL_IN_METER);
        batch.draw(planeta, getX(), getY(), getWidth() *2.8f, getHeight() *2.8f);
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public float getMass(){
        return 31.4159f * radius*radius;
    }
}