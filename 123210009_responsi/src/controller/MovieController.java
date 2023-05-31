/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.MovieModel;
import view.GUIMovieView;

/**
 *
 * @author Lab Informatika
 */
public class MovieController {

    private MovieModel movieModel;
    private GUIMovieView movieView;
    String judul;
    
    public MovieController(GUIMovieView movieView , MovieModel movieModel) {
        this.movieView = movieView;
        this.movieModel = movieModel;
        
        // event listener untuk menampilkan data ke tabel dan database
        movieView.getTampilButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String data[][] = movieModel.TampilkanData();
                    // memasukkan data movie yang sudah di ambil dari model ke dalam tabel GUI
                    movieView.getTabelData().setModel(new JTable(data, movieView.header).getModel());
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }finally{
                    resetField();
                }
            }
        });
        
        // event listener untuk menambahkan data ke tabel dan database
        movieView.getTambahButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    // mengecek apakah field masih ada yang kosong
                    if(movieView.getJudulTextfield().isBlank()||
                       movieView.getAlurTextfield().isBlank()||
                       movieView.getPenokohanTextfiedl().isBlank()||
                       movieView.getAktingTextfield().isBlank()){
                        throw new IllegalAccessException("Field Data Movie Masih Ada Yang Kosong !");
                    }
                    // konversi data yang dapat di hitung ke double (awalnya textfield/string)
                    double alur = Double.parseDouble(movieView.getAlurTextfield());
                    double penokohan = Double.parseDouble(movieView.getPenokohanTextfiedl());
                    double akting = Double.parseDouble(movieView.getAktingTextfield());
                    double nilai = (alur+penokohan+akting) / 3;
                    movieModel.TambahData(movieView.getJudulTextfield(),alur, penokohan, akting, nilai);
                    String data[][]=movieModel.TampilkanData();
                    movieView.getTabelData().setModel((new JTable(data,movieView.header)).getModel());
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }finally{
                    resetField();
                }
            }
        });
        
        // event listener jika data pada tabel di klik 
        movieView.getTabelData().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = movieView.getTabelData().getSelectedRow();
                int col = movieView.getTabelData().getSelectedColumn();
                movieView.setJudulTextfield(movieView.getTabelData().getValueAt(row, 0).toString());
                movieView.setAlurTextfield(movieView.getTabelData().getValueAt(row, 1).toString());
                movieView.setPenokohanTextfiedl(movieView.getTabelData().getValueAt(row, 2).toString());
                movieView.setAktingTextfield(movieView.getTabelData().getValueAt(row, 3).toString());
                judul = movieView.getTabelData().getValueAt(row, 0).toString();
                
            }
        });
        
        // event listener untuk menghapus data 
        movieView.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("judul delete : "+judul);
                try{
                    if(judul.isEmpty()){
                        System.out.println("Tsting");
                        throw new IllegalArgumentException("Pilih Data terlebih dahulu");
                    }
                    int input=JOptionPane.showConfirmDialog(null, "Hapus data : "+judul+" ", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (input==0){
                        movieModel.HapusData(judul);
                        String data[][] = movieModel.TampilkanData();
                        movieView.getTabelData().setModel((new JTable(data,movieView.header)).getModel());
                    }
                    judul = "";
                }catch(Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }finally{
                    resetField();
                }
            }
        });
        
        // event listener untuk mengedit data
        movieView.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(judul.isEmpty()){
                        throw new IllegalArgumentException("Pilih Data terlebih dahulu");
                    }else{
                        if(movieView.getJudulTextfield().isBlank()||
                           movieView.getAlurTextfield().isBlank()||
                           movieView.getPenokohanTextfiedl().isBlank()||
                           movieView.getAktingTextfield().isBlank()){
                            
                                throw new IllegalAccessException("Lengkapi Data !");
                        }else{
                            double alur = Double.parseDouble(movieView.getAlurTextfield());
                            double penokohan = Double.parseDouble(movieView.getPenokohanTextfiedl());
                            double akting = Double.parseDouble(movieView.getAktingTextfield());
                            double nilai = (alur+penokohan+akting) / 3;
                            movieModel.UpdateData(judul, alur, penokohan, akting,nilai);
                            String data[][]= movieModel.TampilkanData();
                            movieView.getTabelData().setModel((new JTable(data,movieView.header)).getModel());
                        }
                    }
                    judul = "";
                }catch(Exception error){
                    JOptionPane.showMessageDialog(null, error.getMessage());
                }finally{
                    resetField();
                }
            }
        });
        
        // event listener jika tombol clear di klik
        movieView.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetField();
            }
        });
        
    }
    
    // fungsi untuk reset field input
    public void resetField(){
        movieView.setJudulTextfield("");
        movieView.setAlurTextfield("");
        movieView.setPenokohanTextfiedl("");
        movieView.setAktingTextfield("");
    }
    
}
