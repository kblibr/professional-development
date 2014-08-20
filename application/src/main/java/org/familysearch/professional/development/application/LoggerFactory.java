/*
 * Copyright (c) 2014  Intellectual Reserve, Inc.  All rights reserved.
 */
package org.familysearch.professional.development.application;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

/**
 * Author Brian Amesbury
 * Created by ramesbury on 9/20/14.
 */
public final class LoggerFactory {
  private LoggerFactory() {
  }

  public static Logger createLogger(Class cls) {
    LoggerContext context = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
    context.reset();

    Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(cls.getCanonicalName());
    if (ApplicationProperties.useLoggingConsoleAppender()) {
      logger.addAppender(getConsoleAppender(context));
    }
    if (ApplicationProperties.useLoggingFileAppender() || ApplicationProperties.useLoggingRollingFileAppender()) {
      if (ApplicationProperties.useLoggingRollingFileAppender()) {
        logger.addAppender(getRollingFileAppender(context));
      }
      else {
        logger.addAppender(getFileAppender(context));
      }
    }
    logger.setLevel(Level.toLevel(ApplicationProperties.getLoggingLevel()));
    logger.setAdditive(false);
    return logger;
  }

  private static FileAppender<ILoggingEvent> getFileAppender(LoggerContext context) {
    FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
    fileAppender.setFile(ApplicationProperties.getLoggingFile());
    fileAppender.setEncoder(getPatternLayoutEncoder(context));
    fileAppender.setContext(context);
    fileAppender.start();
    return fileAppender;
  }

  private static RollingFileAppender<ILoggingEvent> getRollingFileAppender(LoggerContext context) {
    RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<>();
    rollingFileAppender.setFile(ApplicationProperties.getLoggingFile());
    rollingFileAppender.setRollingPolicy(getRollingPolicy(context, rollingFileAppender));
    rollingFileAppender.setTriggeringPolicy(getTriggeringPolicy());
    rollingFileAppender.setEncoder(getPatternLayoutEncoder(context));
    rollingFileAppender.setContext(context);
    rollingFileAppender.start();
    return rollingFileAppender;
  }

  private static FixedWindowRollingPolicy getRollingPolicy(LoggerContext context, RollingFileAppender<ILoggingEvent> rollingFileAppender) {
    FixedWindowRollingPolicy fixedWindowRollingPolicy = new FixedWindowRollingPolicy();
    fixedWindowRollingPolicy.setMinIndex(0);
    fixedWindowRollingPolicy.setMaxIndex(ApplicationProperties.getLogFilesToKeep());
    fixedWindowRollingPolicy.setFileNamePattern(ApplicationProperties.getLoggingFile());
    fixedWindowRollingPolicy.setContext(context);
    fixedWindowRollingPolicy.setParent(rollingFileAppender);
    fixedWindowRollingPolicy.start();
    return fixedWindowRollingPolicy;
  }

  private static SizeBasedTriggeringPolicy<ILoggingEvent> getTriggeringPolicy() {
    SizeBasedTriggeringPolicy<ILoggingEvent> sizeBasedTriggeringPolicy = new SizeBasedTriggeringPolicy<>();
    sizeBasedTriggeringPolicy.setMaxFileSize(ApplicationProperties.getLoggingFileSize());
    return null;
  }

  private static ConsoleAppender<ILoggingEvent> getConsoleAppender(LoggerContext context) {
    ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    consoleAppender.setEncoder(getPatternLayoutEncoder(context));
    consoleAppender.setContext(context);
    consoleAppender.start();
    return consoleAppender;
  }

  private static PatternLayoutEncoder getPatternLayoutEncoder(LoggerContext context) {
    PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
    patternLayoutEncoder.setPattern(ApplicationProperties.getLoggingDefaultPattern());
    patternLayoutEncoder.setContext(context);
    patternLayoutEncoder.start();
    return patternLayoutEncoder;
  }

}
