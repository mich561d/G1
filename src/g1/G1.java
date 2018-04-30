package g1;

/**
 * @author Michael, Christian og Jesper
 */
import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class G1 implements PlayerFactory<BattleshipsPlayer> {

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new MensaShooter();
    }

    @Override
    public String getID() {
        return "G1";
    }

    @Override
    public String getName() {
        return "Mensa Shooter";
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"Christian Ryge, Jesper Petersen og Michael Due"};
        return res;
    }

}
