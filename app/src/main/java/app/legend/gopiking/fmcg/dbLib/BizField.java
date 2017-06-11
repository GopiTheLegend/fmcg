package app.legend.gopiking.fmcg.dbLib;

public class BizField {
    public String FName;
    public String FValue;
    public String FType;
    public String FCon;

    public BizField(String FName, String FValue, String FType) {
        this.FName = FName;
        this.FValue = FValue;
        this.FType = FType;
        this.FCon = "";
    }

    public BizField(String FName, String FValue, String FType, String FCon) {
        this.FName = FName;
        this.FValue = FValue;
        this.FType = FType;
        this.FCon = FCon;
    }

}
