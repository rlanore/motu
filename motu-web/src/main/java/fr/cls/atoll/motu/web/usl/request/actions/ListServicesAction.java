package fr.cls.atoll.motu.web.usl.request.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;

import fr.cls.atoll.motu.api.message.MotuRequestParametersConstant;
import fr.cls.atoll.motu.library.misc.configuration.ConfigService;
import fr.cls.atoll.motu.library.misc.exception.MotuException;
import fr.cls.atoll.motu.web.bll.BLLManager;
import fr.cls.atoll.motu.web.common.utils.StringUtils;
import fr.cls.atoll.motu.web.usl.request.parameter.CommonHTTPParameters;
import fr.cls.atoll.motu.web.usl.request.parameter.exception.InvalidHTTPParameterException;
import fr.cls.atoll.motu.web.usl.request.parameter.validator.CatalogTypeParameterValidator;
import fr.cls.atoll.motu.web.usl.response.velocity.VelocityTemplateManager;
import fr.cls.atoll.motu.web.usl.response.velocity.model.converter.VelocityModelConverter;

/**
 * <br>
 * <br>
 * Copyright : Copyright (c) 2016 <br>
 * <br>
 * Société : CLS (Collecte Localisation Satellites) <br>
 * <br>
 * This interface is used to download data with subsetting.<br>
 * Operation invocation consists in performing an HTTP GET request.<br>
 * Input parameters are the following: [x,y] is the cardinality<br>
 * <ul>
 * <li><b>action</b>: [1]: {@link #ACTION_NAME}</li>
 * <li><b>catalogtype</b>: [0,1]: Catalog type: TDS, FTP.</li>
 * </ul>
 * 
 * @author Sylvain MARTY
 * @version $Revision: 1.1 $ - $Date: 2007-05-22 16:56:28 $
 */
public class ListServicesAction extends AbstractAuthorizedAction {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ACTION_NAME = "listservices";

    private CatalogTypeParameterValidator catalogTypeParameterValidator;

    /**
     * 
     * @param actionName_
     */
    public ListServicesAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        super(ACTION_NAME, request, response, session);

        catalogTypeParameterValidator = new CatalogTypeParameterValidator(
                MotuRequestParametersConstant.PARAM_CATALOG_TYPE,
                CommonHTTPParameters.getServiceFromRequest(getRequest()),
                "");
    }

    @Override
    public void process() throws MotuException {
        String catalogType = catalogTypeParameterValidator.getParameterValueValidated();
        writeResponseWithVelocity(filterConfigService(catalogType));
    }

    private List<ConfigService> filterConfigService(String catalogType) {
        List<ConfigService> csList = new ArrayList<ConfigService>();
        for (ConfigService cs : BLLManager.getInstance().getConfigManager().getMotuConfig().getConfigService()) {
            if (StringUtils.isNullOrEmpty(catalogType)
                    || (!StringUtils.isNullOrEmpty(catalogType) && cs.getCatalog().getType().equalsIgnoreCase(catalogType))) {
                csList.add(cs);
            }
        }
        return csList;
    }

    private void writeResponseWithVelocity(List<ConfigService> csList_) throws MotuException {
        VelocityContext context = VelocityTemplateManager.getPrepopulatedVelocityContext();
        context.put("body_template", VelocityTemplateManager.getTemplatePath(ACTION_NAME, VelocityTemplateManager.DEFAULT_LANG));
        context.put("serviceList", VelocityModelConverter.converServiceList(csList_));

        try {
            Template template = VelocityTemplateManager.getInstance().initVelocityEngineWithGenericTemplate(null);
            template.merge(context, getResponse().getWriter());
        } catch (Exception e) {
            throw new MotuException("Error while using velocity template", e);
        }
    }

    /**
     * @return a new context with some tools initialized.
     * 
     * @see NumberTool
     * @see DateTool
     * @see MathTool
     */
    public static VelocityContext getPrepopulatedVelocityContext() {
        final NumberTool numberTool = new NumberTool();
        final DateTool dateTool = new DateTool();
        final MathTool mathTool = new MathTool();

        VelocityContext vc = new VelocityContext();
        vc.put("numberTool", numberTool);
        vc.put("dateTool", dateTool);
        vc.put("mathTool", mathTool);
        vc.put("enLocale", Locale.ENGLISH);
        return vc;
    }

    /** {@inheritDoc} */
    @Override
    protected void checkHTTPParameters() throws InvalidHTTPParameterException {
        catalogTypeParameterValidator.validate();
    }

}
