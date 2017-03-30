(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('ThemeDetailController', ThemeDetailController);

    ThemeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Theme'];

    function ThemeDetailController($scope, $rootScope, $stateParams, previousState, entity, Theme) {
        var vm = this;

        vm.theme = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:themeUpdate', function(event, result) {
            vm.theme = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
