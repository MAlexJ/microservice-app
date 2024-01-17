'use strict';

app.component('subscriptions', {
    controller: function (RestAPI, $transitions, $scope, $location) {

        // REST api call
        restApiGet()

        function restApiGet(pageNumber) {
        }

        // $scope.searchBills = {}
        // $scope.searchBillNumber = ''
        // $scope.user_username = ''
        // $scope.user_email = ''

        // $scope.findBillByNumber = function () {
        //     if ($scope.searchBillNumber === '') {
        //         alert("Please enter bill number to search")
        //         return;
        //     }
        //     let path = "/bills/" + $scope.searchBillNumber;
        //     $scope.searchBills = {}
        //     $scope.searchBillNumber = ''
        //     RestAPI.get(path)
        //         .then(function (response) {
        //             $scope.searchBills = JSON.parse(JSON.stringify(response.data));
        //         }, function (reason) {
        //             $scope.error = reason.data
        //             alert(reason.data)
        //         });
        // }
        //
        // $scope.subscribe = function () {
        //     let path = "/bills"
        //     let jsRequest = JSON.parse(JSON.stringify({
        //         user: {
        //             username: $scope.user_username,
        //             email: $scope.user_email
        //         },
        //         active: true,
        //         bills: $scope.searchBills.bills
        //     }))
        //     RestAPI.post(path, jsRequest)
        //         .then(function (response) {
        //             $scope.searchBills = {}
        //         }, function (reason) {
        //             $scope.error = reason.data
        //             alert(reason.data)
        //         });
        // }
        //
        // $scope.initModalState = function () {
        //     $scope.searchBillNumber = ''
        //     $scope.searchBills = {}
        // }


    }, template: `
<div class="container">
   <div class="row" style="padding-top: 40px;padding-bottom: 40px">
      <div class="row justify-content-md-center" style="display: block padding-top: 40px;padding-bottom: 40px">
         <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
               <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-home" type="button" role="tab" aria-controls="nav-home" aria-selected="true">Home</button>
               <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button" role="tab" aria-controls="nav-profile" aria-selected="false">Profile</button>
               <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-contact" type="button" role="tab" aria-controls="nav-contact" aria-selected="false">Contact</button>
            </div>
         </nav>
         <div class="tab-content" id="nav-tabContent" style="padding-top: 10px;">
            <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab" >
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
            <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">..profile..</div>
            <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">..contact..</div>
         </div>
      </div>
   </div>
</div>
   <!--      <div class="col-12">-->
<!--         &lt;!&ndash; Button trigger modal &ndash;&gt;-->
<!--         <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">-->
<!--         Create new-->
<!--         </button>-->
<!--         &lt;!&ndash; Modal &ndash;&gt;-->
<!--         <div class="modal fade" -->
<!--            id="staticBackdrop" -->
<!--            data-bs-backdrop="static" -->
<!--            data-bs-keyboard="false" -->
<!--            tabindex="-1" -->
<!--            aria-labelledby="staticBackdropLabel" -->
<!--            aria-hidden="true">-->
<!--            <div class="modal-dialog modal-xl">-->
<!--               <div class="modal-content">-->
<!--                  <div class="modal-header">-->
<!--                     <h5 class="modal-title" id="staticBackdropLabel">Create new subscription</h5>-->
<!--                     <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
<!--                  </div>-->
<!--                  <div class="modal-body">-->
<!--                     <form>-->
<!--                        <div class="mb-3">-->
<!--                           <label for="recipient-name" class="col-form-label">Bill number:</label>-->
<!--                           <input type="text"-->
<!--                              ng-model="searchBillNumber" -->
<!--                              class="form-control" -->
<!--                              id="bill-number">-->
<!--                        </div>-->
<!--                        <div class="mb-3">-->
<!--                           <button class="btn btn-primary form-control" -->
<!--                              id="find-bill-btn"-->
<!--                              ng-click="findBillByNumber()">Search</button>-->
<!--                        </div>-->
<!--                        <div class="mb-3">-->
<!--                           <div ng-repeat="item in searchBills.bills track by $index">-->
<!--                              <div>-->
<!--                                 <input type="checkbox">-->
<!--                                 <div>number: {{item.number}} </div>-->
<!--                                 <div>name: {{item.name}} </div>-->
<!--                                 <div>Registration date: {{item.registrationDate}}</div>-->
<!--                              </div>-->
<!--                           </div>-->
<!--                           <div ng-show="searchBills.bills">-->
<!--                              <div class="mb-3">-->
<!--                                 <label for="recipient-name" class="col-form-label">Username:</label>-->
<!--                                 <input type="text"                                    -->
<!--                                    ng-model="user_username" -->
<!--                                    class="form-control" -->
<!--                                    id="bill-number">-->
<!--                              </div>-->
<!--                              <div class="mb-3">-->
<!--                                 <label for="recipient-name" class="col-form-label">Email:</label>-->
<!--                                 <input type="text"-->
<!--                                    ng-model="user_email" -->
<!--                                    class="form-control" -->
<!--                                    id="bill-number">-->
<!--                              </div>-->
<!--                           </div>-->
<!--                        </div>-->
<!--                        <div class="mb-3">-->
<!--                           <button type="button" -->
<!--                              class="btn btn-primary" -->
<!--                              data-bs-dismiss="modal"-->
<!--                              ng-click="subscribe()">Subscribe</button>-->
<!--                        </div>-->
<!--                     </form>-->
<!--                  </div>-->
<!--                  <div class="modal-footer">-->
<!--                     <button type="button" -->
<!--                        class="btn btn-secondary" -->
<!--                        data-bs-dismiss="modal"-->
<!--                        ng-click="initModalState()">Close</button>-->
<!--                  </div>-->
<!--               </div>-->
<!--            </div>-->
<!--         </div>-->
<!--      </div>-->
<!--</div>-->
`
});
