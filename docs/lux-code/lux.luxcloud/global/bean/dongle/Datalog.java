package com.lux.luxcloud.global.bean.dongle;

import com.lux.luxcloud.global.bean.inverter.Inverter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\LuxCloud_4.2.0_APKPure\com.lux.luxcloud\classes3.dex */
public class Datalog {
    private Long clusterId;
    private Date commissionDate;
    private Date createDate;
    private DATALOG_TYPE datalogType;
    private Long engineerId;
    private String firmwareVersion;
    private Boolean handleDuplicateSnDone;
    private Integer inputInterval;
    private List<Inverter> inverters = new ArrayList();
    private Date lastUpdateTime;
    private boolean lost;
    private Boolean needHandleDuplicateSn;
    private Boolean needResetToCluster100;
    private String orginSnDuplicate;
    private Long ownerL2Id;
    private Long ownerL3Id;
    private Long plantId;
    private Integer protocolVersion;
    private String resetSnDuplicate;
    private Date resetSnDuplicateDoneTime;
    private Date resetSnDuplicateLastTime;
    private Date resetSnDuplicateStartTime;
    private String serialNum;
    private Long serverId;
    private String simCardSn;
    private Long userId;

    public Datalog() {
    }

    public Datalog(String str) {
        this.serialNum = str;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String str) {
        this.serialNum = str;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long l) {
        this.userId = l;
    }

    public Long getOwnerL2Id() {
        return this.ownerL2Id;
    }

    public void setOwnerL2Id(Long l) {
        this.ownerL2Id = l;
    }

    public Long getOwnerL3Id() {
        return this.ownerL3Id;
    }

    public void setOwnerL3Id(Long l) {
        this.ownerL3Id = l;
    }

    public Long getEngineerId() {
        return this.engineerId;
    }

    public void setEngineerId(Long l) {
        this.engineerId = l;
    }

    public Long getPlantId() {
        return this.plantId;
    }

    public void setPlantId(Long l) {
        this.plantId = l;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date date) {
        this.createDate = date;
    }

    public DATALOG_TYPE getDatalogType() {
        return this.datalogType;
    }

    public void setDatalogType(DATALOG_TYPE datalog_type) {
        this.datalogType = datalog_type;
    }

    public String getSimCardSn() {
        return this.simCardSn;
    }

    public void setSimCardSn(String str) {
        this.simCardSn = str;
    }

    public Integer getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(Integer num) {
        this.protocolVersion = num;
    }

    public boolean isLost() {
        return this.lost;
    }

    public void setLost(boolean z) {
        this.lost = z;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date date) {
        this.lastUpdateTime = date;
    }

    public Long getServerId() {
        return this.serverId;
    }

    public void setServerId(Long l) {
        this.serverId = l;
    }

    public Long getClusterId() {
        return this.clusterId;
    }

    public void setClusterId(Long l) {
        this.clusterId = l;
    }

    public Date getCommissionDate() {
        return this.commissionDate;
    }

    public void setCommissionDate(Date date) {
        this.commissionDate = date;
    }

    public Integer getInputInterval() {
        return this.inputInterval;
    }

    public void setInputInterval(Integer num) {
        this.inputInterval = num;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String str) {
        this.firmwareVersion = str;
    }

    public Boolean getNeedHandleDuplicateSn() {
        return this.needHandleDuplicateSn;
    }

    public boolean getNeedHandleDuplicateSnValue() {
        Boolean bool = this.needHandleDuplicateSn;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public void setNeedHandleDuplicateSn(Boolean bool) {
        this.needHandleDuplicateSn = bool;
    }

    public Boolean getHandleDuplicateSnDone() {
        return this.handleDuplicateSnDone;
    }

    public boolean getHandleDuplicateSnDoneValue() {
        Boolean bool = this.handleDuplicateSnDone;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public void setHandleDuplicateSnDone(Boolean bool) {
        this.handleDuplicateSnDone = bool;
    }

    public String getOrginSnDuplicate() {
        return this.orginSnDuplicate;
    }

    public void setOrginSnDuplicate(String str) {
        this.orginSnDuplicate = str;
    }

    public String getResetSnDuplicate() {
        return this.resetSnDuplicate;
    }

    public void setResetSnDuplicate(String str) {
        this.resetSnDuplicate = str;
    }

    public Date getResetSnDuplicateStartTime() {
        return this.resetSnDuplicateStartTime;
    }

    public void setResetSnDuplicateStartTime(Date date) {
        this.resetSnDuplicateStartTime = date;
    }

    public Date getResetSnDuplicateLastTime() {
        return this.resetSnDuplicateLastTime;
    }

    public void setResetSnDuplicateLastTime(Date date) {
        this.resetSnDuplicateLastTime = date;
    }

    public Date getResetSnDuplicateDoneTime() {
        return this.resetSnDuplicateDoneTime;
    }

    public void setResetSnDuplicateDoneTime(Date date) {
        this.resetSnDuplicateDoneTime = date;
    }

    public Boolean getNeedResetToCluster100() {
        return this.needResetToCluster100;
    }

    public boolean getNeedResetToCluster100Value() {
        Boolean bool = this.needResetToCluster100;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public void setNeedResetToCluster100(Boolean bool) {
        this.needResetToCluster100 = bool;
    }

    public void setInverters(List<Inverter> list) {
        this.inverters = list;
    }

    public List<Inverter> getInverters() {
        return this.inverters;
    }

    public void addInverter(Inverter inverter) {
        this.inverters.add(inverter);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Datalog)) {
            return false;
        }
        Datalog datalog = (Datalog) obj;
        String str = this.serialNum;
        if (str == null) {
            return datalog.serialNum == null;
        }
        return str.equals(datalog.serialNum);
    }

    public int hashCode() {
        String str = this.serialNum;
        if (str == null) {
            return 0;
        }
        return str.hashCode();
    }
}