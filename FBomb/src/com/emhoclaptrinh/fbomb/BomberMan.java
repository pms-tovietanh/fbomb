package com.emhoclaptrinh.fbomb;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.emhoclaptrinh.fbomb.systems.BombAttackerScriptingSystem;
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
	private World world;
	private BombAttackerRenderer baRenderer;
	private SpriteRenderer spriteRenderer;
	TiledMap map;
	OrthogonalTiledMapRenderer mapRenderer;
	
	ScriptEngineManager sm;
	ScriptEngine engine;
	ScriptContext context1,context2;
	Bindings bindings;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1, h/w);
		batch = new SpriteBatch();
		
		sm = new ScriptEngineManager();
		engine = sm.getEngineByName("JavaScript");
		
		Texture.setEnforcePotImages(false);//so image size not need to be power of 2
		
		world = new World();
		
		world.setManager(new GroupManager());
		
		world.setSystem(new PlayerInputSystem());
		world.setSystem(new MovementSystem());
		world.setSystem(new CollisionSystem());
		world.setSystem(new BombCountDownSystem());
		world.setSystem(new FireCountDownSystem());
		world.setSystem(new BombAttackerScriptingSystem(engine));
		baRenderer = world.setSystem(new BombAttackerRenderer(camera), true);
		spriteRenderer = world.setSystem(new SpriteRenderer(camera,batch),true);
		
		world.initialize();
		
		EntityFactory.createBombAttacker(world, 24, 24,"",Constants.EntityGroups.Player1).addToWorld();
		EntityFactory.createScriptingBombAttacker(world, 376, 216,"p2",Constants.EntityGroups.Player2,Gdx.files.internal("data/dummy.js").readString()).addToWorld();
		EntityFactory.createHardBricks(world);
		EntityFactory.createBombs(world);
		
		map = new TmxMapLoader().load(Gdx.files.internal("data/maps/map1.tmx").path());
		mapRenderer = new OrthogonalTiledMapRenderer(map,1/Constants.GameWidth,batch);
		mapRenderer.setView(camera);
		
		
		
	}

	@Override
	public void dispose() {
		batch.dispose();
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
