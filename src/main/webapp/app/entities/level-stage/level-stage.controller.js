(function() {
    'use strict';

    angular
        .module('hack47App')
        .controller('LevelStageController', LevelStageController);

    LevelStageController.$inject = ['$scope', '$state', 'LevelStage'];

    function LevelStageController ($scope, $state, LevelStage) {
        var vm = this;

        vm.levelStages = [];

        loadAll();

        function loadAll() {
            LevelStage.query(function(result) {
                vm.levelStages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
