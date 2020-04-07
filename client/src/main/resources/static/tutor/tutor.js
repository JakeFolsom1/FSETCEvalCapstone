/*
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}
*/
//EvaluationList should be Unique for every person.
//Organized by Semester, Name, IsLead?. Link to the evaluation website as well though this will probably be phased out






const tutorsList = [
    ["Fall 2019", "Bob", false],
    ["Fall 2019", "Jake", true],
    ["Fall 2019", "John", false],
    ["Fall 2019", "Carmen", false]
];


const reorderArray = (event, originalArray) => {
    const movedItem = originalArray.find((item, index) => index === event.oldIndex);
    const remainingItems = originalArray.filter((item, index) => index !== event.oldIndex);
    const reorderedItems = [
    ]
}



const evaluationsToComplete = [ //Not sure if I need this anymore, swapped to Tutorslist
    ["Bob", false],
    ["John", true],
    ["Jack", false],
    ["Mark", true]
];

$(document).ready(() => {
    //Setting up the draggable interface
    $('#sortable').sortable();
    $('#sortable').disableSelection();

    //Creates the elements for the draggable interface
    var count = 0;
    var frag = document.createDocumentFragment();

    $.each(tutorsList, function (i, item) {
        var newListItem = document.createElement("li");
        newListItem.className = 'ui-state-default';
        count++;
        var itemText = document.createTextNode(count + " " + item[1]);
        newListItem.appendChild(itemText);
        newListItem.addEventListener("dragstart", function (e) {
            item = e.target;
            e.dataTransfer.setData('text', '')
        }, false);
        frag.append(newListItem);
    });

    $('#sortable')[0].appendChild(frag);

    $('#viewSharedTable').DataTable({
        paging: false,
        info: false,
        searching: false,
        data: tutorsList,
        columns: [
            {
                title: "Semester",
            },
            {
                title: "Name",
            },
            {
                title: "Lead",
                render: data => {
                    return data ? '<div class="dot"/>' : "";
                }
            },
            {
                title: "Actions",
                render: function (data, type, row) {
                    return `<button class="btn btn-primary navb" onclick="openPage(this)">View</button>`
                }
            }
        ]
    });

    $("#sortable").disableSelection();

    $('#completeAssignmentsTable').DataTable({
        paging: false,
        info: false,
        searching: false,
        data: evaluationsToComplete,
        columns: [
            {
                title: "Tutor Name",
            },
            {
                title: "Lead",
                render: data => {
                    return data ? '<div class="dot"/>' : "";
                }
            },
            {
                title: "Actions",
                render: function (data, type, row) {
                    return '<button class="btn btn-primary navb" onclick="openEvaluationPage()">Evaluate</button>'
                }
            }
        ]
    });
});

//This function needs to grab the element clicked and pass it into a state variable on the newWindow
//Ele is a reference to the element that called it
function openPage(ele) {
    //Testing
    /*
    */
    // window.open("http://localhost:63342/FSETCEvalCapstone/src/sampleEvalPage.html?name=", "name");
    //var index = ele._getIndex();
    var name = document.getElementById('viewShared').childNodes[0];
    console.log(name);
    window.open("http://localhost:63342/FSETCEvalCapstone/src/sampleEvalPage.html?val=", name);
    //document.location.href = "http://localhost:63342/FSETCEvalCapstone/src/sampleEvalPage.html?name=" + name;
}

function openEvaluationPage() {
    return '<button class="btn btn-primary navb">Page goes here</button>'
}



function generateTable(document) {
    //Foreach element in API call return, create a new <tr> element as well as <th> for every <tr>
}
