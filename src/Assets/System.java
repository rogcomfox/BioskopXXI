package Assets;

import Bioskop.DatabaseUser;

import java.util.ArrayList;

public class System {
    private int id;

    ArrayList<DatabaseUser> Accounts = new ArrayList<>();
    StudioBioskop studio[] = new StudioBioskop[4];

    public System(){
        studio[0] = new StudioBioskop("Fast and Furious 8");
        studio[1] = new StudioBioskop("Si Juki The Movie");
        studio[2] = new StudioBioskop("Pacific Rim");
        studio[3] = new StudioBioskop("The Conjuring");
    }

    public boolean login(String username, String password) throws UsernameException, BannedException{
        int point = 0;

        //mengecek apakah ada akun yang terdaftar atau tidak
        if (Accounts.isEmpty()){
            throw new UsernameException(username);
        }
        for (int i = 0; i <= Accounts.size();i++){
            if (Accounts.get(i).getUsername().compareTo(username) == 0){
                point++;id = i;break;
            }
        }
        //jika tidak ada inputan, program menthrow ke exception usernameException
        if (point == 0){
            throw new UsernameException(username);
        }
        if (Accounts.get(id).getPassword().compareTo(password) != 0 || Accounts.get(id).getTries() <= 0){
            Accounts.get(id).banned();
            throw new BannedException(username, Accounts.get(id).getTries());
        }
        //Jika tidak ada kesalahan, program akan merun statement berikut
        Accounts.get(id).resetPassword();
        return true;
    }
    public void SignUp(String username, String password, double TopUp){}
}
