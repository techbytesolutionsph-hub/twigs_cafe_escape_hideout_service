// Call the dataTables jQuery plugin
$(document).ready(function() {
   $('#userTable').DataTable({
    pageLength: 5,
    lengthMenu: [
      [5, 10, 25, 50, 100],
      [5, 10, 25, 50, 100]
    ],
    order: [[0, 'desc']],
    language: {
      emptyTable: `
            <div class="icon-empty-state">
                <i class="fa-solid fa-file-circle-exclamation"></i>
                <div>No user registration records found</div>
            </div>
        `
    }
  });

  // $('#employeeTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No employee registration records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#initialAssessmentTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No initial assessment records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#regularAssessmentTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No therapy session records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#upgradingProgramTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No upgrading program records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#neurodevAssessmentTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No Neurodevelopmental assessment records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  $('#candidateTable').DataTable({
    pageLength: 5,
    lengthMenu: [
      [5, 10, 25, 50, 100],
      [5, 10, 25, 50, 100]
    ],
    order: [[0, 'desc']],
    language: {
      emptyTable: `
            <div class="icon-empty-state">
                <i class="fa-solid fa-file-circle-exclamation"></i>
                <div>No candidate/applicant records found</div>
            </div>
        `
    }
  });
  //
  //
  // $('#pendingInterviewTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No pending interview records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#scheduledInterviewTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No scheduled interview records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#interviewRecordsTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[2, 'desc']],
  //   columnDefs: [
  //     { targets: 4, orderable: false }
  //   ],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No interview records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#onboardingTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No onboarding records found</div>
  //           </div>
  //       `
  //   }
  // });
  //
  // $('#jobOfferTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: `
  //           <div class="icon-empty-state">
  //               <i class="fa-solid fa-file-circle-exclamation"></i>
  //               <div>No job offer records found</div>
  //           </div>
  //       `
  //   }
  // });

  // $('#pendingPayoutTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: "No pending PayMongo payment records found"
  //   }
  // });
  //
  // $('#paidPayoutTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: "No paid PayMongo payment records found"
  //   }
  // });
  //
  // $('#cancelPayoutTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: "No cancel PayMongo payout records found"
  //   }
  // });
  //
  // $('#cashSettlementTable').DataTable({
  //   order: [[0, 'desc']],
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   language: {
  //     emptyTable: "No cash settlement records found"
  //   }
  // });
  //
  // $('#cashUnsettledTable').DataTable({
  //   order: [[0, 'desc']],
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   language: {
  //     emptyTable: "No unsettled cash records found"
  //   }
  // });
  //
  // $('#cashSettledTable').DataTable({
  //   order: [[0, 'desc']],
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   language: {
  //     emptyTable: "No settled cash records found"
  //   }
  // });
  //
  // $('#allSettlementsTable').DataTable({
  //   order: [[0, 'desc']],
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   language: {
  //     emptyTable: "No settlement records found"
  //   }
  // });
  //
  // $('#cashSettlementsTable').DataTable({
  //   order: [[0, 'desc']],
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   language: {
  //     emptyTable: "No cash settlement records found"
  //   }
  // });
  //
  // $('#payMongoSettlementsTable').DataTable({
  //   order: [[0, 'desc']],
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   language: {
  //     emptyTable: "No PayMongo settlement records found"
  //   }
  // });
  //
  // $('#payoutDetailsTable').DataTable({
  //   pageLength: 5,
  //   paging: true,
  //   searching: false,   // Hide Search box
  //   lengthChange: false, // Hide "Show entries"
  //   info: true,
  //   ordering: true,
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: "No PayMongo payment records found"
  //   }
  // });
  //
  // $('#couponsTable').DataTable({
  //   pageLength: 5,
  //   lengthMenu: [
  //     [5, 10, 25, 50, 100],
  //     [5, 10, 25, 50, 100]
  //   ],
  //   order: [[0, 'desc']],
  //   language: {
  //     emptyTable: "No coupon records found"
  //   }
  // });
});
