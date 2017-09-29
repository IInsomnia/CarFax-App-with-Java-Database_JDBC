package jjuproject3_carfax;
//@author Johnson

public class CarFaxValidator 
{
    private static String errorMessage = "";
    
    public static String getValidVin(String aVin)
    {
        if(isValidVin(aVin) == false)
        {
            errorMessage += ("Invalid Vin Number, Must be 5 characters \n");
        }
        return aVin;
    }
    
    private static boolean isValidVin(String aVin)
    {
        boolean result = true;
        
        if(aVin.length() != 5 || aVin.matches("[a-zA-Z0-9 ]+") != true)
        {
            result = false;
        }
        return result;
    }
    
    public static String getValidMake(String aMake)
    {
        if(isValidMake(aMake) == false)
        {
            errorMessage += ("Invalid Make name, Cannot be Blank \n");
        }
        return aMake;
    }
    
    private static boolean isValidMake(String aMake)
    {
        boolean result = true;
        
        if(aMake.length() < 1)
        {
            result = false;
        }
        return result;
    }
    
    public static String getValidModel(String aModel)
    {
        if(isValidModel(aModel) == false)
        {
            errorMessage += ("Invalid Model name, Cannot be Blank \n");
        }
        return aModel;
    }
    
    private static boolean isValidModel(String aModel)
    {
        boolean result = true;
        
        if(aModel.length() < 1)
        {
            result = false;
        }
        return result;
    }
    
    public static int getValidYear(int aYear)
    {
        if(isValidYear(aYear) == false)
        {
            errorMessage += ("Invalid Year Number, Cannot be Blank or 0 \n");
        }
        return aYear;
    }
    
    private static boolean isValidYear(int aYear)
    {
        boolean result = true;
        
        if(aYear < 1)
        {
            result = false;
        }
        return result;
    }
    
    //method to return the error message	
    public static String getError() 
    {
        return errorMessage;
    }

    //method to clear the error message
    public static void clearError() 
    {
        errorMessage = "";
    }
}

//@author Johnson
