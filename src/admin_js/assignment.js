$(document).ready(() => {
    let names = {};
    let numAssignments = 0;
    let tutorList = [];
    let assignments = [];
    let preferences = [];
    let leadTeams = [];
    let activeSemester = "";
    $.when(
        $.when(
            $.ajax({
                type: "GET",
                url: apiUrl + "/semesters/active",
                headers: { Accept: "application/text" },
                success: function (activeSemesterRes) {
                    activeSemester = activeSemesterRes;
                }
            })
        ).then(function () {
            $.getJSON(apiUrl + "/numAssignments/" + activeSemester,
                function (numAssignmentsJson) {
                    numAssignments = numAssignmentsJson.numAssignments;
                }
            )
        }),
        $.getJSON(apiUrl + "/accounts/names",
            function (namesJson) {
                names = namesJson;
            }
        ),
        $.getJSON(apiUrl + "/accounts/tutors",
            function (tutorsJson) {
                tutorList = tutorsJson.map(tutor => tutor.asurite);
            }
        ),
        $.getJSON(apiUrl + "/assignments",
            function (assignmentsJson) {
                assignments = assignmentsJson;
            }
        ),
        $.getJSON(apiUrl + "/preferences",
            function (preferencesJson) {
                preferences = preferencesJson;
            }
        ),
        $.getJSON(apiUrl + "/teamMembers",
            function (teamMemberJson) {
                leadTeams = teamMemberJson;
            }
        )
    ).then(function () {
        $("#numAssignments").val(numAssignments);
        $("#numAssignmentsForm").bind()
        $("#numAssignmentsForm").submit(event => {
            event.preventDefault();
            const num = $("#numAssignments").val();
            $.ajax({
                type: "PUT",
                url: `${apiUrl}/numAssignments/${activeSemester}/${num}`,
                headers: { Accept: "application/json" },
                success: function () {
                    const button = $("#numAssignmentsButton");
                    button.attr("disabled", "true");
                    button.text("Saved");
                    setTimeout(() => {
                        button.removeAttr("disabled");
                        button.text("Save");
                        location.reload();
                    }, 500);
                }
            });
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

        const leadMap = Object.keys(leadTeams).map(val => [val, true, "", "", ""]);

        // sort or ensure data will be sorted by preference number
        Object.keys(prefMap).forEach((val) => { prefMap[val] = prefMap[val].sort((a, b) => a.preferenceNumber - b.preferenceNumber); });

        // you also need to figure out if they are a lead or not by calling the account api or if team member is redone
        const tableData = Object.keys(prefMap).map(val => [val, false, prefMap[val][0].preferredAsurite, prefMap[val][1].preferredAsurite, prefMap[val][2].preferredAsurite]).concat(leadMap);

        $('#assignmentTable').DataTable({
            paging: false,
            info: false,
            searching: false,
            data: tableData,
            columns: [
                {
                    title: "Tutor Name",
                    render: data => {
                        return names[data];
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
                            return names[data];
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
                            return names[data];
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
                            return names[data];
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
                        const lead = Object.keys(leadTeams).find(value => tutorName === value); // define a lead's name if the current user is a lead
                        const tutorsLead = Object.keys(leadTeams).find(value => leadTeams[value].includes(tutorName)); // else find the current user's lead
                        return (`
                        <button id="${tutorName}AssignButton" class="btn btn-primary" data-toggle="modal" data-target="#${tutorName}AssignModal">Assign</button>
                        <button id="${tutorName}AutoAssignButton" class="btn btn-primary" onclick="autoAssign('${tutorName}')" ${row[1] ? "disabled" : ""}>Auto-Assign</button>
                        <div class="modal fade" id="${tutorName}AssignModal" tabindex="-1">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h3 class="modal-title">
                                            Assign ${names[tutorName]}
                                        </h3>
                                    </div>
                                    <div class="modal-body">
                                        <form id="${tutorName}AssignForm">
                                            ${tutorList.includes(tutorName) ? `
                                                <div class="form-group">
                                                    <label for="${tutorName}Lead">Lead Evaluation:</label>
                                                    <select id="${tutorName}Lead" class="form-control" readonly>
                                                        <option value=${tutorsLead}>${names[tutorsLead]}</option>
                                                    </select>
                                                </div>` +
                                Array.from(Array(numAssignments).keys()).map((_val, index) => {
                                    const existingAssignment = assignments.find(value => value.asurite === tutorName && value.assignmentNumber === (index + 1) && value.evalType === "p2p");
                                    return (`
                                    <div class="form-group">
                                        <label for="${tutorName}Peer${index}">Peer Evaluation ${index + 1}:</label>
                                        <select id="${tutorName}Peer${index}" class="form-control">
                                            <option>None</option>
                                            ${tutorList.filter(value => value !== tutorName).map(value => // possibly make this from preference list because each tutor will have preferences for all tutors in their majorCluster
                                        `<option value=${value} ${existingAssignment && existingAssignment.assignedAsurite === value ? "selected" : ""}>${names[value]}</option>`)}
                                        </select>
                                    </div>
                                `)
                                }).join("") :
                                lead ? leadTeams[lead].map((value, index) => {
                                    return (`
                                    <div class="form-group">
                                        <label for="${tutorName}Team${index}">Team Member Evaluation ${index + 1}:</label>
                                        <select id="${tutorName}Team${index}" class="form-control" readonly>
                                            <option value=${value}>${names[value]}</option>
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