package com.emhoclaptrinh.fbomb.utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.emhoclaptrinh.fbomb.components.Bomb;
import com.emhoclaptrinh.fbomb.components.BombAttacker;
import com.emhoclaptrinh.fbomb.components.Bounds;
import com.emhoclaptrinh.fbomb.components.Fire;
import com.emhoclaptrinh.fbomb.components.LastPosition;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.components.Sprite;
import com.emhoclaptrinh.fbomb.components.Velocity;

public class EntityFactory {
	public static Entity createBombAttacker(World world,float x, float y){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x,y));
		
		e.addComponent(new LastPosition(x,y));
		
		e.addComponent(new Velocity(0,0));
		
		e.addComponent(new Bounds(16, 16));
		
		Sprite sprite = new Sprite();
		sprite.name = "Down0";
		e.addComponent(sprite);
		
		e.addComponent(new BombAttacker());
	
		world.getManager(GroupManager.class).add(e, Constants.EntityGroups.BombAttacker);
		return e;
	}
	
	public static Entity createHardBrick(World world, float x, float y){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x,y));
		
		e.addComponent(new Bounds(16, 16));
		
		world.getManager(GroupManager.class).add(e, Constants.EntityGroups.Brick);
		world.getManager(GroupManager.class).add(e, Constants.EntityGroups.HardBrick);
		return e;
	}
	
	public static void createHardBricks(World world){
		for(int i = 0 ; i < 25;i++){
			createHardBrick(world,8+i*16,8).addToWorld();
		}
		for(int i = 0 ; i < 25;i++){
			createHardBrick(world,8+i*16,232).addToWorld();
		}
		for(int i = 0 ; i < 13;i++){
			createHardBrick(world,8,24+i*16).addToWorld();
		}
		for(int i = 0 ; i < 13;i++){
			createHardBrick(world,392,24+i*16).addToWorld();
		}
		
		for(int i = 1;i<=11;i++){
			for(int j = 1; j <= 6;j++){
				createHardBrick(world,8+2*i*16,8+2*j*16).addToWorld();
			}
		}
	}

	public static Entity createBomb(World world, float x, float y){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x, y));
		e.addComponent(new Bounds(16, 16));
		e.addComponent(new Bomb());
		
		Sprite sprite = new Sprite();
		sprite.name = "bomb";
		e.addComponent(sprite);
		
		return e;
	}
	
	public static Entity createFire(World world, float x, float y, String sprite){
		Entity e = world.createEntity();
		
		e.addComponent(new Position(x,y));
		e.addComponent(new Sprite(sprite));
		e.addComponent(new Fire());
		e.addComponent(new Bounds(16,16));
		world.getManager(GroupManager.class).add(e, Constants.EntityGroups.Fire);
		
		return e;
	}
	
	public static void createFireForBomb(World world,float x, float y, int length){
		//center
		createFire(world, x, y, "c_fire").addToWorld();
		
		for(int i = 1; i <= length;i++){
			for(int d = 0; d < 4;d++){
				createFire(world, x+Constants.dx[d]*i*16, y+Constants.dy[d]*i*16, 
						Constants.middleFireSprites[d]).addToWorld();
			}
		}
		for(int d =0;d<4;d++){
			createFire(world, x+Constants.dx[d]*(length+1)*16, x+Constants.dy[d]*(length+1)*16, 
					Constants.endFireSprites[d]).addToWorld();
		}
	}
}
