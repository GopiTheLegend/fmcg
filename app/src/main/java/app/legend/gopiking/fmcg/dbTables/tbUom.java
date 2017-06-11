package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

/**
 * Created by GopiKing on 09-06-2017.
 */

public class tbUom {

    public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
    public BizField Symbol = new BizField("Symbol", "", "TEXT");
    public BizField FormalName = new BizField("FormalName", "", "TEXT");

    public tbUom() {
        Id.FValue="";

    }
    public String GetTableName()
    {
        String TableName="tbUom";
        return TableName;
    }

    public BizField [] GetFieldNames()
    {
        BizField  [] FieldNames = {this.Id,this.Symbol,this.FormalName};
        return FieldNames;
    }

    public BizTable GetTable()
    {
        BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
        return MyTable;
    }
}
