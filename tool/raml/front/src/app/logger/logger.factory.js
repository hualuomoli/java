(function () {
  'use strict';

  angular.module('app.logger')
    .factory('logger', logger);

  /* @ngInject */
  function logger($log, loggerHandler) {
    return {
      log: log,
      debug: debug,
      info: info,
      warn: warn,
      success: success,
      error: error
    };

    /////////////////////

    function log(message, datas) {
      if (loggerHandler.config.level <= loggerHandler.config.lOG) {
        $log.log(message, datas);
      }
    }

    function debug(message, datas) {
      if (loggerHandler.config.level <= loggerHandler.config.DEBUG) {
        $log.log(message, datas);
      }
    }

    function info(message, datas) {
      if (loggerHandler.config.level <= loggerHandler.config.INFO) {
        $log.log(message, datas);
      }
    }

    function warn(message, datas) {
      if (loggerHandler.config.level <= loggerHandler.config.WARN) {
        $log.log(message, datas);
      }
    }

    function success(message, datas) {
      if (loggerHandler.config.level <= loggerHandler.config.SUCCESS) {
        $log.log(message, datas);
      }
    }

    function error(message, datas) {
      if (loggerHandler.config.level <= loggerHandler.config.ERROR) {
        $log.log(message, datas);
      }
    }
  }
})();