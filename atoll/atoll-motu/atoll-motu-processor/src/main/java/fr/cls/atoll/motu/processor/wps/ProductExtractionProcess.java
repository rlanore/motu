//$HeadURL: svn+ssh://mschneider@svn.wald.intevation.org/deegree/base/trunk/resources/eclipse/files_template.xml $
/*----------------    FILE HEADER  ------------------------------------------
 This file is part of deegree.
 Copyright (C) 2001-2009 by:
 Department of Geography, University of Bonn
 http://www.giub.uni-bonn.de/deegree/
 lat/lon GmbH
 http://www.lat-lon.de

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 Contact:

 Andreas Poth
 lat/lon GmbH
 Aennchenstr. 19
 53177 Bonn
 Germany
 E-Mail: poth@lat-lon.de

 Prof. Dr. Klaus Greve
 Department of Geography
 University of Bonn
 Meckenheimer Allee 166
 53115 Bonn
 Germany
 E-Mail: greve@giub.uni-bonn.de
 ---------------------------------------------------------------------------*/

package fr.cls.atoll.motu.processor.wps;

import org.deegree.services.controller.OGCFrontController;
import org.deegree.services.wps.Processlet;
import org.deegree.services.wps.ProcessletException;
import org.deegree.services.wps.ProcessletExecutionInfo;
import org.deegree.services.wps.ProcessletInputs;
import org.deegree.services.wps.ProcessletOutputs;
import org.deegree.services.wps.input.LiteralInput;
import org.deegree.services.wps.output.LiteralOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.cls.atoll.motu.library.exception.MotuException;
import fr.cls.atoll.motu.library.exception.MotuExceptionBase;
import fr.cls.atoll.motu.library.intfce.Organizer;
import fr.cls.atoll.motu.msg.xml.ErrorType;
import fr.cls.atoll.motu.msg.xml.TimeCoverage;





/**
 * The purpose of this {@link Processlet} is to provide the time coverage of a product
 * 
 * @author last edited by: $Author: dearith $
 * 
 * @version $Revision: 1.1 $, $Date: 2009-03-31 14:38:36 $
 */
public class ProductExtractionProcess extends MotuWPSProcess {

    /**
     * Constructeur.
     */
    public ProductExtractionProcess() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(ProductExtractionProcess.class);

    /** {@inheritDoc} */
    @Override
    public void process(ProcessletInputs in, ProcessletOutputs out, ProcessletExecutionInfo info) throws ProcessletException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("BEGIN TimeCoverageProcess.process(), context: " + OGCFrontController.getContext());
        }

        /*
        
        try {

            setLanguageParameter(request, session, response);
        } catch (ServletException e) {
            LOG.error("isActionProductDownload(String, HttpServletRequest, HttpSession, HttpServletResponse)", e);

            response.sendError(500, String.format("ERROR: %s", e.getMessage()));

            if (LOG.isDebugEnabled()) {
                LOG.debug("isActionProductDownload(String, HttpServletRequest, HttpSession, HttpServletResponse) - exiting");
            }
            return true;
        }

        Writer out = null;
        Organizer.Format responseFormat = null;

        String mode = getMode(request);

        int priority = getRequestPriority(request);

        overrideMaxPoolAnonymous(request);
        overrideMaxPoolAuthenticate(request);

        String userId = getLogin(request, session);
        boolean anonymousUser = isAnonymousUser(request, userId);
        String userHost = getRemoteHost(request);

        if (MotuServlet.isNullOrEmpty(userId)) {
            userId = userHost;
        }

        if (RunnableHttpExtraction.noMode(mode)) {
            out = response.getWriter();
            responseFormat = Organizer.Format.HTML;
        }

        ExtractionParameters extractionParameters = new ExtractionParameters(
                request.getParameter(MotuRequestParametersConstant.PARAM_SERVICE),
                request.getParameter(MotuRequestParametersConstant.PARAM_DATA),
                getVariables(request),
                getTemporalCoverage(request),
                getGeoCoverage(request),
                getDepthCoverage(request),
                request.getParameter(MotuRequestParametersConstant.PARAM_PRODUCT),
                Organizer.Format.NETCDF,
                out,
                responseFormat,
                userId,
                anonymousUser);

        extractionParameters.setBatchQueue(isBatch(request));
        extractionParameters.setUserHost(userHost);

        productDownload(extractionParameters, mode, priority, session, response);

        boolean noMode = RunnableHttpExtraction.noMode(mode);
        if (!noMode) {
            removeOrganizerSession(session);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("isActionProductDownload(String, HttpServletRequest, HttpSession, HttpServletResponse) - exiting");
        }
        return true;
        
        
        
        */
        
        
        
        
        
        
        
        
        LiteralInput serviceNameParam = (LiteralInput) in.getParameter(MotuWPSProcess.PARAM_SERVICE);
        LiteralInput locationDataParam = (LiteralInput) in.getParameter(MotuWPSProcess.PARAM_URL);
        LiteralInput productIdParam = (LiteralInput) in.getParameter(MotuWPSProcess.PARAM_PRODUCT);

        if (MotuWPSProcess.isNullOrEmpty(locationDataParam) && MotuWPSProcess.isNullOrEmpty(productIdParam)) {
            if (LOG.isDebugEnabled()) {
                LOG.info(" empty locationData and empty productId");
                LOG.debug("END TimeCoverageProcess.process()");
            }

            String msg = String.format("ERROR: neither '%s' nor '%s' parameters are filled - Choose one of them",
                                       MotuWPSProcess.PARAM_URL,
                                       PARAM_PRODUCT);

            MotuWPSProcess.setReturnCode(ErrorType.INCONSISTENCY, msg, out);
            throw new ProcessletException(msg);
        }

        if (!MotuWPSProcess.isNullOrEmpty(locationDataParam) && !MotuWPSProcess.isNullOrEmpty(productIdParam)) {
            if (LOG.isDebugEnabled()) {
                LOG.info(" non empty locationData and non empty productId");
                LOG.debug("END TimeCoverageProcess.process()");
            }
            String msg = String.format("ERROR: '%s' and '%s' parameters are not compatible - Choose only one of them",
                                       MotuWPSProcess.PARAM_URL,
                                       MotuWPSProcess.PARAM_PRODUCT);

            MotuWPSProcess.setReturnCode(ErrorType.INCONSISTENCY, msg, out);
            throw new ProcessletException(msg);
        }

        if (MotuWPSProcess.isNullOrEmpty(serviceNameParam) && !MotuWPSProcess.isNullOrEmpty(productIdParam)) {
            if (LOG.isDebugEnabled()) {
                LOG.info("empty serviceName  and non empty productId");
                LOG.debug("END TimeCoverageProcess.process()");
            }
            String msg = String.format("ERROR: '%s' parameter is filled but '%s' is empty. You have to fill it.", PARAM_PRODUCT, PARAM_SERVICE);

            MotuWPSProcess.setReturnCode(ErrorType.INCONSISTENCY, msg, out);
            throw new ProcessletException(msg);
        }

        // -------------------------------------------------
        // get Time coverage
        // -------------------------------------------------
        try {
            if (!MotuWPSProcess.isNullOrEmpty(locationDataParam)) {
                productGetTimeCoverage(locationDataParam.getValue(), out);
            } else if (!MotuWPSProcess.isNullOrEmpty(serviceNameParam) && !MotuWPSProcess.isNullOrEmpty(productIdParam)) {
                productGetTimeCoverage(serviceNameParam.getValue(), productIdParam.getValue(), out);
            }
        } catch (MotuExceptionBase e) {
            // TODO Auto-generated catch block
            throw new ProcessletException(e.notifyException());
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("END TimeCoverageProcess.process()");
        }

        return;
    }

    /**
     * Product get time coverage.
     * 
     * @param response the response
     * @param locationData the location data
     * 
     */
    private void productGetTimeCoverage(String locationData, ProcessletOutputs response) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("productGetTimeCoverage(String, ProcessletOutputs) - entering");
        }

        Organizer organizer = getOrganizer(response);
        if (organizer == null) {
            return;
        }

        TimeCoverage timeCoverage = null;
        try {
            timeCoverage = organizer.getTimeCoverage(locationData);
        } catch (MotuException e) {
            LOG.error("productGetTimeCoverage(String, ProcessletOutputs)", e);
            // Do nothing error is in response code
        }

        productGetTimeCoverage(timeCoverage, response);

        if (LOG.isDebugEnabled()) {
            LOG.debug("productGetTimeCoverage(String, ProcessletOutputs) - exiting");
        }
    }

    /**
     * Product get time coverage.
     * 
     * @param response the response
     * @param serviceName the service name
     * @param productId the product id
     * @throws MotuExceptionBase
     * 
     */
    private void productGetTimeCoverage(String serviceName, String productId, ProcessletOutputs response) throws MotuExceptionBase {
        if (LOG.isDebugEnabled()) {
            LOG.debug("productGetTimeCoverage(String, String, ProcessletOutputs) - entering");
        }

        Organizer organizer = getOrganizer(response);
        if (organizer == null) {
            return;
        }

        TimeCoverage timeCoverage = null;
        try {
            timeCoverage = organizer.getTimeCoverage(serviceName, productId);
        } catch (MotuExceptionBase e) {

            LOG.error("productGetTimeCoverage(String, String, ProcessletOutputs", e);
            timeCoverage = Organizer.createTimeCoverage(e);
            throw e;
        }

        productGetTimeCoverage(timeCoverage, response);

        if (LOG.isDebugEnabled()) {
            LOG.debug("productGetTimeCoverage(String, String, ProcessletOutputs) - exiting");
        }
    }

    /**
     * Product get time coverage.
     * 
     * @param timeCoverage the time coverage
     * @param out the out
     */
    private void productGetTimeCoverage(TimeCoverage timeCoverage, ProcessletOutputs out) {

        if (timeCoverage == null) {
            return;
        }

        LiteralOutput startParam = (LiteralOutput) out.getParameter(MotuWPSProcess.PARAM_START);
        LiteralOutput endParam = (LiteralOutput) out.getParameter(MotuWPSProcess.PARAM_END);
        LiteralOutput codeParam = (LiteralOutput) out.getParameter(MotuWPSProcess.PARAM_CODE);
        LiteralOutput msgParam = (LiteralOutput) out.getParameter(MotuWPSProcess.PARAM_MESSAGE);

        if (startParam != null) {
            startParam.setValue(timeCoverage.getStart().normalize().toXMLFormat());
        }
        if (endParam != null) {
            endParam.setValue(timeCoverage.getEnd().normalize().toXMLFormat());
        }

        if (codeParam != null) {
            codeParam.setValue(timeCoverage.getCode().toString());
        }
        if (msgParam != null) {
            msgParam.setValue(timeCoverage.getMsg());
        }

    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("TimeCoverageProcess#destroy() called");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void init() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("TimeCoverageProcess#init() called");
        }
        super.init();
    }
}