package tr.exemple.demo.config;

import org.apache.log4j.Logger;

public class ConfigException extends RuntimeException {

    // log4j
    final static Logger log = Logger.getLogger(ConfigException.class);

    /*
     * Constructeurs
     */
    public ConfigException(String message) {
        // super(message);
        log.error(message);
    }

    public ConfigException(String message, Throwable cause) {
        // super(message, cause);
        log.error(message, cause);
    }

    public ConfigException(Throwable cause) {
        // super(cause);
        log.error(cause);
    }
}