package app.legend.gopiking.fmcg.dbLib;

import android.util.Log;

public class BizTable

{
    public String TableName;
    public BizField[] Fields;

    public BizTable(String TName, BizField[] FNames) {
        this.TableName = TName;
        this.Fields = FNames;
    }

    public String GetCreateTableQry() {

        String RVal = "Create Table " + this.TableName;

        RVal += "(";

        for (BizField fld : this.Fields) {
            RVal += fld.FName + " " + fld.FType + " " + fld.FCon + ",";
        }
        RVal = RVal.substring(0, RVal.length() - 2);
        RVal += ")";

        Log.d("bala", RVal);
        return RVal;
    }

    public String GetDropTableQry() {

        String RVal = "DROP TABLE IF EXISTS " + this.TableName;
        Log.d("bala", RVal);
        return RVal;
    }

    public String[] GetFieldNames() {
        String[] RV = new String[Fields.length];
        for (int i = 0; i < Fields.length; i++) {
            RV[i] = Fields[i].FName;
        }
        return RV;
    }
}
