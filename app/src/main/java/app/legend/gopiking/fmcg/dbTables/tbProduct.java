package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;


public class tbProduct {
	public BizField Id = new BizField("Id", "0", "INTEGER", "PRIMARY KEY AUTOINCREMENT");
	public BizField ProductName = new BizField("ProductName", "", "TEXT");
	public BizField Category = new BizField("Category", "", "TEXT");
	public BizField Rate = new BizField("Rate", "", "DOUBLE");
	public BizField ImagProd = new BizField("ImagProd", "", " BLOB);");

	public tbProduct() {
		Id.FValue = "";
	}

	public String GetTableName() {
		String TableName = "tbProduct";
		return TableName;
	}

	public BizField[] GetFieldNames() {
		BizField[] FieldNames = {this.Id, this.ProductName, this.Category, this.Rate,this.ImagProd};
		return FieldNames;
	}

	public BizTable GetTable() {
		BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
		return MyTable;
	}
}
