package com.trinarybrain.magianaturalis.common.core;

import com.github.elenterius.magianaturalis.Tags;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

    public static final Logger logger = LogManager.getLogger(MagiaNaturalis.MOD_ID);

    private Log() {
    }

    public static void initLog() {
        logger.info("Starting {} v{}", Tags.MOD_NAME, Tags.MOD_VERSION);
        logger.info("Copyright (c) TrinaryBrain 2015-2025");
    }

    public static void logInRed(String str) {
        logger.error(str);
    }

}
