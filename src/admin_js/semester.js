// fetch these instead of sample data
const semesters = [
    ["Fall 2019", true],
    ["Spring 2019", false],
    ["Fall 2018", false]
];

$(document).ready(() => {
    $('#semesterTable').DataTable({
        paging: false,
        searching: false,
        info: false,
        data: semesters,
        columns: [
            {
                title: "Semester",
            },
            {
                title: "Actions",
                render: (data, _type, row) => {
                    const modalKey = row[0].replace(/\s+/g, '');
                    let actionButtons =
                        `<button class="btn btn-primary" ${data ? "disabled" : ""} onclick="activateSemester('${row[0]}')">Set Active</button>
                        <button 
                        class="btn btn-outline-primary" 
						data-toggle="modal"
						data-target="#delete${modalKey}Modal">Delete</button>
                        <div
                            class="modal fade"
                            id="delete${modalKey}Modal"
                            tabindex="-1"
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
                                            class="btn btn-outline-primary"
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
    document.getElementById("newSemesterForm").addEventListener("submit", event => {
        event.preventDefault();
        const semesterName = document.getElementById("semesterNameInput").value;
        console.log("Creating new semester: " + semesterName);
        $('#addSemesterModal').modal('hide');
    });
});

const activateSemester = semesterName => {
    console.log("Activating semester: " + semesterName);
};

const deleteSemester = semesterName => {
    console.log("Deleting semester: " + semesterName);
    $(`#delete${semesterName.replace(/\s+/g, '')}Modal`).modal('hide');
};