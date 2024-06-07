/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import Model.KetNoi;
import Model.TraSua;
import bantrasua_bientieulinh_nguyencattuong.KhachHang;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Cat Tuong
 */
public class DatTraSuaController {
     public ArrayList<String> getTenTraSua()
    {
                   ArrayList<String> lsTenTraSua = new ArrayList<>();

       try
          {
            Connection con;
            con = KetNoi.connectToDatabase();
            
            String sql= "select  TENTRASUA from TRASUA";
            
               Statement st = con.createStatement();
               ResultSet rs = st.executeQuery(sql);
              while (rs.next())
              {
                lsTenTraSua.add(rs.getString("TENTRASUA"));
              }
        
        } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
     
return lsTenTraSua;
    }
     
     public ArrayList<String> getGiaBan ()
     {
         ArrayList<String> lsGiaBan = new ArrayList<>();
         try
          {
            Connection con;
            con = KetNoi.connectToDatabase();
            
            String sql= "select * from TRASUA";
            
               Statement st = con.createStatement();
               ResultSet rs = st.executeQuery(sql);
              while (rs.next())
              {
                  Object o = rs.getInt("GiaBan");
                  lsGiaBan.add(o.toString());
              }
        
        } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
         return lsGiaBan;
     }
     
    public ArrayList<TraSua> getInforTraSua ()
     {
         TraSua traSua = new TraSua();
         ArrayList<TraSua> lsTraSua = new ArrayList<>();
         Connection connection;
         try {
             connection = KetNoi.connectToDatabase();
             String sql = "select TRASUA.MATRASUA, TRASUA.TENTRASUA, TRASUA.GiaBan, NhaCungCap.TenNCC,TRASUA.IdHinh from TRASUA JOIN NhaCungCap on TRASUA.MaNCC = NhaCungCap.MaNCC";
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql);
             ImageIcon icon = null;
             while (rs.next())
             {
                    lsTraSua.add(new TraSua(rs.getString("MATRASUA"),rs.getString("TENTRASUA"),rs.getFloat("GiaBan"),rs.getString("TenNCC"),rs.getString("IdHinh")));
                
             }
           
         } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return lsTraSua;
     }
     
//     public ArrayList<String> getIdImg()
//     {
//         ArrayList<String> lsIdHinh =  new ArrayList<>();
//        try
//        {
//             Connection connection = KetNoi.connectToDatabase();
//             String sql = "select IdHinh from TRASUA";
//         Statement st = connection.createStatement();
//         ResultSet rs = st.executeQuery(sql);
//         while (rs.next())
//         {
//             lsIdHinh.add(rs.getString("IdHinh"));
//         }
//        } catch (SQLException ex) {
//             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
//         }
//        return lsIdHinh;
//     }
     
     public String makeNewMaHDB()
     {
         String MahDB = new String();
         try
         {
             
                      Connection connection = KetNoi.connectToDatabase();
                      String sql = "select count(*) AS count from ChiTietHoaDon";
                      PreparedStatement st = connection.prepareStatement(sql);
                      ResultSet rs = st.executeQuery();
                      if(rs.next())
                      {
                          int stt = rs.getInt("count");
                              MahDB = "HD" + stt+1 + "";
 
                      }
                          
         } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
         return MahDB;
     }
         public String getIdTraSua(String tenTraSua)
         {
                             String ma = new String();
            try
            {
                String sql = "Select MATRASUA from TRASUA where TENTRASUA = N'" +tenTraSua+"'";
                 Connection connection = KetNoi.connectToDatabase();
                 Statement st = connection.createStatement();
                 ResultSet rs = st.executeQuery(sql);
                 
                 if (rs.next())
                 {
                     ma = rs.getString("MATRASUA");
                 }
            } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
             return ma;
         }
     
//  public void taoHoaDon()
//  {
//    try
//    {
//         Connection connection = KetNoi.connectToDatabase();
//         DatTraSua datTraSua = new DatTraSua();
//     String sql = "insert into ChiTietHoaDon (MaHDB,MATRASUA,SoLuong,TongTien) values '"+makeNewMaHDB()+"','" +getIdTraSua(datTraSua.getTenTraSua())+"','"+datTraSua.getSoLuong()+"','";
//    }
//  }
          
     public String makeMaHoaDon()
     {
         String hoaDonId = null;
          try 
          {
              String sql = "Select count(MaHDB) as MaHDB from HoaDonBan";
              Connection conn = KetNoi.connectToDatabase();
              Statement st = conn.createStatement();
              ResultSet rs = st.executeQuery(sql);
              int idHoDon = 0;
              if(rs.next())
              {
                                 idHoDon = rs.getInt("MaHDB");

              }
              if(idHoDon < 10 && idHoDon >= 0)
                  hoaDonId = "HD00" + (idHoDon+1);
              else if(idHoDon >= 10 && idHoDon < 100)
                  hoaDonId = "HD0" + (idHoDon + 1);
              else if(idHoDon >= 100 && idHoDon <1000)
                  hoaDonId = "HD" + (idHoDon + 1);
              else
                  hoaDonId = "Full";
          } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
          return hoaDonId;
     }
    
   
     public ArrayList<String> getTenNhanVien()
    {
                   ArrayList<String> lsNhanVien = new ArrayList<>();

       try
          {
            Connection con;
            con = KetNoi.connectToDatabase();
            
            String sql= "select * from NHANVIEN";
            
               Statement st = con.createStatement();
               ResultSet rs = st.executeQuery(sql);
              while (rs.next())
              {
                  Object o = rs.getString("TENNV");
                  lsNhanVien.add(o.toString());
              }
        
        } catch (SQLException ex) {
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
     
return lsNhanVien;
    }
     
     public String getTenKH (String soDienThoai)
     {
            KhachHang khachHang = new KhachHang();
         String tenKH = khachHang.getTenKhachHang();
        try
        {
          Connection conn = KetNoi.connectToDatabase();
        String sql = "Select TENKH as tenkh from KHACHHANG where SDT = '" +soDienThoai+ "'";
        Statement st  = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(rs.next())
            tenKH = rs.getString("tenkh");
        } catch (SQLException ex) { 
             Logger.getLogger(DatTraSuaController.class.getName()).log(Level.SEVERE, null, ex);
         }
        return tenKH;
     }
     
     

}
