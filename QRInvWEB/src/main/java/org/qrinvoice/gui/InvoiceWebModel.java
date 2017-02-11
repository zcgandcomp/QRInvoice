package org.qrinvoice.gui;

import org.qrinvoice.model.InvoiceModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 * class is here basically to annotated model class with JSF features
 */

// Managed Bean has to be there because @RequestScoped annotation - JSF part of application
@ManagedBean
// this has to be from jsf package otherwise resteasy will fail to properly use bean
@Named("invoiceWebModel")
@RequestScoped
public class InvoiceWebModel extends InvoiceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5199699913462799919L;

}
