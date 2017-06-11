package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.legend.gopiking.fmcg.BTLib.BTDeviceList;
import app.legend.gopiking.fmcg.BTLib.BTPrint;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbCustomer;
import app.legend.gopiking.fmcg.dbTables.tbProduct;
import app.legend.gopiking.fmcg.dbTables.tbSales;
import app.legend.gopiking.fmcg.dbTables.tbSalesDetails;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.activity;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class AppLib {



    public static List<BillItem> lstBillItem= new ArrayList<BillItem>();
    public static BizDatabase db;
    public static String UserName="";
    public static String Password="";
    public static boolean noChild;
    public static tbCustomer selectedCustomer;
    public static tbStockGroup selectedCat;
    public static tbProduct selectedItem;
    public static String selectedBillNo="";

    //sale cat id
    public static String tempTbl = "";
    public static String tempCat = "";
    public static String tempItm = "";

    public static  boolean isViewSelectedItem;
    public static String OwnerName;

    //company setting to pdf report
    public static String companyName;

    public static float productList_Total;

    public static String tot;

    public static String sales_total_amt;

    public static String noImage = activity.getString(R.string.Noimage);


    public static double Displaysize;
    public static double x;
    public static double y;

    public static String billno;
    public static String voucherno;
    public static String ShowReportOf = "Default";

    public static SimpleDateFormat dateDispFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat dateSaveFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    public static String excel_link ="";

    public static String purchaseSupplierName="";
    public static String purchaseItemName="";
    public static String purchaseInvoiceNumber="";

//    public  static boolean SaveBill(Context context,Activity activity){
//
//        try{
//
//            if (BTPrint.btsocket == null) {
//                try {
//                    Intent BTIntent = new Intent(context, BTDeviceList.class);
//                    activity.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
//                } catch (Exception e) {
//                    return  false;
//                }
//            } else {
//                String id="";
//                SimpleDateFormat simpleDateFormat;
//                String time;
//                Calendar calander;
//
//                calander = Calendar.getInstance();
//                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//
//                time = simpleDateFormat.format(calander.getTime());
//
//                db = new BizDatabase(context);
//                tbSales sale = new tbSales();
//                Cursor ComInfo = (Cursor) db.ExequteQuery("Select * from tbSettings");
//                ComInfo.moveToFirst();
//
//                sale.Id.FValue = id;
//
//                sale.SaleDate.FValue = dateSaveFormat.format(Calendar.getInstance().getTime());
//
//                float amt = FindItemAmount("");
//                sale.BillNumber.FValue = selectedBillNo;
//                sale.ItemAmount.FValue = Float.toString(amt);
//                sale.Discount.FValue = "0";
//                sale.TotalAmount.FValue = Float.toString(amt);
//
//                sale.Narration.FValue = "";
//
//                long RV;
//                if (id.equals("")) {
//                    RV = db.AddRecord(sale.GetTable());
//                    id = Long.toString(RV);
//                } else {
//
//                    RV = db.UpdateRecord(sale.GetTable());
//                }
//
//                List<String> billedRecId = new ArrayList<String>();
//
//                if (RV != 0) {
//
//                    BTPrint.ResetPrinter();
//                    BTPrint.SetAlign(Paint.Align.CENTER);
//                    BTPrint.PrintTextLine(ComInfo.getString(0));
//                    BTPrint.PrintTextLine(ComInfo.getString(1));
//                    BTPrint.PrintTextLine(String.format("Ph: %s", ComInfo.getString(2)));
//                    BTPrint.PrintTextLine("--------------------------------");
//
//                    BTPrint.SetAlign(Paint.Align.LEFT);
//                    BTPrint.PrintTextLine(String.format("Bill No : %d ", RV));
//                    BTPrint.PrintTextLine(String.format("Date: %s  Time: %s", AppLib.dateDispFormat.format(Calendar.getInstance().getTime()),time));
//                    BTPrint.PrintTextLine("--------------------------------");
////                BTPrint.PrintTextLine("PId        Item            Qty");
////                BTPrint.PrintTextLine("Date         Rate       Amount");
//                    BTPrint.PrintTextLine("SNO PARTICULAR");
//                    BTPrint.PrintTextLine("--------------------------------");
//
//                    tbSalesDetails sd = new tbSalesDetails();
//                    db.DeleteRecordByCode(sd.GetTable(), id);
//                    int SNo=1;
//                    for (int i = 0; i < lstBillItem.size(); i++) {
//
//                        BillItem data  = lstBillItem.get(i);
//
//                        if(selectedTable.Id.FValue.equals(data.TableId) && selectedBillNo.equals(data.BillNo)){
//                            sd.Id.FValue="";
//                            sd.SalesId.FValue = id;
//                            sd.ProductID.FValue = data.ProductId;
//                            sd.Qty.FValue = Integer.toString(data.Qty);
//                            sd.Rate.FValue = Float.toString(data.Rate);
//                            sd.Amount.FValue = Float.toString(data.Amount);
//
//                            db.AddRecord(sd.GetTable());
//                            billedRecId.add(Integer.toString(i));
//                            BTPrint.PrintTextLine(String.format("%3d %-25.25s",SNo++, data.ProductName));
//                            BTPrint.PrintTextLine(String.format("     %4.2f x%2d = %5.2f", data.Rate, data.Qty, data.Amount));
//
//                        }
//                    }
//                    BTPrint.PrintTextLine("--------------------------------");
//                    BTPrint.SetAlign(Paint.Align.RIGHT);
//                    BTPrint.PrintTextLine(String.format("  Total RM %.2f", amt));
//                    BTPrint.PrintTextLine("--------------------------------");
//
//                    BTPrint.SetAlign(Paint.Align.CENTER);
//
//                    BTPrint.PrintTextLine("    Thank You ! Visit Again   \n");
//                    BTPrint.PrintTextLine("******************************\n\n\n");
//
//
//
//                    for(int i =0;i<billedRecId.size();i++){
//                        int n = Integer.parseInt(billedRecId.get(i));
//                        lstBillItem.remove(n-i);
//                    }
//                    // Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
////                   context.startActivity(new Intent(context.getApplicationContext(),FrmSaleTableList.class));
//                    return true;
//
//
//                } else {
//                    //Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            }
//
//        }catch (Exception e){msg(e.toString());}
//        return  false;
//    }

    public  static boolean SaveBill(Context context, Activity activity){

        try{

            new Def_Lib(activity);

            if (BTPrint.btsocket == null) {
                try {
                    Intent BTIntent = new Intent(context, BTDeviceList.class);
                    activity.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
                } catch (Exception e) {

                    msg(e.toString());
                    return  false;
                }
            } else {
                String id="";
                SimpleDateFormat simpleDateFormat;
                String time;
                Calendar calander;

                calander = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                time = simpleDateFormat.format(calander.getTime());

                db = new BizDatabase(context);
                tbSales sale = new tbSales();
                Cursor ComInfo = (Cursor) db.ExequteQuery("Select * from tbSettings");
                ComInfo.moveToFirst();

                sale.Id.FValue = id;

                sale.SaleDate.FValue = dateSaveFormat.format(Calendar.getInstance().getTime());

                float amt = FindItemAmount("");
                sale.BillNumber.FValue = selectedBillNo;
                sale.ItemAmount.FValue = Float.toString(amt);
                sale.Discount.FValue = "0";
                sale.TotalAmount.FValue = Float.toString(amt);

                sale.Narration.FValue = "";

                long RV;
                if (id.equals("")) {
                    RV = db.AddRecord(sale.GetTable());
                    id = Long.toString(RV);
                } else {

                    RV = db.UpdateRecord(sale.GetTable());
                }

                List<String> billedRecId = new ArrayList<String>();

                if (RV != 0) {

                    BTPrint.ResetPrinter();
                    BTPrint.SetAlign(Paint.Align.CENTER);
                    BTPrint.PrintTextLine(ComInfo.getString(0));
                    BTPrint.PrintTextLine(ComInfo.getString(1));
                    BTPrint.PrintTextLine(String.format("Ph: %s", ComInfo.getString(2)));
                    BTPrint.PrintTextLine("--------------------------------");

                    BTPrint.SetAlign(Paint.Align.LEFT);
                    BTPrint.PrintTextLine(String.format("Bill No : %d ", RV));
                    BTPrint.PrintTextLine(String.format("Date: %s  Time: %s", AppLib.dateDispFormat.format(Calendar.getInstance().getTime()),time));
                    BTPrint.PrintTextLine("--------------------------------");
//                BTPrint.PrintTextLine("PId        Item            Qty");
//                BTPrint.PrintTextLine("Date         Rate       Amount");
                    BTPrint.PrintTextLine("SNO PARTICULAR");
                    BTPrint.PrintTextLine("--------------------------------");

                    tbSalesDetails sd = new tbSalesDetails();
                    db.DeleteRecordByCode(sd.GetTable(), id);
                    int SNo=1;
                    for (int i = 0; i < lstBillItem.size(); i++) {

                        BillItem data  = lstBillItem.get(i);

                        if(selectedCustomer.Id.FValue.equals(data.CustomerId) && selectedBillNo.equals(data.BillNo)){
                            sd.Id.FValue="";
                            sd.SalesId.FValue = id;
                            sd.ProductID.FValue = data.ProductId;
                            sd.Qty.FValue = Integer.toString(data.Qty);
                            sd.Rate.FValue = Float.toString(data.Rate);
                            sd.Amount.FValue = Float.toString(data.Amount);

                            db.AddRecord(sd.GetTable());
                            billedRecId.add(Integer.toString(i));
                            BTPrint.PrintTextLine(String.format("%3d %-25.25s",SNo++, data.ProductName));
                            BTPrint.PrintTextLine(String.format("     %4.2f x%2d = %5.2f", data.Rate, data.Qty, data.Amount));

                        }
                    }
                    BTPrint.PrintTextLine("--------------------------------");
                    BTPrint.SetAlign(Paint.Align.RIGHT);
                    BTPrint.PrintTextLine(String.format("  Total RM %.2f", amt));
                    BTPrint.PrintTextLine("--------------------------------");

                    BTPrint.SetAlign(Paint.Align.CENTER);

                    BTPrint.PrintTextLine("    Thank You ! Visit Again   \n");
                    BTPrint.PrintTextLine("******************************\n\n\n");



                    for(int i =0;i<billedRecId.size();i++){
                        int n = Integer.parseInt(billedRecId.get(i));
                        lstBillItem.remove(n-i);
                    }
                    // Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
//                   context.startActivity(new Intent(context.getApplicationContext(),FrmSaleTableList.class));
                    return true;


                } else {
                    //Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_LONG).show();
                    return false;
                }
            }

        }catch (Exception e){msg(e.toString());}
        return  false;
    }


    public static float FindItemAmount(String TId){
        int i=0;
        float amt=0;
        String TableId="";



        if(TId!=""){
            TableId = TId;
        }else if(selectedCustomer!=null){
            TableId=selectedCustomer.Id.FValue;
        }

        for(i=0;i<lstBillItem.size();i++){
            BillItem data = lstBillItem.get(i);
            if(TableId.equals(data.CustomerId) && selectedBillNo.equals(data.BillNo)){
                amt+=data.Amount;
            }
        }
        return  amt;
    }

//	public static List<String> AcList(){
//		List<String> itemList = new ArrayList<String>();
//		try{
//			tbAccount accsearch = new tbAccount();
//			itemList = db.GetColumn(accsearch.GetTableName(), accsearch.AccountFrom.FName,"");
//			List<String> itemList2 = db.GetColumn(accsearch.GetTableName(), accsearch.AccountTo.FName,"");
//
//			for(int i =0;i<itemList2.size();i++){
//				if(!itemList.contains(itemList2.get(i))){
//					itemList.add(itemList2.get(i));
//				}
//			}
//
//		}catch(Exception ex){}
//		if(itemList.size()==0){
//			itemList.add("Cash Account");
//		}
//		return itemList;
//
//	}

}
