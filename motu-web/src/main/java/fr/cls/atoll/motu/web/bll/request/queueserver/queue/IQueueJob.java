package fr.cls.atoll.motu.web.bll.request.queueserver.queue;

import fr.cls.atoll.motu.web.bll.exception.MotuException;
import fr.cls.atoll.motu.web.bll.request.model.RequestDownloadStatus;

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
public interface IQueueJob extends Runnable {

    /**
     * .
     * 
     * @param e
     */
    void onJobException(MotuException e);

    /**
     * .
     */
    void stop();

    /**
     * .
     * 
     * @return
     */
    RequestDownloadStatus getRequestDownloadStatus();
}
