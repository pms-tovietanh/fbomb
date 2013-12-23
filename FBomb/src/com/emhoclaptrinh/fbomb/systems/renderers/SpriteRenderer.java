package com.emhoclaptrinh.fbomb.systems.renderers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.emhoclaptrinh.fbomb.components.BombAttacker;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.components.Sprite;
import com.emhoclaptrinh.fbomb.utils.Constants;

public class SpriteRenderer extends EntitySystem {

	@Mapper ComponentMapper<Sprite> sm;
	@Mapper ComponentMapper<Position> pm;
	
	private HashMap<String, AtlasRegion> regions;
	private TextureAtlas textureAtlas;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Bag<AtlasRegion> regionsByEntity;
	private List<Entity> sortedEntities;
	
	@SuppressWarnings("unchecked")
	public SpriteRenderer(OrthographicCamera camera,SpriteBatch batch) {
		super(Aspect.getAspectForAll(Sprite.class, Position.class));
		this.camera = camera;
		this.batch = batch;
	}
	
	@Override
	protected void initialize() {
		regions = new HashMap<String, AtlasRegion>();
		textureAtlas = new TextureAtlas(Gdx.files.internal("data/pok-terrain.atlas"));
		for (AtlasRegion r : textureAtlas.getRegions()) {
			regions.put(r.name, r);
		}
		regionsByEntity = new Bag<AtlasRegion>();
		
		sortedEntities = new ArrayList<Entity>();

	}

	@Override
	protected void begin() {
		batch.begin();
	}
	
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; sortedEntities.size() > i; i++) {
			process(sortedEntities.get(i));
		}
		
	}
	
	protected void process(Entity e) {
		if(pm.has(e)) {
		Position position = pm.getSafe(e);
		Sprite sprite = sm.get(e);

		AtlasRegion spriteRegion = regionsByEntity.get(e.getId());
		batch.setColor(sprite.r, sprite.g, sprite.b, sprite.a);

		float posX = position.x - (spriteRegion.getRegionWidth() / 2 * sprite.scaleX);
		float posY = position.y - (spriteRegion.getRegionHeight() / 2 * sprite.scaleX);
		batch.draw(spriteRegion, posX/Constants.GameWidth,posY/Constants.GameWidth, 0, 0, 
				spriteRegion.getRegionWidth()/Constants.GameWidth, spriteRegion.getRegionHeight()/Constants.GameWidth, 
				sprite.scaleX, sprite.scaleY, sprite.rotation);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	protected void end() {
		batch.end();
	}
	
	@Override
	protected void inserted(Entity e) {
		boolean isBombAttacker = false;
		ImmutableBag<String> groups = world.getManager(GroupManager.class).getGroups(e);
		if(groups!=null)for(int i = 0; i < groups.size();i++){
			if(groups.get(i).equals(Constants.EntityGroups.BombAttacker))isBombAttacker=true;
		}
		if(isBombAttacker)return;
		Sprite sprite = sm.get(e);
		regionsByEntity.set(e.getId(), regions.get(sprite.name));

		sortedEntities.add(e);
		
		Collections.sort(sortedEntities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				Sprite s1 = sm.get(e1);
				Sprite s2 = sm.get(e2);
				return s1.layer.compareTo(s2.layer);
			}
		});
	}

	@Override
	protected void removed(Entity e) {
		regionsByEntity.set(e.getId(), null);
		sortedEntities.remove(e);
	}

}
