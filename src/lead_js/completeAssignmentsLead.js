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
let userAssignments = [];
let names = {};
$(document).ready(() => {
    let completedEvals = [], teamMembers = [], leadAsurite = "aarunku3";
    $.when(
        $.getJSON(apiUrl + `/assignments/active/${leadAsurite}`,
            function (assignmentsJson) {
                userAssignments = assignmentsJson;
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
        const tableData = userAssignments.map(eval => [
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
                    render: (data, _type, row) => {
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let evalButton =
                            `<button
                        class="btn btn-primary"
                        style="border-color: #8C1D40;"
                        onclick="buildEvaluation('${row[0]}', userAssignments)" id="myButton">
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
                    render: (data, _type, row) => {
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

const buildEvaluation = (evaluatee, assignments) => {
    let asurite = Object.keys(names).find(key => names[key] == evaluatee)
    const assignmentId = assignments.find(assignment => assignment.assignedAsurite == asurite).assignmentId

    let evalQuestions = [];
    $('#questionsAndResponses').empty();
    $('#evalHeader h3').remove();
    $.when(
        $.getJSON(apiUrl + "/questions/l2t",
            function (result) {
                evalQuestions = result;
            }
        ),
    ).then(function () {
        const title = `<h3>${evaluatee}'s Evaluation</h3>`;
        $('#evalHeader').append(title)
        $.each(evalQuestions, (index, question) =>{
            const innerHTML =
                `<li>
                <h4 style="font-weight: bold">Question ${index + 1}:</h4>
                <p class="tab-eval">  ${question.questionPrompt}</p>
                ${question.questionType == 'numeric' ? 
                    `<input type="range" class="form-control custom-range" min="0" max="5" id="response${index}">`:
                    `<input type="text" placeholder="Enter Answer Here..." class="form-control" id="response${index}">`}
                <br>
             </li>`;
            $('#questionsAndResponses').append(innerHTML)
        });
        $('#testmodal').modal('show')
        $('#submitEvalButton').click(function () {
            $.each(evalQuestions, (index, question) => {
                $.ajax({
                    type: "POST",
                    url: apiUrl + "/responses",
                    data: JSON.stringify({
                        assignment: assignmentId,
                        isShared: false,
                        question: question.questionId,
                        response: $(`#response${index}`).val()
                    }),
                    headers: {"Accept": "application/json", "Content-Type": "application/json"},
                    success: function (response) {
                        console.log(response)
                    }
                })
            })
            $.ajax({
                type: "PUT",
                url: apiUrl + '/assignments/id/' + assignmentId,
                headers: {"Accept": "application/json", "Content-Type": "application/json"},
                success: function (response) {
                    console.log(response)
                }
            })
        })
    })
}


