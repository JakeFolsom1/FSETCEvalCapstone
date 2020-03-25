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
                    render: () => {
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let evalButton =
                            `<button
                        class="btn btn-primary"
                        style="border-color: #8C1D40;"
                        onclick="buildEvaluation()" id="myButton">
                        Evaluate
                        </button>`;
                        return evalButton;
                    }
                }
            ]
        })

	$('#searchText').on('keyup change', function () {
            	console.log("Character read from search box ")
		assignTable.search(this.value).draw();
		console.log("Table searched")
        })

        completedEvals = completedEvals.filter(eval => teamMembers.includes(eval.evaluatee));
        const completedEvalsTableData = completedEvals.map(eval => [eval.semester, eval.evaluator, eval.evaluatee, null])
        
	let completedEvalTable = $('#completedEvaluationTable').DataTable({
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

const buildEvaluation = () => {
    let evalQuestions = [];
    $('#questionsAndResponses').empty();
    $('#evalHeader h3').remove();
    $.when(
        $.getJSON(apiUrl + "/questions/l2t",
            function (result) {
                console.log(result);
                evalQuestions = result;
            }
        ),
    ).then(function () {
        $.each(evalQuestions, (index, question) =>{
            const innerHTML =
                `<li>
                <h4 style="font-weight: bold">Question ${index + 1}:</h4>
                <p class="tab-eval">  ${question.questionPrompt}</p>
                <br>
             </li>`;
            $('#questionsAndResponses').append(innerHTML)
        });
        $('#testmodal').modal('show')
    })
}

const submitEvaluation = () => {

}
