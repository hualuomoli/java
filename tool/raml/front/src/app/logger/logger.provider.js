(function () {
  'use strict';

  angular.module('app.logger')
    .provider('loggerHandler', loggerHandler);

  function loggerHandler() {
    /* jshint validthis:true */
    this.config = {
      lOG: 0,
      DEBUG: 1,
      INFO: 2,
      WARN: 3,
      SUCCESS: 4,
      ERROR: 5,

      level: 0
    };

    this.$get = function () {
      return {
        config: this.config
      }
    }
  }

})();