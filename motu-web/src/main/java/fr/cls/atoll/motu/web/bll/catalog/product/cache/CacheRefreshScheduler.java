package fr.cls.atoll.motu.web.bll.catalog.product.cache;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.cls.atoll.motu.web.bll.BLLManager;
import fr.cls.atoll.motu.web.common.thread.StoppableDaemonThread;
import fr.cls.atoll.motu.web.common.utils.DateUtils;
import fr.cls.atoll.motu.web.dal.config.xml.model.ConfigService;

/**
 * This class is the scheduler of the cache refresh. When the Motu system needs to refresh the cache, it's
 * this class which have to manage this request. This class implements the pattern singleton. . <br>
 * <br>
 * Copyright : Copyright (c) 2017 <br>
 * <br>
 * Company : CLS (Collecte Localisation Satellites)
 * 
 * @author Pierre LACOSTE
 * @version $Revision: 1456 $ - $Date: 2011-04-08 18:37:34 +0200 $
 */
public class CacheRefreshScheduler extends StoppableDaemonThread {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The list of ConfigService which are waiting to be updated. It's used as a FIFO stack.
     */
    private LinkedList<ConfigService> waitingConfigServiceToUpdate;

    /**
     * This is the unique instance of the class.
     */
    private static CacheRefreshScheduler instance = null;
    /**
     * This is the instance of the service which refresh a ConfigService
     */
    private CacheUpdateService refreshService;

    /**
     * This is the catalog cache to refresh
     */
    private ICatalogCache catalogCache;
    /**
     * This is the product cache to refresh
     */
    private IProductCache productCache;

    private List<Object> listeners;

    /**
     * Private constructor of the class. Call the super constructor to initialize the StopableDaemonThread
     * part.
     */
    private CacheRefreshScheduler() {
        super(
            "Product and Catalog Cache Thread Daemon",
            BLLManager.getInstance().getConfigManager().getMotuConfig().getDescribeProductCacheRefreshInMilliSec());
        waitingConfigServiceToUpdate = new LinkedList<>();
        catalogCache = new CatalogCache();
        productCache = new ProductCache();
        refreshService = new CacheUpdateService(catalogCache, productCache);
        listeners = new ArrayList<>();
    }

    /**
     * Initialize if needed and return the unique instance of the singleton class. .
     * 
     * @return The unique class instance
     */
    public final static CacheRefreshScheduler getInstance() {
        if (instance == null) {
            instance = new CacheRefreshScheduler();
        }
        return instance;
    }

    /**
     * This method is used to add new ConfigService to refresh to the cache resfresh scheduler. If a
     * ConfigService on the provided list is already on the waiting list of the scheduler, only one occurrence
     * it's saved. .
     * 
     * @param configServiceToUpadte The list of ConfigService to update
     */
    public void update(Set<ConfigService> configServiceToUpadte) {
        synchronized (waitingConfigServiceToUpdate) {
            for (ConfigService configService : configServiceToUpadte) {
                if (!waitingConfigServiceToUpdate.contains(configService)) {
                    LOGGER.info("add to refresh list : " + configService.getName());
                    waitingConfigServiceToUpdate.add(configService);
                }
            }
        }
        synchronized (this) {
            notify();
        }
    }

    /**
     * Return the cache of the catalog .
     * 
     * @return the catalog cache
     */
    public ICatalogCache getCatalogCache() {
        return catalogCache;
    }

    /**
     * Return the cache of the product .
     * 
     * @return the product cache
     */
    public IProductCache getProductCache() {
        return productCache;
    }

    @Override
    protected void runProcess() {
        long startRefresh = System.currentTimeMillis();
        ConfigService currentConfigService = null;
        do {
            synchronized (waitingConfigServiceToUpdate) {
                // Retrieve the next ConfigService to update in the FIFO stack
                currentConfigService = waitingConfigServiceToUpdate.pollFirst();
            }
            if (currentConfigService != null) {

                long startConfigServiceRefresh = System.currentTimeMillis();
                // Launch the refresh of the ConfiService
                refreshService.updateConfigService(currentConfigService);
                LOGGER.info("Refresh " + currentConfigService.getName() + " cache in: "
                        + DateUtils.getDurationMinSecMsec((System.currentTimeMillis() - startConfigServiceRefresh)));
            }

        } while (currentConfigService != null && !isDaemonStoppedASAP());
        LOGGER.info("Refresh cache of the waiting list in: " + DateUtils.getDurationMinSecMsec((System.currentTimeMillis() - startRefresh)));
    }

    public void addListener(Object listener) {
        listeners.add(listener);
    }

    @Override
    public void onThreadStopped() {
        super.onThreadStopped();
        for (Object currentListener : listeners) {
            synchronized (currentListener) {
                currentListener.notify();
            }
        }
    }

}