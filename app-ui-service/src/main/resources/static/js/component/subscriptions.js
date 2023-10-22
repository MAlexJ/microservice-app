'use strict';

app.component('subscriptions', {
    controller: function (RestAPI, $transitions, $scope) {

        // REST api call
        restApiGet()

        function restApiGet(pageNumber) {
            // let path = "/logs";
            // if (pageNumber) {
            //     path = path + "?page=" + pageNumber;
            // }
            // RestAPI.get(path)
            //     .then(function (response) {
            //         let responseData = response.data;
            //         $scope.logs = responseData;
            //         if (responseData) {
            //             let responseData = response.data;
            //             $scope.logs = responseData;
            //             $scope.totalEvents = responseData.totalPages - 1;
            //             if (pageNumber) {
            //                 $scope.pagination = calculatePagination(pageNumber);
            //             } else {
            //                 $scope.pagination = initDefaultPagination();
            //             }
            //         }
            //     }, function (reason) {
            //         $scope.error = reason.data
            //         alert(reason.data)
            //     });
        }


    }, template: `
<div class="container">
   <div class="row justify-content-md-center" style="display: block">
      <div class="row" style="padding-top: 40px;padding-bottom: 20px;margin-right: 0px;padding-right: 0px;">
         <ul class="nav nav-tabs" style="cursor: pointer">
            <li class="nav-item">
               <a class="nav-link active" aria-current="page" style="color: black;">Events</a>
            </li>
            <li class="nav-item">
               <a class="nav-link" style="color: black;">Errors</a>
            </li>
         </ul>
      </div>
      <div class="row" style="padding-bottom: 40px;margin-right: 0px;padding-right: 0px;">
         <div class="col-lg-12"></div>
         <div class="col-lg-12 table-responsive">
            <table class="table">
               <thead class="table-dark">
                  <tr>
                     <th scope="col">#</th>
                     <th scope="col">Event</th>
                     <th scope="col">Message</th>
                     <th scope="col">Scheduler</th>
                     <th scope="col">Date</th>
                  </tr>
               </thead>
               <tbody>
                  <tr ng-repeat="log in logs.content track by $index" >
                     <th scope="row">{{log.id}}</th>
                     <td>{{log.event}}</td>
                     <td>{{log.message}}</td>
                     <td>{{log.scheduler}}</td>
                     <td>{{log.created}}</td>
                  </tr>
               </tbody>
            </table>
         </div>
         <div class="col-lg-12" style="padding-bottom: 60px;position: fixed;right: 0;left: 0;bottom: 0;"></div>
         <div class="col-lg-12">
            <nav aria-label="Page navigation example">
               <ul class="pagination justify-content-center">
                  <li class="page-item disabled">
                     <a class="page-link" href="#" tabindex="-1" aria-disabled="true"> < </a>
                  </li>
                  <li class="page-item" 
                     ng-repeat="pageNumber in pagination" 
                     style="cursor: pointer"
                     ng-class="{ active : pageNumber == activePaginationPosition}">
                     <a id="page-number-id-{{ pageNumber }}"
                        ng-click="getLogData(pageNumber)"
                        class="page-link">
                     {{ pageNumber }}
                     </a>
                  </li>
                  <li class="page-item">
                     <a class="page-link" href="#"> > </a>
                  </li>
               </ul>
            </nav>
         </div>
      </div>
   </div>
</div>
`
});
