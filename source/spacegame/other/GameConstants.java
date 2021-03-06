package spacegame.other;

import org.lwjgl.input.*;
import org.newdawn.slick.*;

public class GameConstants {
	public static int GAME_WIDTH;
	public static int GAME_HEIGHT;
	public static String RESOURCE = "resources/";
	
	public static float WINDOW_SCALE = 1.1f;
	
	public static UnicodeFont[] GAME_FONT = new UnicodeFont[4];
	
	//Keyboard Keys
	public static int UP = Keyboard.KEY_W;
	public static int DOWN = Keyboard.KEY_S;
	public static int LEFT = Keyboard.KEY_A;
	public static int RIGHT = Keyboard.KEY_D;	
	public static int CUT_VELOCITY = Keyboard.KEY_X;
	public static int FIRE_WEAPON = Keyboard.KEY_SPACE;
	public static int CYCLE_WEAPON = Keyboard.KEY_Q;
	public static int PAUSE_MENU = Keyboard.KEY_ESCAPE;
	public static int MAP = Keyboard.KEY_M;
	public static int INVENTORY = Keyboard.KEY_I; //one main ui to open this
	public static int PLANET = Keyboard.KEY_L;
	public static int MAP_ZOOMIN = Keyboard.KEY_Z;
	public static int MAP_ZOOMOUT = Keyboard.KEY_X;
	public static int COMMAND = Keyboard.KEY_RETURN;
	public static int SHOP = Keyboard.KEY_P; //one main ui to open this
	public static int DIAGNOSTICS = Keyboard.KEY_TAB;
	public static int UPGRADE_MENU = Keyboard.KEY_U; //one main ui to open this
}
