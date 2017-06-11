package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

/**
 * Created by Extreme on 3/19/2017.
 */

public class tbSettings {
   /* public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");*/
    public BizField CompanyName = new BizField("CompanyName", "", "TEXT");
    public BizField Address = new BizField("Address", "", "TEXT");
    public BizField PhoneNumber = new BizField("PhoneNumber", "", "TEXT");
    public BizField Email = new BizField("Email","","TEXT");
    public BizField SecurityQuestion = new BizField("SecurityQuestion","","TEXT");
    public BizField Answer = new BizField("Answer","","TEXT");
    public BizField LoginName = new BizField("LoginName", "", "TEXT");
    public BizField Password = new BizField("Password", "", "TEXT");


    public tbSettings()
    {
        /*Id.FValue="";*/
    }

    public static String GetTableName()
    {
        String TableName="tbSettings";
        return TableName;
    }

    public BizField [] GetFieldNames()
    {
        BizField  [] FieldNames = {this.CompanyName, this.Address,this.PhoneNumber,this.Email,this.SecurityQuestion,this.Answer,this.LoginName,this.Password};
        return FieldNames;
    }

    public BizTable GetTable()
    {
        BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
        return MyTable;
    }


}




