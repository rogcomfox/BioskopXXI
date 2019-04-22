package Assets;

public class PasswordException extends Exception{
    public String toString(){
        return "Password Kurang dari 8 Karakter!";
    }
}
