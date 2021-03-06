package view.assets;

import javafx.scene.image.Image;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

public class Assets {

    private static final Assets INSTANCE = new Assets();
    private HashMap<String, Image> assets = new HashMap<String, Image>();


    private Assets(){}

    static Assets getInstance(){
        return INSTANCE;
    }

    public void loadResources(){
        loadImages();
    }

    public void loadImages(){
        //Load Tiles, rivers
        loadItem("GRASS_TILE", "Assets/Tiles/GrassTile.png");
        loadItem("MOUNTAIN_TILE", "Assets/Tiles/MountainTile.png");
        loadItem("ROCK_TILE", "Assets/Tiles/RockTile.png");
        loadItem("SEA_TILE", "Assets/Tiles/SeaTile.png");
        loadItem("DESERT_TILE", "Assets/Tiles/DesertTile.png");
        loadItem("WOOD_TILE", "Assets/Tiles/WoodTile.png");
        loadItem("NORMAL_RIVER_180", "Assets/Rivers/NormalRiver180.png");
        loadItem("NORMAL_RIVER_60", "Assets/Rivers/NormalRiver60.png");
        loadItem("NORMAL_RIVER_120", "Assets/Rivers/NormalRiver120.png");
        loadItem("SOURCE_RIVER", "Assets/Rivers/SourceRiver.png");
        loadItem("FORKED_RIVER", "Assets/Rivers/ForkedRiver.png");
        loadItem("EMPTY_HEX_GRID","Assets/Tiles/EmptyHexGrid.png");
        loadItem("TILE_SELECTOR", "Assets/Tiles/TileSelector.png");
        //Load Buttons
        loadItem("NEW_MAP_BUTTON", "Assets/Buttons/NewFileButton.png");
        loadItem("FILE_SAVE_BUTTON", "Assets/Buttons/SaveFileButton.png");
        loadItem("FILE_LOAD_BUTTON", "Assets/Buttons/LoadFileButton.png");
        loadItem("START_GAME_BUTTON", "Assets/Buttons/StartGameButton.png");
        loadItem("OPTION_BUTTON","Assets/Buttons/OptionButton.png");
        loadItem("MAP_MAKER_BUTTON", "Assets/Buttons/MapMakerButton.png");
        loadItem("RETURN_MAIN_MENU_BUTTON", "Assets/Buttons/ExitToMainMenu.png");
        loadItem("WONDER_BUTTON", "Assets/Buttons/WonderButton.png");
        //Load Panels, backgrounds
        loadItem("BOTTOM_PANEL", "Assets/Background/BottomPanel.png");
        loadItem("TERRAIN_SELECTED","Assets/MapMakerPreview/TerrainSelected.png");
        loadItem("RIVER_SELECTED","Assets/MapMakerPreview/RiverSelected.png");
        loadItem("FINAL_SELECTED","Assets/MapMakerPreview/FinalSelected.png");
        loadItem("START_GAME_BUTTON_TEXT", "Assets/Buttons/StartGameButtonText.png");
        loadItem("INTRO_BACKGROUND_IMAGE", "Assets/Background/IntroBackground.png");
        loadItem("GAME_BORDER","Assets/Background/GameBorder.png");
        loadItem("SIDE_PANEL_BACKGROUND", "Assets/Background/SidePanelBackground.png");
        loadItem("WONDER_BACKGROUND", "Assets/Background/WonderBackground.png");
        //Load wonder stuff
        loadItem("WONDER_BOARD", "Assets/WonderBoard.png");
        //Load transporters
        loadItem("DONKEY", "Assets/Transporters/Donkey.png");
        loadItem("WAGON","Assets/Transporters/Wagon.png");
        loadItem("TRUCK","Assets/Transporters/Truck.png");
        loadItem("RAFT", "Assets/Transporters/Raft.png");
        loadItem("ROWBOAT", "Assets/Transporters/ROWBOAT.png");
        loadItem("STEAMSHIP", "Assets/Transporters/Steamship.png");
        //Load Resources
        loadItem("BOARD","Assets/Resources/Boards.png");
        loadItem("CLAY","Assets/Resources/Clay.png");
        loadItem("COIN","Assets/Resources/Coin.png");
        loadItem("FUEL","Assets/Resources/Fuel.png");
        loadItem("GOLD","Assets/Resources/Gold.png");
        loadItem("IRON","Assets/Resources/Iron.png");
        loadItem("PAPER","Assets/Resources/Paper.png");
        loadItem("STOCK","Assets/Resources/stock.png");
        loadItem("STONE","Assets/Resources/Stone.png");
        loadItem("TRUNK","Assets/Resources/Trunk.png");
        //Load Primary Producers
        loadItem("CLAY_PIT", "Assets/Producers/PrimaryProducers/Claypit.png");
        loadItem("MINE", "Assets/Producers/PrimaryProducers/Mine.png");
        loadItem("OIL_RIG", "Assets/Producers/PrimaryProducers/OilRig.png");
        loadItem("QUARRY", "Assets/Producers/PrimaryProducers/Quarry.png");
        loadItem("WOOD_CUTTER", "Assets/Producers/PrimaryProducers/Woodcutter.png");
        //Load Secondary Producers
        loadItem("COAL_BURNER", "Assets/Producers/SecondaryProducers/Coalburner.png");
        loadItem("MINT", "Assets/Producers/SecondaryProducers/Mint.png");
        loadItem("PAPER_MILL", "Assets/Producers/SecondaryProducers/Papermill.png");
        loadItem("RAFT_FACTORY", "Assets/Producers/SecondaryProducers/RaftFactory.png");
        loadItem("ROWBOAT_FACTORY", "Assets/Producers/SecondaryProducers/RowboatFactory.png");
        loadItem("SAW_MILL", "Assets/Producers/SecondaryProducers/Sawmill.png");
        loadItem("STEAMER_FACTORY", "Assets/Producers/SecondaryProducers/SteamerFactory.png");
        loadItem("STOCK_EXCHANGE", "Assets/Producers/SecondaryProducers/StockExchange.png");
        loadItem("STONE_FACTORY", "Assets/Producers/SecondaryProducers/StoneFactory.png");
        loadItem("TRUCK_FACTORY", "Assets/Producers/SecondaryProducers/TruckFactory.png");
        loadItem("WAGON_FACTORY", "Assets/Producers/SecondaryProducers/WagonFactory.png");

        //Load Road
        loadItem("ROAD","Assets/Road.png");

        //Load Brick
        loadItem("RED_BRICK","Assets/RedBrick.png");

        //Load Phase Indicator
        loadItem("MOVE_PHASE", "Assets/ControlState/MovePhase.png");
        loadItem("BUILD_PHASE", "Assets/ControlState/BuildPhase.png");
        loadItem("PRODUCTION_PHASE", "Assets/ControlState/ProductionPhase.png");
        loadItem("WONDER_PHASE", "Assets/ControlState/WonderPhase.png");
        loadItem("PHASE_INDICATOR", "Assets/ControlState/PhaseIndicator.png");
        loadItem("UNIT_INDICATOR", "Assets/UnitIndicator.png");
    }

    public void loadItem(String name, String path){
        try {
            File file = new File(path);
            String localUrl = "";
            try {
                localUrl = file.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                System.out.println("Invalid image file: " + name + " " + path);
                e.printStackTrace();
            }
            assets.put(name, new Image(localUrl));
            System.out.println("Loaded item: " + name);
        } catch (NullPointerException e){
            System.out.println();
        }
    }

    public Image getImage(String imageName){return assets.get(imageName);}
}
