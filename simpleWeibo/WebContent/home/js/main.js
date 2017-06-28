require.config({
	paths: {
		'angular': '../../js/angular.min',
		'angularAMD': '../../js/angularAMD.min',
		'angular-ui-router': '../../js/angular-ui-router',
		'ngload': '../../js/ngload.min',
		'ng-file-upload': '../../js/ng-file-upload.min'
	},
	shim: {
		'angular': {exports: "angular" },
		'angularAMD': ["angular"],
		'angular-ui-router': ["angular"],
		'ngload': ["angularAMD"],
		'ng-file-upload': ['angular']
	},
	
	deps: ['app']
})

