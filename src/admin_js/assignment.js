let numAssignments = 3;

const tutorList = [
    "jjbowma2",
    "smurra11",
    "asdfqw21",
    "qwerty1"
]

const assignments = [
    {
        "assignedAsurite": "jjbowma2",
        "assignmentId": 54,
        "evalNumber": 1,
        "asurite": "smurra11",
        "evalType": "p2p",
        "isComplete": false,
        "semester": "Fall 2019"
    },
    {
        "assignedAsurite": "asdfqw21",
        "assignmentId": 55,
        "evalNumber": 2,
        "asurite": "smurra11",
        "evalType": "p2p",
        "isComplete": true,
        "semester": "Fall 2019"
    },
    {
        "assignedAsurite": "smurra11",
        "assignmentId": 54,
        "evalNumber": 1,
        "asurite": "jjbowma2",
        "evalType": "p2p",
        "isComplete": false,
        "semester": "Fall 2019"
    },
    {
        "assignedAsurite": "asdfqw21",
        "assignmentId": 54,
        "evalNumber": 2,
        "asurite": "jjbowma2",
        "evalType": "p2p",
        "isComplete": false,
        "semester": "Fall 2019"
    }
];

const preferences = [
    {
        "asurite": "jjbowma2",
        "preferenceId": 1,
        "preferenceNumber": 1,
        "preferredAsurite": "smurra11",
        "semester": "Fall 2019"
    },
    {
        "asurite": "jjbowma2",
        "preferenceId": 2,
        "preferenceNumber": 2,
        "preferredAsurite": "asdfqw21",
        "semester": "Fall 2019"
    },
    {
        "asurite": "jjbowma2",
        "preferenceId": 3,
        "preferenceNumber": 3,
        "preferredAsurite": "qwerty1",
        "semester": "Fall 2019"
    },
    {
        "asurite": "smurra11",
        "preferenceId": 1,
        "preferenceNumber": 1,
        "preferredAsurite": "qwerty1",
        "semester": "Fall 2019"
    },
    {
        "asurite": "smurra11",
        "preferenceId": 2,
        "preferenceNumber": 2,
        "preferredAsurite": "asdfqw21",
        "semester": "Fall 2019"
    },
    {
        "asurite": "smurra11",
        "preferenceId": 3,
        "preferenceNumber": 3,
        "preferredAsurite": "jjbowma2",
        "semester": "Fall 2019"
    },
    {
        "asurite": "asdfqw21",
        "preferenceId": 1,
        "preferenceNumber": 1,
        "preferredAsurite": "smurra11",
        "semester": "Fall 2019"
    },
    {
        "asurite": "asdfqw21",
        "preferenceId": 2,
        "preferenceNumber": 2,
        "preferredAsurite": "qwerty1",
        "semester": "Fall 2019"
    },
    {
        "asurite": "asdfqw21",
        "preferenceId": 3,
        "preferenceNumber": 3,
        "preferredAsurite": "jjbowma2",
        "semester": "Fall 2019"
    },
    {
        "asurite": "qwerty1",
        "preferenceId": 1,
        "preferenceNumber": 1,
        "preferredAsurite": "smurra11",
        "semester": "Fall 2019"
    },
    {
        "asurite": "qwerty1",
        "preferenceId": 2,
        "preferenceNumber": 2,
        "preferredAsurite": "asdfqw21",
        "semester": "Fall 2019"
    },
    {
        "asurite": "qwerty1",
        "preferenceId": 3,
        "preferenceNumber": 3,
        "preferredAsurite": "jjbowma2",
        "semester": "Fall 2019"
    },
];

const leadTeams = [
    {
        leadAsurite: "zxcvbnm5",
        teamMembers: [
            "jjbowma2",
            "smurra11",
            "qwerty1",
            "asdfqw21"
        ]
    }
];

$(document).ready(() => {
    document.getElementById("numAssignments").value = numAssignments;

    document.getElementById("numAssignmentsForm").addEventListener('submit', event => {
        const num = document.getElementById("numAssignments").value;
        console.log("Setting number of assignments to " + num);
        numAssignments = num; // this is where you would post the new num
        const button = document.getElementById('numAssignmentsButton');
        button.setAttribute("disabled", "true");
        button.innerHTML = "Saved";
        setTimeout(() => {
            button.removeAttribute("disabled");
            button.innerHTML = "Save";
        }, 1000);
    });

    const prefMap = preferences.reduce((acc, val) => {
        const { asurite, semester, preferenceId, ...values } = val;
        if (acc[val.asurite]) {
            acc[val.asurite].push(values);
        }
        else {
            acc[val.asurite] = [values];
        }
        return acc;
    }, {});

    const leadMap = leadTeams.map(val => [val.leadAsurite, true, "", "", ""]);

    // sort or ensure data will be sorted by preference number
    // Object.keys(prefMap).forEach((val) => { prefMap[val] = prefMap[val].sort((a, b) => a.preferenceNumber > b.preferenceNumber); });

    // you also need to figure out if they are a lead or not by calling the account api
    const tableData = Object.keys(prefMap).map(val => [val, false, prefMap[val][0].preferredAsurite, prefMap[val][1].preferredAsurite, prefMap[val][2].preferredAsurite]).concat(leadMap);

    $('#assignmentTable').DataTable({
        paging: false,
        info: false,
        searching: false,
        data: tableData,
        columns: [
            {
                title: "Tutor Name",
                render: (data, _type, _row) => {
                    return `<a
                                id="${data}AssignAnchor"
                                data-toggle="collapse"
                                data-parent="#assignmentTable"
                                href="#${data}AssignCollapse"
                                style="text-decoration: none; color: black;"
                            >${data}  <span>&#x1F6C8;</span>
                            </a>`;
                }
            },
            {
                title: "Lead",
                render: data => {
                    return data ? '<div class="dot" />' : "";
                }
            },
            {
                title: "Preference 1",
                render: data => {
                    if (data) {
                        return data;
                    }
                    else {
                        return "N/A";
                    }
                }
            },
            {
                title: "Preference 2",
                render: data => {
                    if (data) {
                        return data;
                    }
                    else {
                        return "N/A";
                    }
                }
            },
            {
                title: "Preference 3",
                render: data => {
                    if (data) {
                        return data;
                    }
                    else {
                        return "N/A";
                    }
                }
            },
            {
                title: "Actions",
                render: (_data, _type, row) => {
                    return `<button id="${row[0]}AutoAssignButton" class="btn btn-primary" onclick="autoAssign('${row[0]}')" ${row[1] ? "disabled" : ""}>Auto-Assign</button>`;
                }
            }
        ]
    });
    // the styling is slightly strange here consider redesigning this UI
    const addCollapse = () => {
        tableData.forEach(val => {
            const lead = leadTeams.find(value => val[0] === value.leadAsurite);
            const tutorsLead = leadTeams.find(value => value.teamMembers.includes(val[0]));
            const form = `<form id="${val[0]}AssignForm">
            ${tutorList.includes(val[0]) ?
                    `<div class="form-group">
                    <label for="${val[0]}Lead">Lead Evaluation:</label>
                    <select id="${val[0]}Lead" class="form-control" readonly>
                        <option>${tutorsLead.leadAsurite}</option>
                    </select>
                </div>` +
                    Array.from(Array(numAssignments).keys()).map((_val, index) => {
                        const existingAssignment = assignments.find(value => value.asurite === val[0] && value.evalNumber === index + 1);
                        return `<div class="form-group">
                            <label for="${val[0]}Peer${index}">Peer Evaluation ${index + 1}:</label>
                            <select id="${val[0]}Peer${index}" class="form-control">
                                <option>None</option>
                                ${tutorList.filter(value => value !== val[0]).map(value =>
                            `<option ${existingAssignment && existingAssignment.assignedAsurite === value ? "selected" : ""}>${value}</option>`)}
                            </select>
                        </div>`
                    }).join("") :
                    lead ? lead.teamMembers.map((value, index) => {
                        return `<div class="form-group">
                <label for="${val[0]}Team${index}">Team Member Evaluation ${index + 1}:</label>
                <select id="${val[0]}Team${index}" class="form-control" readonly>
                        <option>${value}</option>
                </select>
            </div>`
                    }).join("") : '<h2>Error</h2>'
                }
            <button id="${val[0]}AssignButton" class="btn btn-primary" type="submit" ${lead ? "disabled" : ""}>Save Assignment</button>
            </form > `;

            const parentRow = document.getElementById(val[0] + 'AssignAnchor').parentElement.parentElement;
            parentRow.insertAdjacentHTML('afterend', `<tr id = "${val[0]}AssignCollapse" class="panel-collapse collapse" > <td class="panel-body" style="width: 17%">${form}</td> <td /> <td /><td /><td /><td /></<tr>`)
        });

        tableData.forEach(val => {
            document.getElementById(`${val[0]}AssignForm`).addEventListener('submit', event => {
                // lead form cannot currently be submitted as button is disabled
                // if this changes, a lead submission needs to be considered
                event.preventDefault();
                const values = Array.from(Array(numAssignments).keys()).map(value => document.getElementById(`${val[0]}Peer${value}`).value);
                const button = document.getElementById(`${val[0]}AssignButton`);
                let isValid = new Set(values).size === values.length;
                if (isValid) {
                    button.setAttribute("disabled", "true");
                    button.innerHTML = "Saved Assignments";
                    setTimeout(() => {
                        button.removeAttribute("disabled");
                        button.innerHTML = "Save Assignments";
                    }, 1000);
                    console.log("Saving assignments as: " + values);
                }
                else {
                    button.setAttribute("disabled", "true");
                    button.innerHTML = "Error: Duplicates found";
                    setTimeout(() => {
                        button.removeAttribute("disabled");
                        button.innerHTML = "Save Assignments";
                    }, 1000);
                    console.log("Duplicates found: " + values);
                }

            })
        })
    };
    addCollapse();
    // have to add the collapse targets again after a re-order destroys them
    $('#assignmentTable').on('order.dt', addCollapse);
});

const autoAssign = asurite => {
    console.log("Auto-assigning: " + asurite);
    const button = document.getElementById(`${asurite}AutoAssignButton`);
    button.setAttribute("disabled", "true");
    button.innerHTML = "Auto-Assigned";
    setTimeout(() => {
        button.removeAttribute("disabled");
        button.innerHTML = "Auto-Assign";
    }, 1000);
};

const autoAssignAll = () => {
    console.log("Auto-Assigning All");
    const button = document.getElementById('autoAssignAllButton');
    button.setAttribute("disabled", "true");
    button.innerHTML = "Auto-Assigned All";
    setTimeout(() => {
        button.removeAttribute("disabled");
        button.innerHTML = "Auto-Assign All";
    }, 1000);
};