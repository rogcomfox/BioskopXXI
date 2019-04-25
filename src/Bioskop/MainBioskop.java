package Bioskop;

import Assets.*;

import java.io.IOException;
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
        double TopUp = 0;
        System.out.println("Menu Daftar");
        System.out.print("Masukkan Username = ");
        String username = in.next();

        try {
            sistem.available(username);
        } catch (UsernameException m){
            System.out.println("\n"+m+"\n");
        }
        System.out.print("Masukkan Password = ");
        String password = in.next();

        try {
            sistem.passcondition(password);
        } catch (PasswordException p){
            System.out.println("\n"+p+"\n");
        }

        while (TopUp < 10000){
            System.out.println("Menu TopUp (Dalam Rp.)");
            System.out.println("10.000 - 19.999 -> Regular");
            System.out.println("20.000 - 49.999 -> Silver");
            System.out.println("50.000 - 99.999 -> Gold");
            System.out.println(">= 100.000      -> Platinum");
            System.out.print("Masukkan Nominal TopUp = ");
            TopUp = in.nextDouble();
            System.out.println();
            try {
                sistem.TopUpAbove(TopUp);
            }catch (TopUpMinimumException t){
                System.out.println("\n"+t);
            }
        }
        try {
            sistem.SignUp(username, password, TopUp);
        } catch (Exception ignored){

        }
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
                case 2: jumlahtiket(in ,sistem);break;
                case 3: bataltiket(in, sistem);break;
                case 4: cetaktiket(sistem);break;
            }
        } while (pil != 5);
    }

    private static void MenuTopup(Scanner in, BioskopSystem sistem) {
        System.out.print("Masukkan Nominal Top Up = ");
        double jumlah = in.nextDouble();

        try {
            sistem.TopUpAbove(jumlah);
        } catch (TopUpMinimumException t){
            System.out.println("\n"+t);
        }
        System.out.println("Berhasil!");
        System.out.println();
    }

    private static void cetaktiket(BioskopSystem sistem) {
        try {
            sistem.cetakTiket();
        } catch (IOException | PrintException io) {
            System.out.println(io);
            return;
        }
        System.out.println("...Mencetak Tiket...");
    }

    private static void bataltiket(Scanner in, BioskopSystem sistem) {
        sistem.displayfilm();
        System.out.print("Input: ");
        int film = in.nextInt();
        System.out.println("---------------------------------");

        try {
            sistem.seatkita(film);
        } catch (WrongInputException| NoFilmException me) {
            System.out.println(me);
            return;
        }

        System.out.print("Pilih Tiket yang Akan Dibatalkan : ");
        int pil = in.nextInt();

        System.out.println("Peringatan, Saldo Anda Hanya dapat dikembalikan 90 % saja, melanjutkan ?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        System.out.println("Masukkan Pilihan = ");
        int sure = in.nextInt();
        switch (sure){
            case 1:
            try {
                sistem.batalseat(film ,pil);
            } catch (WrongInputException w){
                System.out.println("\n"+w+"\n");
            }
            sistem.tranksaksi((20000 * 9) / 10);
            break;
            case 2:
                MainMenu(in, sistem);break;
        }

    }

    private static void jumlahtiket(Scanner in, BioskopSystem sistem) {
        System.out.println("Masukkan Jumlah Tiket (Rp. 20.000 / tiket) = ");
        int ticket = in.nextByte();
        System.out.println("--------------------");
        try {
            sistem.balanceenough(ticket);
        } catch (BalanceNotEnoughException b){
            System.out.println("\n"+b+"\n");
        }
        pickseat(in, sistem, ticket);
    }

    private static void pickseat(Scanner in, BioskopSystem sistem, int ticket) {
        boolean yes;
        sistem.displayfilm();
        System.out.print("Masukkan Pilihan = ");
        int film = in.nextByte();
        System.out.println("------------------------------------");
        String seatid = "";
        for (int i = 0; i <= ticket; i++) {
            yes = false;

            while (!yes) {
                try {
                    sistem.tampilseat(film);
                } catch (WrongInputException w) {
                    System.out.println("\n" + w + "\n");
                }

                System.out.println("Ticket " + i + " Pilih Baris:");
                System.out.println("1. Baris A");
                System.out.println("2, Baris B");
                System.out.println("3. Baris C");
                System.out.println("4. Baris D");
                System.out.println("5. Baris E");
                System.out.println("---------------------------------");
                System.out.print("Masukkan Pilihan = ");
                int row = in.nextByte();
                System.out.println("---------------------------------");

                try {
                    seatid = sistem.seatresult(row);
                    sistem.tampilseat(film);
                } catch (WrongInputException e) {
                    System.out.println("\n" + e + "\n");
                }

                System.out.println("Silahkan Pilih Kolom ");
                System.out.println("1. Kolom 1");
                System.out.println("2. Kolom 2");
                System.out.println("3. Kolom 3");
                System.out.println("4. Kolom 4");
                System.out.println("5. Kolom 5");
                int column = in.nextByte();

                try {
                    sistem.seatresult(column);
                } catch (WrongInputException w) {
                    System.out.println("\n" + w + "\n");
                }
                System.out.printf("Mohon Konfirmasi Pesanan Anda: %s row %s dengan tiket %d, Apakah Sudah Benar? ", seatid, column, i);
                System.out.println("1. Benar");
                System.out.println("2. Salah");
                int jawab = in.nextByte();
                switch (jawab) {
                    case 2:
                        pickseat(in, sistem, ticket);
                    case 1:
                        try {
                            sistem.pesanseat(film, row, column, seatid);
                        } catch (BookFailException bf) {
                            System.out.println("---------------------------------" + bf + "---------------------------------");
                            i--;
                            break;
                        }
                }
            }
            System.out.printf("\nAnda Mendapat Cashback Sebesar Rp %.0f ", sistem.cashback(20000 * ticket));
            sistem.tranksaksi(-((20000 * ticket) - sistem.cashback(20000 * ticket)));
            return;
        }
    }
}
