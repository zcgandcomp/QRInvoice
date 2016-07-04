package org.qrinvoice.rest;

import org.qrinvoice.core.DateParam;

import javax.ws.rs.FormParam;
import java.math.BigDecimal;

/**
 * Created by zcg on 13.6.2016.
 */
//@BadgerFish


public class testDTO {


    @FormParam(value = "id")
    private String id;

    @FormParam(value = "datum")
    private DateParam datum;

    @FormParam(value = "cislo")
    private BigDecimal cislo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateParam getDatum() {
        return datum;
    }

    public void setDatum(DateParam datum) {
        this.datum = datum;
    }

    public BigDecimal getCislo() {
        return cislo;
    }

    public void setCislo(BigDecimal cislo) {
        this.cislo = cislo;
    }
}
