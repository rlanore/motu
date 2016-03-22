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
package fr.cls.atoll.motu.library.misc.queueserver;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import fr.cls.atoll.motu.library.misc.intfce.ExtractionParameters;

// TODO: Auto-generated Javadoc
/**
 * (C) Copyright 2009-2010, by CLS (Collecte Localisation Satellites).
 *
 * @version $Revision: 1.1 $ - $Date: 2009-03-18 12:18:22 $
 * @author <a href="mailto:dearith@cls.fr">Didier Earith</a>
 */
public class QueueLogInfo {
    /**
     * Logger for this class
     */
    private static final Logger LOG = Logger.getLogger(QueueLogInfo.class);

    /** The queue id. */
    private String queueId = null;

    /** The queue desc. */
    private String queueDesc = null;

    /** The request id. */
    private long requestId = -1;

    /** The elapse wait queue time. */
    private long elapsedWaitQueueTime = 0L;

    /** The elapse run time. */
    private long elapsedRunTime = 0L;

    /** The elapse total time. */
    private long elapsedTotalTime = 0L;

    /** The in queue time. */
    private Date inQueueTime = null;

    /** The start time. */
    private Date startTime = null;
    // private Date outQueueTime = null;
    /** The end time. */
    private Date endTime = null;

    /** The amount data size. */
    private double amountDataSize = 0d;

    /** The total io time. */
    private long totalIOTime = 0L;

    /** The preparing time. */
    private long preparingTime = 0L;

    /** The reading time in milliseconds (ms). */
    private long readingTime = 0L;
    
    /** The writing time in milliseconds (ms). */
    private long writingTime = 0L;

    /** The copying time in milliseconds (ms). */
    protected long copyingTime = 0L;
    
    /** The compressing time in milliseconds (ms). */
    protected long compressingTime = 0L;

    /** The queue log error. */
    private QueueLogError queueLogError = null;

    /** The extraction parameters. */
    private ExtractionParameters extractionParameters = null;

    /** The priority info. */
    private final List<QueueLogPriority> priorities = new ArrayList<QueueLogPriority>();

    /** The download url path. */
    private String downloadUrlPath = null;

    /** The extract location data. */
    private String extractLocationData = null;

    /** The x stream. */
    private final XStream xStream = new XStream();
    
    /** The output stream. */
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    /** The writer. */
    private Writer writer = null;
    /** The encoding. */
    private String encoding = "UTF-8";

    /**
     * Sets the encoding.
     * 
     * @param encoding the encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Gets the encoding.
     * 
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Constructor.
     */
    public QueueLogInfo() {
        // Field[] fields = this.getClass().getDeclaredFields();
        // for (int i = 0 ; i < fields.length ; i++) {
        // xStream.useAttributeFor(this.getClass(), fields[i].getName());
        // }
        try {
            writer = new OutputStreamWriter(outputStream, encoding);
        } catch (UnsupportedEncodingException e) {
            //Do Nothing
        }

        initXStreamOptions();

    }

    /**
     * Inits the X stream options.
     */
    public void initXStreamOptions() {

        xStream.alias("priority", QueueLogPriority.class);

        xStream.useAttributeFor(Date.class);
        xStream.useAttributeFor(long.class);
        xStream.useAttributeFor(boolean.class);
        xStream.useAttributeFor(double.class);
        xStream.useAttributeFor(this.getClass(), "queueId");
        xStream.useAttributeFor(this.getClass(), "queueDesc");
        xStream.useAttributeFor(this.getClass(), "extractLocationData");
        xStream.useAttributeFor(this.getClass(), "downloadUrlPath");

        // xStream.useAttributeFor("priority", int.class);
        // xStream.useAttributeFor("range", int.class);

        xStream.useAttributeFor(QueueLogPriority.class, "priority");
        xStream.useAttributeFor(QueueLogPriority.class, "range");

        xStream.useAttributeFor(ExtractionParameters.class, "userId");
//        xStream.useAttributeFor(ExtractionParameters.class, "user");
//        xStream.aliasAttribute("userId", "user");
//        xStream.registerConverter(new ExtractionParameters.UserBaseConverter());

        xStream.useAttributeFor(ExtractionParameters.class, "userHost");
        xStream.useAttributeFor(ExtractionParameters.class, "locationData");
        xStream.useAttributeFor(ExtractionParameters.class, "productId");
        xStream.useAttributeFor(ExtractionParameters.class, "serviceName");
        xStream.useAttributeFor(ExtractionParameters.class, "temporalCoverageInDays");

        xStream.omitField(ExtractionParameters.class, "dataOutputFormat");
        xStream.omitField(ExtractionParameters.class, "out");      
        xStream.omitField(ExtractionParameters.class, "assertion");
        
        xStream.omitField(this.getClass(), "outputStream");
        xStream.omitField(this.getClass(), "writer");
        xStream.omitField(this.getClass(), "encoding");
        
        xStream.omitField(this.getClass(), "xStream");

    }

    /**
     * To XML.
     * 
     * @return the string
     */
    public String toXML() {

        if (queueLogError == null) {
            xStream.alias("motuQueueServerLog", this.getClass());
        } else {
            xStream.alias("motuQueueServerLogError", this.getClass());
            xStream.useAttributeFor(QueueLogError.class, "errorCode");
            xStream.useAttributeFor(QueueLogError.class, "message");
        }

        // WARNING : reset the output stream before serialization
        // otherwise the content will be duplicate if multiple calls to 'toXML' are done. 
        outputStream.reset();
        //return xStream.toXML(this);
        xStream.toXML(this, writer);
        try {
            return outputStream.toString(encoding);
        } catch (UnsupportedEncodingException e) {
            LOG.error("toXML()", e);
            e.printStackTrace(System.err);
        }
        return "";


    }

    /**
     * Gets the queue log error.
     * 
     * @return the queue log error
     */
    public QueueLogError getQueueLogError() {
        return queueLogError;

    }

    /**
     * Sets the queue log error.
     * 
     * @param queueLogError the queue log error
     */
    public void setQueueLogError(QueueLogError queueLogError) {
        this.queueLogError = queueLogError;
    }

    /**
     * Gets the elapsed run time.
     * 
     * @return the elapsed run time
     */
    public long getElapsedRunTime() {
        setElapsedRunTime();
        return elapsedRunTime;
    }

    // /**
    // * Sets the elapse run time.
    // *
    // * @param elapsedRunTime the elapse run time
    // */
    // public void setElapseRunTime(long elapsedRunTime) {
    // this.elapseRunTime = elapsedRunTime;
    // }

    /**
     * Sets the elapse run time.
     */
    private void setElapsedRunTime() {
        if ((endTime == null) || (startTime == null)) {
            return;
        }
        this.elapsedRunTime = (this.endTime.getTime() - this.startTime.getTime()) + this.preparingTime;
    }

    /**
     * Gets the elapsed total time.
     * 
     * @return the elapsed total time
     */
    public long getElapsedTotalTime() {
        setElapsedTotalTime();
        return elapsedTotalTime;
    }

    // /**
    // * Sets the elapse total time.
    // *
    // * @param elapsedTotalTime the elapse total time
    // */
    // public void setElapseTotalTime(long elapsedTotalTime) {
    // setElapseTotalTime();
    // this.elapseTotalTime = elapsedTotalTime;
    // }

    /**
     * Sets the elapsed total time.
     */
    private void setElapsedTotalTime() {
        if ((endTime == null) || (inQueueTime == null)) {
            return;
        }
        this.elapsedTotalTime = (this.endTime.getTime() - this.inQueueTime.getTime()) +  this.preparingTime;
    }

    /**
     * Gets the elapsed wait queue time.
     * 
     * @return the elapsed wait queue time
     */
    public long getElapsedWaitQueueTime() {
        setElapsedWaitQueueTime();
        return elapsedWaitQueueTime;
    }

    // /**
    // * Sets the elapse wait queue time.
    // *
    // * @param elapsedWaitQueueTime the elapse wait queue time
    // */
    // public void setElapseWaitQueueTime(long elapsedWaitQueueTime) {
    // this.elapseWaitQueueTime = elapsedWaitQueueTime;
    // }

    /**
     * Sets the elapsed wait queue time.
     */
    private void setElapsedWaitQueueTime() {
        if ((startTime == null) || (inQueueTime == null)) {
            return;
        }
        this.elapsedWaitQueueTime = this.startTime.getTime() - this.inQueueTime.getTime();
        if (elapsedWaitQueueTime < 0) {
            elapsedWaitQueueTime = 0L;
        }
    }

    /**
     * Gets the end time.
     * 
     * @return the end time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * 
     * @param endTime the end time
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        setElapsedTotalTime();
        setElapsedRunTime();
        computeTotalIO();
    }

    /**
     * Gets the in queue time.
     * 
     * @return the in queue time
     */
    public Date getInQueueTime() {
        return inQueueTime;
    }

    /**
     * Sets the in queue time.
     * 
     * @param inQueueTime the in queue time
     */
    public void setInQueueTime(Date inQueueTime) {
        this.inQueueTime = inQueueTime;
        setElapsedTotalTime();
        setElapsedWaitQueueTime();
        setElapsedRunTime();
    }

    // public Date getOutQueueTime() {
    // return outQueueTime;
    // }
    //
    // public void setOutQueueTime(Date outQueueTime) {
    // this.outQueueTime = outQueueTime;
    // setElapseTotalTime();
    // }

    /**
     * Gets the start time.
     * 
     * @return the start time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     * 
     * @param startTime the start time
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        setElapsedWaitQueueTime();
        setElapsedRunTime();
    }

    /**
     * Gets the extraction parameters.
     * 
     * @return the extraction parameters
     */
    public ExtractionParameters getExtractionParameters() {
        return extractionParameters;
    }

    /**
     * Sets the extraction parameters.
     * 
     * @param extractionParameters the extraction parameters
     */
    public void setExtractionParameters(ExtractionParameters extractionParameters) {
        this.extractionParameters = extractionParameters;
    }

    /**
     * Gets the amount data size.
     * 
     * @return the amount data size
     */
    public double getAmountDataSize() {
        return amountDataSize;
    }

    /**
     * Sets the amount data size.
     * 
     * @param amountDataSize the amount data size
     */
    public void setAmountDataSize(double amountDataSize) {
        this.amountDataSize = amountDataSize;
    }
    
    /**
     * Compute total io.
     */
    public void computeTotalIO() {
        this.totalIOTime = this.readingTime + this.writingTime + this.copyingTime + this.compressingTime;
    }
    
    /**
     * Gets the preparing time.
     *
     * @return the preparing time in ms
     */
    public long getPreparingTime() {
        return preparingTime;
    }

    /**
     * Sets the preparing time.
     *
     * @param preparingTime the new preparing time in ms
     */
    public void setPreparingTime(long preparingTime) {
        this.preparingTime = preparingTime;
    }
    
    /**
     * Adds the preparing time.
     *
     * @param preparingTime the preparing time in ms
     */
    public void addPreparingTime(long preparingTime) {
        this.preparingTime += preparingTime;
    }

    /**
     * Gets the reading time.
     *
     * @return the reading time in ms
     */
    public double getReadingTime() {
        return this.readingTime;
    }

    /**
     * Sets the reading time.
     *
     * @param readingTime the new reading time in ms.
     */
    public void setReadingTime(long readingTime) {
        this.readingTime = readingTime;
    }
    
    /**
     * Adds the reading time.
     *
     * @param readingTime the reading time in ms.
     */
    public void addReadingTime(long readingTime) {
        this.readingTime += readingTime;
    }
    
    /**
     * Gets the writing time.
     *
     * @return the writing time in ms
     */
    public long getWritingTime() {
        return writingTime;
    }

    /**
     * Sets the writing time.
     *
     * @param writingTime the new writing time in ms.
     */
    public void setWritingTime(long writingTime) {
        this.writingTime = writingTime;
    }
    
    /**
     * Adds the writing time.
     *
     * @param writingTime the writing time in ms.
     */
    public void addWritingTime(long writingTime) {
        this.writingTime += writingTime;
    }

    
    
    /**
     * Gets the copying time.
     *
     * @return the copying time in ms
     */
    public long getCopyingTime() {
        return copyingTime;
    }

    /**
     * Sets the copying time.
     *
     * @param copyingTime the new copying time in ms
     */
    public void setCopyingTime(long copyingTime) {
        this.copyingTime = copyingTime;
    }

    /**
     * Adds the copying time.
     *
     * @param copyingTime the copying time in ms
     */
    public void addCopyingTime(long copyingTime) {
        this.copyingTime += copyingTime;
    }

    /**
     * Gets the compressing time.
     *
     * @return the compressing time in ms
     */
    public long getCompressingTime() {
        return compressingTime;
    }

    /**
     * Sets the compressing time.
     *
     * @param compressingTime the new compressing time in ms
     */
    public void setCompressingTime(long compressingTime) {
        this.compressingTime = compressingTime;
    }
    
    /**
     * Adds the compressing time.
     *
     * @param compressingTime the compressing time in ms
     */
    public void addCompressingTime(long compressingTime) {
        this.compressingTime = compressingTime;
    }


    /**
     * Gets the priorities.
     * 
     * @return the priorities
     */
    public List<QueueLogPriority> getPriorities() {
        return priorities;
    }

    /**
     * Adds the priority.
     *
     * @param priority the priority
     * @param range the range
     * @param date the date
     */
    public void addPriority(int priority, int range, Date date) {

        if (date == null) {
            Calendar cal = Calendar.getInstance();
            date = cal.getTime();
        }
        QueueLogPriority queueLogPriority = new QueueLogPriority(priority, range, date);
        this.priorities.add(queueLogPriority);
    }

    /**
     * Gets the most recent priority.
     * 
     * @return the most recent priority
     */
    public QueueLogPriority getMostRecentPriority() {
        if (priorities == null) {
            return null;
        }
        if (priorities.isEmpty()) {
            return null;
        }

        return priorities.get(priorities.size() - 1);
    }

    // /**
    // * Gets the priorities time.
    // *
    // * @return the priorities time
    // */
    // public List<Date> getPrioritiesTime() {
    // return prioritiesTime;
    // }

    /**
     * Gets the queue desc.
     * 
     * @return the queue desc
     */
    public String getQueueDesc() {
        return queueDesc;
    }

    /**
     * Sets the queue desc.
     * 
     * @param queueDesc the queue desc
     */
    public void setQueueDesc(String queueDesc) {
        this.queueDesc = queueDesc;
    }

    /**
     * Gets the queue id.
     * 
     * @return the queue id
     */
    public String getQueueId() {
        return queueId;
    }

    /**
     * Sets the queue id.
     * 
     * @param queueId the queue id
     */
    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    /**
     * Gets the download url path.
     * 
     * @return the download url path
     */
    public String getDownloadUrlPath() {
        return downloadUrlPath;
    }

    /**
     * Sets the download url path.
     * 
     * @param downloadUrlPath the download url path
     */
    public void setDownloadUrlPath(String downloadUrlPath) {
        this.downloadUrlPath = downloadUrlPath;
    }

    /**
     * Gets the extract location data.
     * 
     * @return the extract location data
     */
    public String getExtractLocationData() {
        return extractLocationData;
    }

    /**
     * Sets the extract location data.
     * 
     * @param extractLocationData the extract location data
     */
    public void setExtractLocationData(String extractLocationData) {
        this.extractLocationData = extractLocationData;
    }

    /**
     * Gets the request id.
     * 
     * @return the request id
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the request id.
     * 
     * @param requestId the request id
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    // long startTime = System.currentTimeMillis();
    // ....
    //
    // long currentTime = System.currentTimeMillis();
    // SimpleDateFormat dateFormat =
    // new SimpleDateFormat("HH:mm:ss");
    //
    // dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    // elapsed = currentTime - startTime;
    //
    // System.out.println(dateFormat.format(new Date(elapsed)));
}