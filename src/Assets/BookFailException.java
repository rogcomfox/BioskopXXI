package Assets;

public class BookFailException extends Exception {
    private String baris;
    private int kolom;

    public BookFailException(String baris, int kolom){
        this.baris = baris;
        this.kolom = kolom;
    }
    public String toString(){
        return String.format("Kursi Pilihan Anda Pada Baris %S dan Kolom %d Tidak Tersedia, Silahkan Pilih Kursi Lain", baris,kolom);
    }
}
