package de.avenuetv.event.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static Connection con;
    private String password = "xxx";

    public void connect(){
        if(!isConnected()){
            try{
                con = DriverManager.getConnection("jdbc:mysql://web.peerhost.de:3306/LiropSQL?autoReconnetc=true", "LuckPerms", password);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void disconnect(){
        if(isConnected()){
            try{
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected(){
        return (con != null);
    }
    public void createTable(){
        try {
            con.prepareStatement("CREATE TABLE IF NOT EXISTS `Kopfgelder` (wantedPlayer VARCHAR(50), huntingPlayer VARCHAR(50), Coins INT(16))").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getInt(String select, String wantedPlayer, String huntingPlayer){
        try {
            String command = "SELECT `"+select+"` FROM `Kopfgelder` WHERE `wantedPlayer` = '"+wantedPlayer+"' AND `huntingPlayer` = '"+huntingPlayer+"'";
            System.out.println(command);
            PreparedStatement st = con.prepareStatement(command);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                return rs.getInt(select);
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        }
        return 0;
    }
    public List getListWithoutSpecs(String select) {
        try {
            String command = "SELECT `"+select+"` FROM `Kopfgelder`";
            PreparedStatement st = con.prepareStatement(command);
            ResultSet rs = st.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (rs.next())
                list.add(rs.getString(select));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List getListWithSpecs(String select, String where, String is) {
        try {
            String command = "SELECT `"+select+"` FROM `Kopfgelder` WHERE `"+where+"` = '"+is+"'";
            PreparedStatement st = con.prepareStatement(command);
            ResultSet rs = st.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while (rs.next())
                list.add(rs.getString(select));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setData(String CMD) {
        try {
            PreparedStatement st = con.prepareStatement(CMD);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
