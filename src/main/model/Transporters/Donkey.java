package model.Transporters;

import model.Animal;
import model.Managers.LandTransporterManager;

/**
 * Created by hankerins on 4/8/17.
 */
public class Donkey extends LandTransporter implements Animal {
    public Donkey(LandTransporterManager landTransporterManager){
        super((landTransporterManager));
    }

    /*@Override
    public Donkey reproduce() {
       return new Donkey();
    }*/
}
