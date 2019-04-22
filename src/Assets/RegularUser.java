package Assets;

import Bioskop.DatabaseUser;

public class RegularUser extends DatabaseUser {

    public RegularUser(String username, String password, String account, double TopUp, int film){
        super(username, password, account, TopUp, film);
    }

    @Override
    public double cashback(double total) {
        return 0;
    }
}
