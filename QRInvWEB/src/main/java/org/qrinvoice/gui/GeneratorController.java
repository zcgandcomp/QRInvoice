package org.qrinvoice.gui;


import org.qrinvoice.core.AccountNotValidException;
import org.qrinvoice.core.AccountNumberImpl;
import org.qrinvoice.core.Generator;
import org.qrinvoice.domain.InvoiceParam;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zcg on 27.6.2016.
 */
@Named
@SessionScoped
@ManagedBean
public class GeneratorController implements Serializable {

    private String qrString;


    @Inject
    @Named("invoiceModel")
    private InvoiceModel model;


    public void generate(ActionEvent actionEvent) {


        Generator generator = new Generator();

        InvoiceParam param = new InvoiceParam();

        param.setCurrencyCode(model.getCurrencyCode());

        // TODO mapstruct


        try {

            AccountNumberImpl accNum = new AccountNumberImpl(model.getAccountNumber().getAccountPrefix(), model.getAccountNumber().getAccountBase(), model.getAccountNumber().getBankCode());

            param.setIBAN(accNum.computeIBAN());

            String qrString = generator.getInvoiceString(param, true);

            setQrString(qrString);
        } catch (IOException e) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Chyba při generování", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (AccountNotValidException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Číslo účtu není validní", null);
            FacesContext.getCurrentInstance().addMessage("acc1", message);
        }

// todo display accuall generated QR code

    }

    public String getQrString() {
        return qrString;
    }

    public void setQrString(String qrString) {
        this.qrString = qrString;
    }

}
