(function () {
  'use strict';

  angular.module('app')
    .controller('TypeaheadDemoCtrl', TypeaheadDemoCtrl);


  /** @ngInject */
  function TypeaheadDemoCtrl($scope) {
    $scope.selected = undefined;
    $scope.states = [{
      "opcode": "dashboard",
      "name": "面板"
    }, {
      "opcode": "calendar",
      "name": "日历"
    }, {
      "opcode": "application",
      "name": "应用"
    }];

  }

})();