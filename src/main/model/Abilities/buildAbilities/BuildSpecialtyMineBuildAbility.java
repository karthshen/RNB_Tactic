package model.Abilities.buildAbilities;

import model.Game;
import model.ManagerSupplier;
import model.Managers.LandProducerManager;
import model.Managers.ResourceManager;
import model.TileSubsystem.Sector;
import model.resources.Board;
import model.resources.Stone;

/**
 * Created by allisonaguirre on 4/11/17.
 */
public class BuildSpecialtyMineBuildAbility extends LandProducerBuildAbility {
    public BuildSpecialtyMineBuildAbility(Board b0, Board b1, Board b2, Stone s) {

    }

    @Override
    public void addToPlayerAbilityAvailabilityList(PlayerAbilityAvailability list) {

    }

    @Override
    public void execute(Sector s, ManagerSupplier ms) {

    }
    public String toString(){
        return "Build Specialty Mine";
    }
}
