define([ "angular", "angularAMD", "angular-ui-router", "ng-file-upload",
		'jquery', 'angular-sanitize', 'bootstrap', 'ImageCropper' ], function(
		angular, angularAMD) {

	var app = angular.module("app", [ "ui.router", "ImageCropper" ]);

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
			templateUrl : './html/admin.html',
			controller : 'adminCtrl',
		}))
		
		.state('mainHome', angularAMD.route({
			url : '/mainHome',
			templateUrl : './html/mainHome.html',
		}))
		
		.state('mainHome.list', angularAMD.route({
			url : '/list',
			views : {
				"header@mainHome" : angularAMD.route({
					templateUrl : './html/head.html',
//					controller:'HeadController',
				}),
				"left@mainHome" : angularAMD.route({
					templateUrl : './html/left.html',
//					controller : "MenuController",
//					controllerUrl:[ ]//必需是数组
				}),
				"main@mainHome":angularAMD.route({
					templateUrl : './html/main.html',
				})
			}
		}))
		
		.state('mainHome.list.userList', angularAMD.route({
			url : '/userList',
			views : {
				'main@mainHome' : angularAMD.route({
					templateUrl : function(params) {
									  return './html/userList.html'
								  },
					controller : 'userListCtrl',
				})
			}
		}))
		
		.state('mainHome.list.userForm', angularAMD.route({
			url : '/userForm/:operator/:id',
			views : {
				'main@mainHome' : angularAMD.route({
					templateUrl : function(params) {
									  return './html/userForm.html'
								  },
					controller : 'userFormCtrl',
				})
			}
		}))
		
		.state('mainHome.list.messageList', angularAMD.route({
			url : '/messageList',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
									  return './html/messageList.html'
								  },
					controller : 'messageListCtrl',
				})
			}
		}))
		
		.state('main.list.messageForm', angularAMD.route({
			url : '/messageForm/:operator/:id',
			views : {
				'main' : angularAMD.route({
					templateUrl : function(params) {
									  return './html/messageForm.html'
								  },
					controller : 'messageFormCtrl',
				})
			}
		}))
	})

	return angularAMD.bootstrap(app);
});