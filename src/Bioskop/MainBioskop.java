package Bioskop;

import Assets.BannedException;
import Assets.BioskopSystem;
import Assets.UsernameException;

import java.util.Scanner;

public class MainBioskop {
    public static void main(String[] args) {
        BioskopSystem sistem =new BioskopSystem();
        Scanner in = new Scanner(System.in);
        int pil;
        System.out.println("Bioskop XXI Cabang FILKOM");
        do {
            System.out.println("------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.println("------------------------------");
            System.out.print("Masukkan Pilihan = ");
            pil = in.nextByte();
            switch (pil){
                case 1: Login(in, sistem);break;
                case 2: Register(in, sistem);break;
                case 3:
                    System.out.println("Terimakasih Telah Menggunakan Program Kami");break;
                default:
                    System.out.println("Masukkan Salah, Silahkan Coba Lagi");
            }
        } while (pil != 3);
    }

    private static void Register(Scanner in, BioskopSystem sistem) {
    }

    private static void Login(Scanner in, BioskopSystem sistem) {
        System.out.print("Masukkan Username : ");
        String username = in.next();
        System.out.print("Masukkan Password : ");
        String password = in.next();

        try {
            sistem.login(username, password);
        } catch (UsernameException | BannedException me){
            System.out.println(me);
            return;
        }
        MainMenu(in, sistem);
    }

    private static void MainMenu(Scanner in, BioskopSystem sistem) {
        int pil;
        do{
            System.out.println("------------------------------");
            System.out.println("1. Akun");
            System.out.println("2. Pesan Tiket");
            System.out.println("3. Batal Tiket");
            System.out.println("4. Cetak Tiket");
            System.out.println("5. Keluar");
            System.out.print("Masukkan Pilihan = ");
            pil = in.nextByte();
            switch (pil){
                case 1:
                    sistem.display();
                    System.out.println("1. Top Up");
                    System.out.println("2. Kembali");
                    System.out.print("Masukkan Pilihan = ");
                    pil = in.nextByte();
                    switch (pil){
                        case 1: MenuTopup(in, sistem);break;
                        case 2: MainMenu(in, sistem);break;
                        default:
                            System.out.println("Masukkan Salah");break;
                    }
            }
        } while (pil != 5);
    }

    private static void MenuTopup(Scanner in, BioskopSystem sistem) {
    }

}
