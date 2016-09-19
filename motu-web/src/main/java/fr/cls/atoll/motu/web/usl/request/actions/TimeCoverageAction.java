package fr.cls.atoll.motu.web.usl.request.actions;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Interval;

import fr.cls.atoll.motu.api.message.MotuRequestParametersConstant;
import fr.cls.atoll.motu.api.message.xml.ErrorType;
import fr.cls.atoll.motu.api.message.xml.ObjectFactory;
import fr.cls.atoll.motu.api.message.xml.TimeCoverage;
import fr.cls.atoll.motu.api.utils.JAXBWriter;
import fr.cls.atoll.motu.web.bll.exception.ExceptionUtils;
import fr.cls.atoll.motu.web.bll.exception.MotuException;
import fr.cls.atoll.motu.web.bll.exception.MotuExceptionBase;
import fr.cls.atoll.motu.web.bll.exception.MotuMarshallException;
import fr.cls.atoll.motu.web.common.utils.StringUtils;
import fr.cls.atoll.motu.web.dal.request.netcdf.data.Product;
import fr.cls.atoll.motu.web.dal.request.netcdf.metadata.ProductMetaData;
import fr.cls.atoll.motu.web.usl.request.parameter.CommonHTTPParameters;
import fr.cls.atoll.motu.web.usl.request.parameter.exception.InvalidHTTPParameterException;
import fr.cls.atoll.motu.web.usl.request.parameter.validator.AbstractHTTPParameterValidator;
import fr.cls.atoll.motu.web.usl.request.parameter.validator.ProductHTTPParameterValidator;
import fr.cls.atoll.motu.web.usl.request.parameter.validator.ServiceHTTPParameterValidator;

/**
 * <br>
 * <br>
 * Copyright : Copyright (c) 2016 <br>
 * <br>
 * Société : CLS (Collecte Localisation Satellites)
 * 
 * @author Pierre LACOSTE
 * @version $Revision: 1.1 $ - $Date: 2007-05-22 16:56:28 $
 */
public class TimeCoverageAction extends AbstractProductInfoAction {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ACTION_NAME = "gettimecov";

    private ServiceHTTPParameterValidator serviceHTTPParameterValidator;
    private ProductHTTPParameterValidator productHTTPParameterValidator;

    public TimeCoverageAction(String actionCode_, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        super(ACTION_NAME, actionCode_, request, response, session);
        serviceHTTPParameterValidator = new ServiceHTTPParameterValidator(
                MotuRequestParametersConstant.PARAM_SERVICE,
                CommonHTTPParameters.getServiceFromRequest(getRequest()),
                AbstractHTTPParameterValidator.EMPTY_VALUE);
        serviceHTTPParameterValidator.setOptional(true);
        productHTTPParameterValidator = new ProductHTTPParameterValidator(
                MotuRequestParametersConstant.PARAM_PRODUCT,
                CommonHTTPParameters.getProductFromRequest(getRequest()),
                AbstractHTTPParameterValidator.EMPTY_VALUE);
        productHTTPParameterValidator.setOptional(true);
    }

    /** {@inheritDoc} */
    @Override
    protected void checkHTTPParameters() throws InvalidHTTPParameterException {
        super.checkHTTPParameters();
        serviceHTTPParameterValidator.validate();
        productHTTPParameterValidator.validate();

        try {
            String hasProductIdentifierErrMsg = hasProductIdentifier();
            if (hasProductIdentifierErrMsg != null) {
                throw new InvalidHTTPParameterException(hasProductIdentifierErrMsg, null, null, null);
            }
        } catch (MotuException e) {
            LOGGER.error("Error while calling hasProductIdentifier", e);
        }
    }

    protected String hasProductIdentifier() throws MotuException {
        String productId = getProductId();
        String locationData = CommonHTTPParameters.getDataFromParameter(getRequest());
        String serviceName = serviceHTTPParameterValidator.getParameterValueValidated();
        String hasProductIdentifierErrMsg = null;
        if (StringUtils.isNullOrEmpty(locationData) && StringUtils.isNullOrEmpty(productId)) {
            hasProductIdentifierErrMsg = String.format("ERROR: neither '%s' nor '%s' parameters are filled - Choose one of them",
                                                       MotuRequestParametersConstant.PARAM_DATA,
                                                       MotuRequestParametersConstant.PARAM_PRODUCT);

        }

        if (!StringUtils.isNullOrEmpty(locationData) && !StringUtils.isNullOrEmpty(productId)) {
            hasProductIdentifierErrMsg = String.format("ERROR: '%s' and '%s' parameters are not compatible - Choose only one of them",
                                                       MotuRequestParametersConstant.PARAM_DATA,
                                                       MotuRequestParametersConstant.PARAM_PRODUCT);
        }

        if (AbstractHTTPParameterValidator.EMPTY_VALUE.equals(serviceName) && !StringUtils.isNullOrEmpty(productId)) {
            hasProductIdentifierErrMsg = String.format("ERROR: '%s' parameter is filled but '%s' is empty. You have to fill it.",
                                                       MotuRequestParametersConstant.PARAM_PRODUCT,
                                                       MotuRequestParametersConstant.PARAM_SERVICE);
        }

        return hasProductIdentifierErrMsg;
    }

    /** {@inheritDoc} */
    @Override
    protected void process() throws MotuException {
        String hasProductIdentifierErrMsg = hasProductIdentifier();
        if (hasProductIdentifierErrMsg == null) {
            TimeCoverage timeCoverage;
            try {
                Product product = getProduct();
                initProductMetaData(product);
                timeCoverage = initTimeCoverage(product.getProductMetaData());

                marshallTimeCoverage(timeCoverage, getResponse().getWriter());

                getResponse().setContentType(null);
            } catch (MotuExceptionBase | JAXBException | IOException e) {
                throw new MotuException(ErrorType.SYSTEM, e);
            }
        } else {
            throw new MotuException(ErrorType.BAD_PARAMETERS, new InvalidHTTPParameterException(hasProductIdentifierErrMsg, null, null, null));
        }
    }

    /**
     * Marshall time coverage.
     * 
     * @param timeCoverage the time coverage
     * @param writer the writer
     * 
     * @throws MotuMarshallException the motu marshall exception
     * @throws JAXBException
     * @throws IOException
     */
    public static void marshallTimeCoverage(TimeCoverage timeCoverage, Writer writer) throws MotuMarshallException, JAXBException, IOException {
        if (writer == null) {
            return;
        }

        JAXBWriter.getInstance().write(timeCoverage, writer);
        writer.flush();
        writer.close();
    }

    public TimeCoverage initTimeCoverage(ProductMetaData productMetaData) throws MotuException {
        if (productMetaData == null) {
            return null;
        }
        Interval datePeriod = productMetaData.getTimeCoverage();
        return initTimeCoverage(datePeriod);
    }

    /**
     * Inits the time coverage.
     * 
     * @param datePeriod the date period
     * 
     * @return the time coverage
     * 
     * @throws MotuException the motu exception
     */
    public TimeCoverage initTimeCoverage(Interval datePeriod) throws MotuException {
        TimeCoverage timeCoverage = createTimeCoverage();
        if (datePeriod == null) {
            return timeCoverage;
        }

        Date start = datePeriod.getStart().toDate();
        Date end = datePeriod.getEnd().toDate();

        timeCoverage.setStart(dateToXMLGregorianCalendar(start));
        timeCoverage.setEnd(dateToXMLGregorianCalendar(end));
        timeCoverage.setCode(StringUtils.getErrorCode(getActionCode(), ErrorType.OK));
        timeCoverage.setMsg(ErrorType.OK.toString());

        return timeCoverage;
    }

    /**
     * Creates the time coverage.
     * 
     * @return the time coverage
     */
    public TimeCoverage createTimeCoverage() {
        ObjectFactory objectFactory = new ObjectFactory();

        TimeCoverage timeCoverage = objectFactory.createTimeCoverage();
        timeCoverage.setStart(null);
        timeCoverage.setEnd(null);
        ExceptionUtils
                .setError(getActionCode(),
                          timeCoverage,
                          new MotuException(ErrorType.SYSTEM, "If you see that message, the request has failed and the error has not been filled"));
        return timeCoverage;

    }

}
