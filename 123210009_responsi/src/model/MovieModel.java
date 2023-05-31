/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DAOImplements.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static model.Connector.koneksi;

/**
 *
 * @author Lab Informatika
 */
public class MovieModel extends Connector implements DAOImplements.CRUD{

    // menghitung banyak data pada tabel movie
    public int getBanyakData(){
        int jumlahData = 0;
        try {
            statement = koneksi.createStatement();
            String query = "SELECT * FROM movie";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                jumlahData++;
            }
            return jumlahData;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL Error");
            return 0;
        }
    }
    
    // method untuk mengambil data yang ada di database
    @Override
    public String[][] TampilkanData() {
        String data[][]=new String[getBanyakData()][5];
        try{
            int indexData = 0;
            String tampil = "SELECT * from movie";
            statement = koneksi.createStatement();
            ResultSet resultSet = statement.executeQuery(tampil);
            while(resultSet.next()){
                data[indexData][0]=resultSet.getString("judul");
                data[indexData][1]=resultSet.getString("alur");
                data[indexData][2]=resultSet.getString("penokohan");
                data[indexData][3]=resultSet.getString("akting");
                data[indexData][4]=resultSet.getString("nilai");
                indexData++;
            }
            statement.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error !"+e.getMessage());
        }finally{
            return data;
        }
    }
    
    // method untuk insert data movie yang di input ke dalam database
    @Override
    public void TambahData(String judul, double alur, double penokohan, double akting, double nilai) {
        try {
            String tambah="INSERT INTO movie VALUES "+ "('"+judul+"','"+alur+"','"+ penokohan+"','"+akting+"','"+nilai+"')";
            statement = koneksi.createStatement();
            statement.executeUpdate(tambah);
            statement.close();
            JOptionPane.showMessageDialog(null, "Tambah Data Berhasil!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error !"+e.getMessage());
        }
    }

    // method untuk delete data movie
    @Override
    public void HapusData(String judul) {
        try {
            String hapus = "DELETE FROM movie WHERE judul = '"+judul+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(hapus);
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            statement.close();
            System.out.println("true try hapus");
        } catch (Exception e) {
            System.out.println("false catch hapus");
            JOptionPane.showMessageDialog(null, "Error !"+e.getMessage());
        }
    }

    // method untuk update data movie
    @Override
    public void UpdateData(String judul, double alur, double penokohan, double akting, double nilai) {
        try {
            String update = "UPDATE movie SET judul = '" + judul + "', alur = " + alur + ", penokohan = " + penokohan + ", akting = " + akting + ", nilai = " + nilai + " WHERE judul = '" + judul + "'";
            statement=koneksi.createStatement();
            statement.executeUpdate(update);
            statement.close();
            JOptionPane.showMessageDialog(null, "Edit Data Berhasil!");
        }catch (Exception e) {
            System.out.println("UPDATE GAGAL " + e.getMessage());
        }
    }

    
    
}
