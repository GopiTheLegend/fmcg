package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbSalesDetails {
    public BizField Id = new BizField("Id", "0", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
    public BizField SalesId = new BizField("SalesId", "0", "INTEGER");
    public BizField ProductID = new BizField("ProductID", "", "INTEGER");
    public BizField Rate = new BizField("Rate", "", "DOUBLE");
    public BizField Qty = new BizField("Qty", "", "DOUBLE");
    public BizField Amount = new BizField("Amount", "", "DOUBLE");
    public String ProductName = "";

    public tbSalesDetails() {

    }

    public String GetTableName() {
        String TableName = "tbSalesDetails";
        return TableName;
    }

    public BizField[] GetFieldNames() {
        BizField[] FieldNames = {this.Id, this.SalesId, this.ProductID, this.Rate, this.Qty, this.Amount};
        return FieldNames;
    }

    public BizTable GetTable() {
        BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
        return MyTable;
    }
}
