let activeSemester = "", namesForTeams = {}, tutorList = [], leadList = [], leadTeams = {};

$(document).ready(() => {
    $.when(
        $.ajax({
            type: "GET",
            url: apiUrl + "/semesters/active",
            headers: { Accept: "application/text" },
            success: function (activeSemesterRes) {
                activeSemester = activeSemesterRes;
            }
        }),
        $.getJSON(apiUrl + "/staff/names",
            function (namesJson) {
                namesForTeams = namesJson;
            }
        ),
        $.getJSON(apiUrl + "/staff/tutors",
            function (tutorsJson) {
                tutorList = tutorsJson;
            }
        ),
        $.getJSON(apiUrl + "/staff/leads",
            function (leadsJson) {
                leadList = leadsJson;
            }
        )
    ).then(() => {
        leadList.forEach(lead => {
            $("#leadBox").append(`<option value="${lead.asurite}_${lead.cluster}">${namesForTeams[lead.asurite]} - ${lead.cluster}</option>`);
        });
    });

    $("#leadBox").change(function (e) {
        loadTeamAndAvailable(e.target.value);
    });

    $("#removeTeamMembersButton").click(function () {
        const selected = $("#leadBox option:selected");
        if (selected.length === 1 && selected[0].value !== "none") {
            $("#teamBox option:selected").each((_, element) => {
                const tutorAsurite = element.value;
                $.ajax({
                    type: "DELETE",
                    url: apiUrl + `/teamMembers/tutor/${tutorAsurite}`,
                    headers: { "Accept": "application/json" },
                    success: function () {
                        reloadTeamMembers();
                    }
                })
            })
        }
    });

    $("#addTeamMembersButton").click(function () {
        const selected = $("#leadBox option:selected");
        if (selected.length === 1 && selected[0].value !== "none") {
            const leadAsurite = selected[0].value.split("_")[0];
            $("#availableBox option:selected").each((_, element) => {
                const tutorAsurite = element.value;
                $.ajax({
                    type: "POST",
                    url: apiUrl + "/teamMembers",
                    data: JSON.stringify({ leadAsurite: leadAsurite, semesterName: activeSemester, tutorAsurite: tutorAsurite }),
                    headers: { "Accept": "application/json", "Content-Type": "application/json" },
                    success: function () {
                        reloadTeamMembers();
                    }
                })
            })
        }
    });

    reloadTeamMembers();

})

const tutorHasTeam = tutor => {
    for (const team of Object.values(leadTeams)) {
        if (team.includes(tutor)) {
            return true;
        }
    }
    return false;
};

const loadTeamAndAvailable = lead => {
    if (lead === "none") {
        $("#teamBox").empty();
        $("#availableBox").empty();
        tutorList.filter(tutor => !tutorHasTeam(tutor.asurite)).forEach(availableTutor => {
            $("#availableBox").append(`<option value="${availableTutor.asurite}">${namesForTeams[availableTutor.asurite]} - ${availableTutor.cluster}</option>`);
        });
    } else {
        const [leadAsurite, leadCluster] = lead.split("_");

        $("#teamBox").empty();
        leadTeams[leadAsurite].forEach(tutor => {
            $("#teamBox").append(`<option value="${tutor}">${namesForTeams[tutor]}</option>`);
        });

        $("#availableBox").empty();
        tutorList.filter(tutor => !tutorHasTeam(tutor.asurite) && tutor.cluster === leadCluster).forEach(availableTutor => {
            $("#availableBox").append(`<option value="${availableTutor.asurite}">${namesForTeams[availableTutor.asurite]}</option>`);
        });
    }
};

const reloadTeamMembers = () => {
    $.when(
        $.getJSON(apiUrl + "/teamMembers",
            function (teamMemberJson) {
                leadTeams = teamMemberJson;
            }
        )
    ).then(function () {

        if ($("#leadBox option:selected").length === 1) {
            loadTeamAndAvailable($("#leadBox option:selected")[0].value);
        }
    })
}