(function () {
  'use strict';

  angular.module('app')
    .config(config);

  /* @ngInject */
  function config($locationProvider) {


    $locationProvider.hashPrefix('!');
    // Without server side support html5 must be disabled.
    $locationProvider.html5Mode(false);

  }


})();