(function () {
  'use strict';

  angular.module('app', [
    'ui.router',
    'app.logger',

    // business
    'app.user',
    'app.raml'

  ]);

})();