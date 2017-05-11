(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('SideMenuController', SideMenuController);

    SideMenuController.$inject = ['$state'];

    function SideMenuController ($state) {
        var vm = this;
    }
})();
