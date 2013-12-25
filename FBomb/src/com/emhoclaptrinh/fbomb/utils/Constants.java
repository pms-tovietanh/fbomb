package com.emhoclaptrinh.fbomb.utils;

public class Constants {
	public static final float GameWidth = 400;
	public static final float GameHeight = 240;
	public static final int[] dx = {1,-1,0,0};
	public static final int[] dy = {0,-0,1,-1};
	public static final String[] middleFireSprites = {"h_m_fire","h_m_fire","v_m_fire","v_m_fire"};
	public static final String[] endFireSprites = {"r_e_fire","l_e_fire","u_e_fire","d_e_fire"};
	
	public class EntityGroups{
		public static final String BombAttacker = "BombAttacker";
		public static final String Player1 = "Player1";
		public static final String Player2 = "Player2";
		public static final String Brick = "Brick";
		public static final String HardBrick = "HardBrick";
		public static final String Fire = "Fire";
		public static final String Bomb = "Bomb";
	}
	
	public class ScriptingActions{
		public static final String None = "none";
		public static final String Left = "left";
		public static final String Right = "right";
		public static final String Up = "up";
		public static final String Down = "down";
		public static final String Bomb = "bomb";
	}
}
