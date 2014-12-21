package trinarybrain.magia.naturalis.common.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import trinarybrain.magia.naturalis.common.ProjectInfo;

public class Log
{
	public static final Logger logger = LogManager.getLogger(ProjectInfo.ID);
	private Log() {}

	public static void initLog()
	{
		logger.info(String.format("Starting Magia Naturalis ", ProjectInfo.VERSION));
		logger.info("Copyright (c) TrinaryBrain 2014");
	}

	public static void logInRed(String str)
	{
		logger.error(str);
	}
}
