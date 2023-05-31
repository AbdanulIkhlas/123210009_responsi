/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOImplements;

/**
 *
 * @author Lab Informatika
 */
public interface CRUD {
    String[][] TampilkanData();
    void TambahData(String judul, double alur, double penokohan, double akting, double nilai);
    void HapusData(String judul);
    void UpdateData(String judul, double alur, double penokohan, double akting, double nilai);
}
