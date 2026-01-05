package com.josh.example.searchviewnew;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by johan on 17/01/2016.
 */
public class Data implements Serializable
{

    private String Title;
    private String Description;
    private String Company;





    public static ArrayList<Data> getData() {
        ArrayList<Data> datas = new ArrayList<>();
//        datas.add(new Data("Warehouse Support Staffs", "Nyan.SDN.BHD" , "London" , "Part Time" , 10, Data.Desc_data, Data.SkillReq_Data , Data.Qualification_Data));

        return datas;
    }


    public Data(String Title, String Company, String Location)
    {
        this.Title = Title;
        this.Company = Company;
        this.Description = Location;
    }


}

