const dummyData = [
    {
        "evalType": "p2p",
        "evaluatee": "smurra11",
        "evaluator": "jjbowma2",
        "isShared": true,
        "questionsAndResponses": [
            {
                "question": {
                    "questionNumber": 1,
                    "questionPrompt": "Does tutor obey all procedures and policies of the center?",
                    "questionType": "freeResponse"
                },
                "response": "Yes. Steven has yet to make a mistake"
            },
            {
                "question": {
                    "questionNumber": 2,
                    "questionPrompt": "Does tutor obey all procedures and policies of the center?",
                    "questionType": "freeResponse"
                },
                "response": "Steven does not wear his sash when he's on shift."
            },
            {
                "question": {
                    "questionNumber": 3,
                    "questionPrompt": "Does the tutor show up to work on time?",
                    "questionType": "freeResponse"
                },
                "response": "No. he is consistently late"
            }
        ],
        "semester": "Fall 2019"
    },
    {
        "evalType": "p2p",
        "evaluatee": "cjstanfi",
        "evaluator": "jjbowma2",
        "isShared": true,
        "questionsAndResponses": [
            {
                "question": {
                    "questionNumber": 1,
                    "questionPrompt": "Does tutor obey all procedures and policies of the center?",
                    "questionType": "freeResponse"
                },
                "response": "John does not wear his sash when he's on shift."
            }
        ],
        "semester": "Fall 2019"
    },
    {
        "evalType": "p2p",
        "evaluatee": "smurra11",
        "evaluator": "cjstanfi",
        "isShared": true,
        "questionsAndResponses": [
            {
                "question": {
                    "questionNumber": 1,
                    "questionPrompt": "Does tutor obey all procedures and policies of the center?",
                    "questionType": "freeResponse"
                },
                "response": "John does not wear his sash when he's on shift."
            },
            {
                "question": {
                    "questionNumber": 2,
                    "questionPrompt": "Test question?",
                    "questionType": "freeResponse"
                },
                "response": "Test response"
            }
        ],
        "semester": "Fall 2019"
    },
    {
        "evalType": "p2p",
        "evaluatee": "asdonova",
        "evaluator": "amariajo",
        "isShared": true,
        "questionsAndResponses": [
            {
                "question": {
                    "questionNumber": 1,
                    "questionPrompt": "Does tutor obey all procedures and policies of the center?",
                    "questionType": "freeResponse"
                },
                "response": "John does not wear his sash when he's on shift."
            },
            {
                "question": {
                    "questionNumber": 2,
                    "questionPrompt": "Test question?",
                    "questionType": "freeResponse"
                },
                "response": "Test response"
            }
        ],
        "semester": "Fall 2019"
    }
];
let completedEvals = [];
let names = [];
$(document).ready(() => {
    $.when(
        $.getJSON(apiUrl + "/completedEvaluations",
            function (completedEvalJson) {
                console.log(completedEvalJson);
                completedEvals = completedEvalJson;
            }
        ),
        $.getJSON(apiUrl + "/staff",
            function (namesJson) {
                console.log(namesJson);
                names = namesJson;
            }
        )
    ).then(function () {
        const tableData = dummyData.map(eval => [
            eval.semester,
            getName(eval.evaluator) + (eval.evalType === "l2t" ? " - Lead" : ""),
            getName(eval.evaluatee) + (eval.evalType === "t2l" ? " - Lead" : "")
        ])
           let table =  $('#completedEvaluationTable').DataTable({
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
                            let viewButton =
                                `<button
                            class="btn btn-primary"
                            style="border-color: #8C1D40;"
                            onclick="getEval('${row[1]}', '${row[2]}')"
                            id="myButton">
                            View
                            </button>`;
                            return viewButton;
                        }
                    }
                ]
            })

            $('#searchText').on('keyup change', function () {
                table.search(this.value).draw();
            })
        }
    )

});

const getEval = (evaluator, evaluatee) => {
    //Clear any old evaluations in the modal. There is probably a better way to do this
    $('#questionsAndResponses').empty();
    $('#evalHeader h3').remove();

    //Split the name and get the Asurite of that person. This will be used to search for the evaluation
    const evaluatorAsurite = getAsurite(evaluator.split(" "));
    const evaluateeAsurite = getAsurite(evaluatee.split(" "));

    //search for the evaluation and questions
    const currentEval = dummyData.find(evaluation => evaluatorAsurite == evaluation.evaluator && evaluateeAsurite == evaluation.evaluatee);
    const questions = currentEval.questionsAndResponses;

    //Add the title to the Modal
    const title = `<h3>${evaluator}'s Evaluation of ${evaluatee} </h3>`;
    $('#evalHeader').append(title);

    //Add the questions to the modal. Needs styling.
    $.each(questions, (index, question) =>{
        const innerHTML =
            `<li>
                <h4 style="font-weight: bold">Question ${question.question.questionNumber}:</h4>
                <p class="tab-eval">  ${question.question.questionPrompt}</p>
                <h4 style="font-weight: bold">Response:</h4>
                <p class="tab-eval">${question.response}</p>
                <br>
             </li>`;
        $('#questionsAndResponses').append(innerHTML)
    });
    $('#testmodal').modal('show');
}

const getName = (asurite) => {
    let result = names.find(employee => employee.asurite == asurite);
    if(result != undefined){
        return result.fname + " " + result.lname;
    }else
        return "Invalid asurite";
}

const getAsurite = (name) => {
    let result = names.find(employee => employee.fname == name[0] && employee.lname == name[1]);
    if(result != undefined){
        return result.asurite;
    }else
        return "Asurite not found";
}




