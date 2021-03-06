package fr.cls.atoll.motu.web.usl.request.actions;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.cls.atoll.motu.api.message.MotuRequestParametersConstant;
import fr.cls.atoll.motu.api.message.xml.ErrorType;
import fr.cls.atoll.motu.api.message.xml.StatusModeResponse;
import fr.cls.atoll.motu.api.message.xml.StatusModeType;
import fr.cls.atoll.motu.web.bll.BLLManager;
import fr.cls.atoll.motu.web.bll.exception.MotuException;
import fr.cls.atoll.motu.web.bll.request.IBLLRequestManager;
import fr.cls.atoll.motu.web.bll.request.queueserver.IQueueServerManager;
import fr.cls.atoll.motu.web.bll.request.queueserver.queue.QueueManagement;
import fr.cls.atoll.motu.web.common.utils.StringUtils;
import fr.cls.atoll.motu.web.dal.config.xml.model.QueueType;
import fr.cls.atoll.motu.web.usl.common.utils.HTTPUtils;
import fr.cls.atoll.motu.web.usl.request.parameter.CommonHTTPParameters;
import fr.cls.atoll.motu.web.usl.request.parameter.exception.InvalidHTTPParameterException;
import fr.cls.atoll.motu.web.usl.request.parameter.validator.DebugOrderHTTParameterValidator;
import fr.cls.atoll.motu.web.usl.response.xml.converter.XMLConverter;
import fr.cls.atoll.motu.web.usl.wcs.request.actions.WCSGetCoverageAction;

/**
 * <br>
 * <br>
 * Copyright : Copyright (c) 2016 <br>
 * <br>
 * Société : CLS (Collecte Localisation Satellites) <br>
 * <br>
 * <br>
 * This interface is used to display debug information in HTML.<br>
 * Operation invocation consists in performing an HTTP GET request.<br>
 * Input parameters are the following: [x,y] is the cardinality<br>
 * <ul>
 * <li><b>action</b>: [1]: {@link #ACTION_NAME}</li>
 * </ul>
 * 
 * @author Sylvain MARTY
 * @version $Revision: 1.1 $ - $Date: 2007-05-22 16:56:28 $
 */
public class DebugAction extends AbstractAction {

    /** Logger for this class. */
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ACTION_NAME = "debug";
    public static final String ACTION_NAME_ALIAS_QUEUE_SERVER = "queue-server";

    private DebugOrderHTTParameterValidator debugOrderHTTParameterValidator;

    /**
     * Constructeur.
     * 
     * @param actionName_
     */
    public DebugAction(String actionCode_, HttpServletRequest request, HttpServletResponse response) {
        super(ACTION_NAME, actionCode_, request, response);

        debugOrderHTTParameterValidator = new DebugOrderHTTParameterValidator(
                MotuRequestParametersConstant.PARAM_DEBUG_ORDER,
                CommonHTTPParameters.getDebugOrderFromRequest(getRequest()));
    }

    @Override
    public void process() throws MotuException {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("<html>\n");
        stringBuffer.append("<head>\n");
        stringBuffer.append("<title>Motu | Debug</title>\n");
        stringBuffer.append("</head>\n");
        stringBuffer.append("<body>\n");

        debugRequestAllStatus(stringBuffer);
        debugPendingRequest(stringBuffer);

        stringBuffer.append("</body>\n");
        stringBuffer.append("<html>\n");

        try {
            writeResponse(stringBuffer.toString(), HTTPUtils.CONTENT_TYPE_HTML_UTF8);
        } catch (IOException e) {
            throw new MotuException(ErrorType.SYSTEM, "Error while wirting the response", e);
        }
    }

    /**
     * Debug request all status.
     * 
     * @param stringBuffer the string buffer
     */
    private void debugRequestAllStatus(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return;
        }
        stringBuffer.append("<h1 align=\"center\">\n");
        stringBuffer.append("Request status");
        stringBuffer.append("</h1>\n");

        List<String> orders = debugOrderHTTParameterValidator.getParameterValueValidated();
        for (String currentItem : orders) {
            debugRequestStatus(stringBuffer, statusMapping(currentItem));
        }
    }

    private StatusModeType statusMapping(String status) {
        return StatusModeType.valueOf(status);
    }

    /**
     * Debug request status.
     * 
     * @param stringBuffer the string buffer
     * @param statusModeType the status mode type
     */
    private void debugRequestStatus(StringBuffer stringBuffer, StatusModeType statusModeType) {

        if (stringBuffer == null) {
            return;
        }

        IBLLRequestManager requestManager = BLLManager.getInstance().getRequestManager();
        List<Long> requestIds = requestManager.getRequestIds();
        Collections.sort(requestIds, Collections.reverseOrder());
        String requestCountToken = "@@requestCountForStatus@@";
        stringBuffer.append("<h2>\n");
        stringBuffer.append(statusModeType.toString() + " (x" + requestCountToken + ")");
        stringBuffer.append("</h2>\n");
        stringBuffer.append("<table border=\"1\">\n");
        stringBuffer.append("<tr>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Request Id");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Request Type");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("User Id");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Time");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Status");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Code");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Message");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Remote data");
        stringBuffer.append("</th>\n");
        stringBuffer.append("<th>\n");
        stringBuffer.append("Local data");
        stringBuffer.append("</th>\n");
        stringBuffer.append("</tr>\n");

        int requestCount = 0;
        for (Long requestId : requestIds) {
            AbstractAction action = requestManager.getRequestAction(requestId);
            if (action != null) {
                String userId = action.getUserId();
                if (userId == null) {
                    userId = "Anonymous";
                }
                if (action instanceof DownloadProductAction || action instanceof WCSGetCoverageAction) {
                    if (manageTheDownloadProductActionLog(stringBuffer, requestId, statusModeType, action, userId)) {
                        requestCount++;
                    }
                } else if (requestManager.getRequestStatus(requestId) == statusModeType) {
                    requestCount++;
                    manageTheActionLog(stringBuffer,
                                       requestId,
                                       requestManager.getRequestAction(requestId),
                                       requestManager.getRequestStatus(requestId),
                                       userId);
                }
            }
        }
        stringBuffer.append("</table>\n");
        int startIndex = stringBuffer.indexOf(requestCountToken);
        stringBuffer.replace(startIndex, startIndex + requestCountToken.length(), Integer.toString(requestCount));
    }

    private void manageTheActionLog(StringBuffer stringBuffer, Long requestId, AbstractAction action, StatusModeType status, String userId) {
        stringBuffer.append("<tr>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append(requestId.toString());
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append(action.getActionName());
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append(userId);
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(requestId);
        stringBuffer.append(cal.getTime().toString());
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append(status.name());
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append(status.value());
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append(action.getParameters());
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append("</td>\n");
        stringBuffer.append("<td>\n");
        stringBuffer.append("</td>\n");
        stringBuffer.append("</tr>\n");
        stringBuffer.append("\n");
    }

    private boolean manageTheDownloadProductActionLog(StringBuffer stringBuffer,
                                                      Long requestId,
                                                      StatusModeType statusModeType,
                                                      AbstractAction action,
                                                      String userId) {
        StatusModeResponse statusModeResponse;
        try {
            statusModeResponse = XMLConverter
                    .convertStatusModeResponse(getActionCode(), BLLManager.getInstance().getRequestManager().getDownloadRequestStatus(requestId));
            if (statusModeResponse.getStatus() != statusModeType) {
                return false;
            }

            stringBuffer.append("<tr>\n");
            stringBuffer.append("<td>\n");
            stringBuffer.append(requestId.toString());
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            stringBuffer.append(action.getActionName());
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            stringBuffer.append(userId);
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(requestId);
            stringBuffer.append(cal.getTime().toString());
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            stringBuffer.append(statusModeResponse.getStatus().toString());
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            stringBuffer.append(statusModeResponse.getCode().toString());
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            if (!StringUtils.isNullOrEmpty(statusModeResponse.getMsg())) {
                stringBuffer.append(statusModeResponse.getMsg());
                stringBuffer.append("<BR>");
            }
            stringBuffer.append("Length : ");
            if (statusModeResponse.getSize() != null) {
                stringBuffer.append(statusModeResponse.getSize());
                stringBuffer.append("Mbits");
            }
            stringBuffer.append("<BR>Last modified: ");
            if (statusModeResponse.getDateProc() != null) {
                XMLGregorianCalendar lastModified = statusModeResponse.getDateProc().normalize();
                if (lastModified.getYear() == 1970) {
                    stringBuffer.append("Unknown");
                } else {
                    stringBuffer.append(lastModified.toString());
                }
            }
            if (!StringUtils.isNullOrEmpty(statusModeResponse.getScriptVersion())) {
                stringBuffer.append("<BR> ScriptVersion:" + statusModeResponse.getScriptVersion());
            }
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            if (statusModeResponse.getRemoteUri().endsWith("/")) {
                stringBuffer.append("No file.");
            } else if (!statusModeResponse.getRemoteUri().endsWith("null")) {
                stringBuffer.append(statusModeResponse.getRemoteUri());
            } else {
                stringBuffer.append("In progress...");
            }
            stringBuffer.append("</td>\n");
            stringBuffer.append("<td>\n");
            if (statusModeResponse.getLocalUri().endsWith("/")) {
                stringBuffer.append("No file.");
            } else if (!statusModeResponse.getLocalUri().endsWith("null")) {
                stringBuffer.append(statusModeResponse.getLocalUri());
            } else {
                stringBuffer.append("In progress...");
            }
            stringBuffer.append("</td>\n");
            stringBuffer.append("</tr>\n");
            stringBuffer.append("\n");
        } catch (MotuException e) {
            LOGGER.error(e);
        }
        return true;
    }

    /**
     * Debug pending request.
     * 
     * @param stringBuffer the string buffer
     */
    private void debugPendingRequest(StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            return;
        }

        stringBuffer.append("<hr/><h1 align=\"center\">\n");
        stringBuffer.append("Queue server general configuration\n");
        stringBuffer.append("</h1>\n");

        IQueueServerManager queueServerManagement = BLLManager.getInstance().getRequestManager().getQueueServerManager();
        if (queueServerManagement == null) {
            stringBuffer.append("<p> Queue server is not active</p>");
            return;
        }
        stringBuffer.append("<p>\n");
        // stringBuffer.append(" Default priority: ");
        // stringBuffer.append(queueServerManagement.getDefaultPriority());
        stringBuffer.append(" Max. data threshold: ");
        stringBuffer.append(String.format("%8.2f Mo", BLLManager.getInstance().getRequestManager().getQueueServerManager().getMaxDataThreshold()));
        stringBuffer.append("</p>\n");

        // stringBuffer.append("<h2>\n");
        // stringBuffer.append("Non-Batch Queues");
        // stringBuffer.append("</h2>\n");
        debugPendingRequest(stringBuffer, queueServerManagement); // , false
        // stringBuffer.append("<h2>\n");
        // stringBuffer.append("Batch Queues");
        // stringBuffer.append("</h2>\n");
        // debugPendingRequest(stringBuffer, queueServerManagement, true);
    }

    /**
     * Debug pending request.
     *
     * @param stringBuffer the string buffer
     * @param queueServerManagement the queue server management
     * @param batch the batch
     */
    private void debugPendingRequest(StringBuffer stringBuffer, IQueueServerManager queueServerManagement) {
        List<QueueType> queuesConfig = BLLManager.getInstance().getConfigManager().getMotuConfig().getQueueServerConfig().getQueues();
        QueueManagement queueManagement = null;
        boolean hasQueue = false;
        // queues are sorted by data threshold (ascending)
        for (QueueType queueConfig : queuesConfig) {
            queueManagement = queueServerManagement.getQueueManagementMap().get(queueConfig);
            if (queueManagement == null) {
                continue;
            }

            hasQueue = true;

            stringBuffer.append("<h3>\n");
            stringBuffer.append(queueConfig.getId());
            stringBuffer.append(": ");
            stringBuffer.append(queueConfig.getDescription());
            stringBuffer.append("</h3>\n");
            stringBuffer.append("<table border=\"1\">\n");
            stringBuffer.append("<p>\n");
            stringBuffer.append("Max. pool anonymous: ");
            short maxPoolAnonymous = BLLManager.getInstance().getConfigManager().getMotuConfig().getQueueServerConfig().getMaxPoolAnonymous();
            stringBuffer.append(maxPoolAnonymous < 0 ? "unlimited" : maxPoolAnonymous);

            stringBuffer.append(" Max. pool authenticate: ");
            short maxPoolAuth = BLLManager.getInstance().getConfigManager().getMotuConfig().getQueueServerConfig().getMaxPoolAuth();
            stringBuffer.append(maxPoolAuth < 0 ? "unlimited" : maxPoolAuth);
            stringBuffer.append("</p>\n");
            stringBuffer.append("<p>\n");
            stringBuffer.append("Max. threads: ");
            stringBuffer.append(queueConfig.getMaxThreads());
            stringBuffer.append(" Data threshold: ");
            stringBuffer.append(String.format("%8.2f Mo", queueConfig.getDataThreshold()));
            stringBuffer.append(" Max. pool size: ");
            stringBuffer.append(queueConfig.getMaxPoolSize());
            stringBuffer.append(" Low priority waiting: ");
            stringBuffer.append(queueConfig.getLowPriorityWaiting());
            stringBuffer.append("</p>\n");
            stringBuffer.append("<p>\n");
            stringBuffer.append(" Approximate number of threads that are actively executing tasks: ");
            stringBuffer.append(queueManagement.getThreadPoolExecutor().getActiveCount());
            stringBuffer.append("</p>\n");
            stringBuffer.append("<p>\n");
            stringBuffer.append(" Approximate total number of tasks that have completed execution: ");
            stringBuffer.append(queueManagement.getThreadPoolExecutor().getCompletedTaskCount());
            stringBuffer.append("</p>\n");

            stringBuffer.append("</table>\n");
        }
        if (!hasQueue) {
            stringBuffer.append("None");
            return;
        }

    }

    /** {@inheritDoc} */
    @Override
    protected void checkHTTPParameters() throws InvalidHTTPParameterException {
        debugOrderHTTParameterValidator.validate();
    }

}
