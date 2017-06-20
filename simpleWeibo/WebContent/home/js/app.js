var app = angular.module('app', ['ui.router']);
app.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise('mainHome');
	$stateProvider
	
	.state('mainHome', {
		url: '/mainHome',
		views : {
			'head':{
				templateUrl:'./html/head.html',
			},
			'main':{
				templateUrl:'./html/mainHome.html',
				controller:'mainHomeCtrl',
			}
		}
	})
	
	
});
app.controller('mainHomeCtrl',['$scope', '$stateParams', '$http', '$state',
                                 function($scope, $stateParams, $http, $state) 
{
	$scope.currentPage = 1;
	$scope.pageSize = 20;
	$scope.messages = [];
	
	$scope.init = function() {
		$scope.findAllMessages();
	}
	
	$scope.findAllMessages = function() {
		var url = "http://localhost:8080/simpleWeibo/MessageServlet"
		$http.post(url,{currentPage:$scope.currentPage,pageSize:$scope.pageSize,method:"findAllMessages"},
			{
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },    
            	transformRequest: function(obj) {    
            		var str = [];    
            		for (var p in obj) {    
            			str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));    
            		}    
            		return str.join("&");    
            	}
			})
		.success(function(data) {
			$scope.messages = data;
		})
		.error(function(error){
		});
	}
	
	$scope.init();
}]);

app.controller('formCtrl',['$scope', '$stateParams', '$http', '$state',
                       function($scope, $stateParams, $http, $state) {
	
}]);

app.controller('listCtrl',['$scope', '$stateParams', '$http', '$state',
                            function($scope, $stateParams, $http, $state) {
	
}]);

app.controller('leftCtrl',['$scope', '$stateParams', '$http', '$state',
                       function($scope, $stateParams, $http, $state) {
	
}])
			

