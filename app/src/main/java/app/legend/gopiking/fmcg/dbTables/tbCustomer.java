package app.legend.gopiking.fmcg.dbTables;

import app.legend.gopiking.fmcg.dbLib.BizField;
import app.legend.gopiking.fmcg.dbLib.BizTable;

public class tbCustomer {
	public BizField Id = new BizField("Id", "0", "INTEGER","PRIMARY KEY AUTOINCREMENT");
	public BizField CustomerName = new BizField("CustomerName", "", "TEXT");
	public BizField Address1 = new BizField("Address1", "", "TEXT");
	public BizField Address2 = new BizField("Address2", "", "TEXT");
	public BizField City = new BizField("City", "", "TEXT");
	public BizField Phone = new BizField("Phone", "", "TEXT");
	public BizField MailId = new BizField("MailId","","TEXT");
	public BizField GST = new BizField("GST", "", "TEXT");
	public BizField OpenningBalance = new BizField("OpenningBalance", "", "TEXT");
	public BizField AcType = new BizField("AcType", "", "TEXT");
	public BizField CustomerCreditAmount = new BizField("CustomerCreditAmount", "", "TEXT");
	public BizField CustomerCreditLimit = new BizField("CustomerCreditLimit", "", "TEXT");
	public BizField CustomerCreditLimitType = new BizField("CustomerCreditLimitType", "", "TEXT");
	public BizField RemoteId = new BizField("RemoteId", "0", "INTEGER");
	
	public tbCustomer()
	{
		
	}
	
	public String GetTableName()
	{
		String TableName="tbCustomer";
		return TableName;
	}
	
	public BizField [] GetFieldNames()
	{
		BizField  [] FieldNames = {this.Id, this.CustomerName, this.Address1,  this.Address2,this.City, this.Phone, this.MailId,this.GST,this.OpenningBalance,this.AcType,this.CustomerCreditAmount,this.CustomerCreditLimit,this.CustomerCreditLimitType,this.RemoteId};
		return FieldNames;
	}
	
	public BizTable GetTable()
	{
		BizTable MyTable = new BizTable(GetTableName(), GetFieldNames());
		return MyTable;
	}	
}
