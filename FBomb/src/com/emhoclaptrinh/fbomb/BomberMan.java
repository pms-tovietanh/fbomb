package com.emhoclaptrinh.fbomb;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.emhoclaptrinh.fbomb.systems.BombCountDownSystem;
import com.emhoclaptrinh.fbomb.systems.CollisionSystem;
import com.emhoclaptrinh.fbomb.systems.FireCountDownSystem;
import com.emhoclaptrinh.fbomb.systems.MovementSystem;
import com.emhoclaptrinh.fbomb.systems.PlayerInputSystem;
import com.emhoclaptrinh.fbomb.systems.renderers.BombAttackerRenderer;
import com.emhoclaptrinh.fbomb.systems.renderers.SpriteRenderer;
import com.emhoclaptrinh.fbomb.utils.Constants;
import com.emhoclaptrinh.fbomb.utils.EntityFactory;

public class BomberMan implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private World world;
	private BombAttackerRenderer baRenderer;
	private SpriteRenderer spriteRenderer;
	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1, h/w);
		batch = new SpriteBatch();
		
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		world = new World();
		
		world.setManager(new GroupManager());
		
		world.setSystem(new PlayerInputSystem());
		world.setSystem(new MovementSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new BombCountDownSystem());
		world.setSystem(new FireCountDownSystem());
		baRenderer = world.setSystem(new BombAttackerRenderer(camera), true);
		spriteRenderer = world.setSystem(new SpriteRenderer(camera,batch),true);
		
		world.initialize();
		
		EntityFactory.createBombAttacker(world, 24, 24,"",Constants.EntityGroups.Player1).addToWorld();
		EntityFactory.createBombAttacker(world, 376, 216,"p2",Constants.EntityGroups.Player2).addToWorld();
		EntityFactory.createHardBricks(world);
		EntityFactory.createBombs(world);
		
		map = new TmxMapLoader().load(Gdx.files.internal("data/maps/map1.tmx").path());
		mapRenderer = new OrthogonalTiledMapRenderer(map,1/Constants.GameWidth,batch);
		mapRenderer.setView(camera);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		mapRenderer.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		mapRenderer.render();

		world.setDelta(Gdx.graphics.getDeltaTime());
		world.process();
		
		spriteRenderer.process();
		baRenderer.process();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
