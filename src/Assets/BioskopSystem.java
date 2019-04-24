package Assets;

import Bioskop.DatabaseUser;

import java.util.ArrayList;

public class BioskopSystem {
    private int id;

    ArrayList<DatabaseUser> Accounts = new ArrayList<>();
    StudioBioskop studio[] = new StudioBioskop[4];

    public BioskopSystem(){
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
    public void SignUp(String username, String password, double TopUp){
        if (TopUp >= 10000 &&  TopUp < 20000){
            Accounts.add(new RegularUser(username, password, "Regular", TopUp, studio.length));
        } else if (TopUp >= 20000 && TopUp < 50000){
            Accounts.add(new SilverUser(username, password, "Silver", TopUp, studio.length));
        } else if (TopUp >= 50000 && TopUp < 100000){
            Accounts.add(new GoldUser(username, password, "Gold", TopUp, studio.length));
        } else if (TopUp >= 100000){
            Accounts.add(new PlatinumUser(username, password, "Platinum", TopUp, studio.length));
        }
    }
    public boolean available(String username)throws UsernameException{
        int poin = 0;
        for (DatabaseUser account : Accounts) {
            if (account.getUsername().compareTo(username) == 0) {
                poin++;
            }
        }
        if (poin != 0){
            throw new UsernameException(username);
        }
        return true;
    }
    public boolean passcondition(String password) throws PasswordException{
        if (password.length() < 8){
            throw new PasswordException();
        }else {
            return true;
        }
    }

    public void tranksaksi(double jumlah){
        Accounts.get(id).topup(jumlah);
    }
    public boolean TopUpAbove(double TopUp) throws TopUpMinimumException{
        if (TopUp < 10000){
            throw new TopUpMinimumException();
        }else {
            return true;
        }
    }
    public String display(){
        return (Accounts.get(id).toString());
    }
    public double cashback(double jumlah){
        return Accounts.get(id).cashback(jumlah);
    }
    public boolean balanceenough(int ticket) throws BalanceNotEnoughException{
        if (Accounts.get(id).getBalance() >= ticket * 20000){
            return true;
        }else{
            throw new BalanceNotEnoughException(Accounts.get(id).getBalance(), ticket);
        }
    }
    //tampilan menu bioskop
    public void tampilseat(int i) throws WrongInputException{
        if (i - 1 < 0 || i > studio.length){
            throw new WrongInputException();
        }
        System.out.println(studio[i-1].getFilmName());
        System.out.println("1 2 3 4 5");
        for (int j = 0; j < 5; j++){
            switch (j){
                case 0:
                    System.out.print("A");
                    break;
                case 1:
                    System.out.print("B");
                    break;
                case 2:
                    System.out.print("C");
                    break;
                case 3:
                    System.out.print("D");
                    break;
                case 4:
                    System.out.print("E");
                    break;
                default:
                    System.out.print("Tidak Ada");
                    break;
            }
            for (int k = 0; k < 5; k++) {
                if (!studio[i - 1].seat.get(0)[j][k]) {
                    System.out.print(" V");
                } else {
                    System.out.print(" X");
                }
            }
            System.out.println();
        }
        System.out.println("---LAYAR---");
    }
    public void displayfilm(){
        for (int i = 0; i < studio.length; i++){
            System.out.print((i+1) + ". " + studio[i].getFilmName());
        }
        System.out.println("----------------------------------");
    }
    public String seatresult(int row) throws WrongInputException{
        if (row < 1 || row > 5){
            throw  new WrongInputException();
        }
        String hasil = null;

        switch (row){
            case 1:
                hasil = "A";
                break;
            case 2:
                hasil = "B";
                break;
            case 3:
                hasil = "C";
                break;
            case 4:
                hasil = "D";
                break;
            case 5:
                hasil = "E";
                break;
            default:
                System.out.println("Tidak Ada");
                break;
        }
        return hasil;
    }
    public void pesanseat(int film, int row, int column, String seatid) throws BookFailException{
        if (studio[film - 1].seat.get(0)[row - 1][column - 1]){
            throw new BookFailException(seatid, row);
        }
        studio[film - 1].seat.get(0)[row - 1][column - 1] = true;
        String seat = seatid + column;
        Accounts.get(id).order(film - 1, seat);
    }

    public void seatkita(int film) throws  NoFilmException,WrongInputException{
        if (film - 1 < 0 || film - 1 >= studio.length) {
            throw new WrongInputException();
        }

        if (Accounts.get(id).OwnTix().get(film - 1).isEmpty()) {
            throw new NoFilmException(studio[film - 1].getFilmName());
        }

        System.out.println(studio[film - 1].getFilmName());
        for (int j = 0; j < Accounts.get(id).OwnTix().get(film - 1).size(); j++) {
            System.out.println((j + 1) + ". " + Accounts.get(id).OwnTix().get(film - 1).get(j));
        }
    }

}
