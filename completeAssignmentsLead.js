const sampleData = [
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
    console.log("Document is ready")
    let completeEvals = [];
    let names = [];

    const tableData = sampleData.map(eval => [
        eval.semester,
        names[eval.evaluator],
        (eval.evalType === "l2t" ? " - Lead" : "")
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
            { title: "Lead",
                render: data => {
                    return data ? '<div class="dot" />' : "";
                }
            },
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