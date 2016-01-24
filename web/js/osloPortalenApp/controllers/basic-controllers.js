var osloPortalenApp = angular.module('osloPortalenApp', []);

osloPortalenApp.constant('appSettings', {
	db : 'http://127.0.0.1:5984/osloportalen'
});

osloPortalenApp.controller('InstagramCtrl', function($scope) {
	$scope.images = [ {
		'name' : 'Oslo By night',
		'url' : 'http:httphttphttp://'
	}, {
		'name' : 'Motorola XOOM™ with Wi-Fi',
		'url' : 'The Next, Next Generation tablet.'
	}, {
		'name' : 'MOTOROLA XOOM™',
		'url' : 'The Next, Next Generation tablet.'
	} ];
});

osloPortalenApp.controller('NewsContentCtrl', function($scope, $http) {
	$http.get('http://localhost:8080/news-feeder/news').success(
			function(data, status, headers, config) {
				$scope.news = data;
				console.log(data.)
			}).error(function(data, status, headers, config) {
		// log error
	});
//	$scope.news = [ {
//		'title' : 'Nyhet 1',
//		'ingress' : 'Vi må skape et bedre miljø for flere',
//		'url' : 'http://www.db.no'
//	} ];
});
