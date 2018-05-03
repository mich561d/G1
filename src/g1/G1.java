package g1;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class G1
        implements PlayerFactory<BattleshipsPlayer> {

    public BattleshipsPlayer getNewInstance() {
        return new CMJAI();
    }

    public String getID() {
        return "G1";
    }

    public String getName() {
        return "MensaHunter";
    }

    public String[] getAuthors() {
        String[] res = {"Humeroush, RangerRyge"};
        return res;
    }
}
