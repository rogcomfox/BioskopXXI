package Bioskop;

import Assets.BioskopSystem;

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
        } while (pil != 3);
    }
}
