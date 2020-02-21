// fetch these instead of sample data
const evalQuestions = [
    {
        questionId: 22,
        questionPrompt: "Does tutor obey all procedures and policies of the tutoring center?",
        questionType: "free-response",
        isActive: true,
        questionNumber: 1,
        evalType: "p2p"
    },
    {
        questionId: 23,
        questionPrompt: "Does tutor obey all procedures and policies of the tutoring center?",
        questionType: "numeric",
        isActive: true,
        questionNumber: 2,
        evalType: "p2p"
    }
]

$(document).ready(() => {
    const accordion = document.getElementById("accordion");
    [
        { type: 'p2p', title: 'Peer Evaluation' },
        { type: 't2l', title: "Lead Evaluations for Tutors" },
        { type: 'l2t', title: "Team Evaluations for Leads" }
    ].forEach(val => {
        const { type: type, title: title } = val;

        // make the panel and add question modal for each eval type
        accordion.insertAdjacentHTML('beforeend',
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
                                                <input
                                                    id="${type}QuestionPrompt"
                                                    type="text"
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
                                                    <option value="y/n">Yes/No</option>
                                                    <option value="free-response"
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
                        <div class="panel-group" id="${type}Questions"></div>
                    </div>
                </div>
            </div>`);
    });

    // apparently adding to innerHTML destroys child elements like event listeners
    // so they need to be added after all innerHTML is changed
    ['p2p', 'l2t', 't2l'].forEach(type => {
        // add submission handlers for the new question forms
        document.getElementById(`new${type}QuestionForm`).addEventListener("submit", event => {
            event.preventDefault();
            console.log(`Adding new ${type} question `);
            const prompt = document.getElementById(`${type}QuestionPrompt`).value;
            const responseType = document.getElementById(`${type}ResponseType`).value;
            console.log(`Prompt: ${prompt}\nResponse Type: ${responseType}`)
            $(`#add${type}QuestionModal`).modal('hide');
        });
    });


    const p2pQuestions = document.getElementById("p2pQuestions");
    const l2tQuestions = document.getElementById("l2tQuestions");
    const t2lQuestions = document.getElementById("t2lQuestions");
    evalQuestions.forEach(question => {
        // generate the inner panels for each question of each eval type
        const innerHTML =
            `<div class="panel panel-default"> 
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
                                <input
                                    id="question${question.questionId}Prompt"
                                    class="form-control"
                                    type="text"
                                    placeholder="Enter question here"
                                    value="${question.questionPrompt}"
                                    required
                                />
                            </div>
                            <br />
                            <div class="input-group" style="width: 100%">
                                <label for="question${question.questionId}ResponseType">Response Type:</label>
                                <select id="question${question.questionId}ResponseType" class="form-control">
                                    <option value="numeric" ${question.questionType === "numeric" ? "selected" : ""}>Numeric</option>
                                    <option value="y/n" ${question.questionType === "y/n" ? "selected" : ""}>Yes/No</option>
                                    <option value="free-response"
                                    ${question.questionType === "free-response" ? "selected" : ""}>Free Response</option
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
                p2pQuestions.insertAdjacentHTML('beforeend', innerHTML);
                break;
            case "l2t":
                l2tQuestions.insertAdjacentHTML('beforeend', innerHTML);
                break;
            case "t2l":
                t2lQuestions.insertAdjacentHTML('beforeend', innerHTML);
                break;
            default:
                console.log("Error: Invalid evaluation type loaded.")
        }
    });

    // add submit handlers after all innerHTML is done loading
    evalQuestions.forEach(question => {
        document.getElementById(`${question.evalType}Question${question.questionId}Form`).addEventListener("submit", event => {
            event.preventDefault();
            console.log("Saving question " + question.questionId);
            // save question
            // reload questions
        });
    });
});

const deleteQuestion = (questionId) => {
    console.log("Deleting question " + questionId);
    // delete question
    // reload question
}
