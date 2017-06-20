var app = angular.module('app', ['ui.router']);
app.config(function($stateProvider, $urlRouterProvider){
	$urlRouterProvider.otherwise('init');
	$stateProvider
	
	.state('init', {
		url: '/init',
		views : {
			"main":{
				templateUrl: './html/init.html',
				controller:'initController'
			},
			'left':{
				templateUrl:'./html/left.html',
				controller:'leftCtrl',
			}
		}
	})
	
	.state('form', {
		url: '/form/:type/:operator/:id',
		views: {
			'main':{
				templateUrl: './html/entityform.html',
				controller:'formCtrl'
			},
			'left':{
				templateUrl:'./html/left.html',
				controller:'leftCtrl',
			}
		}
	})
	
	.state('list', {
//				params: {'id': null},
		url: '/list/:type', 
		views: {
			'main':{
				templateUrl: './html/entitylist.html',
				controller:  "listCtrl"
			},
			'left':{
				templateUrl:'./html/left.html',
				controller:'leftCtrl',
			}
		}
	})
	
});
app.controller('initController',['$scope', '$stateParams', '$http', '$state',
                                 function($scope, $stateParams, $http, $state) 
{
	$scope.submit = function() {
		var url = "http://localhost:8080/crud/InitServlet"
	$http.post(url,{entity:$scope.entity,method:"submit"},
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
			if(data.success == true) {
				$state.go("list",{type:data.clazz});
			}
			else {
				alert("请确认实体路径是否存在");
			}
		})
		.error(function(error){
			alert(error);
		});
	}
}]);

app.controller('formCtrl',['$scope', '$stateParams', '$http', '$state',
                       function($scope, $stateParams, $http, $state) {
	$scope.type = $stateParams.type;
	$scope.operator = $stateParams.operator;
	$scope.model = {};
	
	$scope.parseOperator = function() {
		if($scope.operator == 'add') {
			$scope.operatorStr = "新增";
		} else if($scope.operator == 'edit') {
			$scope.operatorStr = "编辑";
		}						
	}
	
	$scope.findClass = function() {
		var url = "http://localhost:8080/crud/EntityServlet"
			$http.post(url,{entity:$scope.type, method:"findClass"},
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
			$scope.entity = data.name;
			$scope.columnList = data.fields;
//						$scope.model = {};
			angular.forEach(data.fields, function(field) {
				
				if(field.name) {
					if(!$scope.model.hasOwnProperty(field.name)) {
						$scope.model[field.name] = "";
					}
				}
			})
		})
		.error(function(error){
			alert(error);
		});
	}
	
	$scope.init = function() {
		$scope.parseOperator();
		$scope.findClass();
		
		if($scope.operator == 'edit') {
			$scope.model = eval('(' + $stateParams.id + ')');
		}
	}
	$scope.init();
	$scope.submit = function() {
		var url = "http://localhost:8080/crud/EntityServlet"
		$http.post(url,{model:JSON.stringify($scope.model),method:"submit", type:$scope.type, operator:$scope.operator},
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
			if(data == 'true') {
				$state.go("list",{type:$stateParams.type});
			}
			else {
				alert("保存失败");
			}
		})
		.error(function(error){
			alert(error);
		});
	}
}]);

app.controller('listCtrl',['$scope', '$stateParams', '$http', '$state',
                            function($scope, $stateParams, $http, $state) {
	$scope.type = $stateParams.type;
	$scope.entitys = [];
	
	$scope.loadEntitys = function() {
		var url = "http://localhost:8080/crud/EntityServlet"
			$http.post(url,{entity:$scope.type, method:"loadEntitys"},
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
			$scope.entitys = data;
			$scope.keys = [];
			
			for(var key in $scope.entitys[0]) {
				$scope.keys.push(key);
			}
		})
		.error(function(error){
			alert(error);
		})
	}
	
	$scope.updateEntity = function(entity) {
		$state.go("form",{type:$scope.type, operator:"edit", id:JSON.stringify(entity)});
	}
	
	$scope.addEntity  = function() {
		$state.go("form",{type:$scope.type, operator:"add",id:null});
	}
	
	$scope.deleteEntity = function(model) {
		var url = "http://localhost:8080/crud/EntityServlet"
			$http.post(url,{entity:$scope.type, model:JSON.stringify(model), method:"deleteEntity"},
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
			if(data == "true") {
				location.reload();
			} else {
				alert("删除失败");
			}
		})
		.error(function(error){
			alert(error);
		})
	}
	
	$scope.init = function() {
		$scope.loadEntitys();
	}
	$scope.init();
}]);

app.controller('leftCtrl',['$scope', '$stateParams', '$http', '$state',
                       function($scope, $stateParams, $http, $state) {
	
	$scope.types = [];
	
	$scope.init = function() {
		var url = "http://localhost:8080/crud/InitServlet"
		$http.post(url,{method:"init"},
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
				if(data[0].success == true) {
					$scope.types = data;
				}
				else {
					alert("请确认实体路径是否存在");
				}
			})
			.error(function(error){
				alert(error);
			});
	}
	
	$scope.toInit = function() {
		$state.go('init',{});
	}
	
	$scope.toList = function(clazz) {
		$state.go('list',{type:clazz});
	}
	$scope.init();
}])
			

