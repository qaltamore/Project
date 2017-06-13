(function() {
    'use strict';

    angular
        .module('jHipsterAppliApp')
        .controller('DrawDetailsController', DrawDetailsController);

    DrawDetailsController.$inject = ['$scope', '$rootScope', '$state', '$stateParams', 'previousState', 'entity', 'QuestionAnswerPlayer', 'Player', 'QuestionAnswer'];

    function DrawDetailsController($scope, $rootScope, $state, $stateParams, previousState, entity, QuestionAnswerPlayer, Player, QuestionAnswer) {
        var vm = this;

        vm.questionAnswerPlayer = entity;
        vm.idCardInHand = null;
        vm.previousState = previousState.name;
        vm.useCard = useCard;
        
        loadIdCardInHand();

        var unsubscribe = $rootScope.$on('jHipsterAppliApp:questionAnswerPlayerUpdate', function(event, result) {
            vm.questionAnswerPlayer = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        function loadIdCardInHand() {
        	QuestionAnswerPlayer.query(function(result) {
        		for(var i = 0; i < result.length; i++) {
        			if(result[i].questionAnswer.id == vm.questionAnswerPlayer.id) {
        				vm.idCardInHand = result[i].id;
        			}
        		}
        	});
        }
        
        function useCard() {
        	//console.log("Id : "+vm.idCardInHand);
        	QuestionAnswerPlayer.delete({id: vm.idCardInHand},
                    function () {
                        $state.go('^');
                    },
                    function () {
                    	$state.go('^');
                    });
        }
    }
})();
