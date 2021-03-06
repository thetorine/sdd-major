package spacegame.core;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

//manages the sound for the game, loads it and allows playing.
public class SoundManager {

    //game
    public Sound backgroundMusic;
    public Sound laser;
    public Sound spaceTrash;
    public Sound missile;
    public Sound explosion;

    //ui
    public Sound clickPress;
    public Sound switchButton;

    public SoundManager() {
        try {
            backgroundMusic = new Sound("resources/sound/game/bg.ogg");
            laser = new Sound("resources/sound/game/laser1.ogg");
            spaceTrash = new Sound("resources/sound/game/spaceTrash1.ogg");
            missile = new Sound("resources/sound/game/missile.wav");
            explosion = new Sound("resources/sound/game/explosion.wav");

            clickPress = new Sound("resources/sound/ui/click1.wav");
            switchButton = new Sound("resources/sound/ui/switch1.wav");
        } catch (SlickException e) {
            System.err.println("Error loading sounds");
            e.printStackTrace();
        }
    }
}
