package org.qrinvoice.gui;

import org.apache.log4j.Logger;
import org.qrinvoice.core.DateParam;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.text.ParseException;

/**
 * Created by zcg on 3.7.2016.
 */
@FacesConverter("DateParamConverter")
public class DateParamConverter implements Converter {


    static Logger log = Logger.getLogger(DateParamConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        log.info("getAsObject:" + s);

        if (s == null) {
            return null;
        } else {
            try {
                return new DateParam(s);
            } catch (ParseException e) {
                throw new ConverterException(e);
            }
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        log.info("getAsString:" + o);

        if (o == null) {
            return null;
        }
        return o.toString();
    }
}
