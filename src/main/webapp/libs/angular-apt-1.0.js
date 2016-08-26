angular.module("apt", [])
.value("serverEndpoint", "http://angular-apt.azurewebsites.net/api/")
.service("apt", ["$http", "serverEndpoint", function apt($http, serverEndpoint) {
    this.getCoverabilityGraph = function(pn) {
        return $http.post(serverEndpoint + "coverabilityGraph", {pn: pn})
    }

    this.getSynthesizedNet = function(lts, options) {
        return $http.post(serverEndpoint + "synthesize", {lts: lts, options: options})
    }

    this.pnAnalysis = function(pn) {
        return $http.post(serverEndpoint + "pnAnalysis", {pn: pn})
    }
}]);