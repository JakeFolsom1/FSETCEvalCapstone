let completedEvals = [];
let names = [];
let evaluationsReleased = {};
$(document).ready(() => {
  $.when(
    $.getJSON(apiUrl + "/completedEvaluations", function (completedEvalJson) {
      completedEvals = completedEvalJson;
    }),
    $.getJSON(apiUrl + "/staff", function (namesJson) {
      names = namesJson;
    }),
    $.getJSON(apiUrl + "/evaluationsReleased", function (evaluationsReleasedJson) {
      evaluationsReleased = evaluationsReleasedJson;
    })
  ).then(function () {
    $("#releaseSwitch").prop("checked", evaluationsReleased.isReleased);
    $("#releaseSwitch").change(e => {
      $.ajax({
        type: "PUT",
        url: `${apiUrl}/evaluationsReleased/${e.target.checked}`,
        headers: { Accept: "application/json" },
      });
    })

    const tableData = completedEvals.map((eval, index) => [
      eval.semester,
      eval.evaluator + (eval.evalType === "l2t" ? " - Lead" : ""),
      eval.evaluatee + (eval.evalType === "t2l" ? " - Lead" : ""),
      {isShared: eval.isShared, assignmentId: eval.assignmentId},
      index
    ]);
    let table = $("#completedEvaluationTable").DataTable({
      stripe: true,
      paging: false,
      searching: true,
      info: false,
      data: tableData,
      columns: [
        {
          title: "Semester",
          render: data => {
            const semester = (data.includes("fall") ? "Fall" : data.includes("spring") ? "Spring" : data.includes("summer") ? "Summer" : "Invalid Semester");
            const year = data.substr(-2);
            return `${semester} 20${year}`;
          }
        },
        { title: "Evaluator Name" },
        { title: "Evaluatee Name" },
        {
          title: "Evaluation Shared?",
          render: (data) => {
            let checkboxShared =
                `<input type="checkbox" onclick = "updateIsShared(this, ${data.assignmentId})" ${data.isShared == true ? 'checked' : ''}>`
            return checkboxShared;
          }
        },
        {
          title: "Actions",
          render: (data) => {
            let viewButton = `<button
                            class="btn btn-primary"
                            style="border-color: #8C1D40;"
                            onclick="viewEvaluation('${data}')"
                            id="myButton">
                            View
                            </button>`;
            return viewButton;
          },
        },
      ],
    });

    $("#searchText").on("keyup change", function () {
      table.search(this.value).draw();
    });
  });
});

const updateIsShared = (checkbox, assignmentId) => {
  let response = [];
  $.when(
      $.getJSON(apiUrl + `/responses/${assignmentId}`, function (value) {
         response = value;
      })
  ).then(function () {
    $.each(response, (index, question) => {
      $.ajax({
        type: "PUT",
        url: apiUrl + "/responses",
        data: JSON.stringify({
          assignment: assignmentId,
          isShared: checkbox.checked,
          question: question.question,
          response: question.response
        }),
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        success: function (response) {
          // console.log(response);
        },
      });
    })
  })
}

const viewEvaluation = (evaluationIndex) => {
  //Clear any old evaluations in the modal. There is probably a better way to do this
  $("#questionsAndResponses").empty();
  $("#evalHeader h3").remove();

  //search for the evaluation and questions
  const currentEval = completedEvals[evaluationIndex];
  const questions = currentEval.questionsAndResponses;

  //Add the title to the Modal
  const title = `<h3>${currentEval.evaluator}'s Evaluation of ${currentEval.evaluatee} </h3>`;
  $("#evalHeader").append(title);

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
  $("#testmodal").modal("show");
};
