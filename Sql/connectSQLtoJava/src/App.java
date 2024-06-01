
//NPM: 6182201018 - Rayner Cuanda
//NPM: 6182201023 - Gabriela Ivy Effendi
//NPM: 6182201025 - Wilona Theodora Muliadi
//NPM: 6182201044 - Eliana Litani Titaningtyas
import java.sql.*;
import java.util.Scanner;

import com.microsoft.sqlserver.jdbc.SQLServerError;

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        // Create the connection
        Connection connection = null;
        Statement stmt = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver()); // Register JDBC driver

            String jdbcURL = "jdbc:sqlserver://";
            String servername = "RAYNER\\SQLEXPRESS"; // Servername beserta instance name
            String portNumber = ":1433;"; // Port number yang digunakan adalah 1433
            String dbName = "databaseName=Furnitur;"; // Database yang digunakan bernama Furnitur
            String security = "encrypt=true;integratedSecurity=true;trustServerCertificate=true"; 
            // karena menggunakan  windows authenticataion,  maka integratedSecurity = true
            String cURL = jdbcURL + servername + portNumber + dbName + security; // Syntax full dari connection URL

            connection = DriverManager.getConnection(cURL);
            stmt = connection.createStatement();
            System.out.println("Connection Established.");

            printMenu();
            int n; // input user
            String query = "";
            ResultSet res;

            System.out.print("Input no: ");
            n = sc.nextInt();
            if (n == 1) {
                query = login();
                res = stmt.executeQuery(query);
                boolean valid = false;
                while (res.next()) {
                    valid = true;
                    String roles = res.getString("roles");
                    if (valid) {
                        System.out.println("Login berhasil");
                        if (roles.equals("PemilikToko")){
                            homePemilik(stmt);
                        } else
                            homePelanggan();
                    }
                }
                if (!valid) {
                    System.out.println("Login gagal");
                }
            } else if (n == 2) {
                query = "SELECT id, nama FROM Kelurahan";
                res = stmt.executeQuery(query);
                query = register(res);
                stmt.execute(query);
                System.out.println("registrasi selesai");
            } else if (n == 3){
                // break;
            } else
                System.out.println("input tidak valid");
        } catch (Exception e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void printMenu() {
        System.out.println("===== FURNITUR CUSTOM =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    public static String register(ResultSet res) throws SQLException {
        System.out.println("===== REGISTER =====");
        return queryPengguna(res);
    }


    public static String login() {
        System.out.println("===== LOGIN =====");
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.next();
        System.out.print("Password: ");
        String passwords = sc.next();
        return "SELECT roles FROM Pengguna WHERE username='" + username + "' AND passwords='"
                + passwords + "'";
    }

    public static void homePelanggan() {
        System.out.println(
            //typo
                "--------------------------------------- FURNITUR CUSTOM ---------------------------------------");
        System.out.println(
                "0. Logout     1. Lihat Profil     2. Pesan Furnitur     3. Pesan Komponen     4. Histori Pesanan");
    }

    public static void homePemilik(Statement stmt) throws Exception {
        Scanner sc = new Scanner(System.in);
        String query  = "";
        ResultSet res;
        stmt = stmt.getConnection().createStatement();
        System.out.println(
//typo fRunitur
                    "------------------------------------------- FURNITUR CUSTOM -------------------------------------------");
        System.out.println(
                    "0. Logout     1. Data Furnitur     2. Data Komponen     3.  Laporan Penjualan     4. Laporan Pendapatan     5.");
//
        System.out.println("Input no: ");
        int option = sc.nextInt();
        if (option == 0){
            return;
        } else if (option == 1){
            query = "SELECT * FROM Furnitur";
            res = stmt.executeQuery(query);
            queryDataFurnitur(res);
            return;
        } else if (option == 2){
            query = "SELECT * FROM Komponen";
            stmt.execute(query);
            res = stmt.executeQuery(query);
            queryDataKomponen(res);  
        } else if (option == 3){
            //LAPORAN PENJUALAN
            stmt.execute("");
        } else if (option == 4){
            //LAPORAN Pendapatan
        } else {
            return;
        }
    }

    public static String queryKomponen() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Komponen: ");
        String id = sc.nextLine();
        System.out.print("Nama Komponen: ");
        String namaKomponen = sc.nextLine();
        System.out.print("Warna Komponen: ");
        String warnaKomponen = sc.nextLine();
        System.out.print("Ukuran Komponen: ");
        String ukuranKomponen = sc.nextLine();
        System.out.print("Harga Beli: ");
        String hargaBeli = sc.nextLine();
        System.out.print("Harga Jual: ");
        String hargaJual = sc.nextLine();
        System.out.print("Stok: ");
        String stok = sc.nextLine();
        System.out.print("ID Material: ");
        String idmaterial = sc.nextLine();
        return "INSERT INTO Komponen " + "Values(" + "" + id + ", '" + namaKomponen + "', '" + warnaKomponen + "', '"
                + ukuranKomponen + "', " + hargaBeli + ", " + hargaJual + ", " + stok + ", " + idmaterial + ")";
    }

    public static String queryFurnitur() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Furnitur: ");
        String idFurnitur = sc.nextLine();
        System.out.print("Nama Furnitur: ");
        String namaFurnitur = sc.nextLine();
        System.out.print("Warna Furnitur: ");
        String warnaFurnitur = sc.nextLine();
        System.out.print("Ukuran Furnitur: ");
        String ukuranFurnitur = sc.nextLine();
        System.out.print("Biaya Produksi: ");
        String biayaProduksi = sc.nextLine();
        return "INSERT INTO FURNITUR " + "Values(" + "" + idFurnitur + ", '" + namaFurnitur + "', '" + warnaFurnitur
                + "', '" + ukuranFurnitur + "', " + biayaProduksi + ")";
    }

    public static String queryKecamatan() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Kecamatan: ");
        String id = sc.nextLine();
        System.out.print("Nama Kecamatan: ");
        String nama = sc.nextLine();
        return "INSERT INTO Kecamatan " + "Values(" + "" + id + ", '" + nama + "')";
    }

    public static String queryKelurahan() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Kelurahan: ");
        String id = sc.nextLine();
        System.out.print("Nama Kelurahan: ");
        String nama = sc.nextLine();
        System.out.print("ID Kecamatan: ");
        String idKecamatan = sc.nextLine();
        return "INSERT INTO Kelurahan " + "Values(" + "" + id + ", '" + nama + "', " + idKecamatan + ")";
    }

    public static void getFurnitur(ResultSet res, Statement stmt)throws SQLException{
        String query;
        query = "SELECT * FROM Komponen";
        stmt.execute(query);
        res = stmt.executeQuery(query);
        queryDataKomponen(res); 
    }

    public static void queryDataFurnitur(ResultSet res) throws SQLException{
        while (res.next()) {
            // mengambil data set untuk setiap baris (record)
            String idFurnitur = res.getString("idFurnitur");
            String idKomponen = res.getString("idKomponen");
            String nama = res.getString("nama");
            String warna = res.getString("warna");
            String ukuran = res.getString("ukuran");
            String biayaProduksi = res.getString("biayaProduksi");
        
            //atribut
            System.out.printf("%-12s | %-12s | %-20s | %-10s | %-10s | %-15s%n", 
                                "ID Furnitur", "ID Komponen", "Nama", "Warna", "Ukuran", "Biaya Produksi");
            System.out.println("-------------------------------------------------------------------------------------");
        
            //data
            System.out.printf("%-12s | %-12s | %-20s | %-10s | %-10s | %-15s%n", 
                              idFurnitur, idKomponen, nama, warna, ukuran, biayaProduksi);
        }
    }

    public static void queryDataKomponen(ResultSet res) throws SQLException{
        while (res.next()) {
            // mengambil data set untuk setiap baris (record)
            int idKomponen = res.getInt("idKomponen");
            String nama = res.getString("nama");
            String warna = res.getString("warna");
            String ukuran = res.getString("ukuran");
            String hargaBeli = res.getString("hargaBeli");
            String hargaJual = res.getString("hargaJual");
            String stok = res.getString("stok");
            String idMaterial = res.getString("idMaterial");
            
            //atribut
            System.out.printf("%-11s | %-20s | %-10s | %-10s | %-15s | %-15s | %-5s | %-15s%n", 
                                "ID Komponen", "Nama", "Warna", "Ukuran", "harga beli", "harga jual", "stok", "ID Material");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        
            // data
            System.out.printf("%-11s | %-20s | %-10s | %-10s | %-15s | %-15s | %-5s | %-15s%n", 
                              idKomponen, nama, warna, ukuran, hargaBeli, hargaJual, stok, idMaterial);
        }
    }

    public static String queryPengguna(ResultSet res) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nama: ");
        String nama = sc.nextLine();
        System.out.print("Alamat: ");
        String alamat = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String passwords = sc.nextLine();
        System.out.println("1. PemilikToko\n2. Pelanggan");
        System.out.print("Role: ");
        String roles = sc.nextLine();
//role diubah jadi "1"        
        if (roles.equals("1"))
            roles = "PemilikToko";
        else
            roles = "Pelanggan";
        System.out.print("Nomor HP: ");
        String noHP = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        while (res.next()) {
            System.out.println(res.getInt("id") + ". " + res.getString("nama"));
        }
        System.out.print("ID Kelurahan: ");
        int idKelurahan = sc.nextInt();
        return "INSERT INTO Pengguna " + "Values(" + " '" + nama + "', '" + alamat + "', '" + username + "', '"
                + passwords + "', '" + roles + "', '" + noHP + "', '" + email + "', " + idKelurahan + ")";
    }

    public static String queryMaterial() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Material: ");
        String id = sc.nextLine();
        System.out.print("Nama Material: ");
        String nama = sc.nextLine();
        return "INSERT INTO Material " + "Values(" + "" + id + ", '" + nama + "')";

    }

    public static String queryPesanan() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Pesanan: ");
        String id = sc.nextLine();
        System.out.print("Tanggal Pesanan: ");
        String tglPesanan = sc.nextLine();
        System.out.print("ID Pelanggan: ");
        String idPelanggan = sc.nextLine();
        return "INSERT INTO Pesanan " + "Values(" + "" + id + ", '" + tglPesanan + "', " + idPelanggan + ")";
    }

    public static String queryPesanKomponen() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Pesanan: ");
        String id = sc.nextLine();
        System.out.print("ID Komponen: ");
        String idkomp = sc.nextLine();
        return "INSERT INTO PesanKomponen " + "Values(" + "" + id + ", " + idkomp + ")";
    }

    public static String queryPesanFurnitur() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID Pesanan: ");
        String id = sc.nextLine();
        System.out.print("ID Furnitur: ");
        String idFurnitur = sc.nextLine();
        System.out.print("ID Komponen: ");
        String idKomponen = sc.nextLine();
        return "INSERT INTO PesanFurnitur " + "Values(" + "" + id + ", " + idFurnitur + ", " + idKomponen + ")";
    }
}