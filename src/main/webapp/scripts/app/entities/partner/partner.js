'use strict';

angular.module('gtmpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partner', {
                parent: 'entity',
                url: '/partners',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Partners'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partner/partners.html',
                        controller: 'PartnerController'
                    }
                },
                resolve: {
                }
            })
            .state('partner.detail', {
                parent: 'entity',
                url: '/partner/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Partner'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partner/partner-detail.html',
                        controller: 'PartnerDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Partner', function($stateParams, Partner) {
                        return Partner.get({id : $stateParams.id});
                    }]
                }
            })
            .state('partner.new', {
                parent: 'partner',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner/partner-dialog.html',
                        controller: 'PartnerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('partner', null, { reload: true });
                    }, function() {
                        $state.go('partner');
                    })
                }]
            })
            .state('partner.edit', {
                parent: 'partner',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner/partner-dialog.html',
                        controller: 'PartnerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Partner', function(Partner) {
                                return Partner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('partner.delete', {
                parent: 'partner',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner/partner-delete-dialog.html',
                        controller: 'PartnerDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Partner', function(Partner) {
                                return Partner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
