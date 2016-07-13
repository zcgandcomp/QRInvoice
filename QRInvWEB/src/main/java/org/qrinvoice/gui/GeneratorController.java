package org.qrinvoice.gui;


import org.apache.log4j.Logger;
import org.qrinvoice.core.AccountNotValidException;
import org.qrinvoice.core.AccountNumberImpl;
import org.qrinvoice.core.Generator;
import org.qrinvoice.core.InvoiceParamDomain;
import org.qrinvoice.model.InvoiceMapper;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created by zcg on 27.6.2016.
 */
@Named
@SessionScoped
@ManagedBean
public class GeneratorController implements Serializable {

    static Logger log = Logger.getLogger(GeneratorController.class.getName());
    private String qrString;


    @Inject
    @Named("invoiceWebModel")
    private InvoiceWebModel model;


    public void generate(ActionEvent actionEvent) {

        log.info("generate:" + actionEvent);

        Generator generator = new Generator();

        InvoiceParamDomain param = InvoiceMapper.INSTANCE.invoiceModelToInvoiceParam(model);

        try {

            AccountNumberImpl accNum = new AccountNumberImpl(model.getAccountNumber().getAccountPrefix(), model.getAccountNumber().getAccountBase(), model.getAccountNumber().getBankCode());

            param.setIBAN(accNum.computeIBAN());


            setQrString(generator.getInvoiceString(param, true));
        } catch (UnsupportedEncodingException e) {
            log.fatal("error generating QR code", e);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chyba při generování", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (AccountNotValidException e) {
            log.fatal("error account number not valid", e);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Číslo účtu není validní", null);
            FacesContext.getCurrentInstance().addMessage("acc1", message);
        }
    }

    public String getQrString() {
        log.debug("getQrString:" + qrString);
        return qrString;
    }

    public void setQrString(String qrString) {

        log.debug("setQrString:" + qrString);
        this.qrString = qrString;
    }

}
