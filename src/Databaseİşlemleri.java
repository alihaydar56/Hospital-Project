
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Databaseİşlemleri {
    public Connection connect=null;
    public Statement statement=null;
    public PreparedStatement prestate=null;
    
    public ArrayList<Hasta> VeriAl(){
        ArrayList<Hasta> çıktı=new ArrayList<Hasta>();
       
        try {
             String sorgu="Select * from hastalar";
             statement=connect.createStatement();
             ResultSet rs=statement.executeQuery(sorgu);
             while (rs.next()) {                
                int  no=rs.getInt("Sıra");
                String name=rs.getString("Name");
                String surname=rs.getString("Surname");
                int  id=rs.getInt("Id");
                String birthday=rs.getString("birthday");
                String gender=rs.getString("gender");
                String dep=rs.getString("Department");
                String doctor=rs.getString("Doctor");
                
                çıktı.add(new Hasta(no, name, surname, id, birthday, gender, dep, doctor));  
             }
             return çıktı;
        } catch (SQLException ex) {
            Logger.getLogger(Databaseİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public void VeriEkle(String Name,String Surname,int id,String birthday,String gender,String Department,String doctor){
        
        try {
            String ekle="Insert into hastalar (Name,Surname,id,birthday,gender,Department,doctor) Values(?,?,?,?,?,?,?)";
            prestate=connect.prepareStatement(ekle);
            prestate.setString(1, Name);
            prestate.setString(2, Surname);
            prestate.setInt(3, id);
            prestate.setString(4, birthday);
            prestate.setString(5, gender);
            prestate.setString(6, Department);
            prestate.setString(7, doctor);
            prestate.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Databaseİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void VeriSil(int no){
        
        try {
            String sil="Delete From hastalar where Sıra=?";
            prestate=connect.prepareStatement(sil);
            prestate.setInt(1,no);
            prestate.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Databaseİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Databaseİşlemleri(){
        
        String url="jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.database_name+"?useUnicode=true&characterEncoding=utf8";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Databaseİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            connect=DriverManager.getConnection(url, Database.username, Database.password);
            System.out.println("Connection is succesfull");
        } catch (SQLException ex) {
            Logger.getLogger(Databaseİşlemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        Databaseİşlemleri baglantı=new Databaseİşlemleri();
        
    }
}
