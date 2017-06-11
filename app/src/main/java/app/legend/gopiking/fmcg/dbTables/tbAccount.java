package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbAccount {
	public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
	public BizField VoucherNo = new BizField("VoucherNo", "", "INTEGER");
	public BizField AccountFrom = new BizField("AccountFrom", "", "TEXT");
	public BizField AccountDate = new BizField("AccountDate", "", "DateTime");
	public BizField AccountAmount= new BizField("AccountAmount", "", "DOUBLE");
	public BizField AccountTo = new BizField("AccountTo", "", "TEXT");
	public BizField Narration = new BizField("Narration", "", "TEXT");

	public tbAccount() {
		Id.FValue="";	
		
	}
	public String GetTableName()
	{
		String TableName="tbAccount";
		return TableName;
	}
	
	public BizField [] GetFieldNames()
	{
		BizField  [] FieldNames = {this.Id,this.VoucherNo,this.AccountDate,  this.AccountFrom, this.AccountTo,  this.AccountAmount, this.Narration};
		return FieldNames;
	}
	
	public BizTable GetTable()
	{
		BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
		return MyTable;
	}	

}
