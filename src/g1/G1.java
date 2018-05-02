package g1;

/**
 * @author Michael
 */
import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class G1 implements PlayerFactory<BattleshipsPlayer> {

    // SN = Shooter Number
    //private final int SN = 0; // 0 = Anti Fllet - 1 = Anti Strat - 2 = Mensa

    @Override
    public BattleshipsPlayer getNewInstance() {
//        BattleshipsPlayer player = null;
//        switch (SN) {
//            case 0:
//                player = new ShooterAntiFleet();
//                break;
//            case 1:
//                player = new ShooterAntiStrat();
//                break;
//            case 2:
//                player = new ShooterMensa();
//                break;
//            default:
//                player = new ShooterAntiFleet();
//                break;
//        }
//        return player;
        return new ShooterAntiFleet();
    }

    @Override
    public String getID() {
        return "G1";
    }

    @Override
    public String getName() {
        String name = "AntiFleet";
//        switch (SN) {
//            case 0:
//                name = "AntiFleet";
//                break;
//            case 1:
//                name = "AntiStrat";
//                break;
//            case 2:
//                name = "Mensa";
//                break;
//            default:
//                name = "AntiFleet";
//                break;
//        }
        return name;
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"Christian Ryge, Jesper Petersen og Michael Due"};
        return res;
    }

}
