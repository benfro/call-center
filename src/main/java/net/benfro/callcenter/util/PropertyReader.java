package net.benfro.callcenter.util;


import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public enum PropertyReader {

    INSTANCE;

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyReader.class.getClassLoader().getResourceAsStream("callcenter.properties"));
        } catch (Exception e) {
            //log.info("Poff!", e);
        }
    }

    public int getIntProperty(String property) {
        return Integer.valueOf(getProperty(property));
    }

    public String getProperty(String property) {
        return prop.getProperty(property);
    }

}
