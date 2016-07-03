package org.qrinvoice.helper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.qrinvoice.domain.InvoiceParam;
import org.qrinvoice.gui.InvoiceModel;

/**
 * Created by zcg on 3.7.2016.
 * mapper for inner presentation of Invoice
 */
@Mapper
public interface  InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper( InvoiceMapper.class );

    // TODO compute IBAN using mapstruct
    //@Mapping(source = "xxx", target = "yyy")
    InvoiceParam invoiceModelToInvoiceParam(InvoiceModel invoice);


}
