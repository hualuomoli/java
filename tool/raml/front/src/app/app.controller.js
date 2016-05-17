(function () {
  'use strict';

  angular.module('app')
    .controller('appCtrl', appCtrl);

  /** @ngInject */
  function appCtrl($rootScope, logger) {
    // 路由改变错误
    $rootScope.$on('$stateChangeStart',
      function (event, toState, toParams, fromState, fromParams) {
        logger.debug('change state starting.', [toState, toParams, fromState, fromParams]);
        // 保存到root
        $rootScope.rootState = {
          from: {
            state: fromState,
            params: fromParams
          },
          to: {
            state: toState,
            params: toParams
          }
        }
      }
    )

    // 路由改变错误
    $rootScope.$on('$stateChangeError',
      function (event, toState, toParams, fromState, fromParams, error) {
        logger.error('change state error.', [toState, toParams, fromState, fromParams, error]);
      }
    )

    // 路由改变成功
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState, toParams, fromState, fromParams) {
        logger.success('chage state success', [toState, toParams, fromState, fromParams]);
      }
    )

    // 路由未发现
    $rootScope.$on('$stateNotFound',
      function (event, unfoundState, fromState, fromParams) {
        logger.warn('state not found', [unfoundState, fromState, fromParams]);
      }
    )
  }

})();