package org.qrinvoice.model;

import org.qrinvoice.core.AccountNotValidException;
import org.qrinvoice.core.InvoiceParamDomain;

/**
 * Created by zcg on 3.7.2016.
 * mapper for inner presentation of Invoice
 */

@FunctionalInterface
public interface InvoiceMapper {

    InvoiceParamDomain invoiceModelToInvoiceParam(InvoiceModel invoice) throws AccountNotValidException;

}
