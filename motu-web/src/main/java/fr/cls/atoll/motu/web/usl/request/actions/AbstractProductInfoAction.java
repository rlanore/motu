package fr.cls.atoll.motu.web.usl.request.actions;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import fr.cls.atoll.motu.api.message.MotuRequestParametersConstant;
import fr.cls.atoll.motu.web.bll.BLLManager;
import fr.cls.atoll.motu.web.bll.exception.MotuException;
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
public abstract class AbstractProductInfoAction extends AbstractAction {

    private ServiceHTTPParameterValidator serviceHTTPParameterValidator;
    private ProductHTTPParameterValidator productHTTPParameterValidator;

    /**
     * Constructeur.
     * 
     * @param actionName_
     * @param request_
     * @param response_
     */
    public AbstractProductInfoAction(String actionName_, HttpServletRequest request_, HttpServletResponse response_) {
        this(actionName_, request_, response_, null);
        initValidator();
    }

    /**
     * Constructeur.
     * 
     * @param actionName_
     * @param request_
     * @param response_
     * @param session_
     */
    public AbstractProductInfoAction(String actionName_, HttpServletRequest request_, HttpServletResponse response_, HttpSession session_) {
        super(actionName_, request_, response_, session_);
        initValidator();
    }

    private void initValidator() {
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

    protected ServiceHTTPParameterValidator getServiceHTTPParameterValidator() {
        return serviceHTTPParameterValidator;
    }

    protected ProductHTTPParameterValidator getProductHTTPParameterValidator() {
        return productHTTPParameterValidator;
    }

    /** {@inheritDoc} */
    @Override
    protected void checkHTTPParameters() throws InvalidHTTPParameterException {
        serviceHTTPParameterValidator.validate();
        productHTTPParameterValidator.validate();
    }

    protected boolean hasProductIdentifier() throws MotuException {
        boolean hasproductIdentifier = true;
        String productId = getProductId();
        String locationData = CommonHTTPParameters.getDataFromParameter(getRequest());
        String serviceName = serviceHTTPParameterValidator.getParameterValueValidated();
        try {
            if (StringUtils.isNullOrEmpty(locationData) && StringUtils.isNullOrEmpty(productId)) {
                getResponse().sendError(400,
                                        String.format("ERROR: neither '%s' nor '%s' parameters are filled - Choose one of them",
                                                      MotuRequestParametersConstant.PARAM_DATA,
                                                      MotuRequestParametersConstant.PARAM_PRODUCT));
                hasproductIdentifier = false;

            }

            if (!StringUtils.isNullOrEmpty(locationData) && !StringUtils.isNullOrEmpty(productId)) {
                getResponse().sendError(400,
                                        String.format("ERROR: '%s' and '%s' parameters are not compatible - Choose only one of them",
                                                      MotuRequestParametersConstant.PARAM_DATA,
                                                      MotuRequestParametersConstant.PARAM_PRODUCT));
                hasproductIdentifier = false;
            }

            if (AbstractHTTPParameterValidator.EMPTY_VALUE.equals(serviceName) && !StringUtils.isNullOrEmpty(productId)) {
                getResponse().sendError(400,
                                        String.format("ERROR: '%s' parameter is filled but '%s' is empty. You have to fill it.",
                                                      MotuRequestParametersConstant.PARAM_PRODUCT,
                                                      MotuRequestParametersConstant.PARAM_SERVICE));
                hasproductIdentifier = false;
            }
        } catch (IOException e) {
            throw new MotuException(e);
        }

        return hasproductIdentifier;
    }

    /** {@inheritDoc} */
    @Override
    protected abstract void process() throws MotuException;

    /**
     * Gets the product id.
     *
     * @param paramId the product id
     * @param request the request
     * @param response the response
     * @return the product id
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     * @throws MotuException the motu exception
     */
    protected String getProductId() throws MotuException {
        String paramId = productHTTPParameterValidator.getParameterValueValidated();
        String serviceName = CommonHTTPParameters.getServiceFromRequest(getRequest());

        if (!AbstractHTTPParameterValidator.EMPTY_VALUE.equals(paramId)) {
            if ((StringUtils.isNullOrEmpty(serviceName)) || (StringUtils.isNullOrEmpty(paramId))) {
                return paramId;
            }

            String uri = paramId;
            String[] split = uri.split(".*#");
            if (split.length <= 1) {
                return uri;
            }
            return split[1];
        } else {
            return "";
        }
    }

    /**
     * Return the product object associated to the provided product identifier into http parameters. The
     * result product has the product metadata retrieved also. .
     * 
     * @return The product object with the product metedata.
     * @throws MotuException
     */
    protected Product getProduct() throws MotuException {
        String productId = getProductId();
        String serviceName = getServiceHTTPParameterValidator().getParameterValueValidated();
        String locationData = CommonHTTPParameters.getDataFromParameter(getRequest());

        Product p = null;
        if (!StringUtils.isNullOrEmpty(locationData)) {
            p = BLLManager.getInstance().getCatalogManager().getProductManager().getProduct(locationData);
        } else if (!AbstractHTTPParameterValidator.EMPTY_VALUE.equals(serviceName) && !StringUtils.isNullOrEmpty(productId)) {
            p = BLLManager.getInstance().getCatalogManager().getProductManager().getProduct(serviceName, StringUtils.getDataSetName(productId));
        }

        ProductMetaData pmd = BLLManager.getInstance().getCatalogManager().getProductManager().getProductMetaData(p.getProductId(),
                                                                                                                  p.getLocationData());
        p.setProductMetaData(pmd);

        return p;
    }

    /**
     * Date to XML gregorian calendar.
     * 
     * @param date the date
     * 
     * @return the XML gregorian calendar
     * 
     * @throws MotuException the motu exception
     */
    public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) throws MotuException {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlGregorianCalendar;
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new MotuException("ERROR in dateToXMLGregorianCalendar", e);
        }
        return xmlGregorianCalendar;
    }
}
