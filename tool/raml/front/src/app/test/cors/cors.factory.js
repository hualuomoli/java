(function () {
  'use strict';


  angular.module('app')
    .factory('testCors', cors);

  /** @ngInject */
  function cors($http, http, logger) {

    return {
      ajaxGet: ajaxGet,
      ajaxPost: ajaxPost,
      ngGet: ngGet,
      ngPost: ngPost,
      httpGet: httpGet,
      httpPost: httpPost
    }

    function ajaxGet(url, params) {
      $.ajax(url, {
        type: 'Get',
        data: params,
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success: function (data, status, xhr) {
          logger.debug('ajax get url ', url + ' status ' + status + ' data ' + JSON.stringify(data));
        }
      });
    }

    function ajaxPost(url, params) {
      $.ajax(url, {
        type: 'Post',
        data: params,
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success: function (data, status, xhr) {
          logger.debug('ajax post url ', url + ' status ' + status + ' data ' + JSON.stringify(data));
        }
      });
    }

    function ngGet(url, params) {
      return $http({
          method: 'GET',
          url: url,
          params: params
        })
        .success(function (data, status) {
          logger.debug('ng get url ', url + ' status ' + status + ' data ' + JSON.stringify(data));
        });
    }

    function ngPost(url, params) {
      return $http({
          method: 'POST',
          url: url,
          data: $.param(params),
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        })
        .success(function (data, status) {
          logger.debug('ng post url ', url + ' status ' + status + ' data ' + JSON.stringify(data));
        });
    }

    function httpGet(url, params) {
      return http.get(url, params)
        .success(function (data, status) {
          logger.debug('http get url ', url + ' status ' + status + ' data ' + JSON.stringify(data));
        });
    }

    function httpPost(url, params) {
      return http.post(url, params)
        .success(function (data, status) {
          logger.debug('http post url ', url + ' status ' + status + ' data ' + JSON.stringify(data));
        });
    }

  }

})();