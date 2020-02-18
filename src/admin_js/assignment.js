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
    $("#numAssignments").val(numAssignments);
    $("#numAssignmentsForm").bind()
    $("#numAssignmentsForm").submit(event => {
        const num = $("#numAssignments").val();
        console.log("Setting number of assignments to " + num);
        numAssignments = num; // this is where you would post the new num
        const button = $("#numAssignmentsButton");
        button.attr("disabled", "true");
        button.text("Saved");
        setTimeout(() => {
            button.removeAttr("disabled");
            button.text("Save");
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

    // you also need to figure out if they are a lead or not by calling the account api or if team member is redone
    const tableData = Object.keys(prefMap).map(val => [val, false, prefMap[val][0].preferredAsurite, prefMap[val][1].preferredAsurite, prefMap[val][2].preferredAsurite]).concat(leadMap);

    $('#assignmentTable').DataTable({
        paging: false,
        info: false,
        searching: false,
        data: tableData,
        columns: [
            {
                title: "Tutor Name"
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
                    const tutorName = row[0];
                    const lead = leadTeams.find(value => tutorName === value.leadAsurite);
                    const tutorsLead = leadTeams.find(value => value.teamMembers.includes(tutorName));
                    return (`
                        <button id="${tutorName}AssignButton" class="btn btn-primary" data-toggle="modal" data-target="#${tutorName}AssignModal">Assign</button>
                        <button id="${tutorName}AutoAssignButton" class="btn btn-primary" onclick="autoAssign('${tutorName}')" ${row[1] ? "disabled" : ""}>Auto-Assign</button>
                        <div class="modal fade" id="${tutorName}AssignModal" tabindex="-1">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title" id="addSemesterModalLabel">
                                            Assign ${tutorName}
                                        </h3>
                                    </div>
                                    <div class="modal-body">
                                        <form id="${tutorName}AssignForm">
                                            ${tutorList.includes(tutorName) ? `
                                                <div class="form-group">
                                                    <label for="${tutorName}Lead">Lead Evaluation:</label>
                                                    <select id="${tutorName}Lead" class="form-control" readonly>
                                                        <option>${tutorsLead.leadAsurite}</option>
                                                    </select>
                                                </div>` +
                            Array.from(Array(numAssignments).keys()).map((_val, index) => {
                                const existingAssignment = assignments.find(value => value.asurite === tutorName && value.evalNumber === index + 1);
                                return (`
                                    <div class="form-group">
                                        <label for="${tutorName}Peer${index}">Peer Evaluation ${index + 1}:</label>
                                        <select id="${tutorName}Peer${index}" class="form-control">
                                            <option>None</option>
                                            ${tutorList.filter(value => value !== tutorName).map(value =>
                                    `<option ${existingAssignment && existingAssignment.assignedAsurite === value ? "selected" : ""}>${value}</option>`)}
                                        </select>
                                    </div>
                                `)
                            }).join("") :
                            lead ? lead.teamMembers.map((value, index) => {
                                return (`
                                    <div class="form-group">
                                        <label for="${tutorName}Team${index}">Team Member Evaluation ${index + 1}:</label>
                                        <select id="${tutorName}Team${index}" class="form-control" readonly>
                                            <option>${value}</option>
                                        </select>
                                    </div>
                                `)
                            }).join("") :
                                '<h2>Error</h2>'
                        }
                                        </form >
                                    </div>
                                    <div class="modal-footer">
                                        <button
                                            type="button"
                                            class="btn btn-outline-primary"
                                            data-dismiss="modal"
                                        >
                                            Close
                                        </button>
                                        <button
                                            type="submit"
                                            id="${tutorName}SaveAssignButton"
                                            form="${tutorName}AssignForm"
                                            class="btn btn-primary"
                                            ${lead ? "disabled" : ""}
                                        >
                                            Save
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `);
                }
            }
        ]
    });

    tableData.forEach(row => {
        const tutorName = row[0];
        $(`#${tutorName}AssignForm`).submit(event => {
            // lead form cannot currently be submitted as button is disabled (what if html is edited?)
            // if this changes, a lead submission needs to be considered
            event.preventDefault();
            const values = Array.from(Array(numAssignments).keys()).map(value => $(`#${tutorName}Peer${value}`).val());

            const button = $(`#${tutorName}SaveAssignButton`);
            let isValid = new Set(values).size === values.length;
            if (isValid) {
                button.attr("disabled", "true");
                button.text("Saved");
                console.log("Saving assignments as: " + values);
            }
            else {
                button.attr("disabled", "true");
                button.text("Error: Duplicates found")
                console.log("Duplicates found: " + values);
            }
            setTimeout(() => {
                button.removeAttr("disabled");
                button.text("Save");
            }, 1000);
        });
    })
});

const autoAssign = asurite => {
    console.log("Auto-assigning: " + asurite);
    const button = $(`#${asurite}AutoAssignButton`)
    button.attr("disabled", "true");
    button.text("Auto-Assigned");
    setTimeout(() => {
        button.removeAttr("disabled");
        button.text("Auto-Assign");
    }, 1000);
};

const autoAssignAll = () => {
    console.log("Auto-Assigning All");
    const button = $("#autoAssignAllButton");
    button.attr("disabled", "true");
    button.text("Auto-Assigned All");
    setTimeout(() => {
        button.removeAttr("disabled");
        button.text("Auto-Assign All");
    }, 1000);
};