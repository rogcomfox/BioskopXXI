package Assets;

import Bioskop.DatabaseUser;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BioskopSystem {
    private int id;

    private ArrayList<DatabaseUser> Accounts = new ArrayList<>();
    private StudioBioskop[] studio = new StudioBioskop[4];

    public BioskopSystem(){
        studio[0] = new StudioBioskop("Fast and Furious 8");
        studio[1] = new StudioBioskop("Si Juki The Movie");
        studio[2] = new StudioBioskop("Pacific Rim");
        studio[3] = new StudioBioskop("The Conjuring");
    }

    public void login(String username, String password) throws UsernameException, BannedException{
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
    public void available(String username)throws UsernameException{
        int poin = 0;
        for (DatabaseUser account : Accounts) {
            if (account.getUsername().compareTo(username) == 0) {
                poin++;
            }
        }
        if (poin != 0){
            throw new UsernameException(username);
        }
    }
    public void passcondition(String password) throws PasswordException{
        if (password.length() < 8){
            throw new PasswordException();
        }
    }

    public void tranksaksi(double jumlah){
        Accounts.get(id).topup(jumlah);
    }
    public void TopUpAbove(double TopUp) throws TopUpMinimumException{
        if (TopUp < 10000){
            throw new TopUpMinimumException();
        }
    }
    public void display(){
        Accounts.get(id).toString();
    }
    public double cashback(double jumlah){
        return Accounts.get(id).cashback(jumlah);
    }
    public void balanceenough(int ticket) throws BalanceNotEnoughException{
        if (Accounts.get(id).getBalance() <= ticket * 20000){
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
    public void batalseat(int film, int pil) throws WrongInputException{
        if (pil - 1 < 0 || pil - 1 > Accounts.get(id).OwnTix().get(film - 1).size()) {
            throw new WrongInputException();
        }

        String seatid = Accounts.get(id).OwnTix().get(film - 1).get(pil - 1);
        int index0 = index0seat(seatid);
        int index1 = index1seat(seatid);

        studio[film - 1].seat.get(0)[index0][index1] = false;
        Accounts.get(id).OwnTix().get(film - 1).remove(pil - 1);
    }
    private int index0seat(String a) {
        char index0 = a.charAt(0);
        int hasil = 0;

        switch (index0) {
            case 'A':
                hasil = 0;
                break;
            case 'B':
                hasil = 1;
                break;
            case 'C':
                hasil = 2;
                break;
            case 'D':
                hasil = 3;
                break;
            case 'E':
                hasil = 4;
                break;
        }
        return hasil;
    }
    private int index1seat(String a) {
        int index1seat = Character.getNumericValue(a.charAt(1));

        return index1seat - 1;
    }

    public void cetakTiket() throws IOException, PrintException {
        String judul = null, seat;
        int poin = 0;

        for (int i = 0; i < studio.length; i++) {
            if (Accounts.get(id).OwnTix().get(i).isEmpty()) {
                poin++;
            }
        }

        if (poin >= studio.length) {
            throw new PrintException();
        }

        for (int i = 0; i < studio.length; i++) {
            if (!Accounts.get(id).OwnTix().get(i).isEmpty())
                judul = studio[i].getFilmName();

            for (int j = 0; j < Accounts.get(id).OwnTix().get(i).size(); j++) {
                seat = Accounts.get(id).OwnTix().get(i).get(j);
                assert judul != null;
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\Cetak Hasil\\" + judul.substring(0, 2) + "_eTicket(" + seat + ").txt"), StandardCharsets.UTF_8))) {
                    bw.write("Tiket Bioskop XXI | " + judul + " | " + seat + " | " + Accounts.get(id).getUsername()+ " | " + Accounts.get(id).getAccount());
                }

            }
        }
    }
}
