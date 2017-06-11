package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbUserRegistration {
	public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
	public BizField UserName = new BizField("UserName", "", "TEXT");
	public BizField Password = new BizField("Password", "", "TEXT");
	public BizField Type = new BizField("Type", "", "TEXT");
		
	
	public tbUserRegistration()
	{
		
	}
	
	public String GetTableName()
	{
		String TableName="tbUserRegistrtion";
		return TableName;
	}
	
	public BizField [] GetFieldNames()
	{
		BizField  [] FieldNames = {this.Id, this.UserName, this.Password,  this.Type};
		return FieldNames;
	}
	
	public BizTable GetTable()
	{
		BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
		return MyTable;
	}	
}
