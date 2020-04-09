let userAssignments = [];
let names = {};

$(document).ready(() => {
    reloadAssignments();
});

const reloadAssignments = () => {
    $.when(
        $.getJSON(apiUrl + `/assignments/active/${asurite}`, function (
            assignmentsJson
        ) {
            userAssignments = assignmentsJson;
        }),
        $.getJSON(apiUrl + "/staff/names", function (namesJson) {
            names = namesJson;
        })
    ).then(() => {
        const tableData = userAssignments.filter(eval => eval.isComplete == false).map((eval) => [
            names[eval.assignedAsurite],
            null,
        ]);
        let assignTable = $("#assignmentsTable").DataTable({
            destroy: true,
            stripe: true,
            paging: false,
            searching: true,
            info: false,
            data: tableData,
            columns: [
                { title: "Tutor Name" },
                {
                    title: "Actions",
                    render: (data, _type, row) => {
                        let evaluateeAsurite = Object.keys(names).find(
                            (key) => names[key] == row[0]
                        );
                        const assignment = userAssignments.find(
                            (assignment) => assignment.assignedAsurite == evaluateeAsurite
                        );
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        let evalButton = `<button
                        class="btn btn-primary"
                        style="border-color: #8C1D40;"
                        onclick="buildEvaluation('${row[0]}')" id="myButton">
                        Evaluate
                        </button>`;
                        return evalButton;
                    },
                },
            ],
        });
    });
}

const buildEvaluation = (evaluatee) => {
    let evaluateeAsurite = Object.keys(names).find(
        (key) => names[key] == evaluatee
    );
    const assignment = userAssignments.find(
        (assignment) => assignment.assignedAsurite == evaluateeAsurite
    );

    let evalQuestions = [];
    $("#questionsAndResponses").empty();
    $("#evalHeader h3").remove();
    $("#submitEvalButton").show();
    $.when(
        $.getJSON(apiUrl + `/questions/${assignment.evalType}`, function (result) {
            evalQuestions = result;
        })
    ).then(function () {
        const title = `<h3>${evaluatee}'s Evaluation</h3>`;
        const sharedRadioInput = `<div class="input-group">
                <p>Would you like to share this evaluation with ${evaluatee}?</p>
                <input type="radio" id="isSharedYes" name="shared" checked="checked">
                <label for="isSharedYes">Yes</label>
                <input type="radio" id="isSharedNo" name="shared">
                <label for="isSharedNo">No</label>
             </div>`;
        $("#evalHeader").append(title);
        $.each(evalQuestions, (index, question) => {
            const innerHTML = `<li>
                <h4 style="font-weight: bold">Question ${index + 1}:</h4>
                <p class="tab-eval">  ${question.questionPrompt}</p>
                ${
                question.questionType == "numeric"
                    ? `<input type="range" min="1" max="5" id="response${index}" value="3"/> <p class="text-center" id="response${index}Label">3</p>`
                    : question.questionType == "yesNo"
                    ? `<input type="radio" id="response${index}" name="yesNo${index}" checked="checked"/>
                      <label for="response${index}">Yes</label>
                      <input type="radio" id="response${index}No" name="yesNo${index}"/>
                      <label for="response${index}No">No</label>`
                    : `<textarea placeholder="Enter Answer Here..." class="form-control" id="response${index}"/>`
            }
                <br>
             </li>`;
            $("#questionsAndResponses").append(innerHTML);
            if (question.questionType == "numeric") {
                $(`#response${index}`).on('input', e => {
                    $(`#response${index}Label`).text(e.target.value);
                })
            }
        });
        $("#questionsAndResponses").append(sharedRadioInput);
        $("#testmodal").modal("show");
        $("#isShared").click(function () {
            if (this.checked) $(".isShared label").text("No");
            else $(".isShared label").text("Yes");
        });
        $("#submitEvalButton").off("click")
        $("#submitEvalButton").click(function () {
            let temp = $("#isSharedYes").prop("checked");
            $.when(
                $.each(evalQuestions, (index, question) => {
                    $.ajax({
                        type: assignment.isComplete == true ? "PUT" : "POST",
                        url: apiUrl + "/responses",
                        data: JSON.stringify({
                            assignment: assignment.assignmentId,
                            isShared: $("#isSharedYes")[0].checked,
                            question: question.questionId,
                            response:
                                question.questionType == "yesNo"
                                    ? $(`#response${index}`).prop("checked")
                                    : $(`#response${index}`).val(),
                        }),
                        headers: {
                            Accept: "application/json",
                            "Content-Type": "application/json",
                        },
                        success: function (response) {
                            console.log(response);
                        },
                    });
                }),
                $.ajax({
                    type: "PUT",
                    url: apiUrl + "/assignments/id/" + assignment.assignmentId,
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json",
                    },
                    success: function (response) {
                        console.log(response);
                    },
                })
            ).then(() => reloadAssignments())

        });
    });
};

