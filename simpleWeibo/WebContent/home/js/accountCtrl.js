angular.module('account',[])
	.controller('accountCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		//通过$stateParams获取url参数 id   
		$scope.userId = $stateParams.id;
		
		$scope.localUser = locals.getObject('localUser');
		
		//定义元素
		$scope.user = {};
		
		$scope.go = function(key) {
			$state.go(key, {id:$scope.localUser.userId});
		}
		
		$scope.logout = function() {
			locals.clear("localUser");
			$state.go("login");
		}
		
		//初始化方法
		$scope.init = function () {
			$scope.findUserById();
		}
		
		//显示模态框
		$scope.show=function(){
	        $("#crewmodal").modal('show');
	    };
	    
	    //隐藏模态框
	    $scope.hide=function() {
	    	$("#crewmodal").modal('hide');
	    }
		
	    //改变头像
	    $scope.changeTouxiang = function() {
	    	if($scope.result) {
	    		$scope.user.touxiang = $scope.result;
	    	}
	    	$scope.hide();
	    }
	    
	    $scope.fileChanged = function(e) {			
			
			var files = e.target.files;
		
     		var fileReader = new FileReader();
			fileReader.readAsDataURL(files[0]);		
			
			fileReader.onload = function(e) {
				$scope.imgSrc = this.result;
				$scope.$apply();
			};
			
		}		
	   
		$scope.clear = function() {
			 $scope.imageCropStep = 1;
			 delete $scope.imgSrc;
			 delete $scope.result;
			 delete $scope.resultBlob;
		};
	    
		$scope.submit = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"updateUser", 
				user:JSON.stringify($scope.user), 
			})
			.success(function(data) {
				$state.go("personalPage",{id:$scope.userId});
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.parseTouxiang = function(user) {
			if(user.touxiang) {
				user.touxiang = "data:image/jpg;base64," + user.touxiang;
			}
		} 
		
		$scope.findUserById = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"findUserById", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.user = data;
				$scope.parseTouxiang($scope.user);
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.init();
	}])