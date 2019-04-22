package Bioskop;

import java.util.ArrayList;

public abstract class DatabaseUser {
    private ArrayList<ArrayList<String>> FilmTix = new ArrayList<ArrayList<String>>();

    private String username, password, account;
    private double balance;
    private int tries = 3;

    public DatabaseUser(String username, String password, String account, double TopUp, int film){
        this.username = username;
        this.password = password;
        this.account = account;
        balance += TopUp;
        setFilm(film);
    }

    private void setFilm(int film) {
        for (int i = 0; i < film; i++){
            FilmTix.add(new ArrayList<String>());
        }
    }

    public double getBalance() {
        return balance;
    }

    public int getTries() {
        return tries;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getAccount() {
        return account;
    }

    public int resetPassword(){
        return tries = 3;
    }

    public int banned(){
        return tries--;
    }

    public double topup(double topup){
        return balance+=topup;
    }

    public ArrayList<ArrayList<String>> OwnTix(){
        return FilmTix;
    }

    public abstract double cashback(double total);

    public void order(int datafilm, String seat){
        this.FilmTix.get(datafilm).add(seat);
    }
}
