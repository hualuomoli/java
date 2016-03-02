(function (angular) {
  'use strict';

  angular.module('bz.chart', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('app.core', [
    'ui.router',
    'blocks.exception',
    'blocks.routehelper'
  ]);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard', [
    'bz.dashboard.chat',
    'bz.dashboard.notifications',
    'bz.dashboard.timeline'
  ]);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.form', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.login', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.table', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('blocks.exception', ['blocks.logger']);

})(window.angular);
(function (angular) {
  'use strict';

  // log
  angular.module('blocks.logger', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('blocks.routehelper', ['blocks.logger']);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard.chat', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard.notifications', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard.timeline', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.buttons', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.grid', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.icons', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.notifications', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.panels-wells', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.typography', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.notification', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.sidebar', ['bz.home.header.sidebar.search']);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.sidebar.search', []);

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('blocks.exception')
    .provider('exceptionHandler', exceptionHandlerProvider);

  function exceptionHandlerProvider() {
    /* jshint validthis:true */
    this.config = {
      appErrorPrefix: '' // error log's prefix
    };

    this.$get = function () {
      return {
        config: this.config
      };
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  logger.$inject = ["$log"];
  angular.module('blocks.logger')
    .factory('logger', logger);

  // logger.$inject = ['$log'];
  /* @ngInject */
  function logger($log) {
    var service = {

      error: error,
      info: info,
      success: success,
      warning: warning,

      log: $log.log
    };

    return service;
    /////////////////////

    function error(message, data) {
      $log.error('Error: ' + message, data);
    }

    function info(message, data) {
      $log.info('Info: ' + message, data);
    }

    function success(message, data) {
      $log.info('Success: ' + message, data);
    }

    function warning(message, data) {
      $log.warn('Warning: ' + message, data);
    }
  }
})(window.angular);
(function (angular) {
  'use strict';

  routehelper.$inject = ["$rootScope", "logger"];
  angular.module('blocks.routehelper')
    .factory('routehelper', routehelper);

  /* @ngInject */
  function routehelper($rootScope, logger) {

    var service = {
      'init': init
    };

    return service;
    ///////////////

    function init() {
      stateChangeError();
      stateChangeSuccess();
      stateNotFound();
    }

    function stateChangeError() {
      $rootScope.$on('$stateChangeError',
        function (event, toState, toParams, fromState) {
          logger.error('change state error.', [fromState, toState]);
          // $location.path(routePath);
        }
      );
    }

    function stateChangeSuccess() {
      $rootScope.$on('$stateChangeSuccess',
        function (event, toState) {
          logger.success('change state success. state = ', toState);
        }
      );
    }

    function stateNotFound() {
      $rootScope.$on('$stateNotFound',
        function (event, toState) {
          logger.warning('state not found. state = ', toState.to);
          logger.warning('state not found. params = ', toState.toParams);
        }
      );
    }
  }
})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider", "$urlRouterProvider", "exceptionHandlerProvider"];
  appRun.$inject = ["routehelper"];
  angular.module('app.core')
    .run(appRun)
    .config(config);

  /* @ngInject */
  function config($stateProvider, $urlRouterProvider, exceptionHandlerProvider) {

    $urlRouterProvider.otherwise('/home/dashboard');

    // Configure the common exception handler
    exceptionHandlerProvider.config.appErrorPrefix = '[App Error] ';
  }

  /* @ngInject */
  function appRun(routehelper) {
    routehelper.init();
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$provide"];
  extendExceptionHandler.$inject = ["$delegate", "exceptionHandler", "logger"];
  angular.module('blocks.exception')
    .config(config);

  /* @ngInject */
  function config($provide) {
    // add prefix to log message
    $provide.decorator('$exceptionHandler', extendExceptionHandler);
  }

  /* @ngInject */
  function extendExceptionHandler($delegate, exceptionHandler, logger) {
    var appErrorPrefix = exceptionHandler.config.appErrorPrefix;
    return function (exception, cause) {

      $delegate(exception, cause);

      var errorData = {
        exception: exception,
        cause: cause
      };
      exception.message = appErrorPrefix + exception.message;
      logger.error(exception.message, errorData);
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.chart')
    .service('chartService', chartService);

  /* @ngInject */
  function chartService() {
    return {
      line: line,
      bar: bar,
      donut: donut,
      radar: radar,
      pie: pie,
      polar: polar,
      dynamic: dynamic
    };

    ///////////
    function line() {
      return {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        series: ['Series A', 'Series B'],
        data: [
          [65, 59, 80, 81, 56, 55, 40],
          [28, 48, 40, 19, 86, 27, 90]
        ]
      };
    }

    function bar() {
      return {
        labels: ['2006', '2007', '2008', '2009', '2010', '2011', '2012'],
        series: ['Series A', 'Series B'],

        data: [
          [65, 59, 80, 81, 56, 55, 40],
          [28, 48, 40, 19, 86, 27, 90]
        ]
      };
    }

    function donut() {
      return {
        labels: ["Download Sales", "In-Store Sales", "Mail-Order Sales"],
        data: [300, 500, 100]
      };
    }

    function radar() {
      return {
        labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],

        data: [
          [65, 59, 90, 81, 56, 55, 40],
          [28, 48, 40, 19, 96, 27, 100]
        ]
      };
    }

    function pie() {
      return {
        labels: ["Download Sales", "In-Store Sales", "Mail-Order Sales"],
        data: [300, 500, 100]
      };
    }

    function polar() {
      return {
        labels: ["Download Sales", "In-Store Sales", "Mail-Order Sales", "Tele Sales", "Corporate Sales"],
        data: [300, 500, 100, 40, 120]
      };
    }

    function dynamic() {
      return {
        labels: ["Download Sales", "In-Store Sales", "Mail-Order Sales", "Tele Sales", "Corporate Sales"],
        data: [300, 500, 100, 40, 120],
        type: 'PolarArea',
      };
    }

  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.login')
    .service('loginService', loginService);

  /* @ngInject */
  function loginService() {
    return {
      login: login
    };

    //
    function login(username, password) {
      if (!username || !password) {
        return false;
      }
      if (username === 'admin' && password === 'admin') {
        return true;
      }
      return false;
    }
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.notification')
    .service('headerNotificationService', notificationService);

  /* @ngInject */
  function notificationService() {

    return {
      getAlerts: getAlerts,
      getTasks: getTasks,
      getMessage: getMessage
    };

    function getAlerts() {
      return [{
        "title": "New Comment",
        "time": "4 minutes ago",
        "class": "fa-comment"
      }, {
        "title": "3 New Followers",
        "time": "12 minutes ago",
        "class": "fa-twitter"
      }, {
        "title": "Message Sent",
        "time": "4 minutes ago",
        "class": "fa-envelope"
      }, {
        "title": "New Task",
        "time": "4 minutes ago",
        "class": "fa-tasks"
      }, {
        "title": "Server Rebooted",
        "time": "4 minutes ago",
        "class": "fa-upload"
      }];
    }

    function getTasks() {
      return [{
        "title": "Task 1",
        "content": "40% Complete",
        "value": "40",
        "class": "progress-bar-success"
      }, {
        "title": "Task 2",
        "content": "20% Complete",
        "value": "20",
        "class": "progress-bar-info"
      }, {
        "title": "Task 3",
        "content": "60% Complete",
        "value": "60",
        "class": "progress-bar-warning"
      }, {
        "title": "Task 4",
        "content": "80% Complete",
        "value": "80",
        "class": "progress-bar-danger"
      }];
    }

    function getMessage() {
      return [{
        "title": "John Smith1",
        "time": "Yesterday",
        "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend..."
      }, {
        "title": "John Smith2",
        "time": "Yesterday",
        "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend..."
      }, {
        "title": "John Smith3",
        "time": "Yesterday",
        "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend..."
      }];
    }

  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.sidebar')
    .service('headerSidebarService', sidebarService);

  /* @ngInject */
  function sidebarService() {

    return {
      getAll: getAll
    };

    function getAll() {
      return [{
        "code": "dashboard",
        "name": "Dashboard",
        "sort": "1",
        "icon": "fa-dashboard"
      }, {
        "code": "chart",
        "name": "Charts",
        "sort": "2",
        "icon": "fa-bar-chart-o"
      }, {
        "code": "table",
        "name": "Tables",
        "sort": "3",
        "icon": "fa-table"
      }, {
        "code": "form",
        "name": "Forms",
        "sort": "4",
        "icon": "fa-edit"
      }, {
        "code": "ui-elements",
        "name": "UI Elements",
        "sort": "5",
        "icon": "fa-wrench",
        "children": [{
          "code": "panelsWells",
          "name": "Panels and Wells",
          "sort": "1"
        }, {
          "code": "buttons",
          "name": "Buttons",
          "sort": "2"
        }, {
          "code": "notifications",
          "name": "Notifications",
          "sort": "3"
        }, {
          "code": "typography",
          "name": "Typography",
          "sort": "4"
        }, {
          "code": "icons",
          "name": "Icons",
          "sort": "5"
        }, {
          "code": "grid",
          "name": "Grid",
          "sort": "6"
        }, {
          "code": "demo",
          "name": "Demo",
          "sort": "1",
          "children": [{
            "code": "first",
            "name": "First Menu",
            "sort": "2"
          }, {
            "code": "second",
            "name": "Second Menu",
            "sort": "3"
          }, {
            "code": "thrid",
            "name": "Thrid Menu",
            "sort": "1"
          }]
        }]
      }];
    }

  }

})(window.angular);
(function (angular) {
  'use strict';

  chartController.$inject = ["$scope", "$timeout", "chartService", "logger"];
  angular.module('bz.chart')
    .controller('chartController', chartController);

  /* @ngInject */
  function chartController($scope, $timeout, chartService, logger) {
    /* jshint validthis:true */
    var chart = this;

    chart.header = '';

    chart.line = {};
    chart.bar = {};
    chart.donut = {};
    chart.radar = {};
    chart.pie = {};
    chart.polar = {};
    chart.dynamic = {};

    $scope.lineClick = lineClick;
    $scope.dynamicToggle = dynamicToggle;

    ///////////////////////////////

    $timeout(function () {

      chart.header = 'Charts';

      chart.line = chartService.line();
      chart.bar = chartService.bar();
      chart.donut = chartService.donut();
      chart.radar = chartService.radar();
      chart.pie = chartService.pie();
      chart.polar = chartService.polar();
      chart.dynamic = chartService.dynamic();

      logger.log('load chart data');

    }, 1000);
    ///
    function lineClick(points, evt) {
      console.log(points, evt);
    }

    function dynamicToggle() {
      chart.dynamic.type = chart.dynamic.type === 'PolarArea' ?
        'Pie' : 'PolarArea';
    }

  }

})(window.angular);
(function (angular) {
  'use strict';

  dashboardController.$inject = ["$scope", "$timeout"];
  angular.module('bz.dashboard')
    .controller('dashboardController', dashboardController);

  /* @ngInject */
  function dashboardController($scope, $timeout) {
    /* jshint validthis:true */
    var dashboard = this;
    dashboard.header = '';

    $timeout(function () {
      dashboard.header = 'Dashboard';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  formController.$inject = ["$scope", "$timeout"];
  angular.module('bz.form')
    .controller('formController', formController);

  /* @ngInject */
  function formController($scope, $timeout) {
    /* jshint validthis:true */
    var form = this;
    form.header = '';

    $timeout(function () {
      form.header = 'Forms';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  loginController.$inject = ["$scope", "$state", "$timeout", "loginService", "logger"];
  angular.module('bz.login')
    .controller('loginController', loginController);

  /* @ngInject */
  function loginController($scope, $state, $timeout, loginService, logger) {
    /* jshint validthis:true */
    var login = this;
    login.remember = false;
    login.error = false;
    login.errorMsg = '';
    login.username = '';
    login.password = '';

    $scope.doLogin = doLogin;

    ///////////////////////////////////
    function doLogin() {
      logger.log(login);
      if (loginService.login(login.username, login.password)) {
        $state.go('home.dashboard');
      } else {
        login.error = true;
        login.errorMsg = 'username or password error!';
      }
    }

    $timeout(function () {
      login.remember = true;
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  tableController.$inject = ["$scope", "$timeout"];
  angular.module('bz.table')
    .controller('tableController', tableController);

  /* @ngInject */
  function tableController($scope, $timeout) {
    /* jshint validthis:true */
    var table = this;
    table.header = '';

    $timeout(function () {
      table.header = 'Tables';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  buttonsController.$inject = ["$scope", "$timeout"];
  angular.module('bz.buttons')
    .controller('buttonsController', buttonsController);

  /* @ngInject */
  function buttonsController($scope, $timeout) {
    /* jshint validthis:true */
    var buttons = this;
    buttons.header = '';

    $timeout(function () {
      buttons.header = 'Buttons';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  gridController.$inject = ["$scope", "$timeout"];
  angular.module('bz.grid')
    .controller('gridController', gridController);

  /* @ngInject */
  function gridController($scope, $timeout) {
    /* jshint validthis:true */
    var grid = this;
    grid.header = '';

    $timeout(function () {
      grid.header = 'Grid';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  iconsController.$inject = ["$scope", "$timeout"];
  angular.module('bz.icons')
    .controller('iconsController', iconsController);

  /* @ngInject */
  function iconsController($scope, $timeout) {
    /* jshint validthis:true */
    var icons = this;
    icons.header = '';

    $timeout(function () {
      icons.header = 'Icons';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  notificationsController.$inject = ["$scope", "$timeout"];
  angular.module('bz.notifications')
    .controller('notificationsController', notificationsController);

  /* @ngInject */
  function notificationsController($scope, $timeout) {
    /* jshint validthis:true */
    var notifications = this;
    notifications.header = '';

    $timeout(function () {
      notifications.header = 'Notifications';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  panelsWellsController.$inject = ["$scope", "$timeout"];
  angular.module('bz.panels-wells')
    .controller('panelsWellsController', panelsWellsController);

  /* @ngInject */
  function panelsWellsController($scope, $timeout) {
    /* jshint validthis:true */
    var panelsWells = this;
    panelsWells.header = '';

    $timeout(function () {
      panelsWells.header = 'Panels And Wells';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  typographyController.$inject = ["$scope", "$timeout"];
  angular.module('bz.typography')
    .controller('typographyController', typographyController);

  /* @ngInject */
  function typographyController($scope, $timeout) {
    /* jshint validthis:true */
    var typography = this;
    typography.header = '';

    $timeout(function () {
      typography.header = 'Typography';
    }, 1000);

  }

})(window.angular);
(function (angular) {
  'use strict';

  notificationController.$inject = ["$timeout", "headerNotificationService", "logger"];
  angular.module('bz.home.header.notification')
    .controller('headerNotificationController', notificationController);

  /* @ngInject */
  function notificationController($timeout, headerNotificationService, logger) {
    /* jshint validthis:true */
    var notification = this;

    notification.alerts = {};
    notification.messages = {};
    notification.tasks = {};

    $timeout(function () {
      notification.alerts = headerNotificationService.getAlerts();
      notification.tasks = headerNotificationService.getTasks();
      notification.messages = headerNotificationService.getMessage();

      logger.log('load notification data');

    }, 1000);

  }

})(window.angular);
(function (angular, $) {
  'use strict';

  sidebarController.$inject = ["$scope", "$state", "$timeout", "headerSidebarService", "logger"];
  angular.module('bz.home.header.sidebar')
    .controller('headerSidebarController', sidebarController);

  /* @ngInject */
  function sidebarController($scope, $state, $timeout, headerSidebarService, logger) {
    /* jshint validthis:true */
    var sidebar = this;

    sidebar.menus = [];

    $scope.toggleClass = toggleClass;
    $scope.goMenu = goMenu;

    $scope.$on('init', init);

    /////////////////////
    $timeout(function () {
      sidebar.menus = headerSidebarService.getAll();

      logger.log('load sidebar data');

    }, 1000);

    function init() {
      $('.metisMenu').metisMenu();
    }

    function goMenu(menuCode) {
      // console.log(menuCode);
      $state.go('home.' + menuCode);
    }

    function toggleClass(key) {
      $(key).slideToggle(400);
    }

  }

})(window.angular, window.jQuery);
(function (angular) {
  'use strict';

  finishRender.$inject = ["$timeout"];
  angular.module('app.core')
    .directive('onFinishRender', finishRender);

  /* @ngInject */
  function finishRender($timeout) {
    return {
      restrict: 'A',
      link: function (scope, element, attr) {
        $timeout(function () {
          if (attr.onFinishRender) {
            scope.$emit(attr.onFinishRender);
          } else {
            scope.$emit('init');
          }
        });
      }
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard.chat')
    .directive('bzDashboardChat', chat);

  function chat() {
    return {
      templateUrl: 'dashboard/chat/chat.html',
      restrict: 'AE',
      replace: true,
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard.notifications')
    .directive('bzDashboardNotifications', notifications);

  function notifications() {
    return {
      templateUrl: 'dashboard/notifications/notifications.html',
      restrict: 'AE',
      replace: true,
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.dashboard.timeline')
    .directive('bzDashboardTimeline', timeline);

  function timeline() {
    return {
      templateUrl: 'dashboard/timeline/timeline.html',
      restrict: 'AE',
      replace: true
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header')
    .directive('bzHeader', header);

  function header() {
    return {
      templateUrl: 'home/header/header.html',
      restrict: 'AE',
      replace: true
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.notification')
    .directive('bzHeaderNotification', notification);

  function notification() {
    return {
      templateUrl: 'home/header/notification/notification.html',
      controller: 'headerNotificationController',
      controllerAs: 'notification',
      restrict: 'AE',
      replace: true
    };
  }
})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.sidebar')
    .directive('bzHeaderSidebar', sidebar);

  function sidebar() {
    return {
      templateUrl: 'home/header/sidebar/sidebar.html',
      controller: 'headerSidebarController',
      controllerAs: 'sidebar',
      restrict: 'AE',
      replace: true
    };
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('bz.home.header.sidebar.search')
    .directive('bzHeaderSidebarSearch', search);

  function search() {
    return {
      templateUrl: 'home/header/sidebar/search/search.html',
      scope: {},
      restrict: 'E',
      replace: true,
    };
  }
})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.chart')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.chart', {
      url: '/chart',
      templateUrl: 'chart/chart.html',
      controller: 'chartController',
      controllerAs: 'chart'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.dashboard')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.dashboard', {
      url: '/dashboard',
      templateUrl: 'dashboard/dashboard.html',
      controller: 'dashboardController',
      controllerAs: 'dashboard'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.form')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.form', {
      url: '/form',
      templateUrl: 'form/form.html',
      controller: 'formController',
      controllerAs: 'form'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.home')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home', {
      url: '/home',
      templateUrl: 'home/home.html',
      // abstract: true
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.login')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('login', {
      url: '/login',
      templateUrl: 'login/login.html',
      controller: 'loginController',
      controllerAs: 'login'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.table')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.table', {
      url: '/table',
      templateUrl: 'table/table.html',
      controller: 'tableController',
      controllerAs: 'table'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.buttons')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.buttons', {
      url: '/buttons',
      templateUrl: 'ui-elements/buttons/buttons.html',
      controller: 'buttonsController',
      controllerAs: 'buttons'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.grid')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.grid', {
      url: '/grid',
      templateUrl: 'ui-elements/grid/grid.html',
      controller: 'gridController',
      controllerAs: 'grid'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.icons')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.icons', {
      url: '/icons',
      templateUrl: 'ui-elements/icons/icons.html',
      controller: 'iconsController',
      controllerAs: 'icons'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.notifications')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.notifications', {
      url: '/notifications',
      templateUrl: 'ui-elements/notifications/notifications.html',
      controller: 'notificationsController',
      controllerAs: 'notifications'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.panels-wells')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.panelsWells', {
      url: '/panels-wells',
      templateUrl: 'ui-elements/panels-wells/panels-wells.html',
      controller: 'panelsWellsController',
      controllerAs: 'panelsWells'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  config.$inject = ["$stateProvider"];
  angular.module('bz.typography')
    .config(config);

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider.state('home.typography', {
      url: '/typography',
      templateUrl: 'ui-elements/typography/typography.html',
      controller: 'typographyController',
      controllerAs: 'typography'
    });
  }

})(window.angular);
(function (angular) {
  'use strict';

  angular.module('app', [
    'app.core',
    'chart.js',

    'bz.home',
    'bz.home.header',
    'bz.home.header.notification',
    'bz.home.header.sidebar',

    'bz.dashboard',
    'bz.chart',
    'bz.table',
    'bz.form',
    'bz.buttons',
    'bz.grid',
    'bz.icons',
    'bz.notifications',
    'bz.panels-wells',
    'bz.typography',

    'bz.login'
  ]);

  angular.element(document).ready(function () {
    angular.bootstrap(document, ['app']);
    angular.element(document).find('html').addClass('ng-app');
  });

})(window.angular);