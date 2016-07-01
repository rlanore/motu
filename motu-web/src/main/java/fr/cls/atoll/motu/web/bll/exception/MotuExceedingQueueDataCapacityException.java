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

/**
 * 
 * (C) Copyright 2009-2010, by CLS (Collecte Localisation Satellites)
 * 
 * @version $Revision: 1.1 $ - $Date: 2009-03-18 12:18:22 $
 * @author <a href="mailto:dearith@cls.fr">Didier Earith</a>
 */
public class MotuExceedingQueueDataCapacityException extends MotuExceptionBase {

    private static final long serialVersionUID = -1L;

    /**
     * @param max max capacity allowed in Megabytes.
     */
    public MotuExceedingQueueDataCapacityException(double max) {
        super("Exceeding capacity.");
        this.actual = Double.MAX_VALUE;
        this.max = max;
        this.batchQueue = false;
    }

    /**
     * The Constructor.
     * 
     * @param batchQueue the batch queue
     * @param max max capacity allowed in Megabytes.
     * @param actual actual capacity in Megabytes.
     */
    public MotuExceedingQueueDataCapacityException(double actual, double max, boolean batchQueue) {
        super("Exceeding queue data capacity.");
        this.actual = actual;
        this.max = max;
        this.batchQueue = batchQueue;
    }

    /**
     * Actual capacity value in Megabytes.
     * 
     * @uml.property name="actual"
     */
    final private double actual;

    /**
     * Getter of the property <tt>actual</tt>.
     * 
     * @return Returns the actual.
     * @uml.property name="actual"
     */
    public double getActual() {
        return this.actual;
    }

    /**
     * @return the actual as a string representation
     */
    public String getActualAsString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (actual != Double.MAX_VALUE) {
            stringBuffer.append(String.format("%8.2f", actual));
        } else {
            stringBuffer.append("???");
        }
        stringBuffer.append(" Megabyte(s)");
        return stringBuffer.toString();
    }

    /**
     * Max capacity allowed value in Megabytes.
     * 
     * @uml.property name="max"
     */
    final private double max;

    /**
     * Getter of the property <tt>max</tt>.
     * 
     * @return Returns the max.
     * @uml.property name="max"
     */
    public double getMax() {
        return this.max;
    }

    /**
     * @return the max as a string representation
     */
    public String getMaxAsString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (max != Double.MAX_VALUE) {
            stringBuffer.append(String.format("%8.2f", max));
        } else {
            stringBuffer.append("???");
        }
        stringBuffer.append(" Megabyte(s)");
        return stringBuffer.toString();
    }

    /** The batch queue. */
    final private boolean batchQueue;

    /**
     * Checks if is batch queue.
     * 
     * @return true, if is batch queue
     */
    public boolean isBatchQueue() {
        return batchQueue;
    }

}
