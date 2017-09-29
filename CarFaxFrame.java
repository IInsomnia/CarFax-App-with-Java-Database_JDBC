package jjuproject3_carfax;
//@author Johnson

import java.text.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;



public class CarFaxFrame extends javax.swing.JFrame implements ActionListener
{
    private JPanel jPanel1;
    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton clearBtn;
    private JButton findBtn;
    private JLabel labelTitle, labelVin, labelMake, labelModel, labelYear;
    private JTextField txtVin, txtMake, txtModel, txtYear, txtFind;
    private HashMap <String, Car> carHashMap;
    
    public CarFaxFrame()
    {
        //Puts the HashMap with the current Database Data into carHashMap
        carHashMap = CarFaxDB.carHashMap();

        //Displays Window on Exit with Number of Cars in Database
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                JOptionPane.showMessageDialog(null, "Thank you for using CarFax App. \n" + carHashMap.size() + " Cars has been saved to the database");
                e.getWindow().dispose();
            }
        });

        GridLayout thisLayout = new GridLayout();
        getContentPane().setLayout(thisLayout);

        jPanel1 = new JPanel();
        getContentPane().add(jPanel1);
        GridBagLayout jPanel1Layout = new GridBagLayout();
        jPanel1.setLayout(jPanel1Layout);
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 500));

        labelTitle = new JLabel();
        jPanel1.add(labelTitle, gc(4, 1, 1, 3));
        labelTitle.setText("Welcome to Java DB CarFax");

        labelVin = new JLabel();
        jPanel1.add(labelVin, gc(2, 3, 1, 1));
        labelVin.setText("VIN");
        txtVin = new JTextField(10);
        jPanel1.add(txtVin, gc(3, 3, 1, 4));

        labelMake = new JLabel();
        jPanel1.add(labelMake, gc(2, 5, 1, 1));
        labelMake.setText("Car Make");
        txtMake = new JTextField(10);
        jPanel1.add(txtMake, gc(3, 5, 1, 4));

        labelModel = new JLabel();
        jPanel1.add(labelModel, gc(2, 7, 1, 1));
        labelModel.setText("Car Model");
        txtModel = new JTextField(10);
        jPanel1.add(txtModel, gc(3, 7, 1, 4));

        labelYear = new JLabel();
        jPanel1.add(labelYear, gc(2, 9, 1, 1));
        labelYear.setText("Car Year");
        txtYear = new JTextField(10);
        jPanel1.add(txtYear, gc(3, 9, 1, 4));

        addBtn = new JButton();
        jPanel1.add(addBtn, gc(3, 11, 1, 1));
        addBtn.setText("Add");
        addBtn.addActionListener(this);

        updateBtn = new JButton();
        jPanel1.add(updateBtn, gc(4, 11, 1, 1));
        updateBtn.setText("Update");
        updateBtn.addActionListener(this);

        deleteBtn = new JButton();
        jPanel1.add(deleteBtn, gc(5, 11, 1, 1));
        deleteBtn.setText("Delete");
        deleteBtn.addActionListener(this);

        clearBtn = new JButton();
        jPanel1.add(clearBtn, gc(6, 11, 1, 1));
        clearBtn.setText("Clear");
        clearBtn.addActionListener(this);

        findBtn = new JButton();
        jPanel1.add(findBtn, gc(3, 13, 1, 1));
        findBtn.setText("Find");
        findBtn.addActionListener(this);
        txtFind = new JTextField(10);
        jPanel1.add(txtFind, gc(4, 13, 1, 3));

        this.add(jPanel1);
        this.setBounds(0, 0, 700, 600);
        this.setTitle("CIS CarFax DB");   

        pack();
        this.setSize(550, 525);
    }
    

    private GridBagConstraints gc(int x, int y, int h, int w) 
    {
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = x;
        c.gridy = y;
        c.gridheight = h;
        c.gridwidth = w;

        c.anchor = GridBagConstraints.WEST;

        c.fill = GridBagConstraints.HORIZONTAL;

        c.insets = new Insets(20, 20, 20, 20);

        return c;
    }

    public void actionPerformed(ActionEvent e) 
    {
        Object s = e.getSource();
        
        if(s == addBtn)
        {
            addRecord();
        }
        else if(s == updateBtn)
        {
            updateRecord();
        }else if(s == deleteBtn)
        {
            deleteRecord();
        }
        else if(s == clearBtn)
        {
            clearForm();
        }
        else if(s == findBtn)
        {
            findRecord();
        }
    }
    //Adds a Record to Database and HashMap after Validation
    private void addRecord()
    {
        int aYear = 0;
        CarFaxValidator.clearError();
        String aVin = CarFaxValidator.getValidVin(txtVin.getText());
        String aMake = CarFaxValidator.getValidMake(txtMake.getText());
        String aModel = CarFaxValidator.getValidModel(txtModel.getText());
        String errors = "";
        //Checks if Year input is blank
        if(txtYear.getText().isEmpty() != true)
        {
            aYear = CarFaxValidator.getValidYear(Integer.parseInt(txtYear.getText()));
        }
        else 
        {
            errors += "Invalid Year Number, Cannot be Blank or 0 \n";
        }
        errors += CarFaxValidator.getError();
        
        if (errors.length() > 0) 
        {
            JOptionPane.showMessageDialog(null, "An Error Occured:\n\n" + errors);
        } 
        else 
        {
            Car aCar = new Car(aVin, aMake, aModel, aYear);
            //Adds input into Database and HashMap
            CarFaxDB.dbAdd(aCar);
            AddCarToList(aVin, aCar);
            //Error check not needed here, but I'll leave it here anyways
            if(CarFaxDB.getErrors().length() > 0) 
            {
                JOptionPane.showMessageDialog(null, CarFaxDB.getErrors());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Record was added");
            }
        }
    }

    //Update Record to Database and HashMap after Validation
    private void updateRecord()
    {
        int aYear = 0;
        CarFaxValidator.clearError();
        String aVin = CarFaxValidator.getValidVin(txtVin.getText());
        String aMake = CarFaxValidator.getValidMake(txtMake.getText());
        String aModel = CarFaxValidator.getValidModel(txtModel.getText());
        String errors = CarFaxValidator.getError();
        
        if(txtYear.getText().isEmpty() != true)
        {
        aYear = CarFaxValidator.getValidYear(Integer.parseInt(txtYear.getText()));
        }
        else 
        {
            errors += "Invalid Year Number, Cannot be Blank or 0 \n";
        }
        errors += CarFaxValidator.getError();
        
        if (errors.length() > 0) 
        {
            JOptionPane.showMessageDialog(null, "An Error Occured:\n\n" + errors);
        } 
        else 
        {
            Car aCar = new Car(aVin, aMake, aModel, aYear);
            CarFaxDB.dbUpdate(aCar);
            UpdateCarList(txtVin.getText(), aCar);

            if(CarFaxDB.getErrors().length() > 0)
            {
                JOptionPane.showMessageDialog(null, CarFaxDB.getErrors());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Record was Updated");
            }
        }
    }
    
    //Deletes the given Vin Number record from Database and HashMap
    private void deleteRecord()
    {
        String aVIN = txtVin.getText();
        CarFaxDB.dbDelete(aVIN);
        DeleteFromCarList(aVIN);
        
        if(CarFaxDB.getErrors().length() > 0)
        {
            JOptionPane.showMessageDialog(null, CarFaxDB.getErrors());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Record was Deleted");
        }
    }
    
    //Clears the Form
    private void clearForm()
    {
        txtVin.setText("");
        txtMake.setText("");
        txtModel.setText("");
        txtYear.setText("");
        txtFind.setText("");
    }
    
    //Finds Record using the HashMap
    private void findRecord()
    {
        String aVin = txtFind.getText();
        Car findCar = carHashMap.get(aVin);
        
        if(carHashMap.containsKey(aVin) == true)
        {
            clearForm();
            txtVin.setText(aVin);
            txtMake.setText(findCar.getMake());
            txtModel.setText(findCar.getModel());
            txtYear.setText(String.valueOf(findCar.getYear()));
            
            JOptionPane.showMessageDialog(null, "The VIN " + aVin + " was found");
            
        }
        else
        {
            JOptionPane.showMessageDialog(null, "The VIN " + aVin + " was not found");
        }
        
    if(CarFaxDB.getErrors().length() > 0)
        {
            JOptionPane.showMessageDialog(null, CarFaxDB.getErrors());
        }
    }

    //Method to Add car to HashMap
    public void AddCarToList(String aVin, Car aCar)
    {
        carHashMap.put(aVin, aCar);
    }
    
    //Method to Delete a Record from HashMap
    public void DeleteFromCarList(String aVin)
    {
        carHashMap.remove(aVin);
    }
    
    //Method to Replace Data in HashMap
    public void UpdateCarList(String aVin, Car aCar)
    {
        carHashMap.replace(aVin, aCar);
    }
    
                //Lists HashMap (to verify) after retrieving data from Database
//            Car vinNum;
//            String result = "";
//           
//            for(String aVin: carHashMap.keySet())
//                {
//                    vinNum = carHashMap.get(aVin);
//                    String make = vinNum.getMake();
//                    String model = vinNum.getModel();
//                    int carYear = vinNum.getYear();
//                    result += "Vin: " + aVin + "\n" + "Make: " + make + "Model: " + model + "Year: " + carYear + "\n";
//                }
//                JOptionPane.showMessageDialog(null, result);
}

//@author Johnson
