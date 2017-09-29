package jjuproject3_carfax;
//@author Johnson

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
import javax.swing.JOptionPane;


public class CarFaxDB 
{

    private static Connection con;
    private static String URL = "jdbc:derby://localhost:1527/CarInfoDB";
    private static String errors = "";
    private HashMap <String, Car> carHashMap;
    
    public CarFaxDB()
    {
        carHashMap = new HashMap <String, Car>();
    }
    
    //Connects to the Database with Database Login info
    private static void dbConnect()
    {
        try
        {
            clearErrors();
            
            if(con == null || con.isClosed())
            {
                con = DriverManager.getConnection(URL, "Johnson", "Johnson");
            }
        }
        catch (SQLException sqlEx)
        {
            addErrors("ERROR: could not Connect to Database \n" + sqlEx.getMessage());
        }
    }
    
    //Method to Add Data into Database
    public static void dbAdd(Car aCar)
    {
        try
        {
            //Clear any previous Errors
            clearErrors();
            //Connects to the Database
            dbConnect();
            //Creates a statement Object
            Statement st = con.createStatement();
            //Creating an Insert Statement
            String sql = "INSERT INTO CARFAXDB (VIN, MAKE, MODEL, CARYEAR) VALUES "
                    + "('"+ aCar.getVin() + "', "
                    + "'" + aCar.getMake() + "', "
                    +"'" + aCar.getModel() + "', "
                    +"" + aCar.getYear() + ")";
            //Executing the SQL Statement
            st.execute(sql);
            //Close the Statement
            st.close();
            //Close the Connection
            con.close();
        }
        catch(SQLException sqlEX)
        {
            errors += "ERROR: Failed to insert Car Record " + aCar.getVin() + "\n" + sqlEX.getMessage();
        }   
    }
    
    //Method to Update Data
    public static void dbUpdate(Car aCar)
    {
        try
        {
            clearErrors();
            dbConnect();
            Statement st = con.createStatement();
            //SQL Syntax: UPDATE TABLE_t SET column = 
            String sql = "UPDATE CARFAXDB SET "
                        + "MAKE = '" + aCar.getMake() + "', "
                        + "MODEL = '" + aCar.getModel() + "', "
                        + "CARYEAR = " + aCar.getYear() + " "
                        + "WHERE VIN = '" + aCar.getVin() + "' ";
            st.execute(sql);
            
            if(st.getUpdateCount() < 1)
            {
                errors += "No Matching Record was found";
            }

            st.close();
            con.close(); 
        }
        catch(SQLException sqlEx)
        {
            errors += "ERROR: Failed to update Record for Car VIN " + aCar.getVin() + "\n" + sqlEx.getMessage();
        }
    }
    
    public static void dbDelete(String aVin)
    {
        try
        {
            clearErrors();
            dbConnect();
            Statement st = con.createStatement();
            String sql = "DELETE FROM CARFAXDB WHERE VIN = '" + aVin + "'";
            st.execute(sql);
            
            if(st.getUpdateCount() < 1)
            {
                errors += "No Matching Record was found";
            }
            
            st.close();
            con.close();
        }
        catch(SQLException sqlEx)
        {
            errors += "ERROR: Failed to Delete Record for Car VIN " + aVin + "\n" + sqlEx.getMessage();
        }
    }
    
    //Retrieve Data from Database and puts it into carHashMapList
    public static HashMap<String, Car> carHashMap()
    {
        HashMap<String, Car> carHashMapList = new HashMap <String, Car>();
        try
        {
            dbConnect();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM CARFAXDB";
            ResultSet rs = st.executeQuery(sql);
            
            int carCount = 0;
            
            while(rs.next())
            {
                String vin = rs.getString("VIN");
                String make = rs.getString("MAKE");
                String model = rs.getString("MODEL");
                int carYear = rs.getInt("CARYEAR");
                carHashMapList.put(vin, new Car(vin, make, model, carYear));
                
                carCount++;
            }
            
            JOptionPane.showMessageDialog(null, " Welcome to Carfax App! \n\n " + carCount + " Cars have been loaded from \n CARFAXDB Database");
        }
        catch(SQLException sqlEx)
        {
            errors += "ERROR: Failed to load Database Record to HashMap " + "\n" + sqlEx.getMessage();
        }
        return carHashMapList;
    }
           
    //Adds Errors to error String
    private static void addErrors(String msg)
    {
        errors += msg + "\n";
    }
    
    //Retrieves Recorded Errors when Called
    public static String getErrors()
    {
        return errors;
    }
    
    //Clears existing List of Errors
    public static void clearErrors()
    {
        errors = "";
    }
    
        //To retrieve Data from Database, (Currently using Hashmap instead)
//    public static void dbFind(String aVin)
//    {
//        try
//        {
//            clearErrors();
//            dbConnect();
//            Statement st = con.createStatement();
//            String sql = "SELECT * FROM CARFAXDB WHERE VIN = '" + aVin + "'";
//            st.executeQuery(sql);
//            
//            if(st.getUpdateCount() < 1)
//            {
//                errors += "No Matching VIN was found";
//            }
//            st.close();
//            con.close();
//        }
//        catch(SQLException sqlEx)
//        {
//            errors += "ERROR: Failed to FIND Record for Car VIN " + aVin + "\n" + sqlEx.getMessage();
//        }
//    }
}

//@author Johnson
