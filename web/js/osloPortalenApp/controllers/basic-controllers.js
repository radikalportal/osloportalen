var osloPortalenApp = angular.module('osloPortalenApp', []);

osloPortalenApp.controller('InstagramCtrl', function ($scope) {
  $scope.images = [
    {'name': 'Oslo By night',
     'url': 'http:httphttphttp://'},
    {'name': 'Motorola XOOM™ with Wi-Fi',
     'url': 'The Next, Next Generation tablet.'},
    {'name': 'MOTOROLA XOOM™',
     'url': 'The Next, Next Generation tablet.'}
  ];
});
