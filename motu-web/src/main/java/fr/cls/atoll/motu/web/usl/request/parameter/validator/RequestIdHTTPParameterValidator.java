package fr.cls.atoll.motu.web.usl.request.parameter.validator;

import fr.cls.atoll.motu.web.usl.request.parameter.exception.InvalidHTTPParameterException;

/**
 * <br>
 * <br>
 * Copyright : Copyright (c) 2016 <br>
 * <br>
 * Société : CLS (Collecte Localisation Satellites)
 * 
 * @author Sylvain MARTY
 * @version $Revision: 1.1 $ - $Date: 2007-05-22 16:56:28 $
 */
public class RequestIdHTTPParameterValidator extends AbstractHTTPParameterValidator<Long> {

    public RequestIdHTTPParameterValidator(String parameterName_, String parameterValue_) {
        super(parameterName_, parameterValue_);
    }

    /**
     * .
     * 
     */
    @Override
    public Long onValidateAction() throws InvalidHTTPParameterException {
        String requestIdStr = getParameterValue();
        try {
            Long rqtId = Long.parseLong(requestIdStr);
            return rqtId;
        } catch (Exception e) {
            throw new InvalidHTTPParameterException(getParameterName(), getParameterValue(), getParameterBoundaries());
        }
    }

    @Override
    protected String getParameterBoundaries() {
        return "[1;" + Long.MAX_VALUE + "]";
    }
}
