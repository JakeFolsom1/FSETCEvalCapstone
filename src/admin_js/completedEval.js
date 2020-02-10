// dynamically display the finished evaluations
const completedEvals = [
    [
        'Fall 2019',
        'Bob',
        'Jeff',
    ],
    [
        'Spring 2019',
        'Jedde',
        'Steven',
    ],
    [
        'Spring 2019',
        'Cole',
        'Prakhar',
    ],
    [
        'Spring 2019',
        'Dummy',
        'Data',
    ]
]

$(document).ready(() => {
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
                        class="btn btn-primary"
                        onclick="location.href = './evaluations.html';" id="myButton">
                        View
                        </button>`;
                    return viewButton;
                }
            }
        ]

    });
});