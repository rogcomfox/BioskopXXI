package Assets;

import Bioskop.DatabaseUser;

public class SilverUser extends DatabaseUser {

    public SilverUser(String username, String password, String account, double TopUp, int film){
        super(username, password, account, TopUp, film);
    }

    @Override
    public double cashback(double total) {
        return 0;
    }
}
