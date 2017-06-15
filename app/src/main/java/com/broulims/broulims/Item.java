package com.broulims.broulims;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Daniel on 6/5/2017.
 * Updated by Nathan to better match the firebase
 */

public class Item {

    private Long Aisle;
    private Long BaseMultiple;
    private Long DepartmentNumber;
    private Long Facing;
    private Long Section;
    private Long Size;
    private String BasePrice;
    private String BaseStart;
    private String DepartmentName;
    private String ItemDescription;
    private String POSDescription;
    private String Side;
    private String UOM;


    public Long getAisle() {
        return Aisle;
    }

    public void setAisle(Long aisle) {
        Aisle = aisle;
    }

    public Long getBaseMultiple() {
        return BaseMultiple;
    }

    public void setBaseMultiple(Long baseMultiple) {
        BaseMultiple = baseMultiple;
    }

    public Long getDepartmentNumber() {
        return DepartmentNumber;
    }

    public void setDepartmentNumber(Long departmentNumber) {
        DepartmentNumber = departmentNumber;
    }

    public Long getFacing() {
        return Facing;
    }

    public void setFacing(Long facing) {
        Facing = facing;
    }

    public Long getSection() {
        return Section;
    }

    public void setSection(Long section) {
        Section = section;
    }

    public Long getSize() {
        return Size;
    }

    public void setSize(Long size) {
        Size = size;
    }

    public String getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(String basePrice) {
        BasePrice = basePrice;
    }

    public String getBaseStart() {
        return BaseStart;
    }

    public void setBaseStart(String baseStart) {
        BaseStart = baseStart;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getPOSDescription() {
        return POSDescription;
    }

    public void setPOSDescription(String POSDescription) {
        this.POSDescription = POSDescription;
    }

    public String getSide() {
        return Side;
    }

    public void setSide(String side) {
        Side = side;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public Item(Long aisle,
                Long baseMultiple,
                Long departmentNumber,
                Long facing,
                Long section,
                Long size,
                String basePrice,
                String baseStart,
                String departmentName,
                String itemDescription,
                String POSDescription,
                String side,
                String UOM) {
        Aisle = aisle;
        BaseMultiple = baseMultiple;
        DepartmentNumber = departmentNumber;
        Facing = facing;
        Section = section;
        Size = size;
        BasePrice = basePrice;
        BaseStart = baseStart;
        DepartmentName = departmentName;
        ItemDescription = itemDescription;
        this.POSDescription = POSDescription;
        Side = side;
        this.UOM = UOM;
    }

    public Item()
    {
        Aisle = 0l;
        BaseMultiple = 0l;
        DepartmentNumber = 0l;
        Facing = 0l;
        Section = 0l;
        Size = 0l;
        BasePrice = "";
        BaseStart = "";
        DepartmentName = "";
        ItemDescription = "";
        POSDescription = "";
        Side = "";
        UOM = "";
    }

}
