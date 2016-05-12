(function () {
  'use strict';

  angular.module('app.raml')
    .service('raml', raml);

  /** @ngInject */
  function raml($q, $http) {
    var service = {};
    service.getResources = getResources;
    service.addResource = addResource;
    service.getResource = getResource;
    service.updateResource = updateResource;


    return service;

    function getResources() {
      var deferred = $q.defer();
      $http.get('/raml')
        .success(function (datas) {
          deferred.resolve(datas);
        });
      return deferred.promise;
    }

    function addResource(raml) {
      var deferred = $q.defer();
      $http.post('/raml', raml)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

    function getResource(id) {
      var deferred = $q.defer();
      $http.get('/raml/' + id)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

    function updateResource(raml) {
      var deferred = $q.defer();
      $http.put('/raml/' + raml.id, raml)
        .success(function (data) {
          deferred.resolve(data);
        });
      return deferred.promise;
    }

  }


  /** @ngInject */
  // function raml($q, http) {
  //   var datas = [{
  //     url: 'api/u/{usercode}/info',
  //     method: 'get',
  //     description: '获取用户信息'
  //   }];
  //   var service = {};
  //   service.getResources = getResources;
  //   service.addResource = addResource;
  //   service.getResource = getResource;
  //   service.updateResource = updateResource;

  //   return service;

  //   function getResource(url) {

  //     var deferred = $q.defer();

  //     var raml = {};
  //     for (var i = 0; i < datas.length; i++) {
  //       if (datas[i].url === url) {
  //         raml = datas[i];
  //         break;
  //       }
  //     }
  //     deferred.resolve(raml);

  //     return deferred.promise;
  //   }

  //   function updateResource(raml) {
  //     var deferred = $q.defer();

  //     var index = -1;
  //     for (var i = 0; i < datas.length; i++) {
  //       if (datas[i].url === raml.url) {
  //         index = i;
  //         break;
  //       }
  //     }
  //     datas[i] = raml;

  //     deferred.resolve({
  //       code: 0,
  //       msg: 'success'
  //     });

  //     return deferred.promise;
  //   }

  //   function addResource(raml) {
  //     var deferred = $q.defer();

  //     datas[datas.length] = raml;
  //     deferred.resolve({
  //       code: 0,
  //       msg: 'success'
  //     });

  //     return deferred.promise;

  //   }

  //   function getResources() {
  //     var deferred = $q.defer();

  //     deferred.resolve(datas);

  //     return deferred.promise;

  //   }

  // }

})();