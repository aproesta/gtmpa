'use strict';

angular.module('gtmpaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partneruserlink', {
                parent: 'entity',
                url: '/partneruserlinks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Partneruserlinks'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partneruserlink/partneruserlinks.html',
                        controller: 'PartneruserlinkController'
                    }
                },
                resolve: {
                }
            })
            .state('partneruserlink.detail', {
                parent: 'entity',
                url: '/partneruserlink/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Partneruserlink'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partneruserlink/partneruserlink-detail.html',
                        controller: 'PartneruserlinkDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Partneruserlink', function($stateParams, Partneruserlink) {
                        return Partneruserlink.get({id : $stateParams.id});
                    }]
                }
            })
            .state('partneruserlink.new', {
                parent: 'partneruserlink',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partneruserlink/partneruserlink-dialog.html',
                        controller: 'PartneruserlinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('partneruserlink', null, { reload: true });
                    }, function() {
                        $state.go('partneruserlink');
                    })
                }]
            })
            .state('partneruserlink.edit', {
                parent: 'partneruserlink',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partneruserlink/partneruserlink-dialog.html',
                        controller: 'PartneruserlinkDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Partneruserlink', function(Partneruserlink) {
                                return Partneruserlink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partneruserlink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('partneruserlink.delete', {
                parent: 'partneruserlink',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partneruserlink/partneruserlink-delete-dialog.html',
                        controller: 'PartneruserlinkDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Partneruserlink', function(Partneruserlink) {
                                return Partneruserlink.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partneruserlink', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
