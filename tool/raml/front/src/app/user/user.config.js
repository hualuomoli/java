(function () {
  'use strict';

  angular.module('app.user')
    .config(config)
    .run(run);

  /* @ngInject */
  function config($httpProvider) {

    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('userInterceptor');

  }

  /* @ngInject */
  function run($rootScope, $state, logger) {

    // 没有权限
    $rootScope.$on('UserNoPermission', function (res) {
      // login
      logger.debug('there is no permission.', res);
      $state.go('login');
    });

    // 没有权限
    $rootScope.$on('UserError', function (res) {
      // login
      logger.debug('user error.', res);
      $state.go('login');
    });
  }


})();