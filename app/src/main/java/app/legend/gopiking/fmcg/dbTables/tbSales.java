package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbSales {
    public BizField Id = new BizField("Id", "0", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
    public BizField SaleDate = new BizField("saleDate", "", "DateTime");
    public BizField BillNumber = new BizField("BillNumber", "", "INTEGER");
    public BizField ItemAmount = new BizField("ItemAmount", "", "DOUBLE");
    public BizField Discount = new BizField("Discount", "", "DOUBLE");
    public BizField TotalAmount = new BizField("TotalAmount", "", "DOUBLE");
    public BizField Narration = new BizField("Narration", "", "TEXT");

    public tbSales() {

    }

    public String GetTableName() {
        String TableName = "tbSales";
        return TableName;
    }

    public BizField[] GetFieldNames() {
        BizField[] FieldNames = {this.Id, this.SaleDate, this.BillNumber, this.ItemAmount, this.Discount, this.TotalAmount, this.Narration};
        return FieldNames;
    }

    public BizTable GetTable() {
        BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
        return MyTable;
    }
}
