let dataTable = null;
$(document).ready(() => {
    $.getJSON(apiUrl + "/semesters",
        function (semestersJson) {
            const semesters = semestersJson.map(semester => [semester.semesterName, semester.isActive]);
            dataTable = $('#semesterTable').DataTable({
                paging: false,
                searching: false,
                info: false,
                data: semesters,
                columns: [
                    {
                        title: "Semester",
                        render: data => {
                            const semester = (data.includes("fall") ? "Fall" : data.includes("spring") ? "Spring" : data.includes("summer") ? "Summer" : "Invalid Semester");
                            const year = data.substr(-2);
                            return `${semester} 20${year}`;
                        }
                    },
                    {
                        title: "Actions",
                        render: (data, _type, row) => {
                            const modalKey = row[0].replace(/\s+/g, '');
                            let actionButtons =
                                `<button 
                                class="btn btn-secondary" 
                                style="border-color: #8C1D40;" 
                                data-toggle="modal"
                                data-target="#delete${modalKey}Modal">Delete</button>
                                <div
                                    class="modal fade"
                                    id="delete${modalKey}Modal"
                                    tabindex="-1"
                                    role="dialog"
                                    aria-labelledby="delete${modalKey}ModalLabel"
                                    aria-hidden="true"
                                >
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h3 class="modal-title" id="delete${modalKey}ModalLabel">
                                                    Confirm Delete
                                                </h3>
                                            </div>
                                            <div class="modal-body">
                                                <p>Are you sure you want to delete '${row[0]}' and all related data? <strong>This cannot be undone.</strong></p>
                                            </div>
                                            <div class="modal-footer">
                                                <button
                                                    type="button"
                                                    class="btn btn-secondary"
                                                    data-dismiss="modal"
                                                >
                                                    Close
                                                </button>
                                                <button
                                                    type="button"
                                                    onclick="deleteSemester('${row[0]}')"
                                                    class="btn btn-primary"
                                                >
                                                    Confirm
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
                            return actionButtons;
                        }
                    }
                ]
            });
            $("#newSemesterForm").submit(event => {
                event.preventDefault();
                const semesterName = $("#semesterInput").val() + $("#semesterYearInput").val()
                console.log("Creating new semester: " + semesterName);
                $.ajax({
                    type: "POST",
                    url: apiUrl + "/semesters",
                    data: JSON.stringify({ isActive: true, semesterName: semesterName }),
                    headers: { "Accept": "application/json", "Content-Type": "application/json" },
                    success: function () {
                        location.reload();
                    }
                })
                $('#addSemesterModal').modal('hide');
            });
        }
    );

});

// const activateSemester = semesterName => {
//     console.log("Activating semester: " + semesterName);
// };

const deleteSemester = semesterName => {
    console.log("Deleting semester: " + semesterName);
    $.ajax({
        type: "DELETE",
        url: apiUrl + "/semesters/" + semesterName,
        headers: { Accept: "application/json" },
        success: function () {
            location.reload();
        }
    })
    $(`#delete${semesterName.replace(/\s+/g, '')}Modal`).modal('hide');
};