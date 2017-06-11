package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbEmployee {

	
		public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
		public BizField EmployeeName = new BizField("EmployeeName", "", "TEXT");
		public BizField IcNumber = new BizField("IcNumber", "", "TEXT");
		public BizField Address = new BizField("Address", "", "TEXT");
		public BizField Salary = new BizField("Salary", "", "DOUBLE");
		public BizField UName = new BizField("UserName", "", "TEXT");
		public BizField Pwd = new BizField("Password", "", "TEXT");
		
		public tbEmployee()
		{
			Id.FValue="";
		}

		public String GetTableName()
		{
			String TableName="tbEmployee";
			return TableName;
		}
		
		public BizField [] GetFieldNames()
		{
		BizField  [] FieldNames = {this.Id, this.EmployeeName, this.IcNumber,  this.Address, this.Salary, this.UName, this.Pwd};
			return FieldNames;
		}
		
		public BizTable GetTable()
		{
			BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
			return MyTable;
		}	
		
	

	
}
