package org.qrinvoice.model;

import org.apache.log4j.Logger;
import org.qrinvoice.core.AccountNotValidException;
import org.qrinvoice.core.AccountNumberImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by zcg on 17.7.2016.
 */
public class InvoiceModelValidator implements ConstraintValidator<CheckInvoiceModelValidator, InvoiceModel> {

    static Logger log = Logger.getLogger(InvoiceModelValidator.class.getName());

    @Override
    public void initialize(CheckInvoiceModelValidator constraintAnnotation) {
        // empty initialization
    }

    @Override
    public boolean isValid(InvoiceModel model, ConstraintValidatorContext context) {
        log.info("is valid:" + model);
        boolean retVal = true;

        ConstraintValidatorContext.ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate("{org.qrinvoice.InvoiceModelException}");
        // if we fill IBAN parameter account number should be empty

        if (log.isDebugEnabled()) {
            log.debug("iban:" + model.getIBAN());
            log.debug("account number:" + model.getAccountNumber());
        }
        if (!(model.getIBAN() == null || model.getAccountNumber().isEmpty())) {
            try {
                // check if iban is same as computed iban otherwise raise validation exception
                AccountNumberImpl accNum = new AccountNumberImpl(model.getAccountNumber().getAccountPrefix(), model.getAccountNumber().getAccountBase(), model.getAccountNumber().getBankCode());
                String IBAN = accNum.computeIBAN();

                if (!model.getIBAN().equalsIgnoreCase(IBAN)) {
                    retVal = false;
                    builder.addNode("ACC");
                }
            } catch (AccountNotValidException e) {
                log.fatal("invalid account number", e);
                builder.addNode("Account");
            }
        }


        if (!retVal) {
            context.disableDefaultConstraintViolation();
            builder.addConstraintViolation();

        }

        return retVal;
    }
}
