package cn.yq.data;

import com.alibaba.fastjson.annotation.JSONField;

public class vehicle {
    @JSONField(ordinal = 1)
    private String phoneNo;
    @JSONField(ordinal = 2)
    private String vinNo;
    @JSONField(ordinal = 3)
    private String vehicleNo;
    @JSONField(ordinal = 4)
    private String engineNo;

    public vehicle() {
        super();
    }

    public vehicle(String phoneNo, String vinNo, String vehicleNo, String engineNo) {
        super();
        this.phoneNo = phoneNo;
        this.vinNo = vinNo;
        this.vehicleNo = vehicleNo;
        this.engineNo = engineNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }


}
