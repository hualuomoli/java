(function () {
  'use strict';

  angular.module('app')
    .config(config);

  /* @ngInject */
  function config($stateProvider, $urlRouterProvider) {

    // default
    $urlRouterProvider.otherwise('/app/dashboard');

    // app and dashboard
    $stateProvider
      .state('app', {
        abstract: true,
        url: '/app',
        templateUrl: 'tpl/app.html',
        controller: 'appCtrl'
      })
      .state('app.dashboard', {
        url: '/dashboard',
        templateUrl: 'tpl/dashboard/dashboard.html'
      })
  }

})();