package Assets;

public class NoFilmException extends Exception {

    private String filmName;

    public NoFilmException(String filmName){
        this.filmName = filmName;
    }
    public String toString(){
        return String.format("Anda Belum Meiliki Tiket Film %s", filmName);
    }
}
