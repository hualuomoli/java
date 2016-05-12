(function () {
  'use strict';



  angular.module('app')
    .provider('testHandler', testHandlerProvider);

  function testHandlerProvider() {
    /* jshint validthis:true */
    this.config = {
      enable: false // enable test
    };

    this.$get = function () {
      return {
        config: this.config
      };
    };
  }

})();