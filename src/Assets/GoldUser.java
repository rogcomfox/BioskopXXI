package Assets;

import Bioskop.DatabaseUser;

public class GoldUser extends DatabaseUser {

    public GoldUser(String username, String password, String account, double TopUp, int film){
        super(username, password, account, TopUp, film);
    }

    @Override
    public double cashback(double total) {
        return total * 0.07;
    }
}
