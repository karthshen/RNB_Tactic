package model.TileSubsystem;

import model.Abilities.buildAbilities.LandProducerBuildAbility;
import model.Managers.LandTransporterManager;
import model.Managers.ResourceManager;
import model.Transporters.LandTransporter;
import model.resources.Visitor.BuildClayPitAbilityVisitor;

import java.util.ArrayList;

/**
 * Created by hankerins on 3/26/17.
 */
public class Sector {
    ArrayList<CardinalDirection> halfEdges = new ArrayList<CardinalDirection>();

    public Sector(CardinalDirection start, CardinalDirection finish){
        halfEdges.add(start);
        while (start != finish){
            start = start.next();
            halfEdges.add(start);
        }
    }

    public boolean contains(CardinalDirection cd){
        return halfEdges.contains(cd);
    }

    public ArrayList<CardinalDirection> getHalfEdges() {
        return halfEdges;
    }

    //for testing (EDIT: and saving) only
    public String toString(){
        String spacer = "";
        String s = "(";
        for(CardinalDirection cd: halfEdges){
            s += (spacer + cd.name());
            spacer = " ";
        }
        s += ")";
        return s;
    }

    public ArrayList<LandTransporter> getTransporters(LandTransporterManager ltm){
        return ltm.getContentsOfArea(this);
    }
 }
