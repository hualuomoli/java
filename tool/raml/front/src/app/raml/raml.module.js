(function () {
  'use strict';

  angular.module('app.raml', [
    'blocks.logger',
    'blocks.exception',
    'blocks.http',
    'blocks.user',

    'oc.lazyLoad'
  ]);

})();