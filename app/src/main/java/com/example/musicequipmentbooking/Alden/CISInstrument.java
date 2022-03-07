package com.example.musicequipmentbooking.Alden;

public class CISInstrument {
    private String instrumentType;
    private String instrumentID;
    private int instrumentDaysRequest;
    private int instrumentDaysBorrowed;
    private String instrumentBorrower;

    public CISInstrument(String instrumentType, String instrumentID, int instrumentDaysRequest, int instrumentDaysBorrowed, String instrumentBorrower) {
        this.instrumentType = instrumentType;
        this.instrumentID = instrumentID;
        this.instrumentDaysRequest = instrumentDaysRequest;
        this.instrumentDaysBorrowed = instrumentDaysBorrowed;
        this.instrumentBorrower = instrumentBorrower;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

    public int getInstrumentDaysRequest() {
        return instrumentDaysRequest;
    }

    public void setInstrumentDaysRequest(int instrumentDaysRequest) {
        this.instrumentDaysRequest = instrumentDaysRequest;
    }

    public int getInstrumentDaysBorrowed() {
        return instrumentDaysBorrowed;
    }

    public void setInstrumentDaysBorrowed(int instrumentDaysBorrowed) {
        this.instrumentDaysBorrowed = instrumentDaysBorrowed;
    }

    public String getInstrumentBorrower() {
        return instrumentBorrower;
    }

    public void setInstrumentBorrower(String instrumentBorrower) {
        this.instrumentBorrower = instrumentBorrower;
    }

    @Override
    public String toString() {
        return "CISInstrument{" +
                "instrumentType='" + instrumentType + '\'' +
                ", instrumentID='" + instrumentID + '\'' +
                ", instrumentDaysRequest=" + instrumentDaysRequest +
                ", instrumentDaysBorrowed=" + instrumentDaysBorrowed +
                ", instrumentBorrower='" + instrumentBorrower + '\'' +
                '}';
    }
}
