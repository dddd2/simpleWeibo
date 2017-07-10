angular.module('admin',[])
	.controller('adminCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.phone="";
		$scope.password="";
		
		$scope.goWeibo = function() {
			location.href="http://localhost:8080/simpleWeibo/home/index.html"
		}
		
		$scope.managerAdmin = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"managerAdmin",
				phone:$scope.phone,
				password:$scope.password
			})
			.success(function(data) {
				if(data!= "null") {
					locals.setObject("AdminUser",data);
					$state.go("mainHome.list");
				} else {
					alert("没有权限登录");
				}
				
			})
			.error(function(error){
				alert(error);
			});
		}
		
	}])