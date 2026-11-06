public class Musuh {
    // untuk atribut
    private String nama;
    private int hp;
    private int baris;
    private int kolom;
    private int hadiahKoin;

    public Musuh(String nama, int hp, int baris, int kolom, int hadiahKoin) {
        this.nama = nama;
        this.hp = hp;
        this.baris  = baris;
        this.kolom = kolom;
        this.hadiahKoin = hadiahKoin;
    }

    public void gerak() {
        kolom++; // bergerak ke kanan
    }

    public void terkena(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;    // agar tidak kebablasan ke negatif
        }
    }

    public boolean masihHidup() {
        return hp > 0;
    }

    // getter untuk atribut musuhnuya
    public String getNama() {
        return nama;
    }
    
    public int getHp() {
        return hp;
    }

    public int getBaris() {
        return baris;
    }

    public int getKolom() {
        return kolom;
    }

    public int getHadiahKoin() {
        return hadiahKoin;
    }
}