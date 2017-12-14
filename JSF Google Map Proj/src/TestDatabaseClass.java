import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDatabaseClass {
    String dburl = "jdbc:mysql://localhost:3306/toilet_finder";
    private Connection conn;
    public TestDatabaseClass() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(dburl, "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void InsertValue(double lat, double lng){
        try {
            PreparedStatement ps = conn.prepareStatement("Insert INTO toilet " +
                    "(name,latitude,longitude) VALUES (?,?,?)");
            ps.setString(1,"Male Test Toilet");
            ps.setDouble(2,lat);
            ps.setDouble(3,lng);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Marker> DisplayMarker() {
        try {
            List<Marker> mList= new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM toilet");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String name = rs.getString("name");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("Longitude");
                Marker m = new Marker(new LatLng(latitude,longitude),name);
                mList.add(m);
            }
            return mList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    }
