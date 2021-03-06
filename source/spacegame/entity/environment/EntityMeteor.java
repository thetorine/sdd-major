package spacegame.entity.environment;

import spacegame.core.*;
import spacegame.entity.*;

public class EntityMeteor extends EntityBase {
	
	@Override
	public void update(int delta) {
		super.update(delta);
		getVector().rotation += Math.PI/90f*delta/1000f;
	}

	@Override
	public EntityType getType() {
		return EntityType.Meteor;
	}

	@Override
	public void setSpawnCoords() {
		
	}
	
	@Override
	public int getMaxVelocity() {
		return 0;
	}

	@Override
	public int getAcceleration() {
		return 0;
	}

	@Override
	public void setModel() {
		this.model = AssetManager.getCustomImageByName("moon");
	}

	@Override
	public String getModelName() {
		return "moon";
	}

	@Override
	public void onLoad() {
		
	}

	@Override
	public int getRenderWeight() {
		return 3;
	}
}
