let completedEvals = [];
let names = [];
$(document).ready(() => {
  $.when(
    $.getJSON(apiUrl + "/completedEvaluations", function (completedEvalJson) {
      console.log(completedEvalJson);
      completedEvals = completedEvalJson;
    }),
    $.getJSON(apiUrl + "/staff", function (namesJson) {
      console.log(namesJson);
      names = namesJson;
    })
  ).then(function () {
    const tableData = completedEvals.map((eval) => [
      eval.semester,
      eval.evaluator + (eval.evalType === "l2t" ? " - Lead" : ""),
      eval.evaluatee + (eval.evalType === "t2l" ? " - Lead" : ""),
    ]);
    let table = $("#completedEvaluationTable").DataTable({
      stripe: true,
      paging: false,
      searching: true,
      info: false,
      data: tableData,
      columns: [
        { title: "Semester" },
        { title: "Evaluator Name" },
        { title: "Evaluatee Name" },
        {
          title: "Actions",
          render: (data, _type, row) => {
            let viewButton = `<button
                            class="btn btn-primary"
                            style="border-color: #8C1D40;"
                            onclick="viewEvaluation('${row[1].replace(
                              " - Lead",
                              ""
                            )}', '${row[2]}')"
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

const viewEvaluation = (evaluator, evaluatee) => {
  //Clear any old evaluations in the modal. There is probably a better way to do this
  $("#questionsAndResponses").empty();
  $("#evalHeader h3").remove();

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
  $.each(questions, (index, question) => {
    const innerHTML = `<li>
                <h4 style="font-weight: bold">Question ${index + 1}:</h4>
                <p class="tab-eval">  ${question.question.questionPrompt}</p>
                ${
                  question.question.questionType == "numeric"
                    ? `<input type="range" class="form-control custom-range" min="0" max="5" id="response${index}" value="${question.response}" disabled="true">`
                    : question.question.questionType == "yesNo"
                    ? `<input type="checkbox" id="response${index}" checked="${question.response}" class="form-control" disabled="true"><label for="response${index}">Yes</label>`
                    : `<input type="text" value="${question.response}"class="form-control" id="response${index}" disabled="true">`
                }
                <br>
             </li>`;
    $("#questionsAndResponses").append(innerHTML);
  });
  $("#questionsAndResponses").append(sharedRadioInput);
  $("#testmodal").modal("show");
};