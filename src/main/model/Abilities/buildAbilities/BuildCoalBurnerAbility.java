package model.Abilities.buildAbilities;

import model.Game;
import model.Managers.LandProducerManager;
import model.Managers.ResourceManager;
import model.TileSubsystem.Sector;
import model.resources.Board;
import model.structures.producers.primary.ClayPit;
import model.structures.producers.secondary.refinement.CoalBurner;

import java.util.ArrayList;

/**
 * Created by allisonaguirre on 4/11/17.
 */
public class BuildCoalBurnerAbility extends LandProducerBuildAbility {
    private ArrayList<Board> boardArrayList = new ArrayList<>();

    public BuildCoalBurnerAbility(Board b0, Board b1, Board b2) {
        boardArrayList.add(b0);
        boardArrayList.add(b1);
        boardArrayList.add(b2);
    }
    @Override
    public void addToPlayerAbilityAvailabilityList(PlayerAbilityAvailability list) {

    }

    @Override
    public void execute(Sector s, Game game) {
        for (Board board : boardArrayList) {
            game.getResourceManager().remove(s, board);
        }
        game.getLandSecondaryProducerManager().add(s, new CoalBurner(game.getResourceManager()));
    }
}
