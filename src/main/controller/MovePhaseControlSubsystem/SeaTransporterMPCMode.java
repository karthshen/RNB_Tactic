package controller.MovePhaseControlSubsystem;

import controller.MovePhaseControlSubsystem.MPCInstructionSubsystem.*;
import model.Managers.*;
import model.Transporters.LandTransporter;
import model.Transporters.SeaTransporter;
import model.Transporters.Transporter;
import model.resources.Resource;
import model.structures.producers.Product;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by hankerins on 4/15/17.
 */
public class SeaTransporterMPCMode implements MovePhaseControlMode {

    private MPCInstructionState currentMPCInstructionState;
    private ArrayList<MPCInstructionState> mpcInstructionStates = new ArrayList<MPCInstructionState>();
    private SeaTransporter currentSeaTransporter;
    private ArrayList<SeaTransporter> seaTransporters;
    private SeaTransporterManager seaTransporterManager;
    private SeaTransporterShoreManager seaTransporterShoreManager;
    private WaterwayAdjacencyManager waterwayAdjacencyManager;
    private WaterwayToSectorManager waterwayToSectorManager;
    private SectorToWaterwayManager sectorToWaterwayManager;
    private LandTransporterManager landTransporterManager;
    private ResourceManager resourceManager;
    private CargoManager cargoManager;

    public SeaTransporterMPCMode(ArrayList<SeaTransporter> seaTransporters, MovePhaseControl context){
        this.seaTransporters = seaTransporters;
        currentSeaTransporter = seaTransporters.get(0);
        seaTransporterManager = context.getSeaTransporterManager();
        seaTransporterShoreManager = context.getSeaTransporterShoreManager();
        waterwayAdjacencyManager = context.getWaterwayAdjacencyManager();
        waterwayToSectorManager = context.getWaterwayToSectorManager();
        sectorToWaterwayManager = context.getSectorToWaterwayManager();
        landTransporterManager = context.getLandTransporterManager();
        resourceManager = context.getResourceManager();
        cargoManager = context.getCargoManager();
        resetCurrentMPCInstructionState();
    }

    public void nextTransporter(Vector<MovePhaseControlObserver> observers) {
        int next = (seaTransporters.indexOf(currentSeaTransporter)+1)
                % seaTransporters.size();
        currentSeaTransporter = seaTransporters.get(next);
        for(MovePhaseControlObserver observer: observers){
            observer.highlightCurrentSeaTransporter(currentSeaTransporter);
        }
    }

    public void previousTransporter(Vector<MovePhaseControlObserver> observers) {
        int previous = (seaTransporters.indexOf(currentSeaTransporter)-1
                + seaTransporters.size()) % seaTransporters.size();
        currentSeaTransporter = seaTransporters.get(previous);
        for(MovePhaseControlObserver observer: observers){
            observer.highlightCurrentSeaTransporter(currentSeaTransporter);
        }
    }

    public void resetCurrentMPCInstructionState(){
        mpcInstructionStates.clear();

        if(waterwayAdjacencyManager.getAdjacency(seaTransporterManager.getLocation(currentSeaTransporter)) != null){
            mpcInstructionStates.add(new MoveMPCIState());
        }
        if(resourceManager.getQuantityInArea(seaTransporterShoreManager.getLocation(currentSeaTransporter)) > 0){
            mpcInstructionStates.add(new PickUpResourceMPCIState());
        }
        if(cargoManager.getQuantityInArea(currentSeaTransporter) > 0){
            if(seaTransporterManager.containsKey(currentSeaTransporter)){
                mpcInstructionStates.add(new WaterwayDropOffMPCIState());
            }
            else mpcInstructionStates.add(new DropOffMPCIState());
        }
        ArrayList<SeaTransporter> others = seaTransporterManager.getNeighbors(currentSeaTransporter);
        if(others.size() > 0){
            mpcInstructionStates.add(new PickUpSeaTransporterMPCIState(others));
        }
        ArrayList<LandTransporter> landTransporters = landTransporterManager.getContentsOfArea(seaTransporterShoreManager.getLocation(currentSeaTransporter));
        if(landTransporters.size() > 0){
            mpcInstructionStates.add(new PickUpLandTransporterMPCIState(landTransporters));
        }
        ArrayList<SeaTransporter> dockedBoats = seaTransporterShoreManager.getNeighbors(currentSeaTransporter);
        if(dockedBoats.size() > 0){
            mpcInstructionStates.add(new PickUpSeaTransporterMPCIState(dockedBoats));
        }
        //check if you're in the water with adjacent dock locations
        if (waterwayToSectorManager.get(seaTransporterManager.getLocation(currentSeaTransporter)).size() > 0){
            mpcInstructionStates.add(new DockMPCIState());
        }
        //check if you're on land with adjacent water locations
        if (sectorToWaterwayManager.get(seaTransporterShoreManager.getLocation(currentSeaTransporter)).size() > 0){
            mpcInstructionStates.add(new DepartMPCIState());
        }
        if(mpcInstructionStates.size() == 0){
            mpcInstructionStates.add(new NoMoveMPCIState());
        }
        currentMPCInstructionState = mpcInstructionStates.get(0);
    }

    public void dropOff(Product product){
        cargoManager.remove(currentSeaTransporter, product);
        product.dropOff(seaTransporterShoreManager.getLocation(currentSeaTransporter));
    }

    public void waterwayDropOff(Product product){
        cargoManager.remove(currentSeaTransporter, product);
        product.dropOff(seaTransporterManager.getLocation(currentSeaTransporter));
    }

    public void pickUpResource(Resource r){
        resourceManager.remove(seaTransporterShoreManager.getLocation(currentSeaTransporter), r);
        cargoManager.add(currentSeaTransporter, r);
    }

    public void pickUpLandTransporter(LandTransporter lt){
        landTransporterManager.removeOccupant(lt);
        cargoManager.add(currentSeaTransporter, lt);
    }
    public void pickUpSeaTransporter(SeaTransporter st){
        seaTransporterManager.removeOccupant(st);
        seaTransporterShoreManager.removeOccupant(st);
        cargoManager.add(currentSeaTransporter, st);
    }

    @Override
    public void notifyObservers(Vector<MovePhaseControlObserver> movePhaseControlObservers) {
        for(MovePhaseControlObserver observer : movePhaseControlObservers){
            observer.updateModeToSeaTransporter();
        }
    }


    public void select() {

        currentMPCInstructionState.select(this);

    }

    public void nextInstruction() {
        int next = (mpcInstructionStates.indexOf(currentMPCInstructionState)+1)
                % mpcInstructionStates.size();
        currentMPCInstructionState = mpcInstructionStates.get(next);
    }

    public void previousInstruction() {
        int previous = (mpcInstructionStates.indexOf(currentMPCInstructionState)-1
                + mpcInstructionStates.size()) % mpcInstructionStates.size();
        currentMPCInstructionState = mpcInstructionStates.get(previous);
    }

    public void cycleLeft() {currentMPCInstructionState.cycleLeft(this);}

    public void cycleRight() {
        currentMPCInstructionState.cycleRight(this);
    }

    public void setStateToMoveSelected() {
        currentMPCInstructionState = new SeaTransporterMoveSelectedState(this);
    }

    public void setStateToDockSelected() {currentMPCInstructionState = new DockSelectedMPCIState(this);}

    public void setStateToDepartSelected() {currentMPCInstructionState = new DepartSelectedMPCIState(this);}

    public void setStateToWaterwayDropOffSelected() {currentMPCInstructionState = new WaterwayDropOffSelectedState(this);}

    public SeaTransporter getCurrentSeaTransporter() {
        return currentSeaTransporter;
    }

    public SeaTransporterManager getSeaTransporterManager() {
        return seaTransporterManager;
    }

    public SectorTransporterManager getSectorTransporterManager() {
        return seaTransporterShoreManager;
    }


    public MPCInstructionState getCurrentMPCInstructionState() {
        return currentMPCInstructionState;
    }

    @Override
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    @Override
    public CargoManager getCargoManager() {
        return cargoManager;
    }

    public void setCurrentMPCInstructionState(MPCInstructionState currentMPCInstructionState) {
        this.currentMPCInstructionState = currentMPCInstructionState;
    }

    public Transporter getCurrentTransporter(){
        return currentSeaTransporter;
    }

    public SeaTransporterShoreManager getSeaTransporterShoreManager() {
        return seaTransporterShoreManager;
    }

    public WaterwayAdjacencyManager getWaterwayAdjacencyManager() {
        return waterwayAdjacencyManager;
    }

    public WaterwayToSectorManager getWaterwayToSectorManager() {
        return waterwayToSectorManager;
    }

    public SectorToWaterwayManager getSectorToWaterwayManager() {
        return sectorToWaterwayManager;
    }

    //testing only
    public String toString(){
        return "Sea Transporter Mode";
    }
    public int currentIndex(){
        return seaTransporters.indexOf(currentSeaTransporter);
    }
}
