(function () {
  'use strict';

  angular.module('app.raml')
    .controller('ramlCtrl', ramlCtrl)
    .controller('ramlFormCtrl', ramlFormCtrl);

  /** @ngInject */
  function ramlCtrl($scope, $state, raml) {
    $scope.search = '';
    $scope.ramls = [];

    $scope.add = function () {
      $state.go('app.raml.form')
    }

    $scope.update = function (raml) {
      $state.go('app.raml.form', {
        id: raml.id
      })
    }

    //
    raml.getResources()
      .then(function (datas) {
        $scope.ramls = datas;
      });

  }

  /** @ngInject */
  function ramlFormCtrl($scope, $state, $stateParams, raml, logger) {

    var update = false; // 是否是修改

    $scope.raml = {};
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

      raml.getResource(id)
        .then(function (r) {
          if (update && r.url === undefined) {
            throw new Error('no raml to update.');
          }
          $scope.raml = angular.extend({}, r);
          console.log(r.params);
          add({
            level: 0,
            index: r.params === undefined ? 0 : r.params.length
          });
        })
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
    function addParams(param) {
      var newParam = {};
      newParam.level = param.level;
      newParam.index = param.index + 1;

      add(newParam);

    }

    // 子集
    function addChildParams(param) {
      var newParam = {};
      newParam.level = param.level + 1;
      newParam.index = param.index + 1;

      add(newParam);

    }

    // 添加到params
    function add(param) {
      // params
      var params = $scope.raml.params || [];

      // 位置
      var index = param.index;
      // 数据长度
      var length = params.length;

      // 设置空白的个数,与级别相同
      setBlanks(param);

      // 添加到最后
      if (index > params.length) {
        throw new Error("not set param to " + index);
      }

      // move index - end
      for (var i = length - 1; i >= index; i--) {
        var p = params[i];
        p.index = p.index + 1;
        setBlanks(p);
        params[i + 1] = p;
      }

      // add 
      params[index] = param;

      // 设置到$scope
      $scope.raml.params = params;
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

    function removeEmptyParam(raml) {
      var params = raml.params;
      var newParams = [];
      for (var i = 0; i < params.length; i++) {
        var param = params[i];
        if (!validParam(param)) {
          logger.log('invalid param ', (i + 1))
        } else {
          newParams[newParams.length] = param;
        }
      }
      // 重新设置编号
      for (var j = 0; j < newParams.length; j++) {
        newParams[j].index = j;
      }
      raml.params = newParams;
    }

    function validParam(param) {
      if (param === null) {
        return false;
      }
      if (param.displayName === undefined || param.displayName === '') {
        return false;
      }
      if (param.description === undefined || param.description === '') {
        return false;
      }
      if (param.type === undefined || param.type === '') {
        return false;
      }
      return true;
    }

    // 生成
    function create() {
      var r = $scope.raml;
      removeEmptyParam(r);
      logger.log("raml", r);

      var promise;
      if (update) {
        promise = raml.updateResource(r)
      } else {
        promise = raml.addResource(r)
      }

      promise.then(function (result) {
        if (result.code === 0) {
          gotoList();
        } else {
          logger.log('error');
        }
      });

    }

    // to list
    function gotoList() {
      $state.go('app.raml.list');
    }

  }

})();