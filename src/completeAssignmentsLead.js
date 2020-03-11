const assignments = [
    {
        "assignmentId": 251,
        "assignmentNumber": null,
        "asurite": "aarunku3",
        "assignedAsurite": "ravvaru",
        "semesterName": "spring20",
        "isComplete": false,
        "evalType": "l2t"
    },
    {
        "assignmentId": 252,
        "assignmentNumber": null,
        "asurite": "aarunku3",
        "assignedAsurite": "mkmuir",
        "semesterName": "spring20",
        "isComplete": false,
        "evalType": "l2t"
    },
    {
        "assignmentId": 254,
        "assignmentNumber": null,
        "asurite": "aarunku3",
        "assignedAsurite": "kkim124",
        "semesterName": "spring20",
        "isComplete": false,
        "evalType": "l2t"
    },
    {
        "assignmentId": 256,
        "assignmentNumber": null,
        "asurite": "aarunku3",
        "assignedAsurite": "pmalhot3",
        "semesterName": "spring20",
        "isComplete": false,
        "evalType": "l2t"
    },
    {
        "assignmentId": 258,
        "assignmentNumber": null,
        "asurite": "aarunku3",
        "assignedAsurite": "kadep",
        "semesterName": "spring20",
        "isComplete": false,
        "evalType": "l2t"
    },
    {
        "assignmentId": 260,
        "assignmentNumber": null,
        "asurite": "aarunku3",
        "assignedAsurite": "kmmalako",
        "semesterName": "spring20",
        "isComplete": false,
        "evalType": "l2t"
    }
]

$(document).ready(() => {
    // let completeEvals = [];
    // let names = [];

    const tableData = assignments.map(eval => [
        eval.assignedAsurite, null
    ]);
    console.log("Inside then")
    $('#assignmentsTable').DataTable({
        stripe: true,
        paging: false,
        searching: false,
        info: false,
        data: tableData,
        columns: [
            { title: "Tutor Name" },
            {
                title: "Actions",
                render: () => {
                    //Use data variable to pass the evaluation parameters to the evaluations page.
                    let evalButton =
                        `<button
                    class="btn btn-primary"
                    style="border-color: #8C1D40;"
                    onclick="location.href = './evaluations.html';" id="myButton">
                    Evaluate
                    </button>`;
                    return evalButton;
                }
            }
        ]
    })
});