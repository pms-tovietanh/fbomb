package com.emhoclaptrinh.fbomb.systems.renderers;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.emhoclaptrinh.fbomb.components.BombAttacker;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.components.Sprite;
import com.emhoclaptrinh.fbomb.components.Velocity;
import com.emhoclaptrinh.fbomb.utils.Constants;

public class BombAttackerRenderer extends EntitySystem {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Sprite> sm;
	@Mapper ComponentMapper<BombAttacker> bm;
	@Mapper ComponentMapper<Velocity> vm;
	
	private HashMap<String,AtlasRegion> regions;
	private TextureAtlas textureAtlas;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public BombAttackerRenderer(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Position.class, Sprite.class,BombAttacker.class,Velocity.class));
		this.camera = camera;
	}

	@Override
	protected void initialize() {
		regions = new HashMap<String,AtlasRegion>();
		textureAtlas = new TextureAtlas(Gdx.files.internal("data/pok-sprites.atlas"));
		for(AtlasRegion r:textureAtlas.getRegions()){
			regions.put(r.name, r);
		}
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
	}
	
	@Override
	protected void begin() {
		batch.begin();
	}

	@Override
	protected void end() {
		batch.end();
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; i < entities.size();i++){
			process(entities.get(i));
		}
	}
	
	protected void process(Entity e){
		Position position = pm.get(e);
		Sprite sprite = sm.get(e);
		BombAttacker bombAttacker = bm.get(e);
		Velocity velocity = vm.get(e);
		
		float d = Gdx.graphics.getDeltaTime();
		
		if(velocity.vectorX>0){
			bombAttacker.movingRight+=d*velocity.vectorX;
			int i = bombAttacker.GoRightSprites.indexOf(sprite.name);
			if(i<0)i=0;
			if(bombAttacker.movingRight/bombAttacker.distanceLimitPerAction>=0.5*(i%2+1)){
				i = (i+1)%bombAttacker.GoRightSprites.size();
			}
			if(bombAttacker.movingRight>=bombAttacker.distanceLimitPerAction){
				bombAttacker.movingRight = 0;
				velocity.vectorX=0;
				position.x = Math.round(position.x/bombAttacker.distanceLimitPerAction)*bombAttacker.distanceLimitPerAction;
			}
			sprite.name = bombAttacker.GoRightSprites.get(i);
		}
		
		if(velocity.vectorX<0){
			bombAttacker.movingLeft-=d*velocity.vectorX;
			int i = bombAttacker.GoLeftSprites.indexOf(sprite.name);
			if(i<0)i=0;
			if(bombAttacker.movingLeft/bombAttacker.distanceLimitPerAction>=0.5*(i%2+1)){
				i = (i+1)%bombAttacker.GoLeftSprites.size();
			}
			if(bombAttacker.movingLeft>=bombAttacker.distanceLimitPerAction){
				bombAttacker.movingLeft = 0;
				velocity.vectorX=0;
				position.x = Math.round(position.x/bombAttacker.distanceLimitPerAction)*bombAttacker.distanceLimitPerAction;
			}
			sprite.name = bombAttacker.GoLeftSprites.get(i);
		}
		
		if(velocity.vectorY<0){
			bombAttacker.movingDown-=d*velocity.vectorY;
			int i = bombAttacker.GoDownSprites.indexOf(sprite.name);
			if(i<0)i=0;
			if(bombAttacker.movingDown/bombAttacker.distanceLimitPerAction>=0.5*(i%2+1)){
				i = (i+1)%bombAttacker.GoDownSprites.size();
			}
			if(bombAttacker.movingDown>=bombAttacker.distanceLimitPerAction){
				bombAttacker.movingDown = 0;
				velocity.vectorY=0;
				position.y = Math.round(position.y/bombAttacker.distanceLimitPerAction)*bombAttacker.distanceLimitPerAction;
			}
			sprite.name = bombAttacker.GoDownSprites.get(i);
		}
		
		if(velocity.vectorY>0){
			bombAttacker.movingUp+=d*velocity.vectorY;
			int i = bombAttacker.GoUpSprites.indexOf(sprite.name);
			if(i<0)i=0;
			if(bombAttacker.movingUp/bombAttacker.distanceLimitPerAction>=0.5*(i%2+1)){
				i = (i+1)%bombAttacker.GoUpSprites.size();
			}
			if(bombAttacker.movingUp>=bombAttacker.distanceLimitPerAction){
				bombAttacker.movingUp = 0;
				velocity.vectorY=0;
				position.y = Math.round(position.y/bombAttacker.distanceLimitPerAction)*bombAttacker.distanceLimitPerAction;
			}
			sprite.name = bombAttacker.GoUpSprites.get(i);
		}
		
		AtlasRegion region = regions.get(sprite.name);
		batch.draw(region, position.x/Constants.GameWidth-region.getRegionHeight()/Constants.GameWidth/2, 
				position.y/Constants.GameWidth-region.getRegionHeight()/Constants.GameWidth/4, 
				0, 0, region.getRegionWidth()/Constants.GameWidth, region.getRegionHeight()/Constants.GameWidth, sprite.scaleX, sprite.scaleY, sprite.rotation);
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
