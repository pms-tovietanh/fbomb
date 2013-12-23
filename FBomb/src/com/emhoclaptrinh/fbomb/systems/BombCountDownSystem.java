package com.emhoclaptrinh.fbomb.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.emhoclaptrinh.fbomb.components.Bomb;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.utils.EntityFactory;

public class BombCountDownSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Bomb> bm;
	@Mapper ComponentMapper<Position> pm;
	
	@SuppressWarnings("unchecked")
	public BombCountDownSystem() {
		super(Aspect.getAspectForAll(Bomb.class,Position.class));
	}

	@Override
	protected void process(Entity e) {
		Bomb b = bm.get(e);
		b.countDown -= world.delta;
		if(b.countDown<0){
			Position position = pm.get(e);
			EntityFactory.CreateFireForBomb(world, position.x, position.y, b.bombLength);
			e.deleteFromWorld();
		}
	}

}
