const assignmentsProgress = [
    {
        "assignedAsurite": "jjbowma2",
        "assignmentId": 54,
        "asurite": "smurra11",
        "evalType": "p2p",
        "isComplete": false,
        "semester": "Fall 2019"
    },
    {
        "assignedAsurite": "asdfqw21",
        "assignmentId": 55,
        "asurite": "smurra11",
        "evalType": "p2p",
        "isComplete": true,
        "semester": "Fall 2019"
    },
    {
        "assignedAsurite": "smurra11",
        "assignmentId": 54,
        "asurite": "jjbowma2",
        "evalType": "l2t",
        "isComplete": false,
        "semester": "Fall 2019"
    },
    {
        "assignedAsurite": "asdfqw21",
        "assignmentId": 54,
        "asurite": "jjbowma2",
        "evalType": "l2t",
        "isComplete": false,
        "semester": "Fall 2019"
    }
];

$(document).ready(() => {
    // converting the assingment data to be an object with keys on the asurites with incomplete assignments
    // and values being an array of their incomplete assignment objects
    const assignmentMap = assignments.reduce((acc, value) => {
        if (!value.isComplete) {
            if (acc[value.asurite]) {
                acc[value.asurite].push(value);
            }
            else {
                acc[value.asurite] = [value];
            }
        }
        return acc;
    }, {});

    // convert the above data to an array for data tables in the following format [name, lead (true if lead), [asurites they need to evaluate]]
    // we could simplify this logic by returning this flow in the first place
    const tableData = Object.keys(assignmentMap).map(val => {
        return [val, assignmentMap[val][0].evalType === 'l2t', assignmentMap[val].reduce((prev, value) => [...prev, value.assignedAsurite], [])];
    });

    $('#progressTable').DataTable({
        paging: false,
        searching: false,
        info: false,
        data: tableData,
        columns: [
            {
                title: "Tutor Name",
                render: (data, _type, _row) => {
                    return `<a
                                id="${data}Anchor"
                                data-toggle="collapse"
                                data-parent="#progressTable"
                                href="#${data}Collapse"
                                style="text-decoration: none; color: black;"
                            >${data}  <span>&#x1F6C8;</span>
                            </a>`;
                }
            },
            {
                title: "Lead",
                render: (data) => {
                    return data ? '<div class="dot" />' : "";
                }
            },
            {
                title: "Actions",
                render: (_data, _type, row) => {
                    return `<button id="remind${row[0]}Button" class="btn btn-primary" onclick="remindEvaluator('${row[0]}')">Remind</button>`;
                }
            }
        ]
    });
    // the styling is slightly strange here consider redesigning this UI
    const addCollapse = () => {
        tableData.forEach(val => {
            const ul = val[2].reduce((acc, value) => acc + `<li>${val[0]} needs to evaluate ${value}</li>`, '<ul>') + '</ul>'
            const parentRow = document.getElementById(val[0] + 'Anchor').parentElement.parentElement;
            parentRow.insertAdjacentHTML('afterend', `<tr id="${val[0]}Collapse" class="panel-collapse collapse"><td class="panel-body" style="width: 41%">${ul}</td><td/><td/></<tr>`)
        });
    };
    addCollapse();
    // have to add the collapse targets again after a re-order destroys them
    $('#progressTable').on('order.dt', addCollapse);
});

const remindEvaluator = evaluator => {
    console.log("Reminding evaluator: " + evaluator);
    const button = document.getElementById(`remind${evaluator}Button`);
    button.setAttribute("disabled", "true");
    button.innerHTML = "Reminded";
    setTimeout(() => {
        button.removeAttribute("disabled");
        button.innerHTML = "Remind";
    }, 1000);
};

const remindAll = () => {
    console.log("Reminding all evaluators");
    const button = document.getElementById('remindAllButton');
    button.setAttribute("disabled", "true");
    button.innerHTML = "Reminded All";
    setTimeout(() => {
        button.removeAttribute("disabled");
        button.innerHTML = "Remind All";
    }, 1000);
};