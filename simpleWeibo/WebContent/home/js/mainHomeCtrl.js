angular.module('mainHome',[])
	.controller('mainHomeCtrl', ['$scope', '$state', '$stateParams', '$http', 'locals',
		function($scope, $state, $stateParams, $http, locals) {
		
		//通过$stateParams获取url参数 id   
		$scope.userId = $stateParams.id;
		
		//定义元素
		$scope.user = {};
		$scope.localUser = {};
		$scope.fans = [];
		$scope.focusPeoples = [];
		$scope.messages = [];
		$scope.pageSize = 20;
		$scope.currentPage = 1;
		$scope.puserName;
		$scope.who = "";
		$scope.isFocus;
		
		//初始化方法
		$scope.init = function () {
			$scope.findUserById();
			//从缓存中读取localUser
			$scope.localUser = locals.getObject("localUser");
			$scope.findFansByUserId();
			$scope.findFocusPeoplesByUserId();
			$scope.findMessagesByUserId();
			$scope.whoRU();
			if($scope.userId != $scope.localUser.userId) {
				$scope.isFocus();
			}
		}
		
		$scope.logout = function() {
			locals.clear("localUser");
			$state.go("login");
		}
		
		$scope.go = function(key) {
			$state.go(key, {id:$scope.localUser.userId});
		}
		
		$scope.go1 = function(key, id) {
			$state.go(key, {id: id});
		}
		
		$scope.goUserList = function(type) {
			$state.go("userList",{type:type,id:$scope.localUser.userId});
		}
		
		$scope.goUserHome = function(id) {
			$state.go('mainHome',{id:id});
		}
		
		$scope.goMyHtml = function() {
			$state.go("personalPage",{id:$scope.localUser.userId});
		}
		
		//判断当前登录人是不是自己
		$scope.whoRU = function() {
			if($scope.userId == $scope.localUser.userId) {
				$scope.who = "我";
			} else {
				$scope.who = "他";
			}
		}
		
		//刷新页面
		$scope.reloadRoute = function() {
		    $window.location.reload();
		};
/*===============================关注===============================*/
		$scope.focusOn = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"focusOn", 
				fansId:$scope.localUser.userId,
				focusPeopleId:$scope.userId
			})
			.success(function(data) {
				$scope.isFocus = true;
				$scope.user.fansNum++;
			})
			.error(function(error){
//				alert(error);
			});
		}
		
/*================================取关================================*/
		$scope.takeOf = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
				method:"takeOf", 
				fansId:$scope.localUser.userId,
				focusPeopleId:$scope.userId
			})
			.success(function(data) {
				$scope.isFocus = false;
				$scope.user.fansNum--;
			})
			.error(function(error){
//				alert(error);
			});
		}
/*===================================评论==============================*/
		$scope.commentTo = function(message, name) {
			message.comment = '回复 ' + name + ':';
		}
		
		$scope.dealComment = function(comment) {
			var index = comment.indexOf(":");
			$scope.puserName = comment.substring(1,index);
		}
		
		$scope.submitComment = function (message) {
			if(message.comment.charAt(0) == '@') {
				$scope.dealComment(message.comment);
			}
			var url = "http://localhost:8080/simpleWeibo/servlet/CommentServlet"
			$http.post(url,{
				method:"submitComment", 
				userId:$scope.userId,
				comment:message.comment,
				messageId:message.messageId,
				puserName:$scope.puserName})
			.success(function(data) {
				if(data == "true") {
					$scope.reloadRoute();
				} else {
					alert("error");
				}
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findMessagesByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/MessageServlet"
			$http.post(url,
			{	
//				currentPage:1,
//				pageSize:1,
				method:"findMessagesByUserId", 
				userId:$scope.userId 
			})
			.success(function(data) {
				$scope.messages = data;
				$scope.dealMessagesInit($scope.messages);
				$scope.dealMessagesPic($scope.messages);
				$scope.dealMessagesBiaoqing($scope.messages);
				$scope.parseTouxiangForMessageList($scope.messages);
			})
			.error(function(error){
				alert(error);
			});
		}
/*====================================处理@好友标签================================*/		
$scope.dealMessagesInit = function(messages) {
	angular.forEach(messages, function(message){
		if(message.text) {
			var arrs = message.text.split(" ");
			message.text = "";
			angular.forEach(arrs, function(arr){
				if(arr.charAt(0) == "@") {
					arr = '<a>' + arr +'</a>';
				}
				message.text += arr;
			})
		}
	})
}
		
/*==================================处理图片======================================*/		
		$scope.dealMessagesPic = function(messages) {
			angular.forEach(messages, function(message){
				if(message.imgs) {
					var arrs = message.imgs.split("$$$");
					message.imgs=[];
					angular.forEach(arrs, function(arr){
						arr = "data:image/jpg;base64," + arr;
						message.imgs.push(arr);
					})
				}
			})
		} 
		
/*=================================处理表情======================================*/
		$scope.dealMessagesBiaoqing = function(messages) {
			angular.forEach(messages, function(message) {
				if(message.text) {
					message.text = message.text.replace(/imgng-/g, "img ng-");
				}
			})
		}
/*==================================头像=======================================*/
		$scope.parseTouxiangForList = function(users) {
			angular.forEach(users, function(user) {
				if(user.touxiang) {
					user.touxiang = "data:image/jpg;base64," + user.touxiang;
				}
			})
		} 
		
		$scope.parseTouxiang = function(user) {
			if(user.touxiang) {
				user.touxiang = "data:image/jpg;base64," + user.touxiang;
			}
		} 
		
		$scope.parseTouxiangForMessageList = function(messages) {
			angular.forEach(messages, function(message) {
				if(message.user.touxiang) {
					message.user.touxiang = "data:image/jpg;base64," + message.user.touxiang;
				}
				if(message.comments.length > 0) {
					angular.forEach(message.comments, function(comment){
						if(comment.user.touxiang) {
							comment.user.touxiang = "data:image/jpg;base64," + comment.user.touxiang;
						}
					})
				}
			})
		}
/*=========================================初始化*/		
		$scope.findFansByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{
//				currentPage:1,
//				pageSize:3,
				method:"findFansByUserId", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.fans = data;
				$scope.parseTouxiangForList($scope.fans);
			})
			.error(function(error){
				alert(error);
			});
		}
		
		$scope.findFocusPeoplesByUserId = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
			$http.post(url,{	
//				currentPage:1,
//				pageSize:3,
				method:"findFocusPeoplesByUserId", 
				userId:$scope.userId, 
			})
			.success(function(data) {
				$scope.focusPeoples = data;
				$scope.parseTouxiangForList($scope.focusPeoples);
			})
			.error(function(error){
				alert(error);
			});
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
		
		$scope.isFocus = function() {
			var url = "http://localhost:8080/simpleWeibo/servlet/UserServlet"
				$http.post(url,{
					method:"isFocus", 
					fansId:$scope.localUser.userId,
					focusPeopleId:$scope.userId
				})
				.success(function(data) {
					if(data == "true") {
						$scope.isFocus = true;
					} else {
						$scope.isFocus = false;
					}
				})
				.error(function(error){
//					alert(error);
				});
		}
		
		$scope.init();		
	}])