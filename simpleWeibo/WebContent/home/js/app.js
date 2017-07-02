define([ "angular", "angularAMD", "angular-ui-router", "ng-file-upload",
		'jquery', 'angular-sanitize', 'bootstrap', 'ImageCropper' ], function(
		angular, angularAMD) {

	var app = angular.module("app", [ "ui.router", "ImageCropper" ]);

	app.filter('trustHtml', function($sce) {
		return function(input) {
			return $sce.trustAsHtml(input);
		}
	});

	app.factory('locals', [ '$window', function($window) {
		return { // 存储单个属性
			set : function(key, value) {
				$window.localStorage[key] = value;
			}, // 读取单个属性
			get : function(key, defaultValue) {
				return $window.localStorage[key] || defaultValue;
			}, // 存储对象，以JSON格式存储
			setObject : function(key, value) {
				$window.localStorage[key] = JSON.stringify(value);
			}, // 读取对象
			getObject : function(key) {
				return JSON.parse($window.localStorage[key] || '{}');
			}

		}
	} ]);

	app.config(function($httpProvider) {
		$httpProvider.defaults.transformRequest = function(obj) {
			var str = [];
			for ( var p in obj) {
				str.push(encodeURIComponent(p) + "="
						+ encodeURIComponent(obj[p]));
			}
			return str.join("&");
		}

		$httpProvider.defaults.headers.post = {
			'Content-Type' : 'application/x-www-form-urlencoded'
		}

	});

	app.config(function($stateProvider, $urlRouterProvider,
				$rootScopeProvider) {
		$urlRouterProvider.otherwise('/login');
		$stateProvider

		.state('login', angularAMD.route({
			url : '/login',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/login.html'
					},
					controller : 'loginCtrl',
				})
			}
		}))

		.state('personalPage', angularAMD.route({
			url : '/personalPage/:id',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/personalPage.html'
					},
					controller : 'personalPageCtrl',
				})
			}
		}))

		.state('mainHome', angularAMD.route({
			url : '/mainHome/:id',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/mainHome.html'
					},
					controller : 'mainHomeCtrl',
				})
			}
		}))

		.state('account', angularAMD.route({
			url : '/account/:id',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/account.html'
					},
					controller : 'accountCtrl',
				})
			}
		}))

		.state('userList', angularAMD.route({
			url : '/userList/:type/:id',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/userList.html'
					},
					controller : 'userListCtrl',
				})
			}
		}))

		.state('register', angularAMD.route({
			url : '/register',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/register.html'
					},
					controller : 'registerCtrl',
				})
			}
		}))

		.state('test', angularAMD.route({
			url : '/test',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
						return './html/test.html'
					},
					controller : 'testCtrl',
				})
			}
		}))
	})

	return angularAMD.bootstrap(app);
});