//Tugas Project Kelompok 4 PBO
//Nama Anggota  :
// 1. Diah Ayu Rahmawati    (210202007)
// 2. Kukuh Mudhaya         (210202013)
// 3. Pinky Andela          (210202016)
// 4. Salma Citra Khamidah  (210302021)

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.util.Scanner;

public class JavaMysql {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";

    static Scanner input = new Scanner(System.in);

    static Connection conn;
    static Statement stat;
    static ResultSet rs;

    static void bukaKoneksi() {
        try {
            // register driver
            Class.forName(JDBC_DRIVER);

            // koneksi database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stat = conn.createStatement();

            if (!conn.isClosed()) {
                // System.out.println("Berhasil koneksi");
            }
        } catch (Exception e) {
            System.out.println("Gagal koneksi");
        }
    }

    static void tutupKoneksi() {
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
        }
    }

    static void displayData() {
        try {
            System.out.println("\n=========================== TAMPIL DATA ============================");
            bukaKoneksi();
            stat = conn.createStatement();

            // query
            String sql = "SELECT * FROM buku";

            // eksekusi query dan simpan hasil di Resultset
            rs = stat.executeQuery(sql);

            // tampilan
            System.out.printf("%-5s %-35s %-20s %-8s\n", "ID", "JUDUL BUKU", "PENGARANG", "TAHUN");
            System.out.println("====================================================================");
            while (rs.next()) {
                System.out.printf("%-5s %-35s %-20s %-8s\n", rs.getString("id_buku"), rs.getString("judul_buku"),
                        rs.getString("pengarang"), rs.getInt("tahun_terbit"));
            }
            stat.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tutupKoneksi();
        }
        System.out.println();
        backToMenu();
    }

    static void insertData() {
        try {
            System.out.println("\n=========================== BUAT DATA ============================");
            System.out.print("Id.Buku\t\t : ");
            String id = input.nextLine().trim();
            System.out.print("Judul Buku\t : ");
            String judul = input.nextLine().trim();
            System.out.print("Pengarang\t : ");
            String pengarang = input.nextLine().trim();
            System.out.print("Tahun Terbit\t : ");
            int tahun = input.nextInt();
            input.nextLine();

            String sql = "Insert into buku values('%s','%s','%s','%d')";
            sql = String.format(sql, id, judul, pengarang, tahun);

            bukaKoneksi();
            stat = conn.createStatement();
            stat.execute(sql);

            System.out.println("Data Berhasil ditambahkan");
        } catch (Exception e) {
            System.out.println("Data gagal ditambahkan");
        }
        System.out.println();
        backToMenu();
    }

    static void updateBuku() {
        try {
            System.out.println("\n=========================== UPDATE DATA ============================");
            bukaKoneksi();
            stat = conn.createStatement();
            String tampilID = "SELECT id_buku FROM buku";
            rs = stat.executeQuery(tampilID);
            System.out.println("Id Buku yang tersedia");
            while (rs.next()) {
                System.out.println(rs.getString("id_buku"));
            }
            System.out.println();
            System.out.print("Id.Buku\t\t : ");

            String id = input.nextLine().trim();
            System.out.print("Judul Buku\t : ");
            String judul = input.nextLine().trim();
            System.out.print("Pengarang\t : ");
            String pengarang = input.nextLine().trim();
            System.out.print("Tahun Terbit\t : ");
            int tahun = input.nextInt();
            input.nextLine();

            String sql = "UPDATE buku SET judul_buku='%s',pengarang='%s',tahun_terbit='%d' WHERE id_buku='%s'";
            sql = String.format(sql, judul, pengarang, tahun, id);

            bukaKoneksi();
            stat = conn.createStatement();
            stat.execute(sql);
            System.out.println("Data berhasil diupdate...");
        } catch (Exception e) {
            System.out.println("Data gagal diupdate...");
        }
        System.out.println();
        backToMenu();
    }

    static void hapusData() {
        try {
            System.out.println("\n=========================== HAPUS DATA ============================");
            bukaKoneksi();
            stat = conn.createStatement();
            String tampilID = "SELECT id_buku FROM buku";
            rs = stat.executeQuery(tampilID);
            System.out.println("Id Buku yang tersedia");
            while (rs.next()) {
                System.out.println(rs.getString("id_buku"));
            }
            System.out.println();
            // ambil input dari user
            System.out.print("ID yang akan dihapus\t : ");
            String id = input.nextLine();

            String sql = String.format("DELETE FROM buku WHERE id_buku='%s'", id);

            bukaKoneksi();
            stat = conn.createStatement();
            stat.execute(sql);
            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            System.out.println("Data gagal dihapus...");
        }
        System.out.println();
        backToMenu();
    }

    static void mencariData() {
        try {
            System.out.println("\n============================ CARI DATA =============================");
            bukaKoneksi();
            stat = conn.createStatement();
            String tampilID = "SELECT id_buku FROM buku";
            rs = stat.executeQuery(tampilID);
            System.out.println("Id Buku yang tersedia");
            while (rs.next()) {
                System.out.println(rs.getString("id_buku"));
            }
            System.out.println();
            System.out.print("ID yang akan dicari\t\t : ");
            String id = input.nextLine();
            System.out.println();

            String sql = String.format("SELECT * FROM buku WHERE id_buku='%s'", id);

            bukaKoneksi();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);

            System.out.println("====================================================================");
            System.out.printf("%-5s %-35s %-20s %-8s\n", "ID", "JUDUL BUKU", "PENGARANG", "TAHUN");
            System.out.println("====================================================================");
            while (rs.next()) {
                System.out.printf("%-5s %-35s %-20s %-8s\n", rs.getString("id_buku"), rs.getString("judul_buku"),
                        rs.getString("pengarang"), rs.getInt("tahun_terbit"));
            }
            stat.close();

            System.out.println("Data berhasil dicari...");
        } catch (Exception e) {
            System.out.println("Data gagal dicari...");
        }
        System.out.println();
        backToMenu();
    }

    static void tampilkanMenu() {
        System.out.println("\n============================ MENU UTAMA =============================");
        System.out.println("1. Membuat Data");
        System.out.println("2. Menampilkan Data");
        System.out.println("3. Mengupdate Data");
        System.out.println("4. Menghapus Data");
        System.out.println("5. Mencari Data");
        System.out.println("6. Keluar dari program");
        System.out.println("");
        boolean kondisi;
        do {
            kondisi = true;
            System.out.print("PILIHAN> ");
            try {
                int pilihan = input.nextInt();
                input.nextLine();

                switch (pilihan) {
                    case 1:
                        insertData();
                        break;
                    case 2:
                        displayData();
                        break;
                    case 3:
                        updateBuku();
                        break;
                    case 4:
                        hapusData();
                        break;
                    case 5:
                        mencariData();
                        break;
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Pilihan salah!");
                }
            } catch (Exception e) {
                kondisi = false;
                input = new Scanner(System.in);
            }
        } while (!kondisi);
    }

    static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            }
        } catch (final Exception e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    static void backToMenu() {
        System.out.println("");
        System.out.print("Tekan [Enter] untuk kembali..");
        input.nextLine();
        clearScreen();
        tampilkanMenu();
    }

    public static void main(String[] args) {
        tampilkanMenu();
        System.out.println();
    }
}