/* 
 * Motu, a high efficient, robust and Standard compliant Web Server for Geographic
 * Data Dissemination.
 *
 * http://cls-motu.sourceforge.net/
 *
 * (C) Copyright 2009-2010, by CLS (Collecte Localisation Satellites) - 
 * http://www.cls.fr - and  Contributors
 *
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
package fr.cls.atoll.motu.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.cls.atoll.motu.web.bll.exception.MotuException;
import fr.cls.atoll.motu.web.usl.USLManager;
import fr.cls.atoll.motu.web.usl.request.parameter.exception.InvalidHTTPParameterException;

/**
 * The Class MotuServlet.
 * 
 * (C) Copyright 2009-2010, by CLS (Collecte Localisation Satellites)
 * 
 * @version $Revision: 1.1 $ - $Date: 2009-03-18 12:18:22 $
 * @author <a href="mailto:dearith@cls.fr">Didier Earith</a>
 */
public class MotuServlet extends HttpServlet {

    /**
     * .
     */
    private static final long serialVersionUID = 1L;

    /** Logger for this class. */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Default constructor.
     */
    public MotuServlet() {
    }

    /**
     * Handles a GET request.
     *
     * @param request object that contains the request the client has made of the servlet.
     * @param response object that contains the response the servlet sends to the client
     * @throws ServletException the servlet exception
     * @throws IOException the IO exception
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            USLManager.getInstance().getRequestManager().onNewRequest(request, response);
        } catch (InvalidHTTPParameterException e) {
            response.sendError(500, String.format("Oops, an HTTP parameter is not valid: %s", e.getMessage()));
        } catch (MotuException e) {
            LOGGER.error("Error while processing HTTP request", e);
            throw new ServletException(e);
        }

    }

    /**
     * Handles a POST request.
     *
     * @param request object that contains the request the client has made of the servlet.
     * @param response object that contains the response the servlet sends to the client
     * @throws ServletException the servlet exception
     * @throws IOException the IO exception
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
