(function () {
  'use strict';

  angular.module('app.user')
    .factory('userInterceptor', userInterceptor);

  /** ngInject */
  function userInterceptor($q, $rootScope) {
    return {
      responseError: function (res) {

        switch (res.status) {
        case 401:
          // 401 没有权限
          $rootScope.$emit("UserNoPermission", res);
          break;
        default:
          $rootScope.$emit("UserError", res);
        }

        return $q.reject(res);
      }
    }

  }

})();