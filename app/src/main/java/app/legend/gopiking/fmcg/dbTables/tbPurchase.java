package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbPurchase {
	public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
	public BizField InvoiceNumber= new BizField("InvoiceNumber", "", "DOUBLE");
	public BizField SupplierName = new BizField("SupplierName", "", "TEXT");	
	public BizField PurchaseDate = new BizField("PurchaseDate", "", "DateTime");	
	public BizField ItemName = new BizField("ItemName", "", "TEXT");
	public BizField Rate = new BizField("Rate", "", "DOUBLE");
	public BizField Quantity = new BizField("Quantity", "", "DOUBLE");
	public BizField Tax= new BizField("Tax", "", "DOUBLE");
	public BizField Uom= new BizField("Uom", "", "TEXT");
	public BizField Amount = new BizField("Amount", "", "DOUBLE");

	public tbPurchase() {

		Id.FValue="";	
		
	}
	public String GetTableName()
	{
		String TableName="tbPurchase";
		return TableName;
	}
	
	public BizField [] GetFieldNames()
	{
		BizField  [] FieldNames = {this.Id, this.SupplierName,  this.PurchaseDate, this.InvoiceNumber, this.ItemName, this.Rate, this.Quantity, this.Tax,this.Uom, this.Amount};
		return FieldNames;
	}
	
	public BizTable GetTable()
	{
		BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
		return MyTable;
	}	
	

	
}
