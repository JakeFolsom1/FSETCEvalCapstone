$(document).ready(() => {
    let sharedEvals = [];
    $.when(
        $.getJSON(apiUrl + `/completedEvaluations/shared/${asurite}`,
            function (completedEvalJson) {
                sharedEvals = completedEvalJson;
            }
        )
    ).then(function () {
        const tableData = sharedEvals.map(eval => [
            eval.semester,
            /* ---------------NEEDS REFACTOR---------------
                This field will be null, need to rename this column
                Maybe something like Evaluation X where X is the index
             */
            eval.evaluator,
            null
        ])

        $('#sharedEvaluationsTable').DataTable({
            stripe: true,
            paging: false,
            searching: false,
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
                /* ---------------NEEDS REFACTOR---------------
                    This is the column that needs to be changed
                 */
                { title: "Evaluator Name" },
                {
                    title: "Actions",
                    render: (_data, _type, row) => {
                        //Use data variable to pass the evaluation parameters to the evaluations page.
                        /* ---------------NEEDS REFACTOR---------------
                            This buttons id is keyed on the semester and evaluators name
                            Maybe switch this to be just sharedEval${index}
                         */

                        let viewButton =
                            `<button
                            class="btn btn-primary"
                            style="border-color: #8C1D40;"
                            id="${row[0]}ViewButton${row[1].split(" ").join("")}">
                            View
                            </button>`;
                        return viewButton;
                    }
                }
            ]
        })
        tableData.forEach(row => {
            // add event handler to each button
            /* ---------------NEEDS REFACTOR---------------
                This is the button from above. id needs to
                be updated and the function needs to change
                (see below)
             */
            $(`#${row[0]}ViewButton${row[1].split(" ").join("")}`).click(() => {
                viewSharedEvaluation(row[1], names[asurite]);
            })
        })


        /* ---------------NEEDS REFACTOR---------------
            This function uses the evaluator to identify the correct
            evaluation. Maybe just pass the evaluation index to look it up
         */
        const viewSharedEvaluation = (evaluator, evaluatee) => {
            //Clear any old evaluations in the modal. There is probably a better way to do this
            $("#questionsAndResponses").empty();
            $("#evalHeader h3").remove();
            $("#submitEvalButton").hide();

            //search for the evaluation and questions
            /* ---------------NEEDS REFACTOR---------------
                Find the current eval without using the evaluator
             */
            const currentEval = sharedEvals.find(
                (evaluation) =>
                    evaluator == evaluation.evaluator && evaluatee == evaluation.evaluatee
            );
            const questions = currentEval.questionsAndResponses;

            //Add the title to the Modal
            /* ---------------NEEDS REFACTOR---------------
                Take the evaluator out of the title of the modal.
                The rest "should" be fine and it's basically the same
                things to fix in the viewSharedTutor.js
             */
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
    })

});
