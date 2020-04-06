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
let completedEvals = [];
$(document).ready(() => {
    let teamMembers = [], leadAsurite = "aarunku3";
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
        let assignTable = $('#assignmentsTable').DataTable({
            stripe: true,
            paging: false,
            searching: true,
            info: false,
            data: tableData,
            columns: [
                { title: "Tutor Name" },
                {
                    title: "Actions",
                    render: (data, _type, row) => {
                        let asurite = Object.keys(names).find(key => names[key] == row[0])
                        const assignment = userAssignments.find(assignment => assignment.assignedAsurite == asurite)
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let evalButton =
                            `<button
                        class=${assignment.isComplete ? "btn btn-primary" : "btn btn-secondary"}
                        style="border-color: #8C1D40;"
                        onclick="buildEvaluation('${row[0]}')" id="myButton">
                        Evaluate
                        </button>`;
                        return evalButton;
                    }
                }
            ]
        })

        completedEvals = completedEvals.filter(eval => teamMembers.includes(Object.keys(names).find(key => names[key] == eval.evaluatee)));
        const completedEvalsTableData = completedEvals.map(eval => [eval.semester, eval.evaluator, eval.evaluatee, null])
        let completedEvalTable = $('#completedEvaluationTable').DataTable({
            stripe: true,
            paging: false,
            searching: true,
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
                        onclick="viewEvaluation('${leadAsurite}', '${row[2]}')" id="myButton">
                        View
                        </button>`;
                        return viewButton;
                    }
                }
            ]
        })

        $('#searchText').on('keyup change', function () {
            completedEvalTable.search(this.value).draw();
        })
    })

});

const buildEvaluation = (evaluatee) => {
    let asurite = Object.keys(names).find(key => names[key] == evaluatee)
    const assignment = userAssignments.find(assignment => assignment.assignedAsurite == asurite)

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
        const sharedRadioInput =
            `<div>
                <p>Would you like to share this evaluation with ${evaluatee}?</p>
                <input type="radio" id="isSharedYes" checked="checked">
                <label for="isSharedYes">Yes</label>
                <input type="radio" id="isSharedNo">
                <label for="isSharedNo">No</label>
             </div>`
        $('#evalHeader').append(title)
        $.each(evalQuestions, (index, question) => {
            const innerHTML =
                `<li>
                <h4 style="font-weight: bold">Question ${index + 1}:</h4>
                <p class="tab-eval">  ${question.questionPrompt}</p>
                ${question.questionType == 'numeric' ?
                    `<input type="range" class="form-control custom-range" min="0" max="5" id="response${index}">` :
                    `<input type="text" placeholder="Enter Answer Here..." class="form-control" id="response${index}">`}
                <br>
             </li>`;
            $('#questionsAndResponses').append(innerHTML)
        });
        $('#questionsAndResponses').append(sharedRadioInput)
        $('#testmodal').modal('show')
        $('#isShared').click(function () {
            if (this.checked)
                $('.isShared label').text('No')
            else
                $('.isShared label').text('Yes')
        })
        $('#submitEvalButton').click(function () {
            let temp = $('#isSharedYes')[0].checked
            $.each(evalQuestions, (index, question) => {
                $.ajax({
                    type: assignment.isComplete == true ? "PUT" : "POST",
                    url: apiUrl + "/responses",
                    data: JSON.stringify({
                        assignment: assignment.assignmentId,
                        isShared: $('#isSharedYes')[0].checked,
                        question: question.questionId,
                        response: $(`#response${index}`).val()
                    }),
                    headers: { "Accept": "application/json", "Content-Type": "application/json" },
                    success: function (response) {
                        console.log(response)
                    }
                })
            })
            $.ajax({
                type: "PUT",
                url: apiUrl + '/assignments/id/' + assignment.assignmentId,
                headers: { "Accept": "application/json", "Content-Type": "application/json" },
                success: function (response) {
                    console.log(response)
                }
            })
        })
    })
}
const viewEvaluation = (evaluator, evaluatee) => {
    //Clear any old evaluations in the modal. There is probably a better way to do this
    evaluator = names[evaluator]
    $('#questionsAndResponses').empty();
    $('#evalHeader h3').remove();

    //search for the evaluation and questions
    const currentEval = completedEvals.find(evaluation => evaluator == evaluation.evaluator && evaluatee == evaluation.evaluatee);
    const questions = currentEval.questionsAndResponses;

    //Add the title to the Modal
    const title = `<h3>${evaluator}'s Evaluation of ${evaluatee} </h3>`;
    $('#evalHeader').append(title);

    const sharedRadioInput =
        `<div>
            <p>Is this evaluation shared with ${evaluatee}?</p>
            ${currentEval.isShared == true ?
            `<input type="radio" id="isSharedYes" checked="checked" disabled="true"><label for="isSharedYes">Yes</label>
            <input type="radio" id="isSharedNo" disabled="true"><label for="isSharedNo">No</label>`:
            `<input type="radio" id="isSharedYes" disabled="true"><label for="isSharedYes">Yes</label>
            <input type="radio" id="isSharedNo" checked="checked" disabled="true"><label for="isSharedNo">No</label>`}
         </div>`

    //Add the questions to the modal. Needs styling.
    $.each(questions, (index, question) => {
        const innerHTML =
            `<li>
                <h4 style="font-weight: bold">Question ${index + 1}:</h4>
                <p class="tab-eval">  ${question.question.questionPrompt}</p>
                ${question.question.questionType == 'numeric' ?
                `<input type="range" class="form-control custom-range" disabled="true" min="0" max="5" value="${question.response}" id="response${index}" readonly>` :
                `<p class="tab-eval" readonly>${question.response}</p>`}
                <br>
             </li>`;
        $('#questionsAndResponses').append(innerHTML)
    });
    $('#questionsAndResponses').append(sharedRadioInput)
    $('#testmodal').modal('show');
}




