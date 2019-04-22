package Assets;

public class BannedException extends Exception {
    private int tries;
    private String username;

    public BannedException(String username, int tries){
        this.username = username;
        this.tries = tries;
    }
    public String toString(){
        if (tries == 0){
            return String.format("Akun dengan username %s telah diblokir", username);
        } else {
            return String.format("Password salah. Akun anda memiliki kesempatan %d kali sebelum terblokir", tries);
        }
    }
}
