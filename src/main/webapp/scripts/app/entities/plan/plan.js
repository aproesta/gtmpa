'use strict';

angular.module('gtmpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('plan', {
                parent: 'entity',
                url: '/plans',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Plans'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plan/plans.html',
                        controller: 'PlanController'
                    }
                },
                resolve: {
                }
            })
            .state('plan.detail', {
                parent: 'entity',
                url: '/plan/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Plan'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plan/plan-detail.html',
                        controller: 'PlanDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Plan', function($stateParams, Plan) {
                        return Plan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plan.calendar', {
                parent: 'plan',
                url: '/{id}/calendar',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Plan'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/plan/plan-calendar.html',
                        controller: 'PlanCalendarController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Plan', function($stateParams, Plan) {
                        return Plan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plan.milestone', {
                parent: 'plan',
                url: '/{id}/milstone',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Plan Milestone'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/planmilestone/planmilestone-dialog1.html',
                        controller: 'PlanmilestoneDialogController1',
                        size: 'lg',
                        resolve: {

                          /*  entity: ['Plan', function(Plan) {
                                return Plan.get({id : $stateParams.id});
                            }]*/
                        
                            entity: function () {
                                return {
                                    milestoneDate: null,
                                    id: null,
                                    plan: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('plan', null, { reload: true });
                    }, function() {
                        $state.go('plan');
                    })
                }],
                resolve: {
                    entity: ['$stateParams', 'Plan', function($stateParams, Plan) {
                        return Plan.get({id : $stateParams.id});
                    }]
                }
            })
            .state('plan.new', {
                parent: 'plan',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plan/plan-dialog.html',
                        controller: 'PlanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    solutionName: null,
                                    agreedGTMDate: null,
                                    revenueCommitment: null,
                                    dealsRequired: null,
                                    proposalDate: null,
                                    industrySegment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('plan', null, { reload: true });
                    }, function() {
                        $state.go('plan');
                    })
                }]
            })
            .state('plan.edit', {
                parent: 'plan',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plan/plan-dialog.html',
                        controller: 'PlanDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Plan', function(Plan) {
                                return Plan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('plan.delete', {
                parent: 'plan',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/plan/plan-delete-dialog.html',
                        controller: 'PlanDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Plan', function(Plan) {
                                return Plan.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('plan', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
