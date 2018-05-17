package g1;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class G1 implements PlayerFactory<BattleshipsPlayer> {

    @Override
    public BattleshipsPlayer getNewInstance() {
        return new KDM_Absalon();
    }

    @Override
    public String getID() {
        return "G1";
    }

    @Override
    public String getName() {
        return "KDM Absalon";
    }

    @Override
    public String[] getAuthors() {
        String[] res = {"Michael P", "Christian R", "Jesper P"};
        return res;
    }
}
