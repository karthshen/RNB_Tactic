package utilities.FileManager;

import model.Game;
import model.Goose;
import model.Managers.CargoManager;
import model.Transporters.Donkey;
import model.Transporters.Truck;
import model.Transporters.Wagon;
import model.resources.*;
import model.structures.producers.Product;
import model.structures.producers.Visitor.ProductFileVisitor;
import model.structures.producers.Visitor.ProductVisitor;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Doug on 4/9/2017.
 */
public class FileInfoFactory {

    /*
    ~~~REPOST THIS IF YOU'RE A STRONG INDEPENDENT DESIGN PATTERN~~~~~
                ~~~~[WHO DON'T NEED NO CONDITIONAL LOGIC]~~~~
     */

    //Resources

    public static ResourceFileInfo generateFileInfo(Paper p) {
        return new ResourceFileInfo("Paper");
    }

    public static ResourceFileInfo generateFileInfo(Stone s) {
        return new ResourceFileInfo("Stone");
    }

    public static ResourceFileInfo generateFileInfo(Stock s) {
        return new ResourceFileInfo("Stock");
    }

    public static ResourceFileInfo generateFileInfo(Trunk t) {
        return new ResourceFileInfo("Trunk");
    }

    public static ResourceFileInfo generateFileInfo(Iron i) {
        return new ResourceFileInfo("Iron");
    }

    public static ResourceFileInfo generateFileInfo(Gold g) {
        return new ResourceFileInfo("Gold");
    }

    public static ResourceFileInfo generateFileInfo(Fuel f) {
        return new ResourceFileInfo("Fuel");
    }

    public static ResourceFileInfo generateFileInfo(Coin c) {
        return new ResourceFileInfo("Coins");
    }

    public static ResourceFileInfo generateFileInfo(Clay c) {
        return new ResourceFileInfo("Clay");
    }

    public static ResourceFileInfo generateFileInfo(Board b) {
        return new ResourceFileInfo("Board");
    }

    //Land Transporters

    public static TransporterFileInfo generateFileInfo(Donkey d, CargoManager manager) {
        ArrayList<FileInfo> cargo = new ArrayList<>();
        TransporterFileInfo donkeyInfo = new TransporterFileInfo("Donkey", d.getPlayerID());
        ProductFileVisitor visitor = new ProductFileVisitor();
        if (manager.get(d) != null) {
            for (Product p : manager.get(d)) {
                p.accept(visitor);
                cargo.add(visitor.getFileInfo());
            }
        }
        for (FileInfo c: cargo) {
            donkeyInfo.addCargo(c);
        }

        return donkeyInfo;
    }

    public static TransporterFileInfo generateFileInfo(Wagon wagon, CargoManager manager) {
        ArrayList<FileInfo> cargo = new ArrayList<>();
        TransporterFileInfo wagonInfo = new TransporterFileInfo("Wagon", wagon.getPlayerID());
        ProductFileVisitor visitor = new ProductFileVisitor();
        if (manager.get(wagon) != null) {
            for (Product p: manager.get(wagon)) {
                p.accept(visitor);
                cargo.add(visitor.getFileInfo());
            }
        }
        for (FileInfo c: cargo) {
            wagonInfo.addCargo(c);
        }

        return wagonInfo;
    }

    public static TransporterFileInfo generateFileInfo(Truck truck, CargoManager manager) {
        ArrayList<FileInfo> cargo = new ArrayList<>();
        TransporterFileInfo truckInfo = new TransporterFileInfo("Truck", truck.getPlayerID());
        ProductFileVisitor visitor = new ProductFileVisitor();
        if (manager.get(truck) != null) {
            for (Product p: manager.get(truck)) {
                p.accept(visitor);
                cargo.add(visitor.getFileInfo());
            }
        }
        for (FileInfo c: cargo) {
            truckInfo.addCargo(c);
        }

        return truckInfo;
    }


    //Sea Transporters


    //Goose

    public static GooseFileInfo generateFileInfo(Goose g) {return new GooseFileInfo(); }
}

