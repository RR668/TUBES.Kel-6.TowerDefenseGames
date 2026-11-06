public class Menara {
    // atribut menara
    private String jenis;
    private int baris;
    private int kolom;
    private int damage;
    private int jangkauan;
    private int harga;

    // constructor menara
    public Menara(String jenis, int baris, int kolom, int damage, int jangkauan, int harga) {
        this.jenis = jenis;
        this.baris = baris;
        this.kolom = kolom;
        this.damage = damage;
        this.jangkauan = jangkauan;
        this.harga = harga;
    }

    // Poin 1 : checker jangkauan musuh terhadap menara dengan menghitung jarak Manhattan antara menara dan musuh.
    public boolean dalamJangkauan(Musuh m) {
        int jarak = Math.abs(baris - m.getBaris()) + Math.abs(kolom - m.getKolom());
        return jarak <= jangkauan;
    }

    // Poin 2 : mengurangi HP musuh saat dalam jangkauan menara
    public boolean serang(Musuh m) {
        if (dalamJangkauan(m) && m.masihHidup()) {
            m.terkena(damage);
            return true;
        }
        return false;
    }

    // getter untuk atribut menara
    public String getJenis() {
        return jenis;
    }
    public int getBaris() {
        return baris;
    }
    public int getKolom() {
        return kolom;
    }
    public int getDamage() {
        return damage;
    }
    public int getJangkauan() {
        return jangkauan;
    }
    public int getHarga() {
        return harga;
    }
}