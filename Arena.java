import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Arena {
    public static final int UKURAN = 8; // untuk set urutan arena.

    // atribut arena
    private String[][] grid = new String[UKURAN][UKURAN];
    private List<Menara> daftarMenara = new ArrayList<>();
    private List<Musuh> daftarMusuh = new ArrayList<>();
    public String spasi = "   "; // untuk spasi di interface";

    public Arena() {
        perbaruiGrid();
    }

    private void perbaruiGrid() {
        for (int i = 0; i < UKURAN; i++) {
            for (int j = 0; j < UKURAN; j++) {
                grid[i][j] = ".";
            }
        }
        for (Menara m : daftarMenara) {
            grid[m.getBaris()][m.getKolom()] = "M";
        }

        for (Musuh m : daftarMusuh) {
            if (m.masihHidup()) {
                grid[m.getBaris()][m.getKolom()] = "E";
            }
        }
    }

    public void tampilkanArena() {
        perbaruiGrid();
        String garis = "+---";  
        String garisAkhir = "+";
        System.out.println(spasi + "Denah Arena (M = Menara, E = Musuh, . = Kosong)");
        System.out.print(spasi + "  ");
        for (int angka = 0; angka < UKURAN; angka++) {
            System.out.print(angka + "   ");
        }
        System.out.println();
        for (int i = 0; i < UKURAN; i++) {
            System.out.println(spasi + garis.repeat(UKURAN) + garisAkhir);
            System.out.print(" "+ i + " ");
            for (int j = 0; j < UKURAN; j++) {
                System.out.print("| " + grid[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(spasi + garis.repeat(UKURAN) + garisAkhir);
    }

    // Interface daftar menara
    public void tampilkanDaftarMenara() {
        System.out.println(spasi + "Daftar Menara :");
        if (daftarMenara.isEmpty()) {
            System.out.println(spasi + "(Belum ada Menara)");
            return;
        }
        String garisDaftarMenara = "+-----+------------+--------+---------+--------+"; // prinsip dont repeat yourself (DRY)
        System.out.println(spasi + garisDaftarMenara);
        System.out.println(spasi + "| No. | Jenis      | Posisi | Damage  | Range  |");
        System.out.println(spasi + garisDaftarMenara);
        int nomor = 1;
        for (Menara m : daftarMenara) {
            System.out.printf(spasi + "|  %-2d | %-9s  | (%d,%d)  | %-7d | %-6d |%n",
                    nomor, m.getJenis(), m.getBaris(), m.getKolom(), m.getDamage(), m.getJangkauan());
            nomor++;
        }
        System.out.println(spasi + garisDaftarMenara);

    }

    // Interface daftar musuh
    public void tampilkanDaftarMusuh() {
        System.out.println(spasi + "Daftar Musuh :");
        if (daftarMusuh.isEmpty()) {
            System.out.println(spasi + "(tidak ada Musuh)");
            return;
        }
        String garisDaftarMusuh = "+----------+------+---------+"; // prinsip dont repeat yourself (DRY)
        System.out.println(spasi + garisDaftarMusuh);
        System.out.println(spasi + "| Nama     | HP   | Posisi  |");
        System.out.println(spasi + garisDaftarMusuh);
        for (Musuh m : daftarMusuh) {
            System.out.printf(spasi + "| %-8s | %-4d | (%d,%d)   |%n",
                    m.getNama(), m.getHp(), m.getBaris(), m.getKolom());
        }
        System.out.println(spasi + garisDaftarMusuh);
    }

    // Validasi posisi menara agar tidak keluar dari arena dan tidak menabrak menara lain
    public boolean pasangMenara(Menara m) {
        if (m.getBaris() < 0 || m.getBaris() >= UKURAN || m.getKolom() < 0 || m.getKolom() >= UKURAN) {
            System.out.println(spasi + "[!] Posisi menara di luar arena!");
            return false; // posisi menara di luar arena
        }
        perbaruiGrid(); // update grid sebelum validasi posisi menara
        for (Menara ditempati : daftarMenara) {
            if (ditempati.getBaris() == m.getBaris() && ditempati.getKolom() == m.getKolom()) {
                System.out.println(spasi + "[!] Posisi menara sudah ditempati!");
                return false; // posisi menara sudah ditempati menara lain
            }
        }
        daftarMenara.add(m);
        return true;
    }

    public void tambahMusuh(Musuh m) {
        daftarMusuh.add(m);
    }
    // mengecek masih ada musuh
    public boolean adaMusuh() {
        return !daftarMusuh.isEmpty();
    }

    // Point2 - musuh maju tiap tahap
    public void updateMusuh() {
        for (Musuh m : daftarMusuh) {
            if (m.masihHidup()) {
                m.gerak();
            }
        }
    }
    
    // Point3 - tiap menara menyerang satu musuh dan menambahkan koin dari mengalahkan musuh.
    public int updateMenara() {
        int totalKoin = 0;
        for (Menara menara : daftarMenara) {
            for (Musuh musuh : daftarMusuh) {
                if (menara.serang(musuh)) {
                    if (!musuh.masihHidup()) {
                        System.out.println(spasi + "[+] " + musuh.getNama() + " telah dikalahkan! Mendapatkan " + musuh.getHadiahKoin() + " koin.");
                        totalKoin += musuh.getHadiahKoin();
                        daftarMusuh.remove(musuh);
                    }
                    break; // Menara hanya menyerang satu musuh per tahap
                }
            }
        }
        return totalKoin;
    }

    // menghapus musuh yang sudah keluar dari arena di kolom terakhir alias lolos
    public int hapusMusuhLolos() {
        int musuhLolos = 0;
        Iterator<Musuh> iterator = daftarMusuh.iterator();
        while (iterator.hasNext()) {
            Musuh m = iterator.next();
            if (m.getKolom() >= UKURAN) {
                System.out.println(spasi + "[!] " + m.getNama() + " telah lolos dari arena! Nyawa berkurang -1");
                musuhLolos++;
                iterator.remove();
            }
        }
        return musuhLolos;
    }

    // Membersihkan semua musuh setelah permainan selesai
    public void bersihkanMusuh() {
        daftarMusuh.clear();
    }
    
}
