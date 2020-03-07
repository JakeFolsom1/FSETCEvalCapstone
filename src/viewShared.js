const dummyData = [
    {
        "semester": "Fall 2019",
        "evaluator": "aarunku3",
        "evalType": "l2t"
    },
    {
        "semester": "Fall 2019",
        "evaluator": "dvaldera",
        "evalType": "t2l"
    },
    {
        "semester": "Fall 2019",
        "evaluator": "aarunku3",
        "evalType": "l2t"
    },
    {
        "semester": "Fall 2019",
        "evaluator": "aarunku3",
        "evalType": "l2t"
    },
];

$(document).ready(() => {
    let completedEvals = [];
    let names = [];
    $.when(
        $.getJSON(apiUrl + "/completedEvaluations",
            function (completedEvalJson) {
                console.log(completedEvalJson);
                completedEvals = completedEvalJson;
            }
        ),
        $.getJSON(apiUrl + "/accounts/names",
            function (namesJson) {
                console.log(namesJson);
                names = namesJson;
            }
        )
    ).then(function () {
        const tableData = dummyData.map(eval => [
            eval.semester,
            names[eval.evaluator],
	    (eval.evalType === "l2t" ? " - Lead" : "")
        ])
            $('#completedEvaluationTable').DataTable({
                stripe: true,
                paging: false,
                searching: false,
                info: false,
                data: tableData,
                columns: [
                    { title: "Semester" },
                    { title: "Evaluator Name" },
                    { title: "Lead",
                      render: data => {
                        	return data ? '<div class="dot" />' : "";
                      }
                    },
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
        }
    )
});
