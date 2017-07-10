angular.module('login',[])
	.controller('loginCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.password = "";
		$scope.phone = "";
		$scope.user = {};
		
		$scope.register = function() {
			$state.go("register");
		}
		
		$scope.goManager = function() {
			location.href="http://localhost:8080/simpleWeibo/manager/index.html"
		}
		
		$scope.login = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				phone:$scope.phone,
				method:"login", 
				password:$scope.password, 
			})
			.success(function(data) {
				if(data == 'null') {
					alert("用户名不存在或密码错误");
				} else {
					$scope.user = eval(data);

					locals.setObject("localUser", $scope.user);
					$state.go('personalPage',{id:$scope.user.userId});
				}
			})
			.error(function(error){
				alert(error);
			});
		}
		
	}])