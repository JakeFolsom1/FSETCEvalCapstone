$("a[href='#assign']").on('show.bs.tab', function(e) { // experimenting with load on show of tab
    let names = {}, numAssignments = 0, tutorList = [], assignments = [], preferences = [], leadTeams = [], activeSemester = "";

    const reloadPage = () => {
        $.when(
            $.ajax({
                type: "GET",
                url: apiUrl + "/semesters/active",
                headers: { Accept: "application/text" },
                success: function (activeSemesterRes) {
                    activeSemester = activeSemesterRes;
                }
            }),
            $.getJSON(apiUrl + "/numAssignments",
                function (numAssignmentsJson) {
                    numAssignments = numAssignmentsJson.numAssignments;
                }
            ),
            $.getJSON(apiUrl + "/staff/names",
                function (namesJson) {
                    names = namesJson;
                }
            ),
            $.getJSON(apiUrl + "/staff/tutors",
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
                    url: `${apiUrl}/numAssignments/${num}`,
                    headers: { Accept: "application/json" },
                    success: function () {
                        const button = $("#numAssignmentsButton");
                        button.attr("disabled", "true");
                        button.text("Saved");
                        setTimeout(() => {
                            button.removeAttr("disabled");
                            button.text("Save");
                            reloadPage();
                        }, 500);
                    }
                });
            });

            // convert the preferences into datatable format
            const prefMap = preferences.reduce((acc, val) => {
                const { asurite, semesterName, ...values } = val;
                if (acc[val.asurite]) {
                    acc[val.asurite].push(values);
                }
                else {
                    acc[val.asurite] = [values];
                }
                return acc;
            }, {});

            const leadMap = Object.keys(leadTeams).map(val => [val, true, "", "", ""]);

            // sort by preference number
            Object.keys(prefMap).forEach((val) => { prefMap[val] = prefMap[val].sort((a, b) => a.preferenceNumber - b.preferenceNumber); });

            // get table data in form [asurite, isLead, preference1, preference2, preference3]
            const tableData = Object.keys(prefMap).map(val => {
                const pref1 = prefMap[val][0]
                const pref2 = prefMap[val][1];
                const pref3 = prefMap[val][2];
                return [
                    val,
                    false,
                    pref1 ? pref1.preferredAsurite : "",
                    pref2 ? pref2.preferredAsurite : "",
                    pref3 ? pref3.preferredAsurite : ""
                ]
            }).concat(leadMap);

            $('#assignmentTable').DataTable({
                destroy: true, // experimental feature
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
                            return (`
                        <button id="${tutorName}AssignButton" class="btn btn-primary">Assign</button>
                        <button id="${tutorName}AutoAssignButton" class="btn btn-primary" ${row[1] ? "disabled" : ""}>Auto-Assign</button>
                    `);
                        }
                    }
                ]
            });

            tableData.forEach(row => {
                const tutorName = row[0];
                $(`#${tutorName}AssignButton`).click(() => {
                    loadAssignmentModal(tutorName);
                });

                $(`#${tutorName}AutoAssignButton`).click(() => {
                    autoAssign(tutorName);
                })
            });

            $("#autoAssignConfirmButton").click(() => {
                autoAssignAll();
            });

            const getTutorAssignmentsForm = (isFiltered, tutorName) => {
                let optionList;
                if (isFiltered) {
                    optionList = prefMap[tutorName].map(pref => pref.preferredAsurite);
                } else {
                    optionList = tutorList;
                }
                const tutorsLead = Object.keys(leadTeams).find(value => leadTeams[value].includes(tutorName));

                return $(`<form id="${tutorName}AssignForm">
                        <div class="form-group">
                            <label for="${tutorName}Lead">Lead Evaluation:</label>
                            <select id="${tutorName}Lead" class="form-control" readonly>
                                <option value=${tutorsLead}>${names[tutorsLead]}</option>
                            </select>
                        </div>
                         ${Array.from(Array(numAssignments).keys()).map((_val, index) => {// create a select for each assignment number
                            // if an assignment exists for a tutor for this assignment number, retrieve it
                            const existingAssignment = assignments.find(value => value.asurite === tutorName && value.assignmentNumber === (index + 1) && value.evalType === "p2p");
                            let outsideMajorOption = "";
                            if (existingAssignment != null && !optionList.includes(existingAssignment.assignedAsurite)) {
                                outsideMajorOption = `<option value=${existingAssignment.assignedAsurite} selected>${names[existingAssignment.assignedAsurite]}</option>`;
                            }
                            return (`
                                    <div class="form-group">
                                        <label for="${tutorName}Peer${index}">Peer Evaluation ${index + 1}:</label>
                                        <select id="${tutorName}Peer${index}" class="form-control">
                                            <option>None</option>
                                            ${optionList.map(value => // map each option to an option for this tutor to be assigned and mark it as selected if it belongs to the existing assignment
                                                `<option value=${value} ${existingAssignment && existingAssignment.assignedAsurite === value ? "selected" : ""}>
                                                    ${names[value]}
                                                </option>`).join("")}
                                            ${outsideMajorOption}
                                         </select>
                                     </div>
                                `)
                        }).join("")}
                    </form >`)
            };

            const submitAssignments = (tutorName, event) => {
                event.preventDefault();
                // retrieve the selected peer assignment values
                const values = Array.from(Array(numAssignments).keys()).map(value => $(`#${tutorName}Peer${value}`).val());

                const button = $(`#assignmentsModalSubmitButton`);
                let isValid = checkDuplicateAssignments(values); //allows duplicate "None"s but all other names may only appear once
                if (isValid) {
                    button.attr("disabled", "true");
                    button.text("Saved");
                    values.forEach((asurite, index) => {
                        if (asurite !== "None") {
                            // check if a past assignments at this slot exists
                            const pastAssignment = assignments.find(assignment =>
                                assignment.asurite === tutorName &&
                                assignment.assignmentNumber === (index + 1) &&
                                assignment.evalType === "p2p");
                            let newAssignment;
                            let method;
                            // if it exists
                            if (pastAssignment) {
                                // modify the assigned asurite
                                newAssignment = Object.assign({}, pastAssignment, { assignedAsurite: asurite });
                                // set the method as put
                                method = "PUT";
                            } else {
                                // else create the assignment
                                newAssignment = {
                                    assignedAsurite: asurite,
                                    assignmentNumber: index + 1,
                                    asurite: tutorName,
                                    evalType: "p2p",
                                    isComplete: false,
                                    semesterName: activeSemester
                                };
                                // set the method as post
                                method = "POST";
                            }
                            $.ajax({
                                type: method,
                                url: apiUrl + "/assignments",
                                data: JSON.stringify(newAssignment),
                                headers: { "Accept": "application/json", "Content-Type": "application/json" },
                                success: () => {
                                    $(`#assignmentsModal`).modal("hide");
                                    reloadPage();
                                }
                            })
                        }
                    })
                }
                else {
                    button.attr("disabled", "true");
                    button.text("Error: Duplicates found")
                }
                setTimeout(() => {
                    button.removeAttr("disabled");
                    button.text("Save");
                }, 1000);
            };

            const loadAssignmentModal = tutorName => {
                const lead = Object.keys(leadTeams).find(value => tutorName === value); // define a lead's name if the current user is a lead

                $("#assignmentsModalLabel").text(`Assign ${names[tutorName]}`);
                $('#assignmentsModalBody').empty();

                const submitButton = $('#assignmentsModalSubmitButton');
                const filterCheckbox = $('#assignmentsFilterByMajorCheckbox');

                // if tutor is a lead
                if (lead) {
                    // create the lead form
                    $('#assignmentsModalBody').append(
                        $(`<form id="${tutorName}AssignForm">
                        ${leadTeams[lead].map((value, index) => { // map each team member to a read-only select box
                                return (`
                                <div class="form-group">
                                    <label for="${tutorName}Team${index}">Team Member Evaluation ${index + 1}:</label>
                                    <select id="${tutorName}Team${index}" class="form-control" readonly>
                                        <option value=${value}>${names[value]}</option>
                                    </select>
                                </div>
                            `)
                            }).join("")}
                        </form>`
                        )
                    );

                    // remove form from the submit button so assignments cannot be modified
                    submitButton.removeAttr("form");
                    // and disable the button to indicate it cannot be pressed
                    submitButton.attr("disabled", "true");
                    // and disable the checkbox in the lead modal
                    filterCheckbox.attr("disabled", "true");

                } else { // else if tutor is a normal tutor

                    filterCheckbox.change(e => {
                        $('#assignmentsModalBody').empty()
                        const form = getTutorAssignmentsForm(e.target.checked, tutorName);
                        form.submit(e => submitAssignments(tutorName, e));
                        $('#assignmentsModalBody').append(form);
                    })

                    const form = getTutorAssignmentsForm(filterCheckbox.prop("checked"), tutorName);
                    form.submit(e => submitAssignments(tutorName, e));
                    $('#assignmentsModalBody').append(form);

                    filterCheckbox.removeAttr("disabled");

                    submitButton.removeAttr("disabled");
                    submitButton.attr("form", `${tutorName}AssignForm`);
                }

                $("#assignmentsModal").modal('show');
            }


            const checkDuplicateAssignments = assignments => {
                const names = {};
                for (assignment of assignments) {
                    if (assignment !== "None") {
                        if (names[assignment]) {
                            return false;
                        }
                        else {
                            names[assignment] = 1;
                        }
                    }
                }
                return true;
            }
        })
    }

    reloadPage();



    const autoAssign = asurite => {
        const button = $(`#${asurite}AutoAssignButton`)
        button.attr("disabled", "true");
        button.text("Auto-Assigning");
        $.ajax({
            type: "POST",
            url: apiUrl + "/assignments/auto/" + asurite,
            headers: { "Accept": "application/json" },
            success: function (response) {
                button.removeAttr("disabled");
                button.text("Auto-Assign");
                reloadPage();
            },
            error: function (response) {
                button.text("Failed");
                setTimeout(() => {
                    button.text("Auto-Assign");
                }, 1000);
            }
        });

    };

    const autoAssignAll = () => {
        $("#autoAssignAllModal").modal("hide");
        const button = $("#autoAssignAllButton");
        button.attr("disabled", "true");
        button.text("Auto-Assigning All");

        $.ajax({
            type: "POST",
            url: apiUrl + "/assignments/auto",
            headers: { "Accept": "application/json" },
            success: function (response) {
                button.removeAttr("disabled");
                button.text("Auto-Assign All");
            }
            // ,
            // error: function (response) {
            //     button.text("Failed");
            //     setTimeout(() => {
            //         button.text("Auto-Assign All");
            //     }, 1000);
            // }
        });
    };

});

