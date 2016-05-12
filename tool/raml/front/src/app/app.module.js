(function () {
  'use strict';

  angular.module('app', [
    'blocks.logger',
    'blocks.exception',
    'blocks.routehelper',
    'blocks.http',
    'blocks.user',

    'ui.bootstrap',
    'oc.lazyLoad',

    'app.raml'

  ]);

})();