package com.example.a1530630.learningapplication.Models;

public class Modules
{
    public static final String MODULE_TABLE_NAME="MODULES";
    public static final String MODULE_COLUMN_ID ="ModuleID";
    public static final String MODULE_COLUMN_NUMBER="ModuleNum";

    public static String CREATE_MODULE_TABLE = "CREATE TABLE "+MODULE_TABLE_NAME+" ("
            + MODULE_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MODULE_COLUMN_NUMBER+" INTEGER);";

    private Integer moduleID, moduleNum;

    public Modules(){}
    public Modules(Integer id, Integer num)
    {
        this.moduleID = id;
        this.moduleNum = num;
    }
    public Modules(Integer num)
    {
        this.moduleID = null;
        this.moduleNum = num;
    }

    public Integer getModuleID() {
        return moduleID;
    }

    public void setModuleID(Integer moduleID) {
        this.moduleID = moduleID;
    }

    public Integer getModuleNum() {
        return moduleNum;
    }

    public void setModuleNum(Integer moduleNum) {
        this.moduleNum = moduleNum;
    }

    public static Modules[] populateData()
    {
        return new Modules[]
                {
                    new Modules(1,1),
                    new Modules(2,2),
                };
    }
}
