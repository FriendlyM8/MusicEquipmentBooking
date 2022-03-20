package com.example.musicequipmentbooking.Alden;


/**
 * This is the instrument class
 */
public class CISInstrument {
    private String instrumentType;
    private String instrumentID;
    private int instrumentDaysRequest;
    private int instrumentDaysBorrowed;
    private int instrumentBorrowLimit;
    private String instrumentBorrower;
    private boolean borrowedStatus;
    private boolean returnedChecked;

    public CISInstrument()
    {
      //blank constructor for RecyclerView OnClick listener
    }

    public CISInstrument(String instrumentType, String instrumentID, int instrumentDaysRequest, int instrumentDaysBorrowed, int instrumentBorrowLimit, String instrumentBorrower, boolean borrowedStatus, boolean returnedChecked) {
        this.instrumentType = instrumentType;
        this.instrumentID = instrumentID;
        this.instrumentDaysRequest = instrumentDaysRequest;
        this.instrumentDaysBorrowed = instrumentDaysBorrowed;
        this.instrumentBorrowLimit = instrumentBorrowLimit;
        this.instrumentBorrower = instrumentBorrower;
        this.borrowedStatus = borrowedStatus;
        this.returnedChecked = returnedChecked;

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

    public int getInstrumentBorrowLimit() {
        return instrumentBorrowLimit;
    }

    public void setInstrumentBorrowLimit(int instrumentBorrowLimit) {
        this.instrumentBorrowLimit = instrumentBorrowLimit;
    }

    public String getInstrumentBorrower() {
        return instrumentBorrower;
    }

    public void setInstrumentBorrower(String instrumentBorrower) {
        this.instrumentBorrower = instrumentBorrower;
    }

    public boolean isBorrowedStatus() {
        return borrowedStatus;
    }

    public void setBorrowedStatus(boolean borrowedStatus) {
        this.borrowedStatus = borrowedStatus;
    }

    public boolean isReturnedChecked() {
        return returnedChecked;
    }

    public void setReturnedChecked(boolean returnedChecked) {
        this.returnedChecked = returnedChecked;
    }

    @Override
    public String toString() {
        return "CISInstrument{" +
                "instrumentType='" + instrumentType + '\'' +
                ", instrumentID='" + instrumentID + '\'' +
                ", instrumentDaysRequest=" + instrumentDaysRequest +
                ", instrumentDaysBorrowed=" + instrumentDaysBorrowed +
                ", instrumentBorrowLimit=" + instrumentBorrowLimit +
                ", instrumentBorrower='" + instrumentBorrower + '\'' +
                ", borrowedStatus=" + borrowedStatus +
                ", returnedChecked=" + returnedChecked +
                '}';
    }
}
