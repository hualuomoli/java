(function () {
  'use strict';

  angular.module('app')
    .controller('userCtrl', userCtrl);


  /** @ngInject */
  function userCtrl($rootScope, $scope, $state, UserService, logger) {
    /* jshint validthis: true */
    var user = this;

    // 属性
    user.errorMsg = '';
    // user.username = 'admin';
    // user.password = 'admin';

    // 方法
    $scope.login = login;

    // 方法定义
    function login() {

      UserService.login(user)
        .then(function (data) {
          if (data.code === 0) {
            $rootScope.username = user.username;
            var fromRoute = $rootScope.rootState && $rootScope.rootState.from;
            if (!fromRoute) {
              $state.go('app.dashboard');
            } else {
              $state.go(fromRoute.state, fromRoute.params);
            }
          } else {
            user.errorMsg = data.msg;
          }
        });
    }

  }

})();