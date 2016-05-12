(function () {
  'use strict';

  angular.module('app.raml')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {

    // default
    // app and dashboard
    $stateProvider
      .state('app.raml', {
        abstract: true,
        url: '/raml',
        templateUrl: 'tpl/raml/main.html'
      })
      .state('app.raml.list', {
        url: '/list',
        templateUrl: 'tpl/raml/list.html',
        controller: 'ramlCtrl'
      })
      .state('app.raml.form', {
        url: '/form/{id}',
        templateUrl: 'tpl/raml/form.html',
        controller: 'ramlFormCtrl'
      })
  }

})();