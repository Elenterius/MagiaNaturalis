package com.trinarybrain.magianaturalis.common.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trinarybrain.magianaturalis.common.Reference;

public class Log
{
	public static final Logger logger = LogManager.getLogger(Reference.ID);
	private Log() {}

	public static void initLog()
	{
		logger.info(String.format("Starting Magia Naturalis ", Reference.VERSION));
		logger.info("Copyright (c) TrinaryBrain 2015");
	}

	public static void logInRed(String str)
	{
		logger.error(str);
	}
}
