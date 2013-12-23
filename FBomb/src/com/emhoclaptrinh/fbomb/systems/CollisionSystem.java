package com.emhoclaptrinh.fbomb.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.emhoclaptrinh.fbomb.components.Bomb;
import com.emhoclaptrinh.fbomb.components.Bounds;
import com.emhoclaptrinh.fbomb.components.LastPosition;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.utils.Constants;

public class CollisionSystem extends EntitySystem {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Bounds> bm;
	@Mapper ComponentMapper<LastPosition> lpm;
	@Mapper ComponentMapper<Bomb> bom;
	
	private Bag<CollisionPair> collisionPairs;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Aspect.getAspectForAll(Position.class,Bounds.class));
	}
	
	@Override
	protected void initialize() {
		collisionPairs = new Bag<CollisionPair>();

		collisionPairs.add(new CollisionPair(Constants.EntityGroups.BombAttacker, Constants.EntityGroups.Brick, new CollisionHandler() {
			@Override
			public void handleCollision(Entity a, Entity b) {
				LastPosition lp = lpm.get(a);
				Position p = pm.get(a);
				p.x = lp.x;
				p.y = lp.y;
			}
		}));
		
		collisionPairs.add(new CollisionPair(Constants.EntityGroups.BombAttacker, Constants.EntityGroups.Fire, new CollisionHandler() {
			@Override
			public void handleCollision(Entity a, Entity f) {
				a.deleteFromWorld();
			}
		}));
		
		collisionPairs.add(new CollisionPair(Constants.EntityGroups.Bomb, Constants.EntityGroups.Fire, new CollisionHandler() {
			@Override
			public void handleCollision(Entity b, Entity f) {
				Bomb bo = bom.get(b);
				bo.countDown = -1;
			}
		}));
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		for(int i = 0; collisionPairs.size() > i; i++) {
			collisionPairs.get(i).checkForCollisions();
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
	
	private class CollisionPair {
		private ImmutableBag<Entity> groupEntitiesA;
		private ImmutableBag<Entity> groupEntitiesB;
		private CollisionHandler handler;

		public CollisionPair(String group1, String group2, CollisionHandler handler) {
			groupEntitiesA = world.getManager(GroupManager.class).getEntities(group1);
			groupEntitiesB = world.getManager(GroupManager.class).getEntities(group2);
			this.handler = handler;
		}

		public void checkForCollisions() {
			for(int a = 0; groupEntitiesA.size() > a; a++) {
				for(int b = 0; groupEntitiesB.size() > b; b++) {
					Entity entityA = groupEntitiesA.get(a);
					Entity entityB = groupEntitiesB.get(b);
					if(collisionExists(entityA, entityB)) {
						handler.handleCollision(entityA, entityB);
					}
				}
			}
		}
		
		private boolean collisionExists(Entity e1, Entity e2) {
			Position p1 = pm.get(e1);
			Position p2 = pm.get(e2);
			
			Bounds b1 = bm.get(e1);
			Bounds b2 = bm.get(e2);
			
			if(p1==null||p2==null||b1==null||b2==null)return false;
			return (Math.abs(p1.x-p2.x)<(b1.width+b2.width)/2&&Math.abs(p1.y-p2.y)<(b1.height+b2.height)/2);
		}
	}
	
	private interface CollisionHandler {
		void handleCollision(Entity a, Entity b);
	}

}
