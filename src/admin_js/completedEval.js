$(document).ready(() => {
    let completedEvals = [];
    $.when(
        $.getJSON(apiUrl + "/completedEvaluations",
            function (completedEvalJson) {
                completedEvals = completedEvalJson.map(completedEval => [
                    completedEval.semester,
                    completedEval.evaluator + (completedEval.evalType === "l2t" ? " - Lead" : ""),
                    completedEval.evaluatee + (completedEval.evalType === "t2l" ? " - Lead" : "")
                ]);
            }
        )
    ).then(function () {
        $('#completedEvaluationTable').DataTable({
            stripe: true,
            paging: false,
            searching: false,
            info: false,
            data: completedEvals,
            columns: [
                {
                    title: "Semester"
                },
                {
                    title: "Evaluator Name"
                },
                {
                    title: "Evaluatee Name"
                },
                {
                    title: "Actions",
                    render: (data, _type) => {
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let viewButton =
                            `<button 
                            class="btn btn-secondary" 
                            style="border-color: #8C1D40;"
                            onclick="location.href = './evaluations.html';" id="myButton">
                            View
                            </button>`;
                        return viewButton;
                    }
                }
            ]
        })
    }
    )
})