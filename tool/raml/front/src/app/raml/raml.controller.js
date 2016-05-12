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

    // methods
    $scope.addParams = addParams;
    $scope.addChildParams = addChildParams;
    $scope.setValidObject = setValidObject;
    $scope.confirmValid = confirmValid;
    $scope.create = create;
    $scope.gotoList = gotoList;

    // init
    init();

    // 初始化
    function init() {

      var id = $stateParams.id;
      logger.log('id', id);

      update = id !== undefined && id !== '';

      if (update) {
        // update
        raml.getResource(id)
          .then(function (resource) {
            // add query params
            add(resource.queryParams, {
              level: 0,
              index: resource.queryParams.length
            });
            add(resource.responseParams, {
              level: 0,
              index: resource.responseParams.length
            });
            $scope.resource = resource
          })
      } else {
        // add

        var resource = {
          queryParams: [],
          responseParams: []
        };
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
        $scope.resource = resource;
      }


    }

    // 设置校验Object
    function setValidObject(param) {
      // set valid
      $scope.valid = param;
      // set rule
      $scope.rule = angular.extend({}, param);
    }

    // 确定规则 
    function confirmValid(rule) {
      angular.extend($scope.valid, rule);
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
      if (resource.queryMimeType === undefined || resource.queryMimeType === '') {
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