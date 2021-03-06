let evalQuestions = [];
$(document).ready(() => {
    const accordion = $("#accordion");
    [
        { type: 'p2p', title: 'Peer Evaluation' },
        { type: 't2l', title: "Lead Evaluations for Tutors" },
        { type: 'l2t', title: "Team Evaluations for Leads" }
    ].forEach(val => {
        const { type: type, title: title } = val;

        // make the panel and add question modal for each eval type
        accordion.append(
            `<div class="panel panel-default">
                        <div class="panel-heading scheduled">
                            <h4 class="panel-title scheduled">
                                <a
                                    data-toggle="collapse"
                                    data-parent="#accordion"
                                    href="#${type}Collapse"
                                    style="text-decoration: none;"
                                >
                                    <strong>${title}</strong>
                                </a>
                            </h4>
                        </div>
                        <div id="${type}Collapse" class="panel-collapse collapse">
                            <div class="panel-body">
                                <button
                                    type="button"
                                    class="btn btn-primary"
                                    data-toggle="modal"
                                    data-target="#add${type}QuestionModal"
                                    style="display: block; margin-left: auto; margin-bottom: 1rem;"
                                >
                                    <span>&#43;</span>
                                </button>
                                <div
                                    class="modal fade"
                                    id="add${type}QuestionModal"
                                    tabindex="-1"
                                    role="dialog"
                                    aria-labelledby="add${type}QuestionModalLabel"
                                    aria-hidden="true"
                                >
                                    <div
                                        class="modal-dialog modal-dialog-centered"
                                        role="document"
                                    >
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h3 class="modal-title" id="add${type}QuestionModalLabel">
                                                    Add New Question
                                                </h3>
                                            </div>
                                            <div class="modal-body">
                                                <form id="new${type}QuestionForm">
                                                    <div class="input-group" style="width: 100%">
                                                        <label for="${type}QuestionPrompt"
                                                            >Question Prompt:</label
                                                        >
                                                        <textarea
                                                            id="${type}QuestionPrompt"
                                                            class="form-control"
                                                            placeholder="Enter question here"
                                                            required
                                                        />
                                                    </div>
                                                    <br />
                                                    <div class="input-group" style="width: 100%">
                                                        <label for="${type}ResponseType">Response Type:</label>
                                                        <select id="${type}ResponseType" class="form-control" >
                                                            <option value="numeric">Numeric</option>
                                                            <option value="yesNo">Yes/No</option>
                                                            <option value="freeResponse"
                                                                >Free Response</option
                                                            >
                                                        </select>
                                                    </div>
                                                </form>
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
                                                    type="submit"
                                                    form="new${type}QuestionForm"
                                                    class="btn btn-primary"
                                                >
                                                    Save
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <ul class="panel-group ${type}sortable" id="${type}Questions"></ul>
                            </div>
                        </div>
                    </div>`);

        $(`.${type}sortable`).sortable({
            start: function (event, ui) {
                // store the initial position of the dragged item
                ui.item.data('start_pos', ui.item.index());
            },
            update: function (event, ui) {
                // on finish sort, get the start position
                const startIndex = ui.item.data('start_pos');
                // get the final sorted position
                const endIndex = ui.item.index();

                // get the ids of the sorted questions
                const sortedIDs = $(`.${type}sortable`).sortable("toArray");
                // find the index of the question to move in the evalQuestions array
                const arrStartIndex = evalQuestions.findIndex(question => question.questionId === Number(sortedIDs[endIndex]));
                // calculate the index to move to
                const arrEndIndex = arrStartIndex + (endIndex - startIndex);


                // store the question to move
                let temp = evalQuestions[arrStartIndex];
                // if moving the question to a larger index
                if (arrStartIndex < arrEndIndex) {
                    // shift questions to left
                    for (let i = arrStartIndex; i < arrEndIndex; i++) {
                        evalQuestions[i] = evalQuestions[i + 1];
                    }
                } else {
                    // else shift questions to the right
                    for (let i = arrStartIndex; i > arrEndIndex; i--) {
                        evalQuestions[i] = evalQuestions[i - 1];
                    }
                }
                // put the question in its place
                evalQuestions[arrEndIndex] = temp;
                // fix the question numbers and update the db
                fixQuestionNumbers();
            }
        });

        $(`#new${val.type}QuestionForm`).submit(event => {
            event.preventDefault();
            const prompt = $(`#${type}QuestionPrompt`).val();
            const responseType = $(`#${type}ResponseType`).val();
            $.ajax({
                type: "POST",
                url: apiUrl + "/questions",
                data: JSON.stringify({
                    evalType: type,
                    isActive: true,
                    questionNumber: evalQuestions.filter(question => question.evalType === type).length + 1,
                    questionPrompt: prompt,
                    questionType: responseType
                }),
                headers: { "Accept": "application/json", "Content-Type": "application/json" },
                success: function (response) {
                    console.log(response);
                    reloadQuestions();
                }
            });
            $(`#add${type}QuestionModal`).modal('hide');
        });
    });

    reloadQuestions();


});

const deleteQuestion = (questionId) => {
    console.log("Deleting question " + questionId);
    $.ajax({
        type: "DELETE",
        url: `${apiUrl}/questions/id/${questionId}`,
        headers: { "Accept": "application/json" },
        success: function () {
            evalQuestions.splice(evalQuestions.findIndex(question => question.questionId === questionId), 1);
            fixQuestionNumbers();
        }
    });
}

const reloadQuestions = () => {
    evalQuestions = [];
    $.when(
        $.getJSON(apiUrl + "/questions/p2p",
            function (p2pQuestions) {
                evalQuestions = evalQuestions.concat(p2pQuestions);
            }
        ),
        $.getJSON(apiUrl + "/questions/t2l",
            function (t2lQuestions) {
                evalQuestions = evalQuestions.concat(t2lQuestions);
            }
        ),
        $.getJSON(apiUrl + "/questions/l2t",
            function (l2tQuestions) {
                evalQuestions = evalQuestions.concat(l2tQuestions);
            }
        ),
    ).then(function () {
        const p2pQuestions = $("#p2pQuestions");
        const l2tQuestions = $("#l2tQuestions");
        const t2lQuestions = $("#t2lQuestions");
        p2pQuestions.empty();
        l2tQuestions.empty();
        t2lQuestions.empty();
        evalQuestions.forEach(question => {
            // generate the inner panels for each question of each eval type
            const innerHTML =
                `<li class="panel panel-default" id="${question.questionId}" style="list-style: none"> 
                        <div class="panel-heading scheduled"> 
                            <h4 class="panel-title scheduled"> 
                                <a 
                                    data-toggle="collapse" 
                                    data-parent="#${question.evalType}Questions" 
                                    href="#question${question.questionId}" 
                                    style="text-decoration: none;" 
                                > 
                                    <strong>Question ${question.questionNumber}</strong> 
                                </a> 
                            </h4> 
                        </div> 
                        <div id="question${question.questionId}" class="panel-collapse collapse"> 
                            <div class="panel-body"> 
                                <form id="${question.evalType}Question${question.questionId}Form">
                                    <div class="input-group" style="width: 100%">
                                        <label for="question${question.questionId}Prompt"
                                            >Question Prompt:</label
                                        >
                                        <textarea
                                            id="question${question.questionId}Prompt"
                                            class="form-control"
                                            placeholder="Enter question here"
                                            required
                                        >${question.questionPrompt}</textarea>
                                    </div>
                                    <br />
                                    <div class="input-group" style="width: 100%">
                                        <label for="question${question.questionId}ResponseType">Response Type:</label>
                                        <select id="question${question.questionId}ResponseType" class="form-control">
                                            <option value="numeric" ${question.questionType === "numeric" ? "selected" : ""}>Numeric</option>
                                            <option value="yesNo" ${question.questionType === "yesNo" ? "selected" : ""}>Yes/No</option>
                                            <option value="freeResponse"
                                            ${question.questionType === "freeResponse" ? "selected" : ""}>Free Response</option
                                            >
                                        </select>
                                    </div>
                                    <br />
                                    <div class="text-right">
                                        <button type="button" class="btn btn-secondary" onclick="deleteQuestion(${question.questionId})">Delete</button>
                                        <button type="submit" class="btn btn-primary" >Save</button>
                                    </div>
                                </form>
                            </div> 
                        </div> 
                    </div>`;
            switch (question.evalType) {
                case "p2p":
                    p2pQuestions.append(innerHTML);
                    break;
                case "l2t":
                    l2tQuestions.append(innerHTML);
                    break;
                case "t2l":
                    t2lQuestions.append(innerHTML);
                    break;
                default:
                    console.log("Error: Invalid evaluation type loaded.")
            }
        });

        // add submit handlers
        evalQuestions.forEach(question => {
            $(`#${question.evalType}Question${question.questionId}Form`).submit(event => {
                event.preventDefault();
                question.questionPrompt = $(`#question${question.questionId}Prompt`).val();
                question.questionType = $(`#question${question.questionId}ResponseType`).val();
                $.ajax({
                    type: "PUT",
                    url: `${apiUrl}/questions`,
                    data: JSON.stringify([question]),
                    headers: { "Accept": "application/json", "Content-Type": "application/json" },
                    success: function () {
                        reloadQuestions();
                    }
                });
            });
        });
    });
}

const fixQuestionNumbers = () => {
    let p2pCount = 0, l2tCount = 0, t2lCount = 0, reloadCounter = 0;
    evalQuestions.forEach(question => {
        if (question.evalType === "p2p") {
            question.questionNumber = ++p2pCount;
        } else if (question.evalType === "l2t") {
            question.questionNumber = ++l2tCount;
        } else {
            question.questionNumber = ++t2lCount;
        }
    })
    $.ajax({
        type: "PUT",
        url: `${apiUrl}/questions`,
        data: JSON.stringify(evalQuestions),
        headers: { "Accept": "application/json", "Content-Type": "application/json" },
        success: function () {
            reloadQuestions();

        }
    });
}