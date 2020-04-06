// const sharedEvals = [
//     {
//         "evalType": "t2l",
//         "evaluatee": "smurra11",
//         "evaluator": "jjbowma2",
//         "isShared": true,
//         "questionsAndResponses": [
//             {
//                 "question": {
//                     "questionNumber": 3,
//                     "questionPrompt": "Does tutor obey all procedures and policies of the center?",
//                     "questionType": "freeResponse"
//                 },
//                 "response": "John does not wear his sash when he's on shift."
//             }
//         ],
//         "semester": "Fall 2019"
//     }
// ]


$(document).ready(() => {
    let sharedEvals = [], names = {}, leadAsurite = "aarunku3";
    $.when(
        $.getJSON(apiUrl + `/completedEvaluations/shared/${leadAsurite}`,
            function (completedEvalJson) {
                sharedEvals = completedEvalJson;
            }
        ),
        $.getJSON(apiUrl + "/staff/names",
            function (namesJson) {
                names = namesJson;
            }
        )
    ).then(function () {
        const tableData = sharedEvals.map(eval => [
            eval.semester,
            names[eval.evaluator],
            null
        ])
        $('#sharedEvaluationsTable').DataTable({
            stripe: true,
            paging: false,
            searching: false,
            info: false,
            data: tableData,
            columns: [
                { title: "Semester" },
                { title: "Evaluator Name" },
                {
                    title: "Actions",
                    render: () => {
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let viewButton =
                            `<button
                            class="btn btn-primary"
                            style="border-color: #8C1D40;"
                            onclick="location.href = '../../templates/evaluations.html';" id="myButton">
                            View
                            </button>`;
                        return viewButton;
                    }
                }
            ]
        })
    })
});
