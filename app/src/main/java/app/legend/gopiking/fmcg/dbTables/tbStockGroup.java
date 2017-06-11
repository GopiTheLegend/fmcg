package app.legend.gopiking.fmcg.dbTables;

/**
 * Created by GopiKing on 10-06-2017.
 */

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;
public class tbStockGroup {

    public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
    public BizField StockName = new BizField("StockName", "", "TEXT");
    public BizField UnderStock = new BizField("UnderStock","","TEXT");
    public BizField ImageCat = new BizField("ImageCat", "", "BLOB);");

    public tbStockGroup()
    {
        Id.FValue="";
    }

    public static String GetTableName()
    {
        String TableName="tbStockGroup";
        return TableName;
    }

    public BizField [] GetFieldNames()
    {
        BizField  [] FieldNames = {this.Id, this.StockName, this.UnderStock,this.ImageCat};
        return FieldNames;
    }

    public BizTable GetTable()
    {
        BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
        return MyTable;
    }
}
