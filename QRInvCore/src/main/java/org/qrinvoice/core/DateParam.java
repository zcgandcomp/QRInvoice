package org.qrinvoice.core;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zcg on 14.6.2016.
 * special type for conversion to DATE param
 */
public class DateParam implements Serializable{


    // Declare the date format for the parsing to be correct
    private transient SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    private Date date;


    /**
     * constructor for creating date param
     *
     * @param dateStr string for date creation it will be parsed by yyyyMMdd format
     * @throws ParseException if string cannot be parset into Date
     */
    // TODO throw business exception if parsing Exception occurs
    public DateParam(String dateStr) throws ParseException {

        date = new Date(df.parse(dateStr).getTime());

    }

    public Date getDate(){
        return date;
    }

    @Override
    public String toString() {
        if (date != null) {
            return df.format(date);
        } else {
            return "";
        }
    }

}
