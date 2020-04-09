let userAssignments = [];
let names = {};
let completedEvals = [];
$(document).ready(() => {
  reloadAssignments();
});

const reloadAssignments = () => {
  let teamMembers = [];
  $.when(
      $.getJSON(apiUrl + `/assignments/active/${asurite}`, function (
          assignmentsJson
      ) {
        userAssignments = assignmentsJson;
      }),
      $.getJSON(apiUrl + `/teamMembers/${asurite}`, function (teamMemberJson) {
        teamMembers = teamMemberJson;
      }),
      $.getJSON(apiUrl + "/completedEvaluations", function (completedEvalJson) {
        completedEvals = completedEvalJson;
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

    completedEvals = completedEvals.filter((eval) =>
        teamMembers.includes(
            Object.keys(names).find((key) => names[key] == eval.evaluatee)
        )
    );
    const completedEvalsTableData = completedEvals.map((eval) => [
      eval.semester,
      eval.evaluator,
      eval.evaluatee,
      null,
    ]);
    let completedEvalTable = $("#completedEvaluationTable").DataTable({
      destroy: true,
      stripe: true,
      paging: false,
      searching: true,
      info: false,
      data: completedEvalsTableData,
      columns: [
        {
          title: "Semester",
          render: data => {
            const semester = (data.includes("fall") ? "Fall" : data.includes("spring") ? "Spring" : data.includes("summer") ? "Summer" : "Invalid Semester");
            const year = data.substr(-2);
            return `${semester} 20${year}`;
          }
        },
        { title: "Evaluator" },
        { title: "Evaluatee" },
        {
          title: "Actions",
          render: (data, _type, row) => {
            //Use data variable to pass the evaluation parameters to the evaluations page.
            let viewButton = `<button
                        class="btn btn-primary"
                        style="border-color: #8C1D40;"
                        onclick="viewEvaluation('${names[asurite]}', '${row[2]}')" id="myButton">
                        View
                        </button>`;
            return viewButton;
          },
        },
      ],
    });

    $("#searchText").on("keyup change", function () {
      completedEvalTable.search(this.value).draw();
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
    $.getJSON(apiUrl + "/questions/l2t", function (result) {
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
                    : `<textarea placeholder="Enter Answer Here..." style="resize: none;" class="form-control" id="response${index}"/>`
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

const viewEvaluation = (evaluator, evaluatee) => {
  //Clear any old evaluations in the modal. There is probably a better way to do this
  $("#questionsAndResponses").empty();
  $("#evalHeader h3").remove();
  $("#submitEvalButton").hide();

  //search for the evaluation and questions
  const currentEval = completedEvals.find(
    (evaluation) =>
      evaluator == evaluation.evaluator && evaluatee == evaluation.evaluatee
  );
  const questions = currentEval.questionsAndResponses;

  //Add the title to the Modal
  const title = `<h3>${evaluator}'s Evaluation of ${evaluatee} </h3>`;
  $("#evalHeader").append(title);

  const sharedRadioInput = `<div>
            <p>Is this evaluation shared with ${evaluatee}?</p>
            ${
              currentEval.isShared == true
                ? `<input type="radio" id="isSharedYes" checked="checked" disabled="true"><label for="isSharedYes">Yes</label>
            <input type="radio" id="isSharedNo" disabled="true"><label for="isSharedNo">No</label>`
                : `<input type="radio" id="isSharedYes" disabled="true"><label for="isSharedYes">Yes</label>
            <input type="radio" id="isSharedNo" checked="checked" disabled="true"><label for="isSharedNo">No</label>`
            }
         </div>`;

  //Add the questions to the modal. Needs styling.
  $.each(questions.sort((q1, q2) => q1.question.questionNumber - q2.question.questionNumber), (index, question) => {
    const innerHTML = `<li>
                <h4 style="font-weight: bold">Question ${question.question.questionNumber}:</h4>
                <p class="tab-eval">  ${question.question.questionPrompt}</p>
                ${
                  question.question.questionType == "numeric"
                    ? `<input type="range" min="1" max="5" id="response${index}" value="${question.response}" disabled="true"/><p class="text-center">${question.response}</p>`
                    : question.question.questionType == "yesNo"
                    ? `<input type="radio" id="response${index}" ${question.response == "true" ? "checked" : ""} disabled="true"/>
                      <label for="response${index}">Yes</label>
                      <input type="radio" id="response${index}No" ${question.response == "true" ? "" : "checked"} disabled="true"/>
                      <label for="response${index}No">No</label>`
                    : `<textarea class="form-control" id="response${index}" disabled="true" >${question.response}</textarea>`
                }
                <br>
             </li>`;
    $("#questionsAndResponses").append(innerHTML);
  });
  $("#questionsAndResponses").append(sharedRadioInput);
  $("#testmodal").modal("show");
};
