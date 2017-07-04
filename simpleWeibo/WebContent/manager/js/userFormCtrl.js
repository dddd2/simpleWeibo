angular.module('userForm',[])
	.controller('userFormCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.userId = $stateParams.id;
		$scope.operator = $stateParams.operator;//值只有add或者edit两种
		$scope.user = {};
		
		if($scope.operator == 'edit') {
			$scope.operatorStr = '编辑';
		} else if($scope.operator == 'add'){
			$scope.operatorStr = '新增';
		}
		
		$scope.toLsit = function() {
			$state.go("mainHome.list.userList");
		}
		
		$scope.init = function() {
			if($scope.operator == 'edit') {
				$scope.findUserById();
			}
		}
		
		//新增用户方法
		$scope.submit = function() {
			
		}
		
		$scope.findUserById = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"findUserById", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.user = data;
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.init();
	}])