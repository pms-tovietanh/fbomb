package com.emhoclaptrinh.fbomb.components;

import java.util.ArrayList;
import java.util.Arrays;

import com.artemis.Component;

public class BombAttacker extends Component {
	
	public ArrayList<String> GoDownSprites = new ArrayList<String>(Arrays.asList("Down0","Down1","Down2","Down3"));
	public ArrayList<String> GoUpSprites = new ArrayList<String>(Arrays.asList("Up0","Up1","Up2","Up3"));
	public ArrayList<String> GoLeftSprites = new ArrayList<String>(Arrays.asList("Left0","Left1","Left2","Left3"));
	public ArrayList<String> GoRightSprites = new ArrayList<String>(Arrays.asList("Right0","Right1","Right2","Right3"));
	
	public float distanceLimitPerAction = 8f;
	public float movingDown = 0;
	public float movingUp = 0;
	public float movingLeft = 0;
	public float movingRight = 0;
	
	public int bombLength = 1;
	
	public boolean isStopped(){
		return (movingUp==0&&movingDown==0&&movingRight==0&&movingLeft==0);
	}
}
