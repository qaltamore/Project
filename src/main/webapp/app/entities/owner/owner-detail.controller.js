(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('OwnerDetailController', OwnerDetailController);

    OwnerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Owner'];

    function OwnerDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Owner) {
        var vm = this;

        vm.owner = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:ownerUpdate', function(event, result) {
            vm.owner = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
