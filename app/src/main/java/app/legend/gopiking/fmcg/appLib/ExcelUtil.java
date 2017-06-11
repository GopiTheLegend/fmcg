package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbPurchase;
import app.legend.gopiking.fmcg.dbTables.tbSales;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static app.legend.gopiking.fmcg.appLib.AppLib.excel_link;

//import static king.legend.gtk.denrestaurant.Frm.Report.FrmPurchaseReport.PFdate;
//import static king.legend.gtk.denrestaurant.Frm.Report.FrmPurchaseReport.PTdate;
//import static king.legend.gtk.denrestaurant.Frm.Report.FrmSaleReport.SFdate;
//import static king.legend.gtk.denrestaurant.Frm.Report.FrmSaleReport.STdate;

public class ExcelUtil {

	public static String root = Environment.getExternalStorageDirectory().getPath();
	static BizDatabase db;

	public static String efdate;
	public static String etodate;

	static Activity activity;


	static Cursor sdGrid;
	static Object obj;

	public ExcelUtil(Activity _activity){
		activity = _activity;
	}

	public static WritableCellFormat getHeader() {
		WritableFont font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD);
		try {
			font.setColour(Colour.BLACK);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			// format.setBorder(Border.ALL, BorderLineStyle.THIN,
			// Colour.BLACK);
			// format.setBackground(Colour.YELLOW);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}

	public static void Read_Excel(Context context, File file_path){
		try{

			/** Excel reader code */

			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file_path), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			try {
				excel_link = "y";
				context.getApplicationContext().startActivity(intent);

			} catch (ActivityNotFoundException e) {
				excel_link = "y";
				Pdf.Permission_Alert("NO Excel Viewer",context,"ACTION NEED");
			}

		}catch (Exception e){e.toString();}
	}

	static void excel_read_alert(String title, String message, final File FilePath, final Context context){
		try{

			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setTitle(title);
			alert.setMessage(message);
			alert.setCancelable(false);
			alert.setNegativeButton("Attach and Mail", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					try{

						String names = null;
						Object obj;
						BizDatabase db;
						db= new BizDatabase(context.getApplicationContext());

						String query = "select * from tbSettings";
						obj = db.ExequteQuery(query);

						Cursor sdGrid=(Cursor)obj;

						if(sdGrid!= null)
						{

							if(sdGrid.moveToFirst())
							{
								do
								{
									names = sdGrid.getString(0);

								}while(sdGrid.moveToNext());
							}
						}

						if(Network.getInstance(context).isOnline()){

							Pdf.attach_and_email(context,new Date().toString()+"REPORT",names+" : REPORT",FilePath);
						}else{
							Network.Check_Internet(context,"No Data Alert","Network Missing Alert");
						}


					}catch (Exception e){
						Log.d("MESS",e.toString());}
				}
			});

			alert.setPositiveButton("Open", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Read_Excel(context,FilePath);
				}
			});

			alert.setNeutralButton("Cancel",new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			AlertDialog dialog = alert.create();
			dialog.show();

		}catch (Exception e){e.toString();}
	}

	//old file example
	public static void Purchase_Excel(Context context, String fileName) throws Exception {

		try{
			if (Def_Lib.isStorageEnough()) {
				Def_Lib.msg( "Storage Space not enough");
				return;
			}

			db = new BizDatabase(context.getApplicationContext());

			Label label;

			tbPurchase Purchase = new tbPurchase();

			String[] title = { "Purchase Date", "Invoice Number", "Total Amount"};
			File file;
			file = new File(root + "/DenRestaurant/"+ "/Excel/", fileName);

			if (!file.exists())
			{
				file.mkdirs();
			}

			file = new File(file, fileName+ ".xls");

			WritableWorkbook wwb;
			OutputStream os = new FileOutputStream(file);
			wwb = jxl.Workbook.createWorkbook(os);

			WritableSheet sheet = wwb.createSheet("PurchaseReport", 0);

			for (int i = 0; i < title.length; i++) {
				label = new Label(i, 0, title[i], getHeader());
				sheet.addCell(label);
			}

			String FNames = "*";

			String WQry = String.format("%s >= '%s'and %s<='%s'", Purchase.PurchaseDate.FName,efdate, Purchase.PurchaseDate.FName, etodate);

			obj = db.GetGrid(Purchase.GetTableName(), FNames, WQry, "", "");
			sdGrid = (Cursor) obj;

			if(sdGrid != null)
			{

				if(sdGrid.moveToFirst())
				{
					int i = 0;
					do
					{
						Label purchase_Date = new Label(0, i + 1, sdGrid.getString(1));
						Label invoice_Number = new Label(1, i + 1, sdGrid.getString(2));
						Label total_Amount = new Label(2,i+1,sdGrid.getString(5));

						sheet.addCell(purchase_Date);
						sheet.addCell(invoice_Number);
						sheet.addCell(total_Amount);

						i++;
					}while(sdGrid.moveToNext());
				}
			}

			excel_read_alert("Select Option","Excel Created",file,context);

			wwb.write();
			wwb.close();

		}catch (Exception e){e.toString();}
	}

	public static void sales_Excel(Context context, String fileName) throws Exception {

		try{
			if (Def_Lib.isStorageEnough()) {
				Def_Lib.msg( "Storage Space not enough");
				return;
			}

			db = new BizDatabase(context.getApplicationContext());

			Label label;

			tbSales Sales = new tbSales();

			String[] title = { "Sale Date", "Bill Number", "Total Amount"};
			File file;
			file = new File(root + "/DenRestaurant/"+ "/Excel/", fileName);

			if (!file.exists())
			{
				file.mkdirs();
			}

			file = new File(file, fileName+ ".xls");

			WritableWorkbook wwb;
			OutputStream os = new FileOutputStream(file);
			wwb = jxl.Workbook.createWorkbook(os);

			WritableSheet sheet = wwb.createSheet("SalesReport", 0);

			for (int i = 0; i < title.length; i++) {
				label = new Label(i, 0, title[i], getHeader());
				sheet.addCell(label);
			}

			String FNames = "*";

			String WQry = String.format("%s >= '%s'and %s<='%s'", Sales.SaleDate.FName,efdate, Sales.SaleDate.FName, etodate);

			obj = db.GetGrid(Sales.GetTableName(), FNames, WQry, "", "");
			sdGrid = (Cursor) obj;

			if(sdGrid != null)
			{

				if(sdGrid.moveToFirst())
				{
					int i = 0;
					do
					{
						Label Sales_Date = new Label(0, i + 1, sdGrid.getString(1));
						Label Bill_Number = new Label(1, i + 1, sdGrid.getString(2));
						Label total_Amount = new Label(2,i+1,sdGrid.getString(5));

						sheet.addCell(Sales_Date);
						sheet.addCell(Bill_Number);
						sheet.addCell(total_Amount);

						i++;
					}while(sdGrid.moveToNext());
				}
			}

			excel_read_alert("Select Option","Excel Created",file,context);

			wwb.write();
			wwb.close();

		}catch (Exception e){e.toString();}
	}
}
