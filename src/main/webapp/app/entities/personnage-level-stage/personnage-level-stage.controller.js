(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('PersonnageLevelStageController', PersonnageLevelStageController);

    PersonnageLevelStageController.$inject = ['$scope', '$state', 'PersonnageLevelStage'];

    function PersonnageLevelStageController ($scope, $state, PersonnageLevelStage) {
        var vm = this;

        vm.personnageLevelStages = [];

        loadAll();

        function loadAll() {
            PersonnageLevelStage.query(function(result) {
                vm.personnageLevelStages = result;
                vm.searchQuery = null;
            });
        }
    }
})();
