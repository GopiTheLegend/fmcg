package app.legend.gopiking.fmcg.dbLib;

import app.legend.gopiking.fmcg.dbTables.*;

public class DBConfig {
    static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "FMC";

    static tbUom TB_UOM = new tbUom();
    static tbStockGroup TB_STOCKGROUP = new tbStockGroup();
    static tbProduct TB_PRODUCT = new tbProduct();
    static tbCustomer TB_CUSTOMER = new tbCustomer();

    static BizTable [] DB_Tables =
            {
                    TB_UOM.GetTable(),
                    TB_STOCKGROUP.GetTable(),
                    TB_PRODUCT.GetTable(),
                    TB_CUSTOMER.GetTable()
            };
}
