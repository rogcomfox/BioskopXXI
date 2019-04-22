package Assets;

public class UsernameException extends Exception {
    private String username;

    public UsernameException(String username){
        this.username = username;
    }
    public String toString(){
        return String.format("%s tidak ada dalam database silahkan melakukan registrasi username", username);
    }
}
