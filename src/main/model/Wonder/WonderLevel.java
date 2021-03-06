package model.Wonder;

import model.PlayerID;

import java.util.ArrayList;

/**
 * Created by Kevin on 4/12/17.
 */

public class WonderLevel {

    private ArrayList<Brick> bricks = new ArrayList<>();
    private int rowSize;
    private int capacity;
    private int numRows;

    WonderLevel(int rowSize, int numRows){

        this.rowSize = rowSize;
        this.numRows = numRows;
        capacity = rowSize * numRows;
    }

    // returns the score of this level for the player passed
    public int getScore(PlayerID playerID){

        int levelScore = 0;                 // total score to be returned
        int currentLocation = 1;            // holds the brick location we are at in the level

        int numMyBricksInRow = 0;           // how many bricks does this player have in current row
        int numPlayerBricksInRow = 0;       // how many non-neutral bricks are in the current row

        // iterate through all bricks in level
        for(Brick currentBrick : bricks){

            // finds out how many PlayerBricks are in current row
            numPlayerBricksInRow += currentBrick.getValue();

            // find out how many of my Bricks are in current row
            if(currentBrick.getPlayerID() == playerID){

                numMyBricksInRow++;
            }

            if(currentLocation % rowSize == 0){           // if last brick in the row

                // calculate row score according to rules
                int rowScore = (int)(numMyBricksInRow * (10.0/numPlayerBricksInRow));

                // add to total score for the level
                levelScore += rowScore;

                // reset row counts, advancing to next row
                numMyBricksInRow = 0;
                numPlayerBricksInRow = 0;
            }

            currentLocation++;
        }

        return levelScore;
    }

    public boolean addBrick(Brick brick){

        // don't add if level is full
        if(bricks.size() > capacity){
            return false;
        }

        bricks.add(brick);

        return true;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getRowSize(){
        return rowSize;
    }

    public int getNumRows(){
        return numRows;
    }

    public boolean isFull(){

        return capacity == bricks.size();
    }

}
