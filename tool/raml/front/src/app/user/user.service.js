(function () {
  'use strict';

  angular.module('app.user')
    .service('UserService', UserService);

  /** @ngInject */
  function UserService($q, $http) {
    /* jshint validthis: true */
    var service = this;

    // 登录
    service.login = login;

    /////////////////////////////////////
    function login(user) {
      var deferred = $q.defer();
      $http.post('/login', user)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

  }

})();