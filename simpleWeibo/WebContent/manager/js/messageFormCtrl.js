angular.module('messageForm',[])
	.controller('messageFormCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.messageId = $stateParams.id;
		$scope.operator = $stateParams.operator;//值只有add或者edit两种
		$scope.message = {};
		
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
				$scope.findMessageById();
			}
		}
		
		//新增用户方法
		$scope.submit = function() {
			
		}
		
		$scope.findMessageById = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
			$http.post(url,{
				method:"findMessageById", 
				messageId:$scope.messageId, 
			})
			.success(function(data) {
				$scope.message = data;
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.init();
	}])