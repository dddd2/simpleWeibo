angular.module('messageList',[])
	.controller('messageListCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		$scope.users = [];
		
		$scope.update = function(id) {
			$state.go('mainHome.list.userForm',{operator:'edit',id:id})
		}
		
		$scope.init = function() {
			//查看全部人方法还没有写，暂时先用这个
			$scope.findFansByUserId();
		}
		
		//查看全部人方法还没有写，暂时先用这个
		$scope.findFansByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				currentPage:1,
				pageSize:3,
				method:"findFansByUserId", 
				userId:11, 
			})
			.success(function(data) {
				$scope.users = data;
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.init();
	}])