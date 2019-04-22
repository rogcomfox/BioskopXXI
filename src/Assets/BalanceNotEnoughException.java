package Assets;

public class BalanceNotEnoughException extends Exception{
    private double balance;
    private int ticket;

    public BalanceNotEnoughException(double balance, int ticket){
        this.balance = balance;
        this.ticket = ticket;
    }
    public String toString(){
        return String.format("Saldo Anda Kurang, Silahkan TopUp Sebesar Rp. %f", ((20000*ticket)-balance));
    }
}
