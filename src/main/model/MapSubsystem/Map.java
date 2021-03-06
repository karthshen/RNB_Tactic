package model.MapSubsystem;

import model.Managers.SectorAdjacencyManager;
import model.Managers.SectorToWaterwayManager;
import model.Managers.WaterwayAdjacencyManager;
import model.Managers.WaterwayToSectorManager;
import model.TileSubsystem.HexSide;
import model.TileSubsystem.Tiles.LandTile;
import model.TileSubsystem.Tiles.RiverTile;
import model.TileSubsystem.Tiles.SeaTile;
import model.TileSubsystem.Tiles.Tile;
import model.TileSubsystem.Visitor.LandTileValidationVisitor;
import model.TileSubsystem.Visitor.RiverTileValidationVisitor;
import model.TileSubsystem.Visitor.TileVisitor;

import java.util.HashMap;

import static java.lang.Math.abs;

/**
 * Created by hankerins on 3/26/17.
 */
public class Map {
    HashMap<Location, Tile> tiles;
    private LandMap landMap = new LandMap();
    private WaterwayMap waterwayMap = new WaterwayMap();

    public Map(){
        tiles = new HashMap<Location, Tile>();
    }

    public boolean addLandTile(LandTile tile, Location location) {
        boolean valid;
        valid = validateLandTilePlacement(tile, location) ;
        if (!valid) {
            System.out.println("river validation failed\n");
            return valid;
        }
        return addTile(tile, location);
    }

    public boolean addRiverTile(RiverTile tile, Location location) {
        boolean valid;
        valid = validateRiverTilePlacement(tile, location) ;
        if (!valid) {
            System.out.println("river validation failed\n");
            return valid;
        }
        return addTile(tile, location);
    }

    public boolean addSeaTile(SeaTile tile, Location location) {

        return addTile(tile, location);
    }

    private boolean addTile(Tile tile, Location location) {
        if(tiles.containsKey(location)){
            System.out.println("already tile there\n");
            return false;
        }
        if(!validateAdjacentToExistingTiles(location)){
            System.out.println("not adjacent existing map\n");
            return false;
        }
        tiles.put(location, tile);
        System.out.println("map.addTile() succeeds\n");
        return true;
    }


    private boolean validateAdjacentToExistingTiles(Location location){
        if(!tiles.isEmpty()){

            if(getAdjacentTiles(location).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Tile getTile(Location location){
        return tiles.get(location);
    }


    public HashMap<HexSide, Tile> getAdjacentTiles(Location location) {
        HashMap<HexSide, Tile> adjacentTiles =new HashMap<HexSide, Tile>();
        for(HexSide hs: HexSide.values()){
            Tile t = tiles.get(location.getAdjacentLocation(hs));
            if(t != null){
                adjacentTiles.put(hs, t);
            }

        }
        return adjacentTiles;
    }

    public boolean validateTilePlacement(TileVisitor v) {
        boolean isValid = true;
        return isValid;
    }

    public SectorAdjacencyManager generateSectorAdjacencyManager(){
        return landMap.generateSectorAdjacencyManager();
    }
    public WaterwayAdjacencyManager generateWaterwayAdjacencyManager(){
        return waterwayMap.generateWaterwayAdjacencyManager();
    }

    public SectorToWaterwayManager generateSectorToWaterwayManager(){
        return landMap.generateSectorToWaterwayManager(waterwayMap);
    }

    public WaterwayToSectorManager generateWaterwayToSectorManager(){
        return waterwayMap.generateWaterwayToSectorManager(landMap);
    }

    /*  MOVED THIS TO LANDMAP, WATERWAYMAP.  FORMAT THEM IN MAP, THEN CALL THEIR GENERATE FUNCTIONS
    public SectorAdjacencyManager generateSectorAdjacencyManager(){
        SectorAdjacencyManager sam = new SectorAdjacencyManager();
        for(Location loc: tiles.keySet()){
            addLocationToSectorAdjacencyMatrix(loc, sam);
        }
        return sam;
    }

    private void addLocationToSectorAdjacencyMatrix(Location loc, SectorAdjacencyManager sam){
        HashMap<HexSide, Tile> adjacents = getAdjacentWaterways(loc);
        for(Sector s: tiles.get(loc).getSectors()) {
            sam.add(s, createSectorAdjacency(loc, s));
        }
    }

    private SectorAdjacency createSectorAdjacency(Location loc, Sector s){
        SectorAdjacency sa = new SectorAdjacency();
        for(CardinalDirection cd: s.getHalfEdges()){
            Tile t = tiles.get(loc.getAdjacentLocation(cd.toHexSide()));
            if(t != null){
                sa.add(cd, t.getSectorAtCardinalDirection(cd.getOppositeSide()));
            }
        }
        return sa;
    }*/

    public void formatSurfaceMaps(){
        landMap = new LandMap();
        waterwayMap = new WaterwayMap();

        for(Location l: tiles.keySet()){
            tiles.get(l).addToSurfaceMap(l, landMap, waterwayMap);
        }
    }


    public boolean validateRiverTilePlacement(RiverTile riverTile, Location location){
        boolean isValid = true;
        HashMap<HexSide, Tile> adjacentTiles = getAdjacentTiles(location);
        
        for(HexSide hs: adjacentTiles.keySet()){
            RiverTileValidationVisitor visitor = new RiverTileValidationVisitor(riverTile, hs);
            adjacentTiles.get(hs).accept(visitor);
            if(!visitor.isValid())
                isValid = false;
            //validate the tile at this hexside with the source tile
        }
        return isValid;
    }

    public boolean remove(Location location) {
        return tiles.remove(location) != null;
    }

    public boolean validateLandTilePlacement(LandTile landTile, Location location) {
        boolean isValid = true;
        HashMap<HexSide, Tile> adjacentTiles = getAdjacentTiles(location);

        for (HexSide hs : adjacentTiles.keySet()) {
            LandTileValidationVisitor visitor = new LandTileValidationVisitor(landTile, hs);
            adjacentTiles.get(hs).accept(visitor);
            if (!visitor.isValid())
                isValid = false;
            //validate the tile at this hexside with the source tile
        }
        return isValid;
    }

    public void updateCenterOfGravity(){

        Location newCenter = getCentralLocation();

        tiles = createNewMapAroundLocation(newCenter);
    }

    private Location getCentralLocation(){

        double xAvg, yAvg, zAvg;
        int xCount, yCount, zCount, tileCount;

        xCount = 0;
        yCount = 0;
        zCount = 0;
        tileCount = 0;

        for(Location loc: tiles.keySet()){

            xCount += loc.getX();
            yCount += loc.getY();
            zCount += loc.getZ();

            tileCount++;
        }

        xAvg = (double)xCount/tileCount;
        yAvg = (double)yCount/tileCount;
        zAvg = (double)zCount/tileCount;

        return getLegalLocationFromFloat(xAvg, yAvg, zAvg);
    }

    private Location getLegalLocationFromFloat(double x, double y, double z){

        int newX = (int) Math.rint(x);
        int newY = (int) Math.rint(y);
        int newZ = (int) Math.rint(z);

        if((newX + newY + newZ) != 0){

            double xDiff = abs( .5 - ( x % 1.0 ) );
            double yDiff = abs( .5 - ( x % 1.0 ) );
            double zDiff = abs( .5 - ( x % 1.0 ) );

            if(xDiff <= yDiff && xDiff <= zDiff){

                newX = -(newY + newZ);

            }else if(yDiff <= xDiff && yDiff <= zDiff){

                newY = -(newX + newZ);

            }else if(zDiff <= xDiff && zDiff <= yDiff){

                newZ = -(newX + newY);
            }
        }

        return new Location(newX, newY, newZ);
    }

    private HashMap<Location, Tile> createNewMapAroundLocation(Location center){

        HashMap<Location, Tile> newMap = new HashMap<>();

        for(Location target: tiles.keySet()){

            Tile tile = tiles.get(target);
            Location newLoc = center.getRelativeLocation(target);

            newMap.put(newLoc, tile);
        }

        return newMap;
    }

    public HashMap<Location, Tile> getMap(){
        return tiles;
    }
    public void setMap(HashMap<Location, Tile> gameMap){
        this.tiles = gameMap;
    }


    //For testing only

    public LandMap getLandMap() {
        return landMap;
    }

    public WaterwayMap getWaterwayMap() {
        return waterwayMap;
    }

    public String toString(){
        String s = "";
        for(Location l: tiles.keySet()){
            s += (l.toString() + " " + tiles.get(l).getTerrain().toString() + "\n");
        }
        return s;
    }
}
