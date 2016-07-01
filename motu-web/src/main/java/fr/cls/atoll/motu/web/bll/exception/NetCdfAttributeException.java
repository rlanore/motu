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
package fr.cls.atoll.motu.web.bll.exception;

import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * Exception class for NetCDF attribute exception.
 * 
 * (C) Copyright 2009-2010, by CLS (Collecte Localisation Satellites)
 * 
 * @version $Revision: 1.1 $ - $Date: 2009-03-18 12:18:22 $
 * @author <a href="mailto:dearith@cls.fr">Didier Earith</a>
 */
public class NetCdfAttributeException extends MotuExceptionBase {

    private static final long serialVersionUID = -1;

    /**
     * @param message message to post.
     * @param cause native exception.
     */
    public NetCdfAttributeException(String message, Throwable cause) {
        super(message, cause);
        this.netCdfAttribute = null;
        this.netCdfVariable = null;
    }

    /**
     * @param message message to post.
     */
    public NetCdfAttributeException(String message) {
        super(message);
        this.netCdfAttribute = null;
        this.netCdfVariable = null;
    }

    /**
     * @param cause
     * @param cause native exception.
     */
    public NetCdfAttributeException(Throwable cause) {
        super(cause);
        this.netCdfAttribute = null;
        this.netCdfVariable = null;
    }

    /**
     * @param netCdfAttribute NetCDF attribute that causes the exception
     * @param message message to post.
     * @param cause native exception.
     */
    public NetCdfAttributeException(Attribute netCdfAttribute, String message, Throwable cause) {
        super(message, cause);
        this.netCdfAttribute = netCdfAttribute;
        this.netCdfVariable = null;
    }

    /**
     * @param netCdfAttribute NetCDF attribute that causes the exception
     * @param message message to post.
     */
    public NetCdfAttributeException(Attribute netCdfAttribute, String message) {
        super(message);
        this.netCdfAttribute = netCdfAttribute;
        this.netCdfVariable = null;
    }

    /**
     * @param netCdfAttribute NetCDF attribute that causes the exception
     * @param cause native exception.
     */
    public NetCdfAttributeException(Attribute netCdfAttribute, Throwable cause) {
        super(cause);
        this.netCdfAttribute = netCdfAttribute;
        this.netCdfVariable = null;
    }

    /**
     * @param netCdfVariable NetCDF variable that causes the exception
     * @param netCdfAttribute NetCDF attribute that causes the exception
     * @param message message to post.
     * @param cause native exception.
     */
    public NetCdfAttributeException(Variable netCdfVariable, Attribute netCdfAttribute, String message, Throwable cause) {
        super(message, cause);
        this.netCdfVariable = netCdfVariable;
        this.netCdfAttribute = netCdfAttribute;
    }

    /**
     * @param netCdfVariable NetCDF variable that causes the exception
     * @param netCdfAttribute NetCDF attribute that causes the exception
     * @param message message to post.
     */
    public NetCdfAttributeException(Variable netCdfVariable, Attribute netCdfAttribute, String message) {
        super(message);
        this.netCdfVariable = netCdfVariable;
        this.netCdfAttribute = netCdfAttribute;
    }

    /**
     * @param netCdfVariable NetCDF variable that causes the exception
     * @param netCdfAttribute NetCDF attribute that causes the exception
     * @param cause native exception.
     */
    public NetCdfAttributeException(Variable netCdfVariable, Attribute netCdfAttribute, Throwable cause) {
        super(cause);
        this.netCdfVariable = netCdfVariable;
        this.netCdfAttribute = netCdfAttribute;
    }

    /**
     * @param netCdfVariable NetCDF variable that causes the exception
     * @param message message to post.
     * @param cause native exception.
     */
    public NetCdfAttributeException(Variable netCdfVariable, String message, Throwable cause) {
        super(message, cause);
        this.netCdfAttribute = null;
        this.netCdfVariable = netCdfVariable;
    }

    /**
     * @param netCdfVariable NetCDF variable that causes the exception
     * @param message message to post.
     */
    public NetCdfAttributeException(Variable netCdfVariable, String message) {
        super(message);
        this.netCdfAttribute = null;
        this.netCdfVariable = netCdfVariable;
    }

    /**
     * @param netCdfVariable NetCDF variable that causes the exception
     * @param cause native exception.
     */
    public NetCdfAttributeException(Variable netCdfVariable, Throwable cause) {
        super(cause);
        this.netCdfAttribute = null;
        this.netCdfVariable = netCdfVariable;
    }

    /**
     * NetCDF attribute which causes the exception.
     */
    final private Attribute netCdfAttribute;

    /**
     * @return the netCdfAttribute
     */
    public Attribute getNetCdfAttribute() {
        return this.netCdfAttribute;
    }

    /**
     * NetCDF variable whose attribute causes the exception.
     */
    final private Variable netCdfVariable;

    /**
     * @return the neCdfVariable
     */
    public Variable getNetCdfVariable() {
        return this.netCdfVariable;
    }

}
