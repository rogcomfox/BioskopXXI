package Assets;

import java.util.ArrayList;

public class StudioBioskop {
    private String filmName;
    ArrayList<boolean[][]> seat = new ArrayList<>();

    public StudioBioskop(String filmName){
        this.filmName = filmName;
        seat.add(new boolean[5][5]);
    }

    public String getFilmName() {
        return filmName;
    }

    public boolean[][] getSeat(int hour) {
        return seat.get(hour);
    }
}
