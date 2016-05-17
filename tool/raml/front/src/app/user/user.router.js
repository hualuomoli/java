(function () {
  'use strict';

  angular.module('app.user')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {

    // app and dashboard
    $stateProvider
      .state('login', {
        url: '/login',
        templateUrl: 'tpl/login/login.html',
        controller: 'userCtrl',
        controllerAs: 'user'
      })
  }

})();