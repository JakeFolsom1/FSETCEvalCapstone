// const assignments = [
//     {
//         "assignmentId": 251,
//         "assignmentNumber": null,
//         "asurite": "aarunku3",
//         "assignedAsurite": "ravvaru",
//         "semesterName": "spring20",
//         "isComplete": false,
//         "evalType": "l2t"
//     },
//     {
//         "assignmentId": 252,
//         "assignmentNumber": null,
//         "asurite": "aarunku3",
//         "assignedAsurite": "mkmuir",
//         "semesterName": "spring20",
//         "isComplete": false,
//         "evalType": "l2t"
//     },
//     {
//         "assignmentId": 254,
//         "assignmentNumber": null,
//         "asurite": "aarunku3",
//         "assignedAsurite": "kkim124",
//         "semesterName": "spring20",
//         "isComplete": false,
//         "evalType": "l2t"
//     },
//     {
//         "assignmentId": 256,
//         "assignmentNumber": null,
//         "asurite": "aarunku3",
//         "assignedAsurite": "pmalhot3",
//         "semesterName": "spring20",
//         "isComplete": false,
//         "evalType": "l2t"
//     },
//     {
//         "assignmentId": 258,
//         "assignmentNumber": null,
//         "asurite": "aarunku3",
//         "assignedAsurite": "kadep",
//         "semesterName": "spring20",
//         "isComplete": false,
//         "evalType": "l2t"
//     },
//     {
//         "assignmentId": 260,
//         "assignmentNumber": null,
//         "asurite": "aarunku3",
//         "assignedAsurite": "kmmalako",
//         "semesterName": "spring20",
//         "isComplete": false,
//         "evalType": "l2t"
//     }
// ]

$(document).ready(() => {
    let assignments = [], completedEvals = [], names = {}, teamMembers = [], leadAsurite = "aarunku3";
    $.when(
        $.getJSON(apiUrl + `/assignments/active/${leadAsurite}`,
            function (assignmentsJson) {
                assignments = assignmentsJson;
            }
        ),
        $.getJSON(apiUrl + `/teamMembers/${leadAsurite}`,
            function (teamMemberJson) {
                teamMembers = teamMemberJson;
            }
        ),
        $.getJSON(apiUrl + "/completedEvaluations",
            function (completedEvalJson) {
                completedEvals = completedEvalJson;
            }
        ),
        $.getJSON(apiUrl + "/staff/names",
            function (namesJson) {
                names = namesJson
            }
        )
    ).then(() => {
        const tableData = assignments.map(eval => [
            names[eval.assignedAsurite], null
        ]);
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

        completedEvals = completedEvals.filter(eval => teamMembers.includes(eval.evaluatee));
        const completedEvalsTableData = completedEvals.map(eval => [eval.semester, eval.evaluator, eval.evaluatee, null])
        $('#completedEvaluationTable').DataTable({
            stripe: true,
            paging: false,
            searching: false,
            info: false,
            data: completedEvalsTableData,
            columns: [
                { title: "Semester" },
                { title: "Evaluator" },
                { title: "Evaluatee" },
                {
                    title: "Actions",
                    render: () => {
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let viewButton =
                            `<button
                        class="btn btn-primary"
                        style="border-color: #8C1D40;"
                        onclick="location.href = './evaluations.html';" id="myButton">
                        View
                        </button>`;
                        return viewButton;
                    }
                }
            ]
        })
    })

});