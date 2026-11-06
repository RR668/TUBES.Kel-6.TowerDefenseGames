import java.util.Scanner;

public class MainTowerDefense {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Arena arenaKu = new Arena();

        int koin = 100, nyawa = 5, wave = 1;

        System.out.println("   ====================================");
        System.out.println("      Welcome to Tower Defense Game!");
        System.out.println("   ====================================");

        // untuk mengitung gelombang
        for (; wave < 6; ) {
            if (nyawa < 1) break;

            System.out.println("   ====== Gelombang " + wave + " dari 5 ======");

            int sedangBeli = 1;
            do {
                arenaKu.tampilkanArena();
                arenaKu.tampilkanDaftarMenara();

                // Print tabel menara
                System.out.print("\n   Koin : " + koin + ", Nyawa : " + nyawa + "\n" +
                                 "   +-----+-----------+-------+--------+-------+\n" +
                                 "   | No. | Jenis     | Harga | Damage | Range |\n" +
                                 "   +-----+-----------+-------+--------+-------+\n" +
                                 "   | 1   | Panah     | 30    | 10     | 3     |\n" +
                                 "   | 2   | Meriam    | 60    | 20     | 2     |\n" +
                                 "   +-----+-----------+-------+--------+-------+\n" +
                                 "   | 0   | Mulai Gelombang                    |\n" +
                                 "   +-----+------------------------------------+\n");
                System.out.print("   Pilih menara (1-2) atau 0 untuk selesai: ");

                String ketikanUser = in.nextLine();
                int pilih = -1;

                // cek string manual
                if (ketikanUser.equals("0")) {
                    pilih = 0;
                } else if (ketikanUser.equals("1")) {
                    pilih = 1;
                } else if (ketikanUser.equals("2")) {
                    pilih = 2;
                } else {
                    System.out.println("   [!] Input salah.");
                    continue;
                }

                if (pilih == 0) {
                    sedangBeli = 0;
                } else {
                    int hg = (pilih == 1) ? 30 : 60;
                    if (koin < hg) {
                        System.out.println("   [!] Koin tidak cukup untuk memasang menara ini!");
                    } else {
                        System.out.print("   Masukkan baris : ");
                        String bStr = in.nextLine();
                        System.out.print("   Masukkan kolom : ");
                        String kStr = in.nextLine();    

                        // ubah string ke int pake ASCII
                        int brs = bStr.charAt(0) - '0';
                        int klm = kStr.charAt(0) - '0';

                        if (brs >= 0 && brs <= 7 && klm >= 0 && klm <= 7) {
                            Menara mnr;
                            if (pilih == 1) mnr = new Menara("Panah", brs, klm, 10, 3, 30);
                            else mnr = new Menara("Meriam", brs, klm, 20, 2, 60);

                            if (arenaKu.pasangMenara(mnr)) {
                                koin -= hg;
                                System.out.println("   [+] Menara berhasil dipasang!");
                            }
                        } else {
                            System.out.println("   [!] Baris atau kolom di luar batas.");
                        }
                    }
                }
            } while (sedangBeli == 1);

            double factor = 1.0;

            for (int z = 1; z < wave; z++) {
                factor = factor * 1.2;
            }

            int byk = (int) (4 * factor + 0.5);
            int d = (int) (20 * factor + 0.5);
            for (int i = 0; i < byk; i++) {
                arenaKu.tambahMusuh(new Musuh("Musuh#" + (i + 1), d, (i % 8), 0, 10));
            }
            System.out.println("   Musuh datang : " + byk + " musuh dengan HP " + d + "!");

            int thp = 0;
            for (;;) {
                if (!arenaKu.adaMusuh() || nyawa <= 0) break;

                System.out.println("   ====== Gelombang " + wave + " | Tahap " + (thp + 1) + " ======");

                if (thp > 0) arenaKu.updateMusuh();

                nyawa -= arenaKu.hapusMusuhLolos();
                if (nyawa < 0) nyawa = 0;

                if (nyawa > 0) koin += arenaKu.updateMenara();

                arenaKu.tampilkanArena();
                arenaKu.tampilkanDaftarMusuh();
                System.out.println("   Koin : " + koin + ", Nyawa : " + nyawa);

                // note : jeda pakai busy-wait
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 1000) { }

                thp++;
            }

            if (nyawa <= 0) arenaKu.bersihkanMusuh();
            else System.out.println("   [+] Gelombang " + wave + " selesai!");

            wave++;
        }

        System.out.println("   ===================================");
        if (nyawa > 0) {
            System.out.println("   Selamat! Anda berhasil mempertahankan arena!\n   Sisa Nyawa: " + nyawa + " | Koin Tersisa: " + koin);
        } else {
            System.out.println("   Game Over! Arena telah ditembus musuh.\n   Koin Tersisa: " + koin);
        }
        System.out.println("   ===================================");

        in.close();
    }
}
