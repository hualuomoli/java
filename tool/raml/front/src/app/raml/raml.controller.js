(function () {
  'use strict';

  angular.module('app.raml')
    .controller('ramlCtrl', ramlCtrl)
    .controller('ramlFormCtrl', ramlFormCtrl);

  /** @ngInject */
  function ramlCtrl($scope, $state, raml) {
    $scope.search = '';
    $scope.resources = [];

    $scope.add = function () {
      $state.go('app.raml.form')
    }

    $scope.update = function (resource) {
      $state.go('app.raml.form', {
        id: resource.id
      })
    }

    //
    raml.getResources()
      .then(function (datas) {
        $scope.resources = datas;
      });

  }

  /** @ngInject */
  function ramlFormCtrl($scope, $state, $stateParams, raml, logger) {

    var update = false; // 是否是修改

    $scope.resource = {};
    $scope.rule = {}; // 规则
    $scope.valid = {}; // 现在校验的param
    $scope.process = 'header'; // 默认为header

    // methods
    $scope.setUriParams = setUriParams;
    $scope.addParams = addParams;
    $scope.addChildParams = addChildParams;
    $scope.setValidObject = setValidObject;
    $scope.confirmValid = confirmValid;
    $scope.create = create;
    $scope.gotoList = gotoList;

    // load
    load();

    // 加载
    function load() {

      var id = $stateParams.id;
      logger.log('id', id);

      update = id !== undefined && id !== '';

      if (update) {
        // update
        raml.getResource(id)
          .then(function (resource) {
            init(resource);
          })
      } else {
        // add
        var resource = {
          headerParams: [],
          uriParams: [],
          queryParams: [],
          responseParams: []
        };
        init(resource);
      }
    }

    // 初始化
    function init(resource) {
      // add header param
      add(resource.headerParams, {
        level: 0,
        index: resource.headerParams.length
      });
      // set uri params
      $scope.saveUriParams = [];
      angular.extend($scope.saveUriParams, resource.uriParams);
      // add query params
      add(resource.queryParams, {
        level: 0,
        index: resource.queryParams.length
      });
      // add response params
      add(resource.responseParams, {
        level: 0,
        index: resource.responseParams.length
      });
      // add to scope
      $scope.resource = resource
    }

    // 设置uri的参数
    function setUriParams(uri) {

      //原来保存的参数
      var saveUriParams = $scope.saveUriParams;

      // 设置当前的参数
      var uriParams = [];

      var array = uri.split('/');
      for (var i = 0; i < array.length; i++) {
        var param = array[i];
        if (param.length <= 2) {
          continue;
        }
        // 参数
        if (param.startsWith('{') && param.endsWith('}')) {
          var displayName = param.substring(1, param.length - 1);
          var uriParam = {
            displayName: displayName
          };
          // 如果存在保存的参数,使用保存的参数
          for (var j = 0; j < saveUriParams.length; j++) {
            var srcUriParam = saveUriParams[j];
            if (srcUriParam.displayName == displayName) {
              uriParam = srcUriParam;
              break;
            }
          }
          uriParams[uriParams.length] = uriParam;
        }
      }
      // 设置新参数到参数集合中
      $scope.resource.uriParams = uriParams;

      // 保存原参数
      angular.extend(saveUriParams, uriParams);

    }

    // 设置校验Object
    function setValidObject(param) {
      if (param.rule === undefined) {
        param.rule = {};
      }
      // set valid
      $scope.valid = param;
      // set rule
      $scope.rule = angular.extend({}, param.rule);
    }

    // 确定规则 
    function confirmValid(rule) {
      angular.extend($scope.valid.rule, rule);
    }

    // 同级
    function addParams(params, param) {
      var newParam = {};
      newParam.level = param.level;
      newParam.index = param.index + 1;

      add(params, newParam);

    }

    // 子集
    function addChildParams(params, param) {
      var newParam = {};
      newParam.level = param.level + 1;
      newParam.index = param.index + 1;

      add(params, newParam);

    }

    // 添加到params
    function add(params, param) {
      // 位置
      var index = param.index;
      // 数据长度
      var length = params.length;

      // 设置空白的个数,与级别相同
      setBlanks(param);

      // move index - end
      for (var i = length - 1; i >= index; i--) {
        var p = params[i];
        p.index = p.index + 1;
        setBlanks(p);
        params[i + 1] = p;
      }

      // add 
      params[index] = param;

    }

    // 设置空白个数
    function setBlanks(param) {
      var level = param.level;
      var blanks = [];
      for (var i = 0; i < level; i++) {
        blanks[blanks.length] = i;
      }
      param.blanks = blanks;
    }

    // 移除空白的参数
    function removeEmptyParam(params) {
      var newParams = [];
      for (var i = params.length - 1; i >= 0; i--) {
        var param = params[i];
        // 如果不合法,移除
        if (!validParam(param)) {
          params.splice(i, 1);
        }
      }
      // 重新设置编号
      for (var j = 0; j < params.length; j++) {
        params[j].index = j;
      }
    }

    // 参数是否合法
    function validParam(param) {
      if (param.displayName === undefined || param.displayName === '') {
        return false;
      }
      if (param.type === undefined || param.type === '') {
        return false;
      }
      return true;
    }

    // raml是否合法
    function validRaml(resource) {
      if (resource.url === undefined || resource.url === '') {
        return false;
      }
      if (resource.method === undefined || resource.method === '') {
        return false;
      }
      if (resource.responseMimeType === undefined || resource.responseMimeType === '') {
        return false;
      }
      return true;
    }

    // 生成
    function create() {
      var resource = $scope.resource;
      removeEmptyParam(resource.headerParams);
      removeEmptyParam(resource.queryParams);
      removeEmptyParam(resource.responseParams);

      if (!validRaml(resource)) {
        return;
      }

      // showKeys(resource);

      var promise;
      if (update) {
        promise = raml.updateResource(resource)
      } else {
        promise = raml.addResource(resource)
      }

      promise.then(function (result) {
        if (result.code === 0) {
          gotoList();
        } else {
          logger.log('error');
        }
      });

    }

    function showKeys(obj) {
      for (var key in obj) {
        var value = obj[key];
        if (typeof value === 'object') {
          showKeys(value);
        }
        console.log(key + ' = ' + value)
      }
    }

    // to list
    function gotoList() {
      $state.go('app.raml.list');
    }

  }

})();