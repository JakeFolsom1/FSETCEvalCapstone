$("a[href='#checkProgress']").on('show.bs.tab', function(e) {
    let assignmentsProgress = [];
    let names = {};
    $.when(
        $.getJSON(apiUrl + "/assignments",
            function (assignmentsProgressJson) {
                assignmentsProgress = assignmentsProgressJson;
            }
        ),
        $.getJSON(apiUrl + "/staff/names",
            function (namesJson) {
                names = namesJson;
            }
        )
    ).then(function () {
        $(() => {
            $('[data-toggle="tooltip"]').tooltip()
        })
        // converting the assignment data to be an object with keys on the asurites with incomplete assignments
        // and values being an array of their incomplete assignment objects
        const assignmentMap = assignmentsProgress.reduce((acc, value) => {
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

        // convert the above data to an array for data tables in the following format [asurite, isLead, [asurites they need to evaluate]]
        const tableData = Object.keys(assignmentMap).map(val => {
            return [val, assignmentMap[val][0].evalType === 'l2t', assignmentMap[val].reduce((prev, value) => [...prev, value.assignedAsurite], [])];
        });

        $('#progressTable').DataTable({
            destroy: true,
            paging: false,
            searching: false,
            info: false,
            data: tableData,
            columns: [
                {
                    title: "Tutor Name",
                    render: (data, _type, row) => {
                        const ul = row[2].reduce((acc, value) => acc + `<li>${names[row[0]]} needs to evaluate ${names[value]}</li>`, '<ol>') + '</ol>'
                        return `<button
                                type="button"
                                class="btn btn-link"
                                data-toggle="tooltip"
                                data-placement="right"
                                data-html="true"
                                title="${ul}"
                                style="text-decoration: none; color: black;"
                            >${names[data]}
                            </button>`;
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
    }
    );
});

const remindEvaluator = evaluator => {
    const button = $(`#remind${evaluator}Button`);
    button.attr("disabled", "true");
    button.text("Reminding");
    $.ajax({
        type: "POST",
        url: apiUrl + "/reminders/" + evaluator,
        headers: {"Accept": "application/json"},
        success: function (response) {
            button.removeAttr("disabled");
            button.text("Remind");
        }
    });
};

const remindAll = () => {
    const button = $("#remindAllButton");
    button.attr("disabled", "true");
    button.text("Reminding All");
    $.ajax({
        type: "POST",
        url: apiUrl + "/reminders",
        headers: {"Accept": "application/json"},
        success: function (response) {
            button.removeAttr("disabled");
            button.text("Remind");
        }
    });
};