package fr.cls.atoll.motu.web.bll.config;

import java.util.List;
import java.util.Map;

import fr.cls.atoll.motu.web.bll.config.version.IBLLVersionManager;
import fr.cls.atoll.motu.web.bll.exception.MotuException;
import fr.cls.atoll.motu.web.dal.config.stdname.xml.model.StandardName;
import fr.cls.atoll.motu.web.dal.config.xml.model.ConfigService;
import fr.cls.atoll.motu.web.dal.config.xml.model.MotuConfig;

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
public interface IBLLConfigManager {

    /**
     * .
     * 
     * @return null if no cas server is used, otherwise the Cas server url
     */
    String getCasServerUrl();

    boolean isCasActivated();

    /**
     * .
     * 
     * @return
     */
    IBLLQueueServerConfigManager getQueueServerConfigManager();

    /**
     * .
     * 
     * @return
     */
    MotuConfig getMotuConfig();

    /**
     * .
     * 
     * @return
     */
    String getProductDownloadHttpUrl();

    /**
     * .
     * 
     * @param parameterValueValidated
     * @return
     */
    ConfigService getConfigService(String configServiceName);

    /**
     * .
     * 
     * @return
     */
    List<StandardName> getStandardNameList();

    /**
     * .
     * 
     * @throws MotuException
     */
    void init() throws MotuException;

    /**
     * .
     * 
     * @return
     */
    String getMotuConfigurationFolderPath();

    /**
     * .
     * 
     * @return
     */
    IBLLVersionManager getVersionManager();

    /**
     * Return the map of the available ConfigService. The keys are the name of the ConfigService. The values
     * are the ConfigService himself. .
     * 
     * @return the ConfigService map
     */
    Map<String, ConfigService> getConfigServiceMap();
}
