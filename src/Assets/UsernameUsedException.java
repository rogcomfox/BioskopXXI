package Assets;

public class UsernameUsedException extends Exception {
    private String username;

    public UsernameUsedException(String username){
        this.username = username;
    }
    public String toString(){
        return String.format("%s Telah Terpakai, Silahkan Gunakan Username Lain", username);
    }
}
