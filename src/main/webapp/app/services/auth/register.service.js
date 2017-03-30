(function () {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
