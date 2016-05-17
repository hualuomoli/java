(function () {
  'use strict';

  angular.module('app.raml')
    .service('raml', raml);

  /** @ngInject */
  function raml($rootScope, $q, $http) {
    /* jshint validthis: true */
    var service = this;

    service.getResources = getResources;
    service.addResource = addResource;
    service.getResource = getResource;
    service.updateResource = updateResource;

    // 获取所有资源
    function getResources() {
      var deferred = $q.defer();
      $http
        .get('/raml')
        .success(function (datas) {
          deferred.resolve(datas);
        });
      return deferred.promise;
    }

    // 添加资源
    function addResource(raml) {
      var deferred = $q.defer();
      $http
        .post('/raml', raml)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

    // 获取资源
    function getResource(id) {
      var deferred = $q.defer();
      $http
        .get('/raml/' + id)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

    // 修改资源
    function updateResource(raml) {
      var deferred = $q.defer();
      $http
        .put('/raml/' + raml.id, raml)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

  }

})();