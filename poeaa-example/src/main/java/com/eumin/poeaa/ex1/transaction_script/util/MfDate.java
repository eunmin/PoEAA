package com.eumin.poeaa.ex1.transaction_script.util;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MfDate {
    private GregorianCalendar myBase;

    public MfDate(Date arg) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(arg);
        initialize(gc);
    }

    private void initialize(GregorianCalendar arg) {
        myBase = trimToDays(arg);
    }

    public MfDate(GregorianCalendar arg) {
        initialize(arg);
    }

    private GregorianCalendar trimToDays(GregorianCalendar arg) {
        arg.set(Calendar.HOUR_OF_DAY, 0);
        arg.set(Calendar.MINUTE, 0);
        arg.set(Calendar.SECOND, 0);
        arg.set(Calendar.MILLISECOND, 0);
        return arg;
    }

    public MfDate(java.sql.Date dateSigned) {
        Date arg = new Date(dateSigned.getTime());
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(arg);
        initialize(gc);
    }

    public java.sql.Date toSqlDate() {
        return new java.sql.Date(myBase.getTime().getTime());
    }

    public int getYear() {
        return myBase.get(Calendar.YEAR);
    }

    public int getMonth() {
        return myBase.get(Calendar.MONTH);
    }

    public int getDayOfMonth() {
        return myBase.get(Calendar.DAY_OF_MONTH);
    }

    public MfDate addDays(int arg) {
        return new MfDate(new GregorianCalendar(getYear(), getMonth(), getDayOfMonth() + arg));
    }
}
